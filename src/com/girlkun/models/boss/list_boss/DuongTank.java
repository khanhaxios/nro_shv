package com.girlkun.models.boss.list_boss;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossManager;
import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Player;
import com.girlkun.server.Client;
import com.girlkun.services.*;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;

public class DuongTank extends Boss {

    private long lastTimeMove;

    public DuongTank(int bossID, BossData bossData, Zone zone, int x, int y) throws Exception {
        super(bossID, bossData);
        this.zone = zone;
        this.location.x = x;
        this.location.y = y;
    }

    @Override
    public void reward(Player plKill) {
        ItemMap itemMap = new ItemMap(this.zone, 76, 1, this.location.x, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id);
        Service.getInstance().dropItemMap(this.zone, itemMap);
    }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        if (isPlayerTargetInvalid()) {
            playerTarger.haveDuongTang = false;
            this.leaveMap();
        }
        if (isPlayerTooFar(500)) {
            notifyPlayer("Đi quá xa, Bunma đã rời đi!");
            playerTarger.haveDuongTang = false;
            this.leaveMap();
        }
        if (isPlayerTooFar(300)) {
            notifyPlayer("Khoảng cách quá xa, Bunma sắp rời xa bạn!!");
        }
        if (isPlayerCloseEnough(300)) {
            followPlayer();
        }
        if (shouldRewardPlayer()) {
            rewardPlayer();
            this.leaveMap();
        }
        if (isPlayerInDifferentZone()) {
            ChangeMapService.gI().changeMap(this, this.playerTarger.zone, this.playerTarger.location.x, this.playerTarger.location.y);
        }
        if (Util.canDoWithTime(this.lastTimeAttack, 10000)) {
            Service.gI().chat(this, playerTarger.name + ", Hãy đưa ta đến " + MapService.gI().getMapById(this.mapCongDuc).mapName);
            this.lastTimeAttack = System.currentTimeMillis();
        }
    }

    private boolean isPlayerTargetInvalid() {
        return this.playerTarger != null && Client.gI().getPlayer(this.playerTarger.id) == null;
    }

    private boolean isPlayerTooFar(int distance) {
        return Util.getDistance(playerTarger, this) > distance && this.zone == this.playerTarger.zone;
    }

    private boolean isPlayerCloseEnough(int distance) {
        return this.playerTarger != null && Util.getDistance(playerTarger, this) <= distance;
    }

    private void followPlayer() {
        int direction = this.location.x - this.playerTarger.location.x <= 0 ? -1 : 1;
        if (Util.canDoWithTime(lastTimeMove, 1000)) {
            lastTimeMove = System.currentTimeMillis();
            this.moveTo(this.playerTarger.location.x + Util.nextInt(direction == -1 ? 0 : -30, direction == -1 ? 10 : 0), this.playerTarger.location.y);
        }
    }

    private boolean shouldRewardPlayer() {
        return this.playerTarger != null && playerTarger.haveDuongTang && this.zone.map.mapId == this.mapCongDuc;
    }

    private void rewardPlayer() {
        playerTarger.haveDuongTang = false;
        int d = Util.nextInt(1, 3);
        playerTarger.diemhotong += d;
        playerTarger.inventory.ruby += 1000;
        Item xuacquy = ItemService.gI().createNewItem((short) 1664);
        xuacquy.quantity = Util.nextInt(1, 5);
        playerTarger.inventory.gem += 2000;
        Service.getInstance().sendMoney(playerTarger);
        InventoryServiceNew.gI().addItemBag(playerTarger, xuacquy);
        Service.getInstance().sendThongBaoOK(playerTarger, "Bạn nhận được " + d + " CĐHT " + xuacquy.quantity + " Đá ngũ sắc xanh!");
    }

    private boolean isPlayerInDifferentZone() {
        return this.playerTarger != null && this.zone != null && this.zone.map.mapId != this.playerTarger.zone.map.mapId;
    }

    private void notifyPlayer(String message) {
        Service.gI().sendThongBao(this.playerTarger, message);
    }

    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (this.isDie()) {
            return 0;
        }

        if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
            this.chat("Xí hụt");
            return 0;
        }

        damage = this.nPoint.subDameInjureWithDeff(damage);
        if (!piercing && effectSkill.isShielding) {
            if (damage > nPoint.hpMax) {
                EffectSkillService.gI().breakShield(this);
            }
        }

        if (plAtt != this.playerTarger) {
            damage = this.nPoint.hpMax / 120;
        } else {
            damage = 0;
        }

        this.nPoint.subHP(damage);
        if (isDie()) {
            this.setDie(plAtt);
            die(plAtt);
        }

        return damage;
    }

    @Override
    public void joinMap() {
        if (zoneFinal != null) {
            joinMapByZone(zoneFinal);
            this.notifyJoinMap();
            return;
        }
        if (this.zone == null) {
            this.zone = (this.parentBoss != null) ? this.parentBoss.zone : (this.lastZone != null ? this.lastZone : getMapJoin());
        }
        if (this.zone != null) {
            ChangeMapService.gI().changeMap(this, this.zone, this.location.x, this.location.y);
            Service.getInstance().sendFlagBag(this);
            this.notifyJoinMap();
        }
    }

    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        this.dispose();
    }
}
