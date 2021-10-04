package com.company.Items;

import com.company.Enums.ItemRarity;

public class Item {
    private ItemRarity rarity;
    private String name;

    public Item(Item item) {
        this.name = item.getName();
        this.rarity = item.getRarity();
    }

    public Item(ItemRarity rarity, String name) {
        this.rarity = rarity;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ItemRarity getRarity() {
        return rarity;
    }
}
