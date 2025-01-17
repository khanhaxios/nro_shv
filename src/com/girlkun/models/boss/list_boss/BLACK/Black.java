package com.girlkun.models.boss.list_boss.BLACK;

import com.girlkun.models.boss.*;
import com.girlkun.models.player.Player;
import com.girlkun.services.EffectSkillService;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;

public class Black extends Boss {

    public Black() throws Exception {
        super(BossID.BLACK, BossesData.BLACK_GOKU);
    }

    @Override
    public void reward(Player plKill) {
        if (Util.isTrue(BossManager.ratioReward, 20)) {
            Service.gI().dropItemMap(this.zone, Util.khongthegiaodich(zone, 992, 1, this.location.x, this.location.y, plKill.id));
        }
        Service.gI().addCongDuc(plKill,Util.nextInt(25000,50000));
    }

    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        this.SendLaiThongBao(10);
    }

    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage / 2);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        long st = System.currentTimeMillis();
    }

    //    @Override
//    public void moveTo(int x, int y) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.moveTo(x, y);
//    }
//
//    @Override
//    public void reward(Player plKill) {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.reward(plKill);
//    }
//    
//    @Override
//    protected void notifyJoinMap() {
//        if(this.currentLevel == 1){
//            return;
//        }
//        super.notifyJoinMap();
//    }
}
