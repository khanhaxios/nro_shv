/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.girlkun.models.boss.list_boss.cell;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.player.Player;
import com.girlkun.services.Service;
import com.girlkun.services.TaskService;
import com.girlkun.utils.Util;
import com.girlkun.services.PlayerService;

import java.util.Random;

/**
 * @Stole By BARCOLL ZALO 0358176187
 */
public class Xencon extends Boss {

    public Xencon() throws Exception {
        super(BossID.XEN_CON, BossesData.XEN_CON, BossesData.XEN_CON_2, BossesData.XEN_CON_3, BossesData.XEN_CON_4);
    }

    @Override
    public void reward(Player plKill) {
        Service.gI().addCongDuc(plKill,Util.nextInt(1000,2000));

        int[] itemDos = new int[]{16};
        int[] NRs = new int[]{16};
        int randomDo = new Random().nextInt(itemDos.length);
        int randomNR = new Random().nextInt(NRs.length);
        if (Util.isTrue(50, 100)) {
            if (Util.isTrue(1, 5)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 16, 1, this.location.x, this.location.y, plKill.id));
                return;
            }
            Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, itemDos[randomDo], 1, this.location.x, this.location.y, plKill.id));
        } else {
            Service.gI().dropItemMap(this.zone, new ItemMap(zone, NRs[randomNR], 1, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
        }
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
            this.SendLaiThongBao(4);
        }
//        this.hapThu();
        this.attack();
    }

    private void hapThu() {
        ///    if (!Util.canDoWithTime(this.lastTimeHapThu, this.timeHapThu) || !Util.isTrue(1, 100)) {
        //        return;
        //    }
        if (Util.isTrue(25, 100)) {
            Player pl = this.zone.getRandomPlayerInMap();
            if (pl == null || pl.isDie()) {
                return;
            }
//        ChangeMapService.gI().changeMapYardrat(this, this.zone, pl.location.x, pl.location.y);
            this.nPoint.dameg += (pl.nPoint.dame * 5 / 100);
            this.nPoint.hpg += (pl.nPoint.hp * 2 / 100);
            this.nPoint.critg++;
            this.nPoint.calPoint();
            PlayerService.gI().hoiPhuc(this, pl.nPoint.hp, 0);
            pl.injured(null, pl.nPoint.hpMax, true, false);
            Service.gI().sendThongBao(pl, "Bạn vừa bị " + this.name + " hấp thu!");
            this.chat(2, "Ui cha cha, kinh dị quá. " + pl.name + " vừa bị tên " + this.name + " nuốt chửng kìa!!!");
            this.chat("Haha, ngọt lắm đấy " + pl.name + "..");
            long lastTimeHapThu = System.currentTimeMillis();
            int timeHapThu = Util.nextInt(7000000, 15000000);
        }
    }

}
