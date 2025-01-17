package com.girlkun.models.player;

import com.girlkun.models.item.Item;

public class SetClothes {

    private Player player;
    private boolean huydietClothers;

    public SetClothes(Player player) {
        this.player = player;
    }

    // skh sgk
    public byte songoku;
    public byte thienXinHang;
    public byte kirin;
    // skh octieu
    public byte ocTieu;
    public byte pikkoroDaimao;
    public byte picolo;
    //skh kakarot
    public byte kakarot;
    public byte cadic;
    public byte nappa;
    // set wordcup
    public byte worldcup;
    public byte setDHD;
    public byte SetTinhAn;
    public byte SetNguyetAn;
    public byte SetNhatAn;
    public byte SetThienSu;
    public boolean godClothes;
    public int ctHaiTac = -1;

    public int tilesongoku;
    public int tilethienXinHang;
    public int tilekirin;

    public int tileocTieu;
    public int tilepikkoroDaimao;
    public int tilepicolo;

    public int tilekakarot;
    public int tilecadic;
    public int tilenappa;

    public void setup() {
        setDefault();
        setupSKT();
        this.godClothes = true;
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id > 567 || item.template.id < 555) {
                    this.godClothes = false;
                    break;
                }
            } else {
                this.godClothes = false;
                break;
            }
        }
        Item ct = this.player.inventory.itemsBody.get(5);
        if (ct.isNotNullItem()) {
            switch (ct.template.id) {
                case 618:
                case 619:
                case 620:
                case 621:
                case 622:
                case 623:
                case 624:
                case 626:
                case 627:
                    this.ctHaiTac = ct.template.id;
                    break;
            }
        }

        this.player.setClothes.SetThienSu = 0;
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id >= 1048 && item.template.id <= 1062) {
                    player.setClothes.SetThienSu++;
                }
            }
        }

        this.player.setClothes.SetTinhAn = 0;
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                int chiso = 0;
                Item.ItemOption optionLevel = null;
                for (Item.ItemOption io : item.itemOptions) {
                    if (io.optionTemplate.id == 34) {
                        chiso = io.param;
                        optionLevel = io;
                        break;
                    }
                }
                if (optionLevel != null) {
                    player.setClothes.SetTinhAn++;
                }

            }
        }
    }

    private void setupSKT() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                boolean isActSet = false;
                for (Item.ItemOption io : item.itemOptions) {
                    switch (io.optionTemplate.id) {
//                        case 129:
                        case 141:
                            isActSet = true;
                            tilesongoku += io.param / 5;
                            songoku++;
                            break;
//                        case 127:
                        case 139:
                            isActSet = true;
                            tilethienXinHang += io.param / 5;
                            thienXinHang++;
                            break;
//                        case 128:
                        case 140:
                            isActSet = true;
                            tilekirin += io.param / 5;
                            kirin++;
                            break;
//                        case 131:
                        case 143:
                            isActSet = true;
                            tileocTieu += io.param / 5;
                            ocTieu++;
                            break;
//                        case 132:
                        case 144:
                            isActSet = true;
                            tilepikkoroDaimao += io.param / 5;
                            pikkoroDaimao++;
                            break;
//                        case 130:
                        case 142:
                            isActSet = true;
                            tilepicolo += io.param / 5;
                            picolo++;
                            break;
//                        case 135:
                        case 138:
                            isActSet = true;
                            tilenappa += io.param / 5;
                            nappa++;
                            break;
//                        case 133:
                        case 136:
                            isActSet = true;
                            tilekakarot += io.param / 5;
                            kakarot++;
                            break;
//                        case 134:
                        case 137:
                            isActSet = true;
                            tilecadic += io.param / 5;
                            cadic++;
                            break;
                        case 21:
                            if (io.param == 80) {
                                setDHD++;
                            }
                            break;
                        //                        case 34:
//                            isActSet = true;
//                            tinhan++;
//                            break;
//                        case 35:
//                            isActSet = true;
//                            nguyetan++;
//                            break;
//                        case 36:
//                            isActSet = true;
//                            nhatan++;
//                            break;
                    }

                    if (isActSet) {
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    //checksetthanlinh
    public boolean setGod() {
        for (int i = 0; i < 5; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id >= 555 && item.template.id <= 567) {
                    i++;
                } else if (i == 5) {
                    this.godClothes = true;
                    break;
                }
            } else {
                this.godClothes = false;
                break;
            }
        }
        return this.godClothes ? true : false;
    }

    // check set huy diet
    public boolean setGod14() {
        for (int i = 0; i < 6; i++) {
            Item item = this.player.inventory.itemsBody.get(i);
            if (item.isNotNullItem()) {
                if (item.template.id >= 650 && item.template.id <= 663) {
                    i++;
                } else if (i == 5) {
                    this.huydietClothers = true;
                    break;
                }
            } else {
                this.huydietClothers = false;
                break;
            }
        }
        return this.huydietClothers ? true : false;
    }

    private void setDefault() {
        this.songoku = 0;
        this.thienXinHang = 0;
        this.kirin = 0;
        this.ocTieu = 0;
        this.pikkoroDaimao = 0;
        this.picolo = 0;
        this.kakarot = 0;
        this.cadic = 0;
        this.nappa = 0;
        this.setDHD = 0;
        this.worldcup = 0;
        this.godClothes = false;
        this.ctHaiTac = -1;

        this.tilesongoku = 0;
        this.tilethienXinHang = 0;
        this.tilekirin = 0;

        this.tileocTieu = 0;
        this.tilepikkoroDaimao = 0;
        this.tilepicolo = 0;

        this.tilekakarot = 0;
        this.tilecadic = 0;
        this.tilenappa = 0;
    }

    public void dispose() {
        this.player = null;
    }
}
