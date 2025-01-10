package com.girlkun.models.boss.vodai;

import com.girlkun.models.boss.BossID;
import com.girlkun.models.boss.BossesData;
import com.girlkun.models.player.Player;

/**
 * @author barcoll sieu cap vippr0
 */
public class Dracula extends BossVD {

    public Dracula(Player player) throws Exception {
        super(BossID.DRACULA, BossesData.DRACULA);
        this.playerAtt = player;
    }
}
