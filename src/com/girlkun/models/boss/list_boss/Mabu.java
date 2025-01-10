/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlkun.models.boss.list_boss;

import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import java.util.Random;

/**
 * @@Stole By BARCOLL ZALO 0358176187
 */
public class Mabu extends Boss {

    public Mabu() throws Exception {
        super(BossID.MABU, BossesData.MABU);
        this.tilepst = 45;
    }

    @Override
    public void reward(Player plKill) {
        int[] itemCt = new int[]{16, 17, 18, 19, 20};
        int randomDo = new Random().nextInt(itemCt.length);
        if (Util.isTrue(99, 100)) {
            if (Util.isTrue(1, 100)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 15, 1, this.location.x, this.location.y, plKill.id));
                return;
            }
            Service.gI().dropItemMap(this.zone, Util.useItem(zone, itemCt[randomDo], 1, this.location.x, this.location.y, plKill.id));
        }
    }

    @Override
    public void active() {
        super.active();
        this.SendLaiThongBao(6);
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        long st = System.currentTimeMillis();
    }

}
