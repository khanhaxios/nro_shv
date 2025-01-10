package com.girlkun.models.boss.vodai;

import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.player.Player;

/**
 * @author barcoll sieu cap vippr0
 */
public class Bongbang extends BossVD {

    public Bongbang(Player player) throws Exception {
        super(BossID.BONGBANG, BossesData.BONGBANG);
        this.playerAtt = player;
    }
}
