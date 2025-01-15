/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlkun.models.boss.list_boss.NgucTu;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Map;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.server.Manager;
import com.girlkun.services.MapService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

import java.util.Random;

/**
 * @Stole By BARCOLL ZALO 0358176187
 */
public class CoolerGold extends Boss {

    public CoolerGold() throws Exception {
        super(BossID.COOLER_GOLD, BossesData.COOLER_GOLD);
    }


    @Override
    public void reward(Player plKill) {
        Service.gI().dropItemMap(this.zone, new ItemMap(zone, 674, Util.nextInt(20, 50), this.location.x, this.location.y, plKill.id));
        byte randomNR = (byte) new Random().nextInt(Manager.itemIds_NR_SB.length);
        int[] itemDos = new int[]{233, 237, 241, 245, 249, 253, 257, 261, 265, 269, 273, 277, 281};
        int randomc12 = new Random().nextInt(itemDos.length);
        if (Util.isTrue(BossManager.ratioReward, 100)) {
            Service.gI().dropItemMap(this.zone, new ItemMap(zone, 674, 1, this.location.x, this.location.y, plKill.id));
        } else if (Util.isTrue(2, 5)) {
            Service.gI().dropItemMap(this.zone, Util.RaitiDoc12(zone, itemDos[randomc12], 1, this.location.x, this.location.y, plKill.id));
            return;
        } else {
            Service.gI().dropItemMap(this.zone, new ItemMap(zone, Manager.itemIds_NR_SB[randomNR], 1, this.location.x, this.location.y, plKill.id));
        }
        if (Util.isTrue(80, 100)) {
            ItemMap it = new ItemMap(this.zone, 674, 5, this.location.x, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id);
            Service.getInstance().dropItemMap(this.zone, it);
            Service.gI().addCongDuc(plKill, 500);
        }
    }

    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        this.SendLaiThongBao(5);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void joinMap() {
        super.joinMap();
    }


    @Override
    public Zone getRandomZone(int mapId) {
        Map map = MapService.gI().getMapById(mapId);
        return map.zones.get(0);
    }

    @Override
    public void leaveMap() {
        super.leaveMap();
        super.dispose();
        BossManager.gI().removeBoss(this);
    }
}
