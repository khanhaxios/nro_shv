package com.girlkun.models.boss.list_boss.boss_even;

import com.girlkun.Log;
import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.*;
import com.girlkun.models.item.Item;
import com.girlkun.models.player.Player;
import com.girlkun.services.*;
import com.girlkun.utils.Util;

public class HongQuanLaoTo extends Boss {
    long timeBuffChoMaHauLaoTo;
    long lastTimeBuffChoMaHauLaoTo;

    byte timeUseVoDic = 0;

    boolean isVodich = false;
    long timeBuffVoDich;

    public HongQuanLaoTo() throws Exception {
        super(BossID.HONG_QUAN_LAO_TO, BossesData.HONGQUANLAOTO);
    }

    private void activeVoDich() {
        if (timeUseVoDic > 0 || isVodich) {
            return;
        }
        if (this.nPoint.hp < this.nPoint.hpMax * .2) {
            timeUseVoDic++;
            isVodich = true;
            this.nPoint.dame*=2;
            timeBuffVoDich = 15000;
        }
    }

    private void buffChoMaHauLaoTo() {
        if (!Util.canDoWithTime(lastTimeBuffChoMaHauLaoTo, timeBuffChoMaHauLaoTo)) {
            return;
        }
        int typeBuff = Util.nextInt(0, 1);
        if (typeBuff == 0) {
            // buff dam
            this.nPoint.dame += this.nPoint.dame * .2;
            this.chat("Tạo hóa ngọc diệp");
        } else if (typeBuff == 1) {
            double point = this.nPoint.hpMax * 0.2;
            this.nPoint.addHp((long) point);
            PlayerService.gI().sendInfoHpMp(this);
            this.chat("Ha ha ta cảm thấy nguồn sức mạnh đang dâng trào aaaa., chết đi lũ kia");
            this.chat("Ma hầu đón lấy ..... aa");
        }
        timeBuffChoMaHauLaoTo = Util.nextInt(15000, 25000);
        lastTimeBuffChoMaHauLaoTo = System.currentTimeMillis();
    }


    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        Service.gI().addCongDuc(plAtt, Util.nextInt(1, 2));
        if (isVodich) {
            timeBuffVoDich -= 150;
            if (timeBuffVoDich <= 0) {
                isVodich = false;
            }
            return 0;
        }
        return super.injured(plAtt, damage, piercing, isMobAttack);
    }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.attack();
        this.buffChoMaHauLaoTo();
        this.activeVoDich();
        super.active();
    }

    @Override
    public void reward(Player plKill) {
        this.chat("Không thể nào aaaaaa,Ta sẽ quay lại sớm thôi, La Hầu ta đi cùng ngươi aaa");
        // reward for user kill;
        Service.gI().addCongDuc(plKill, 100000);
        // create linh thu
        Item item = null;
        if (Util.isTrue(20, 100)) {
            item = ItemService.gI().createLinhThuCongDuc(200);
        } else if (Util.isTrue(50, 100)) {
            item = ItemService.gI().createDeoLungCongDuc();
        } else {
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
