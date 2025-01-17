package com.girlkun.models.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.girlkun.models.item.Item;
import com.girlkun.models.item.Item.ItemOption;

public class Inventory {

    public static final long LIMIT_GOLD = 2000_000_000_000L; // Giới hạn vàng

    public static final int LIMIT_RUBY = 2_000_000_000; // Giới hạn Ruby

    public static final int CAN_TRADE = 2_000_000_000;

    public static final int MAX_ITEMS_BAG = 127;
    public static final int MAX_ITEMS_BOX = 127;

    public Item trainArmor;
    public List<String> giftCode;
    public List<Item> itemsBody;
    public List<Item> itemsBag;
    public List<Item> itemsBox;

    public List<Item> itemsBoxCrackBall;

    public long gold;
    public int gem;
    public int ruby;
    public int coupon;
    public int event;
    public int skien;
    public int skien1;
    public int skien2;
    public int skien3;
    public int skien4;
    public int skien5;

    public Inventory() {
        itemsBody = new ArrayList<>();
        itemsBag = new ArrayList<>();
        itemsBox = new ArrayList<>();
        itemsBoxCrackBall = new ArrayList<>();
        giftCode = new ArrayList<>();
    }

    public int getGemAndRuby() {
        return this.gem + this.ruby;
    }

    public int getParam(Item it, int id) {
        for (ItemOption op : it.itemOptions) {
            if (op != null && op.optionTemplate.id == id) {
                return op.param;
            }
        }
        return 0;
    }

    public boolean haveOption(List<Item> l, int index, int id) {
        Item it = l.get(index);
        if (it != null && it.isNotNullItem()) {
            return it.itemOptions.stream().anyMatch(op -> op != null && op.optionTemplate.id == id);
        }
        return false;
    }

    public boolean hasEquipPercentItem() {
        boolean hasPercentAttack = false;
        for (Item item : this.itemsBody) {
            if (item.haveOption(244)) {
                hasPercentAttack = true;
                break;
            }
        }
        return hasPercentAttack;
    }

    public void subGold(int num) {
        this.gold -= num;
    }

    public void subGemAndRuby(int num) {
        this.ruby -= num;
        if (this.ruby < 0) {
            this.gem += this.ruby;
            this.ruby = 0;
        }
    }

    public void addGold(int gold) {
        this.gold += gold;
        if (this.gold > LIMIT_GOLD) {
            this.gold = LIMIT_GOLD;
        }
    }

    public void addRuby(int ruby) {
        this.ruby += ruby;
        if (this.ruby > LIMIT_RUBY) {
            this.ruby = LIMIT_RUBY;
        }
    }

    public void dispose() {
        if (this.trainArmor != null) {
            this.trainArmor.dispose();
        }
        this.trainArmor = null;
        if (this.itemsBody != null) {
            for (Item it : this.itemsBody) {
                it.dispose();
            }
            this.itemsBody.clear();
        }
        if (this.itemsBag != null) {
            for (Item it : this.itemsBag) {
                it.dispose();
            }
            this.itemsBag.clear();
        }
        if (this.itemsBox != null) {
            for (Item it : this.itemsBox) {
                it.dispose();
            }
            this.itemsBox.clear();
        }
        if (this.itemsBoxCrackBall != null) {
            for (Item it : this.itemsBoxCrackBall) {
                it.dispose();
            }
            this.itemsBoxCrackBall.clear();
        }
        this.itemsBody = null;
        this.itemsBag = null;
        this.itemsBox = null;
        this.itemsBoxCrackBall = null;
    }

    public int getMaxPercentItemParam() {
        List<Item> hasOptionsItem = itemsBody.stream().filter((p) -> p.haveOption(244)).collect(Collectors.toList());
        int maxParams = 0;
        for (Item item : hasOptionsItem) {
            if (item.getOptionById(244).param > maxParams) {
                maxParams = item.getOptionById(244).param;
            }
        }
        return maxParams;
    }
}
