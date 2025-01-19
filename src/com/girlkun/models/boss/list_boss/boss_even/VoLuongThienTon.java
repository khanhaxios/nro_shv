package com.girlkun.models.boss.list_boss.boss_even;

import com.girlkun.consts.ConstPlayer;
import com.girlkun.models.boss.Boss;
import com.girlkun.models.boss.BossData;
import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.item.Item;
import com.girlkun.models.player.Player;
import com.girlkun.services.InventoryServiceNew;
import com.girlkun.services.ItemService;
import com.girlkun.services.PlayerService;
import com.girlkun.services.Service;
import com.girlkun.services.func.ChangeMapService;
import com.girlkun.utils.Util;

public class VoLuongThienTon extends Boss {
    long timeNemPlayer;
    long lastTimeNemPlayer;

    long timeNerf;
    long lastTimeNerf;


    public VoLuongThienTon() throws Exception {
        super(BossID.VO_LUONG_THIEN_TON, BossesData.VOLUONGTHIENTON);
    }

    private void nerfAllPlayer() {
        if (!Util.canDoWithTime(lastTimeNerf, timeNerf)) {
            return;
        }

        this.chat("Thằng nào xui vậy");
        int rand = Util.nextInt(0, this.zone.getNotBosses().size() - 1);
        Player player = this.zone.getNotBosses().get(rand);
        player.nPoint.dame /= 2;
        player.nPoint.hpMax /= 2;
        player.nPoint.setFullHpMp();
        PlayerService.gI().sendInfoHp(player);
        Service.gI().sendThongBao(player, "Bạn đã bị vô lượng thiên tôn nerf");
        lastTimeNerf = System.currentTimeMillis();
        timeNerf = Util.nextInt(5000, 15000);
    }

    private void nemPlayer() {
        int[] mapIds = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        if (!Util.canDoWithTime(lastTimeNemPlayer, timeNemPlayer)) {
            return;
        }
        int rand = Util.nextInt(0, this.zone.getNotBosses().size() - 1);
        Player player = this.zone.getNotBosses().get(rand);

        this.chat("Byee byeee " + player.name);
        ChangeMapService.gI().changeMapNonSpaceship(player, mapIds[Util.nextInt(0, mapIds.length - 1)], 100, this.zone.map.yPhysicInTop(100, 100 - 24));
        timeNemPlayer = Util.nextInt(5000, 20000);
        lastTimeNemPlayer = System.currentTimeMillis();
        Service.gI().sendThongBao(player, "Bạn đã bị Vô Lượng Thiên Tôn ném tới đây");
    }

    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (Util.isTrue(20, 100)) {
            this.chat("Hụt rồi");
            return 0;
        }
        Service.gI().addCongDuc(plAtt, 2);
        return super.injured(plAtt, damage, piercing, isMobAttack);
    }

    @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.attack();
        this.nemPlayer();
        this.nerfAllPlayer();
        super.active();
    }

    @Override
    public void reward(Player plKill) {
        this.chat("Không thể nào aaaaaa,Ta sẽ quay lại sớm thôi, 2 vị ta đến đây");
        // reward for user kill;
        Service.gI().addCongDuc(plKill, 100000);
        // create linh thu
        Item item = null;
        if (Util.isTrue(20, 100)) {
            item = ItemService.gI().createLinhThuCongDuc(200);
        } else {
            item = ItemService.gI().createDeoLungCongDuc();
        }
        Item dns = ItemService.gI().createNewItem((short) 674, 200);
        InventoryServiceNew.gI().addItemBag(plKill, dns);
        InventoryServiceNew.gI().addItemBag(plKill, item);
        InventoryServiceNew.gI().sendItemBags(plKill);
        Service.gI().sendThongBao(plKill, "Bạn nhận được " + item.template.name);
        Service.gI().sendThongBao(plKill, "Bạn nhận được x" + dns.template.name);
    }
}
