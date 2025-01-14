package com.girlkun.models.boss.list_boss.HuyDiet;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossStatus;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.ItemMapService;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

import java.util.List;
import java.util.Random;

public class Vados extends Boss {

    private long timeToOneHit;
    private long lastTimeOneHit;

    public Vados() throws Exception {
        super(Util.randomBossId(), BossesData.THIEN_SU_VADOS);
    }


    @Override
    public void reward(Player plKill) {
        int[][] idDoThienSu = {
                {1048, 1051, 1054, 1057, 1060},
                {1049, 1052, 1055, 1058, 1061},
                {1050, 1053, 1056, 1059, 1062}// trai dat
        };

        if (Util.isTrue(20, 100)) {
            Service.gI().dropItemMap(this.zone, createItemMap(2009, plKill, new int[]{0, 50, 77, 103, 108, 209, 93}, new int[]{40000, 20, 200, 200, 99, 1, 1}, new int[]{60000, 1000, 2000, 2000, 99, 1, 1}));
        }
        if (Util.isTrue(10, 100)) {
            int[] itemsFromGender = idDoThienSu[plKill.gender];
            int randomDoTs = Util.nextInt(0, itemsFromGender.length - 1);
            Item doTs = ItemService.gI().DoThienSu(itemsFromGender[randomDoTs], plKill.gender);
            if (itemsFromGender[randomDoTs] >= 1055 && itemsFromGender[randomDoTs] <= 1057) {
                if (Util.isTrue(10, 50)) {
                    if (plKill.gender == 0) {
                        doTs.itemOptions.add(new Item.ItemOption(129, 1));
                        doTs.itemOptions.add(new Item.ItemOption(141, 100));
                    }
                    if (plKill.gender == 1) {
                        doTs.itemOptions.add(new Item.ItemOption(131, 1));
                        doTs.itemOptions.add(new Item.ItemOption(143, 100));
                    }
                    if (plKill.gender == 2) {
                        int[] options = new int[]{133, 135};
                        int rad = Util.nextInt(0, options.length - 1);
                        doTs.itemOptions.add(new Item.ItemOption(options[rad], 0));
                        doTs.itemOptions.add(new Item.ItemOption(rad == 0 ? 136 : 138, rad == 0 ? 50 : 100));
                    }
                }
            }
            Service.gI().dropItemMap(this.zone, ItemMapService.gI().createItemMapFromItem(this.zone, this.location, doTs, plKill));
        }
        if (Util.isTrue(100, 100)) {
            Service.gI().dropItemMap(this.zone, new ItemMap(this.zone, Util.nextInt(1066, 1070), Util.nextInt(1, 6), this.location.x, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 4), plKill.id));
        }
        Service.gI().subCongDuc(plKill, 2000);
    }

    private ItemMap createItemMap(int itemId, Player player, int[] optIds, int[] optMin, int[] optMax) {
        ItemMap itemMap = new ItemMap(this.zone, itemId, 1, this.location.x,
                this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), player.id);
        Random rand = new Random();
        for (int i = 0; i < optIds.length; i++) {
            itemMap.options.add(new Item.ItemOption(optIds[i], rand.nextInt(optMax[i] - optMin[i] + 1) + optMin[i]));
        }
        return itemMap;
    }

    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (Util.isTrue(80, 100) && plAtt != null) {//tỉ lệ hụt của thiên sứ
            if (Util.isTrue(1, 100)) {
                this.chat("Hãy để bản năng tự vận động");
                this.chat("Tránh các động tác thừa");
            } else if (Util.isTrue(1, 100)) {
                this.chat("Chậm lại,các ngươi quá nhanh rồi");
                this.chat("Chỉ cần hoàn thiện nó!");
                this.chat("Các ngươi sẽ tránh được mọi nguy hiểm");
            } else if (Util.isTrue(1, 100)) {
                this.chat("Đây chính là bản năng vô cực");
            }
            damage = 0;
        }
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1)) {
                this.chat("Xí hụt");
                return 0;
            }
//            damage = this.nPoint.subDameInjureWithDeff(damage);
//            if (!piercing && effectSkill.isShielding) {
//                if (damage > nPoint.hpMax) {
//                    EffectSkillService.gI().breakShield(this);
//                }
//                damage = 1;
//            }
//            if (damage >= 1) {
//                damage = 1;
//            }
//            this.nPoint.subHP(damage);
//            if (isDie()) {
//                this.setDie(plAtt);
//                die(plAtt);
//            }
            return 1;
        } else {
            return 0;
        }
    }

    private void oneHit() {
        if (!Util.canDoWithTime(this.lastTimeOneHit, timeToOneHit)) {
            return;
        }
        this.chat("Chuẩn bị đón nhận cơn thịnh nộ của thiên sứ đi!!!!!");
        List<Player> players = this.zone.getNotBosses();
        for (Player player : players) {
            player.injured(this, player.nPoint.hpMax, true, false);
        }
        this.chat("A aaaa chết hết đi......");
        this.chat("Biết sự lợi hại của ta chưa?");
        this.lastTimeOneHit = System.currentTimeMillis();
        this.timeToOneHit = Util.nextInt(30000, 40000);
    }

    @Override
    public void active() {
        super.active();
        this.oneHit();
        if (Util.canDoWithTime(st, 1000000)) {
            this.changeStatus(BossStatus.LEAVE_MAP);
            this.SendLaiThongBao(8);
        }
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }

    private long st;
}
