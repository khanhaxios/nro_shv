package com.girlkun.services;

import com.girlkun.models.item.Item;
import com.girlkun.models.map.ItemMap;
import com.girlkun.models.map.Zone;
import com.girlkun.models.player.Location;
import com.girlkun.models.player.Player;
import com.girlkun.network.io.Message;
import com.girlkun.utils.Logger;
import com.girlkun.utils.Util;

public class ItemMapService {

    private static ItemMapService i;

    public static ItemMapService gI() {
        if (i == null) {
            i = new ItemMapService();
        }
        return i;
    }

    public void pickItem(Player player, int itemMapId, boolean isThuHut) {
        if (isThuHut || Util.canDoWithTime(player.iDMark.getLastTimePickItem(), 500)) {
            player.zone.pickItem(player, itemMapId);
            player.iDMark.setLastTimePickItem(System.currentTimeMillis());
        }
    }

    //xóa item map và gửi item map biến mất
    public void removeItemMapAndSendClient(ItemMap itemMap) {
        sendItemMapDisappear(itemMap);
        removeItemMap(itemMap);
    }

    public void sendItemMapDisappear(ItemMap itemMap) {
        Message msg;
        try {
            msg = new Message(-21);
            msg.writer().writeShort(itemMap.itemMapId);
            Service.gI().sendMessAllPlayerInMap(itemMap.zone, msg);
            msg.cleanup();
        } catch (Exception e) {
            Logger.logException(ItemMapService.class, e);
        }
    }

    public void removeItemMap(ItemMap itemMap) {
        itemMap.zone.removeItemMap(itemMap);
        itemMap.dispose();
    }

    public boolean isBlackBall(int tempId) {
        return tempId >= 372 && tempId <= 378;
    }

    public boolean isVodaiBall(int tempId) {
        return tempId >= 1816 && tempId <= 1822;
    }

    public boolean isNamecBall(int tempId) {
        return tempId >= 353 && tempId <= 360;
    }

    public ItemMap createItemMapFromItem(Zone zone, Location location, Item item, Player player) {
        ItemMap itemMap = new ItemMap(zone, item.template.id, 1, location.x, zone.map.yPhysicInTop(location.x, location.y - 24), player.id);
        itemMap.options = item.itemOptions;
        return itemMap;
    }
}
