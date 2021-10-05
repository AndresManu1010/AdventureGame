package com.company.Database;
/*==============================================================
Author: DataBaseReader
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Attacks.Attack;
import com.company.Enums.AttackType;
import com.company.Enums.CharacterClass;
import com.company.Enums.ItemRarity;
import com.company.Items.HealingPotion;
import com.company.Items.Item;
import com.company.Items.SkillBook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DatabaseReader {
    public HashMap<CharacterClass, ArrayList<Attack>> readAttacks() {
        HashMap<CharacterClass, ArrayList<Attack>> attackList = new HashMap<>();
        CharacterClass[] attackClassesToRead = {
                CharacterClass.ARCHER,
                CharacterClass.ASSASSIN,
                CharacterClass.BERSERKER,
                CharacterClass.RIDER,
                CharacterClass.LANCER,
                CharacterClass.SABER,
                CharacterClass.CASTER};

        for (CharacterClass cClass : attackClassesToRead) {
            attackList.put(cClass, new ArrayList<Attack>());
            File file = new File("src\\com\\company\\Attacks\\Attacks\\" + cClass.name() + ".txt");


            try {
                Scanner scan = new Scanner(file);
                while (scan.hasNextLine()) {
                    String name = scan.nextLine();
                    String chant = scan.nextLine();
                    String type = scan.nextLine();
                    AttackType attackType = AttackType.DARK;
                    Double dmg;
                    switch (type) {
                        case "FIRE" -> attackType = AttackType.FIRE;
                        case "FROST" -> attackType = AttackType.FROST;
                        case "ELECTRO" -> attackType = AttackType.ELECTRO;
                        case "WATER" -> attackType = AttackType.WATER;
                        case "LIGHT" -> attackType = AttackType.LIGHT;
                        case "DARK" -> attackType = AttackType.DARK;
                        case "POISON" -> attackType = AttackType.POISON;

                    }
                    dmg = scan.nextDouble();
                    if (scan.hasNextLine()) {
                        scan.nextLine();
                        scan.nextLine();
                    }
                    attackList.get(cClass).add(new Attack(name, chant, dmg, attackType));

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return attackList;
    }

    public ArrayList<SkillBook> readSkillBooks() {
        ArrayList<SkillBook> skillBooks = new ArrayList<>();
        try {
            File file = new File("src\\com\\company\\Items\\BluePrints\\skillBook.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String itemName = scan.nextLine();
                String rarityAsString = scan.nextLine();
                String attackName = scan.nextLine();
                String chant = scan.nextLine();
                String type = scan.nextLine();
                ItemRarity rarity = ItemRarity.COMMON;
                AttackType attackType = AttackType.NONE;
                Double dmg;
                switch (type) {
                    case "FIRE" -> attackType = AttackType.FIRE;
                    case "FROST" -> attackType = AttackType.FROST;
                    case "ELECTRO" -> attackType = AttackType.ELECTRO;
                    case "WATER" -> attackType = AttackType.WATER;
                    case "LIGHT" -> attackType = AttackType.LIGHT;
                    case "DARK" -> attackType = AttackType.DARK;
                    case "POISON" -> attackType = AttackType.POISON;

                }
                switch (rarityAsString) {
                    case "COMMON" -> rarity = ItemRarity.COMMON;
                    case "UNCOMMON" -> rarity = ItemRarity.UNCOMMON;
                    case "RARE" -> rarity = ItemRarity.RARE;
                    case "EPIC" -> rarity = ItemRarity.EPIC;
                    case "LEGENDARY" -> rarity = ItemRarity.LEGENDARY;
                }
                dmg = scan.nextDouble();
                if (scan.hasNextLine()) {
                    scan.nextLine();
                    scan.nextLine();
                }
                skillBooks.add(new SkillBook(rarity, itemName, attackName, chant, dmg, attackType));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return skillBooks;
    }

    public ArrayList<HealingPotion> readHealingPotions() {
        ArrayList<HealingPotion> healingPotions = new ArrayList<>();
        try {
            File file = new File("src\\com\\company\\Items\\BluePrints\\HealthPotion.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String itemName = scan.nextLine();
                String rarityAsString = scan.nextLine();
                Double heals = scan.nextDouble();
                int uses = scan.nextInt();
                ItemRarity rarity = ItemRarity.COMMON;


                switch (rarityAsString) {
                    case "COMMON" -> rarity = ItemRarity.COMMON;
                    case "UNCOMMON" -> rarity = ItemRarity.UNCOMMON;
                    case "RARE" -> rarity = ItemRarity.RARE;
                    case "EPIC" -> rarity = ItemRarity.EPIC;
                    case "LEGENDARY" -> rarity = ItemRarity.LEGENDARY;
                }
                if (scan.hasNextLine()) {
                    scan.nextLine();
                    scan.nextLine();
                }
                healingPotions.add(new HealingPotion(itemName, rarity, heals, uses));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return healingPotions;
    }
}
