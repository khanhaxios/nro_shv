package com.girlkun.models.mob;

import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.utils.SkillUtil;
import com.girlkun.services.Service;
import com.girlkun.utils.Util;
import com.girlkun.network.io.Message;
import com.girlkun.utils.Logger;
import java.io.IOException;

public final class MobMe extends Mob {

    private Player player;
    private final long lastTimeSpawn;
    private final int timeSurvive;

    public MobMe(Player player) {
        super();
        this.player = player;
        this.id = (int) player.id;
        int level = player.playerSkill.getSkillbyId(12).point;
        this.tempId = SkillUtil.getTempMobMe(level);
        this.point.maxHp = SkillUtil.getHPMobMe(Util.TamkjllGH(player.nPoint.hpMax), level);
        this.point.dame = SkillUtil.getHPMobMe(Util.TamkjllGH(player.nPoint.getDameAttack(false)), level);
        if (this.player.setClothes.pikkoroDaimao == 5) {
            this.point.dame *= 2;
        }
        this.point.hp = this.point.maxHp;
        this.zone = player.zone;
        this.lastTimeSpawn = System.currentTimeMillis();
        this.timeSurvive = SkillUtil.getTimeSurviveMobMe(level);
        spawn();
    }

    @Override
    public void update() {
        if (Util.canDoWithTime(lastTimeSpawn, timeSurvive) && this.player.setClothes.pikkoroDaimao != 5) {
            this.mobMeDie();
            this.dispose();
        }
    }

    public void attack(Player pl, Mob mob) {
        Message msg;
        try {
            if (pl != null) {
                if (pl.nPoint.hp > this.point.dame && pl.nPoint.hp > pl.nPoint.hpMax * 0.05) {
                    double dameHit = pl.injured(null, this.point.dame, true, true);
                    msg = new Message(-95);
                    msg.writer().writeByte(2);

                    msg.writer().writeInt(this.id);
                    msg.writer().writeInt((int) pl.id);
                    msg.writer().writeInt(1);
                    msg.writer().writeInt(Util.TamkjllGH(pl.nPoint.hp));

                    Service.getInstance().sendMessAllPlayerInMap(this.zone, msg);
                    msg.cleanup();
                }
            }

            if (mob != null) {
                if (!mob.isDie()) {
                    if (!mob.isMobMe) {
                        long dame = this.point.dame;
                        if (mob.isSieuQuai()) {
                            if (dame > mob.point.maxHp / 10) {
                                dame = mob.point.maxHp / 10;
                            }
                        }

                        if (this.point.hp == this.point.maxHp && dame >= this.point.hp) {
                            dame = this.point.hp - 1;
                        }
                        if (mob.point.hp <= 1) {
                            dame = 1;
                        }

                        long tnsm = mob.getTiemNangForPlayer(this.player, dame);

                        msg = new Message(-95);
                        msg.writer().writeByte(3);
                        msg.writer().writeInt(this.id);
                        msg.writer().writeInt((int) mob.id);

                        if (mob.tempId == 0 || this.tempId == 82 || this.tempId == 84) {
                            tnsm = 1;
                            mob.point.sethp(mob.point.gethp() - 10);
                            msg.writer().writeInt(mob.point.gethp());
                            msg.writer().writeInt(10);
                        } else {
                            mob.point.sethp((int) (mob.point.gethp() - dame));
                            msg.writer().writeInt(mob.point.gethp());
                            msg.writer().writeInt((int) dame);
                        }
                        Service.getInstance().sendMessAllPlayerInMap(this.zone, msg);
                        msg.cleanup();
                        Service.getInstance().addSMTN(player, (byte) 2, tnsm, true);
                        if (mob.isDie()) {
                            mob.sendMobDieAfterMobMeAttacked(player, dame);
                        }
                    }
                }
            }
        } catch (IOException e) {
            Logger.logException(MobMe.class, e);
        }
    }

    //tạo mobme
    public void spawn() {
        Message msg;
        try {
            msg = new Message(-95);
            msg.writer().writeByte(0);//type
            msg.writer().writeInt((int) player.id);
            msg.writer().writeShort(this.tempId);
            msg.writer().writeInt(this.point.hp);// hp mob
            Service.gI().sendMessAllPlayerInMap(this.zone, msg);
            msg.cleanup();
        } catch (Exception e) {

        }
    }

    public void goToMap(Zone zone) {
        if (zone != null) {
            this.removeMobInMap();
            this.zone = zone;
        }
    }

    //xóa mobme khỏi map
    private void removeMobInMap() {
        Message msg;
        try {
            msg = new Message(-95);
            msg.writer().writeByte(7);//type
            msg.writer().writeInt((int) player.id);
            Service.gI().sendMessAllPlayerInMap(this.zone, msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void mobMeDie() {
        Message msg;
        try {
            msg = new Message(-95);
            msg.writer().writeByte(6);//type
            msg.writer().writeInt((int) player.id);
            Service.gI().sendMessAllPlayerInMap(this.zone, msg);
            msg.cleanup();
        } catch (Exception e) {
        }
    }

    public void dispose() {
        player.mobMe = null;
        this.player = null;
    }
}
