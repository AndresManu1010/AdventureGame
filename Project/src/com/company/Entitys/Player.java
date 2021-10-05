package com.company.Entitys;
/*==============================================================
Author: Player
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Enums.CharacterClass;
import com.company.Items.HealingPotion;
import com.company.Items.Item;
import com.company.Items.SkillBook;
import com.company.Other.Scanner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player extends Entity {
    private ArrayList<Item> backpack = new ArrayList<>();
    private int totalExperienceToNextLevel;
    private int exp = 0;

    public Player(String name, double maxHealth, double baseAttackDamage, int level, CharacterClass entityClass) {
        super(name, maxHealth, baseAttackDamage, level, entityClass);
        totalExperienceToNextLevel = level * level / 2;
    }

    public void gainEXP(int expGained) {
        this.exp += expGained;
        if (totalExperienceToNextLevel <= exp) {
            levelUp(true);
            this.exp = this.exp - totalExperienceToNextLevel;
            this.totalExperienceToNextLevel = getLevel() * getLevel() / 2;
        }
    }

    public void useSkillBook() {
        Scanner scan = new Scanner();
        ArrayList<SkillBook> skillBooks = new ArrayList<>();
        int counter = 1;
        int userInput = -1;
        for (Item i : backpack) {
            if (i instanceof SkillBook) {
                skillBooks.add(((SkillBook) i));
            }
        }
        if (skillBooks.size() != 0) {
            for (SkillBook sb : skillBooks) {
                System.out.println(counter + "" + sb.getName());
                counter++;
            }
            System.out.println("Which Skillbook do you want to use?\n" +
                    "Please enter its number");
            while (userInput < 1 || userInput > skillBooks.size()) {
                userInput = scan.scanInt();
            }
            userInput = userInput-1;
            learnSpecificAttack(skillBooks.get(userInput).getAttack());
            backpack.remove(skillBooks.get(userInput));
        }else{
            System.out.println("You don't have any Skill books");
        }
    }

    public void useHealingPotion() {
        Scanner scan = new Scanner();
        ArrayList<HealingPotion> healingPotions = new ArrayList<>();
        int counter = 1;
        int userInput = -1;
        for (Item i : backpack) {
            if (i instanceof HealingPotion) {
                healingPotions.add(((HealingPotion) i));
            }
        }
        if (healingPotions.size() != 0) {
            for (HealingPotion hp : healingPotions) {
                System.out.println(counter + ". " + hp.getName() + " Heals: " + hp.getHeals() + " Remaining Uses: " + hp.getRemainingUses());
                counter++;
            }
            System.out.println("Which potion do you want to use?\n" +
                    "Please enter its number");
            while (userInput < 1 || userInput > healingPotions.size()) {
                userInput = scan.scanInt();
            }
            userInput = userInput - 1;
            healFlat(healingPotions.get(userInput).usePotion());
            if (healingPotions.get(userInput).getRemainingUses() == 0) {
              backpack.remove(healingPotions.get(userInput));
            }
        } else {
            System.out.println("You don't have any potions");
        }

    }

    public void addToBackpack(Item i) {
        backpack.add(i);
    }

    public void printBackpack() {
        int counter = 1;
        for (Item i : backpack) {
            System.out.println(counter++ + ". " + i.getName());
        }
    }


}
