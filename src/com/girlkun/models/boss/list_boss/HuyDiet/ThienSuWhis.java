package com.girlkun.models.boss.list_boss.HuyDiet;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.mob.Mob;
import com.girlkun.models.player.Player;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.services.ItemMapService;
import com.girlkun.services.ItemService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThienSuWhis extends Boss {

    private long timeToOneHit;
    private long lastTimeOneHit;

    public ThienSuWhis() throws Exception {
        super(Util.randomBossId(), BossesData.THIEN_SU_WHIS);
    }

    public void reward(Player plKill) {
        int[][] idDoThienSu = {
                {1048, 1051, 1054, 1057, 1060},
                {1049, 1052, 1055, 1058, 1061},
                {1050, 1053, 1056, 1059, 1062}// trai dat
        };

        if (Util.isTrue(20, 100)) {
            Service.gI().dropItemMap(this.zone, createItemMap(2009, plKill, new int[]{0, 50, 77, 103, 108, 209, 93}, new int[]{40000, 20, 200, 200, 99, 1, 1}, new int[]{60000, 1000, 2000, 2000, 99, 1, 1}));
        }
        if (Util.isTrue(10, 1000)) {
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
    public void doneChatE() {
        if (this.parentBoss == null || this.parentBoss.bossAppearTogether == null
                || this.parentBoss.bossAppearTogether[this.parentBoss.currentLevel] == null) {
        }
    }

    @Override
    public void active() {
        super.active();
        this.oneHit();
        this.SendLaiThongBao(3);
    }

    @Override
    public void joinMap() {
        super.joinMap();
        long st = System.currentTimeMillis();
    }

}

