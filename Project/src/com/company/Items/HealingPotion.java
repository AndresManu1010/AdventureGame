package com.company.Items;
/*==============================================================
Author: HealthPotion
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Enums.ItemRarity;

public class HealingPotion extends Item {
    private   double heals;
    private int remainingUses;

    public HealingPotion(String name, ItemRarity Rarity, Double heals, int remainingUses) {
        super(Rarity, name);
        this.heals = heals;
        this.remainingUses = remainingUses;
    }


    public double getHeals() {
        return heals;
    }

    public int getRemainingUses() {
        return remainingUses;
    }
    public double usePotion(){
        remainingUses--;
        return heals;
    }
}