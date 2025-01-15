package com.girlkun.models.boss.list_boss.boss_even;

import com.girlkun.Log;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.*;
import com.girlkun.models.item.Item;
import com.girlkun.models.player.Player;
import com.girlkun.services.*;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;

public class HongQuanLaoTo extends Boss {
    long timeBuffChoMaHauLaoTo;
    long lastTimeBuffChoMaHauLaoTo;
    public Boss maHauLaoTo;

    public HongQuanLaoTo() throws Exception {
        super(BossID.HONG_QUAN_LAO_TO, BossesData.HONGQUANLAOTO);
    }

    private void buffChoMaHauLaoTo() {
        if (!Util.canDoWithTime(lastTimeBuffChoMaHauLaoTo, timeBuffChoMaHauLaoTo)) {
            return;
        }
        int typeBuff = Util.nextInt(0, 1);
        if (typeBuff == 0) {
            // buff dam
            if (maHauLaoTo != null) {
                maHauLaoTo.nPoint.dame += maHauLaoTo.nPoint.dame * 0.2;
                maHauLaoTo.chat("Ha ha ta cảm thấy nguồn sức mạnh đang dâng trào aaaa., chết đi lũ kia");
                this.chat("Ma hầu đón lấy ..... aa");
            }
        } else if (typeBuff == 1) {
            if (maHauLaoTo != null) {
                double point = this.nPoint.hpMax * 0.2;
                maHauLaoTo.nPoint.hp += point;
                this.nPoint.hp -= point;
                PlayerService.gI().sendInfoHp(maHauLaoTo);
                PlayerService.gI().sendInfoHpMp(this);
                maHauLaoTo.chat("Ha ha ta cảm thấy nguồn sức mạnh đang dâng trào aaaa., chết đi lũ kia");
                this.chat("Ma hầu đón lấy ..... aa");
            }
        }
        timeBuffChoMaHauLaoTo = Util.nextInt(15000, 25000);
        lastTimeBuffChoMaHauLaoTo = System.currentTimeMillis();
    }


    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (maHauLaoTo != null) {
            if (!maHauLaoTo.isDie()) {
                return 1;
            }
        }
        Service.gI().addCongDuc(plAtt, Util.nextInt(5, 100) * 500);
        return super.injured(plAtt, damage, piercing, isMobAttack);
    }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.attack();
        buffChoMaHauLaoTo();
        super.active();
    }

    @Override
    public void reward(Player plKill) {
        this.chat("Không thể nào aaaaaa,Ta sẽ quay lại sớm thôi, La Hầu ta đi cùng ngươi aaa");
        // reward for user kill;
        Service.gI().addCongDuc(plKill, 6666666);
        // create linh thu
        Item item = null;
        if (Util.isTrue(20, 100)) {
            item = ItemService.gI().createLinhThuCongDuc(500);
        } else if (Util.isTrue(50, 100)) {
            item = ItemService.gI().createDeoLungCongDuc();
        } else {
            // roi do thien su kich hoat
            // gang
            switch (plKill.gender) {
                case 0:
                    item = ItemService.gI().createGangCongDuc(plKill.gender);
                    item.itemOptions.add(new Item.ItemOption(129, 1));
                    item.itemOptions.add(new Item.ItemOption(141, 100));
                    break;
                case 1:
                    item = ItemService.gI().createGangCongDuc(plKill.gender);
                    item.itemOptions.add(new Item.ItemOption(131, 1));
                    item.itemOptions.add(new Item.ItemOption(143, 100));
                    break;
                case 2:
                    item = ItemService.gI().createGangCongDuc(plKill.gender);
                    item.itemOptions.add(new Item.ItemOption(133, 1));
                    item.itemOptions.add(new Item.ItemOption(136, 100));
                    break;
            }
        }
        Item dns = ItemService.gI().createNewItem((short) 674, 200);
        InventoryServiceNew.gI().addItemBag(plKill, dns);
        InventoryServiceNew.gI().addItemBag(plKill, item);
        InventoryServiceNew.gI().sendItemBags(plKill);
        Service.gI().sendThongBao(plKill, "Bạn nhận được " + item.template.name);
        Service.gI().sendThongBao(plKill, "Bạn nhận được x" + dns.template.name);
    }
}
