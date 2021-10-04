package com.company.Entitys;
/*==============================================================
Author: Entity
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Attacks.Attack;
import com.company.Database.Database;
import com.company.Enums.CharacterClass;
import com.company.Enums.EntityStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Entity {
    private String name;
    private double currentHealth;
    private double maxHealth;
    private double baseAttackDamage;
    private int level;
    private HashMap<String, Attack> attacks;
    private CharacterClass entityClass;
    private ArrayList<EntityStatus> statuses;

    public Entity(String name, double maxHealth, double baseAttackDamage, int level,CharacterClass entityClass) {
        this.name = name;
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
        this.baseAttackDamage = baseAttackDamage;
        this.level = 0;
        this.entityClass = entityClass;
        this.attacks = new HashMap<>();
        this.statuses = new ArrayList<>();
        for (int i = 0; i < level; i++) {
            levelUp();
        }

    }

    public void levelUp() {
        Random rand = new Random();
        this.level++;
        int attackIncrease = rand.nextInt(((int) this.baseAttackDamage / 100 + level)) + 3 +level/2;
        int healthIncrease = rand.nextInt(((int) this.maxHealth / 100 + level)) + 7 + level/2;
        this.baseAttackDamage += attackIncrease;
        this.maxHealth += healthIncrease;
        this.currentHealth += healthIncrease;
        learnRandomAttack();
    }

    public void learnRandomAttack() {
        Attack attack = new Attack(Database.getRandomAttack(this.entityClass));
        if (this.attacks.containsKey(attack.getNameChant())) {
            this.attacks.get(attack.getNameChant()).upgradeDamage(0.1);
        } else {
            this.attacks.put(attack.getNameChant(), attack);
        }
    }

    public void learnSpecificAttack() {

    }

    public void printStats() {
        System.out.println("--------------------------------");
        System.out.println("Name: " + name + " LV." + level);
        System.out.println("Class: " + entityClass.name().toLowerCase());
        System.out.println("Health: " + currentHealth + "/"+maxHealth);
        System.out.println("Base Attack Damage: " + baseAttackDamage);
        System.out.println("--------------------------------");
    }
}
