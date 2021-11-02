package com.company.Items;
/*==============================================================
Author: HealthPotion
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Enums.ItemRarity;

public class HealingPotion extends Item {
    private double heals;
    private int remainingUses;

    public HealingPotion(String name, ItemRarity Rarity, Double heals, int remainingUses) {
        super(Rarity, name);
        this.heals = heals;
        this.remainingUses = remainingUses;
    }

    public HealingPotion(HealingPotion healingPotion) {
        super(healingPotion.getRarity(), healingPotion.getName());
        this.heals = healingPotion.getHeals();
        this.remainingUses = healingPotion.remainingUses;
    }

    /**
     * Using the potion reduces the remaining uses
     * @return the amount to be healed
     */
    public double usePotion() {
        remainingUses--;
        return heals;
    }

    public double getHeals() {
        return heals;
    }

    public int getRemainingUses() {
        return remainingUses;
    }


}