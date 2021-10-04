package com.company.Database;
/*==============================================================
Author: Database
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Attacks.Attack;
import com.company.Enums.CharacterClass;
import com.company.Enums.ItemRarity;
import com.company.Items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Database {
    static HashMap<CharacterClass, ArrayList<Attack>> attackDatabase;
    static HashMap<ItemRarity, ArrayList<Item>> items;

    static public void loadData() {
        DatabaseReader dR = new DatabaseReader();
        attackDatabase = dR.readAttacks();
        items = new HashMap<>();
        for (Item i : dR.readSkillBooks()) {
            if (!items.containsKey(i.getRarity())) {
                items.put(i.getRarity(), new ArrayList<Item>());
            }
            items.get(i.getRarity()).add(i);
        }
        for (Item i : dR.readHealingPotions()) {
            if (!items.containsKey(i.getRarity())) {
                items.put(i.getRarity(), new ArrayList<Item>());
            }
            items.get(i.getRarity()).add(i);
        }
    }


    static public Attack getRandomAttack(CharacterClass c) {
        Random rand = new Random();
        return attackDatabase.get(c).get(rand.nextInt(attackDatabase.get(c).size()));
    }

    public static Item getRandomItemByRarity(ItemRarity rarity) {
        Random rand = new Random();
        return items.get(rarity).get(rand.nextInt(items.get(rarity).size()));
    }
}
