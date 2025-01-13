package com.girlkun.models.boss.list_boss.HuyDiet;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.player.Player;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

import java.util.Random;

public class ThienSuWhis extends Boss {

    public ThienSuWhis() throws Exception {
        super(Util.randomBossId(), BossesData.THIEN_SU_WHIS);
    }

    public void reward(Player plKill) {
        ItemMap[] items = new ItemMap[14];
        items[0] = createItemMap(650, plKill, new int[]{47, 86, 21, 30}, new int[]{1800, 1, 80, 1}, new int[]{2800, 1, 80, 1});
        items[1] = createItemMap(652, plKill, new int[]{47, 86, 21, 30}, new int[]{1800, 1, 80, 1}, new int[]{2800, 1, 80, 1});
        items[2] = createItemMap(654, plKill, new int[]{47, 86, 21, 30}, new int[]{1800, 1, 80, 1}, new int[]{2800, 1, 80, 1});
        items[3] = createItemMap(651, plKill, new int[]{22, 86, 21, 30}, new int[]{85, 1, 80, 1}, new int[]{100, 1, 80, 1});
        items[4] = createItemMap(653, plKill, new int[]{22, 86, 21, 30}, new int[]{85, 1, 80, 1}, new int[]{100, 1, 80, 1});
        items[5] = createItemMap(655, plKill, new int[]{22, 86, 21, 30}, new int[]{85, 1, 80, 1}, new int[]{100, 1, 80, 1});
        items[6] = createItemMap(657, plKill, new int[]{0, 86, 21, 30}, new int[]{8500, 1, 80, 1}, new int[]{10000, 1, 80, 1});
        items[7] = createItemMap(659, plKill, new int[]{0, 86, 21, 30}, new int[]{8500, 1, 80, 1}, new int[]{10000, 1, 80, 1});
        items[8] = createItemMap(661, plKill, new int[]{0, 86, 21, 30}, new int[]{8500, 1, 80, 1}, new int[]{10000, 1, 80, 1});
        items[9] = createItemMap(658, plKill, new int[]{23, 86, 21, 30}, new int[]{80, 1, 80, 1}, new int[]{90, 1, 80, 1});
        items[10] = createItemMap(660, plKill, new int[]{23, 86, 21, 30}, new int[]{80, 1, 80, 1}, new int[]{90, 1, 80, 1});
        items[11] = createItemMap(662, plKill, new int[]{23, 86, 21, 30}, new int[]{80, 1, 80, 1}, new int[]{90, 1, 80, 1});
        items[12] = createItemMap(656, plKill, new int[]{14, 86, 21, 30}, new int[]{17, 1, 80, 1}, new int[]{19, 1, 80, 1});
        items[13] = createItemMap(2009, plKill, new int[]{50, 77, 103, 93, 30}, new int[]{20, 25, 25, 3, 1}, new int[]{40, 45, 45, 7, 1});

        Random rand = new Random();

        // Tính toán xác suất
        double randomValue = rand.nextDouble() * 100;

        if (randomValue <= 100) {
            // Tỷ lệ rơi 100%
            int[] ids100 = {650, 652, 654, 651, 653, 655, 658, 660, 662, 2009};
            int randomIndex = rand.nextInt(ids100.length);
            Service.gI().dropItemMap(this.zone, items[randomIndex]);
        } else if (randomValue <= 20) {
            // Tỷ lệ rơi 20%
            int[] ids20 = {657, 659, 661};
            int randomIndex = rand.nextInt(ids20.length);
            Service.gI().dropItemMap(this.zone, items[6 + randomIndex]); // 657, 659, 661
        } else if (randomValue <= 10) {
            // Tỷ lệ rơi 10%
            Service.gI().dropItemMap(this.zone, items[12]); // 656
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
    public void doneChatE() {
        if (this.parentBoss == null || this.parentBoss.bossAppearTogether == null
                || this.parentBoss.bossAppearTogether[this.parentBoss.currentLevel] == null) {
        }
    }

    @Override
    public void active() {
        super.active();
        this.SendLaiThongBao(3);
    }

    @Override
    public void joinMap() {
        super.joinMap();
        long st = System.currentTimeMillis();
    }

}

/**
 * Vui lòng không sao chép mã nguồn này dưới mọi hình thức. Hãy tôn trọng tác
 * giả của mã nguồn này. Xin cảm ơn! - GirlBeo
 */
