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
import com.company.Items.HealingPotion;
import com.company.Items.Item;
import com.company.Items.SkillBook;

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
static public void reset(){
        attackDatabase = new HashMap<>();
        items = new HashMap<>();
}

    static public Attack getRandomAttack(CharacterClass c) {
        Random rand = new Random();
        return attackDatabase.get(c).get(rand.nextInt(attackDatabase.get(c).size()));
    }

    public static Item getRandomItemByRarity(ItemRarity rarity) {
        Random rand = new Random();
        return items.get(rarity).get(rand.nextInt(items.get(rarity).size()));
    }

    public static ArrayList<Item> getXRandomItems(int numOfItems,boolean forceGoodItems) {
        ArrayList<Item> itemsToReturn = new ArrayList<>();
        Random rand = new Random();
        int randomNumber;
        for (int i = 0; i < numOfItems; i++) {
            if(forceGoodItems){
                randomNumber = rand.nextInt(3);
                if(randomNumber == 0){
                    itemsToReturn.add(new Item(getRandomItemByRarity(ItemRarity.LEGENDARY)));
                }else{
                    itemsToReturn.add(new Item(getRandomItemByRarity(ItemRarity.EPIC)));
                }
            }else {
                randomNumber = rand.nextInt(100) + 1;
                Item itemToAdd;
                if (randomNumber >= 96) {
                     itemToAdd= Database.getRandomItemByRarity(ItemRarity.LEGENDARY);
                    if(itemToAdd instanceof HealingPotion){
                        itemsToReturn.add(new HealingPotion((HealingPotion) itemToAdd));
                    }else{
                        itemsToReturn.add(new SkillBook((SkillBook) itemToAdd));
                    }
                } else if (randomNumber >= 86) {
                    itemToAdd= Database.getRandomItemByRarity(ItemRarity.EPIC);
                    if(itemToAdd instanceof HealingPotion){
                        itemsToReturn.add(new HealingPotion((HealingPotion) itemToAdd));
                    }else{
                        itemsToReturn.add(new SkillBook((SkillBook) itemToAdd));
                    }
                } else if (randomNumber >= 65) {
                    itemToAdd= Database.getRandomItemByRarity(ItemRarity.RARE);
                    if(itemToAdd instanceof HealingPotion){
                        itemsToReturn.add(new HealingPotion((HealingPotion) itemToAdd));
                    }else{
                        itemsToReturn.add(new SkillBook((SkillBook) itemToAdd));
                    }
                } else if (randomNumber >= 41) {
                    itemToAdd= Database.getRandomItemByRarity(ItemRarity.UNCOMMON);
                    if(itemToAdd instanceof HealingPotion){
                        itemsToReturn.add(new HealingPotion((HealingPotion) itemToAdd));
                    }else{
                        itemsToReturn.add(new SkillBook((SkillBook) itemToAdd));
                    }
                } else {
                    itemToAdd= Database.getRandomItemByRarity(ItemRarity.COMMON);
                    if(itemToAdd instanceof HealingPotion){
                        itemsToReturn.add(new HealingPotion((HealingPotion) itemToAdd));
                    }else{
                        itemsToReturn.add(new SkillBook((SkillBook) itemToAdd));
                    }
                }
            }
        }
        return itemsToReturn;
    }
}
