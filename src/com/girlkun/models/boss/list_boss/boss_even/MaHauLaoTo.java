package com.girlkun.models.boss.list_boss.boss_even;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.Item;
import com.girlkun.models.player.Player;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

import java.util.List;

public class MaHauLaoTo extends Boss {
    long timeUseThiThanThuong;
    long lastTimeUseThiThanThuong;

    public Boss hongQuanLaoTo;

    public MaHauLaoTo() throws Exception {
        super(BossID.MA_HAU_LAO_TO, BossesData.MAHAU);
    }

    public void useThiThanThuong() {
        if (!Util.canDoWithTime(lastTimeUseThiThanThuong, timeUseThiThanThuong)) {
            return;
        }
        List<Player> players = this.zone.getNotBosses();
        this.chat("Thí thần thương aa.aaa.aa.....");
        for (Player player : players) {
            player.injured(this, player.nPoint.hpMax, true, false);
        }
        lastTimeUseThiThanThuong = System.currentTimeMillis();
        timeUseThiThanThuong = Util.nextInt(10000, 15000);
        this.chat("Hãy chết hết đi gaagaaa");
    }

    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        int overHpPercent = 2;
        double damePercentHp = damage / this.nPoint.hpMax * 100;
        if (damePercentHp > overHpPercent) {
            this.chat("Già La Phiên Thiên");
            return 0;
        }
        Service.gI().addCongDuc(plAtt, Util.nextInt(5, 100) * 100);
        timeUseThiThanThuong -= 1000;
        return super.injured(plAtt, damage, piercing, isMobAttack);
    }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.attack();
        useThiThanThuong();
        super.active();
    }

    public void buffHongQuanAfterDie() {
        if (hongQuanLaoTo != null) {
            hongQuanLaoTo.nPoint.hpMax += this.nPoint.hpMax * 1.5;
            hongQuanLaoTo.nPoint.dame += this.nPoint.dame;
            hongQuanLaoTo.nPoint.setFullHpMp();
            PlayerService.gI().sendInfoHpMp(this);
        }
    }

    @Override
    public void reward(Player plKill) {
        this.chat("Không thể nào aaaaaa,Ta sẽ quay lại sớm , Hồng Quân hãy trả thù cho ta");
        buffHongQuanAfterDie();
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
                    item = ItemService.gI().createGangCongDuc(1054);
                    item.itemOptions.add(new Item.ItemOption(129, 1));
                    item.itemOptions.add(new Item.ItemOption(141, 100));
                    break;
                case 1:
                    item = ItemService.gI().createGangCongDuc(1055);
                    item.itemOptions.add(new Item.ItemOption(131, 1));
                    item.itemOptions.add(new Item.ItemOption(143, 100));
                    break;
                case 2:
                    item = ItemService.gI().createGangCongDuc(1056);
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
