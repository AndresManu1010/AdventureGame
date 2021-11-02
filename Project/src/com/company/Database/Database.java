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
    private static HashMap<CharacterClass, ArrayList<Attack>> attackDatabase;
    private static HashMap<ItemRarity, ArrayList<Item>> items;

    /**
     * Loads attacks and Items.
     * The Items get Sorted afterwards by rarity
     */
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

    /**
     * Resets the database's hashmaps
     */
    static public void reset() {
        attackDatabase = new HashMap<>();
        items = new HashMap<>();
    }

    /**
     * Returns a random attack based on the parameter.
     * @param c the attacks class type
     * @return the random attack
     */
    static public Attack getRandomAttack(CharacterClass c) {
        Random rand = new Random();
        return attackDatabase.get(c).get(rand.nextInt(attackDatabase.get(c).size()));
    }

    /**
     * Gets a random item based on its rarity
     * @param rarity
     * @return the random item
     */
    public static Item getRandomItemByRarity(ItemRarity rarity) {
        Random rand = new Random();
        return items.get(rarity).get(rand.nextInt(items.get(rarity).size()));
    }

    /**
     * Gets a given amount of mostly random items
     * @param numOfItems the number of items to be returned
     * @param forceGoodItems decides if the items should always have a higher rarity
     * @return the random Items
     */
    public static ArrayList<Item> getXRandomItems(int numOfItems, boolean forceGoodItems) {
        ArrayList<Item> itemsToReturn = new ArrayList<>();
        Random rand = new Random();
        int randomNumber;
        for (int i = 0; i < numOfItems; i++) {
            if (forceGoodItems) {
                randomNumber = rand.nextInt(3);
                if (randomNumber == 0) {
                    itemsToReturn.add(new Item(getRandomItemByRarity(ItemRarity.LEGENDARY)));
                } else {
                    itemsToReturn.add(new Item(getRandomItemByRarity(ItemRarity.EPIC)));
                }
            } else {
                randomNumber = rand.nextInt(100) + 1;
                Item itemToAdd;
                if (randomNumber >= 96) {
                    itemToAdd = Database.getRandomItemByRarity(ItemRarity.LEGENDARY);
                    if (itemToAdd instanceof HealingPotion) {
                        itemsToReturn.add(new HealingPotion((HealingPotion) itemToAdd));
                    } else {
                        itemsToReturn.add(new SkillBook((SkillBook) itemToAdd));
                    }
                } else if (randomNumber >= 86) {
                    itemToAdd = Database.getRandomItemByRarity(ItemRarity.EPIC);
                    if (itemToAdd instanceof HealingPotion) {
                        itemsToReturn.add(new HealingPotion((HealingPotion) itemToAdd));
                    } else {
                        itemsToReturn.add(new SkillBook((SkillBook) itemToAdd));
                    }
                } else if (randomNumber >= 65) {
                    itemToAdd = Database.getRandomItemByRarity(ItemRarity.RARE);
                    if (itemToAdd instanceof HealingPotion) {
                        itemsToReturn.add(new HealingPotion((HealingPotion) itemToAdd));
                    } else {
                        itemsToReturn.add(new SkillBook((SkillBook) itemToAdd));
                    }
                } else if (randomNumber >= 41) {
                    itemToAdd = Database.getRandomItemByRarity(ItemRarity.UNCOMMON);
                    if (itemToAdd instanceof HealingPotion) {
                        itemsToReturn.add(new HealingPotion((HealingPotion) itemToAdd));
                    } else {
                        itemsToReturn.add(new SkillBook((SkillBook) itemToAdd));
                    }
                } else {
                    itemToAdd = Database.getRandomItemByRarity(ItemRarity.COMMON);
                    if (itemToAdd instanceof HealingPotion) {
                        itemsToReturn.add(new HealingPotion((HealingPotion) itemToAdd));
                    } else {
                        itemsToReturn.add(new SkillBook((SkillBook) itemToAdd));
                    }
                }
            }
        }
        return itemsToReturn;
    }
}
