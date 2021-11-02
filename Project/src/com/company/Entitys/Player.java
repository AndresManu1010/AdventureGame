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

    /**
     * Prints the most important information about the player
     */
    @Override
    public void printStats() {
        System.out.println("--------------------------------");
        System.out.println("Name: " + getName() + " LV. " + getLevel());
        System.out.println("EXP: " + exp + "/" + totalExperienceToNextLevel);
        System.out.println("Class: " + getEntityClass().name().toLowerCase());
        System.out.println("Health: " + getCurrentHealth() + "/" + getMaxHealth());
        System.out.println("Base Attack Damage: " + getBaseAttackDamage());
        System.out.println("--------------------------------");
    }

    /**
     * Increases the player's experience points.
     * If the player has enough exp for a level up
     * he level ups
     *
     * @param expGained the amount of exp the player recives
     */
    public void gainEXP(int expGained) {
        this.exp += expGained;
        if (totalExperienceToNextLevel <= exp) {
            levelUp(true);
            this.exp = this.exp - totalExperienceToNextLevel;
            this.totalExperienceToNextLevel = getLevel() * getLevel() / 2;
        }
    }

    /**
     * Let's the player learn a stronger attack from a skill book.
     */
    public void useSkillBook() {
        ArrayList<SkillBook> skillBooks = getSkillbooks();
        printSkillBooks();
        int userInput = selectSkillBook();
        if (userInput != -1) {
            learnSpecificAttack(skillBooks.get(userInput).getAttack());
            backpack.remove(skillBooks.get(userInput));
        }
    }

    /**
     * Prints all skillbooks in the players inventory/backpack
     * if he doesn't have any skillbooks a message will be printed out for that aswell
     */
    private void printSkillBooks() {
        ArrayList<SkillBook> skillBooks = getSkillbooks();
        int counter = 1;
        if (skillBooks.size() != 0) {
            for (SkillBook sb : skillBooks) {
                System.out.println(counter + "" + sb.getName());
                counter++;
            }
        } else {
            System.out.println("You don't have any Skill books");
        }
    }

    /**
     * Lets the player select the skillbook he wants to use
     * @return the index of the skillbook in the list
     */
    public int selectSkillBook() {
        int userInput = 0;
        Scanner scan = new Scanner();
        ArrayList<SkillBook> skillBooks = getSkillbooks();
        if (skillBooks.size() != 0) {
            System.out.println("Which Skillbook do you want to use?\n" +
                    "Please enter its number");
            while (userInput < 1 || userInput > skillBooks.size()) {
                userInput = scan.scanInt();
            }
            return userInput - 1;
        }
        return -1;
    }

    /**
     * Lets the player select a healing potion to use
     */
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

    /**
     * Adds an item to the backpack
     * @param i the item that gets added
     */
    public void addToBackpack(Item i) {
        backpack.add(i);
    }

    /**
     * Prints the contents of the players backpack
     */
    public void printBackpack() {
        int counter = 1;
        for (Item i : backpack) {
            System.out.println(counter++ + ". " + i.getName());
        }
    }

    /**
     *
     * @return returns the all the players skillbooks
     */
    private ArrayList<SkillBook> getSkillbooks() {
        ArrayList<SkillBook> skillBooks = new ArrayList<>();
        for (Item i : backpack) {
            if (i instanceof SkillBook) {
                skillBooks.add(((SkillBook) i));
            }
        }
        return skillBooks;
    }
}
