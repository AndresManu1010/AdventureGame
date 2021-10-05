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
import java.util.Set;

public class Entity {
    private String name;
    private double currentHealth;
    private double maxHealth;
    private double baseAttackDamage;
    private int level;
    private HashMap<String, Attack> attacks;
    private CharacterClass entityClass;
    private ArrayList<EntityStatus> statuses;

    public Entity(String name, double maxHealth, double baseAttackDamage, int level, CharacterClass entityClass) {
        this.name = name;
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
        this.baseAttackDamage = baseAttackDamage;
        this.level = 0;
        this.entityClass = entityClass;
        this.attacks = new HashMap<>();
        this.statuses = new ArrayList<>();
        for (int i = 0; i < level; i++) {
            levelUp(false);
        }

    }

    public void levelUp(boolean printMessage) {
        Random rand = new Random();
        this.level++;
        int attackIncrease = rand.nextInt(((int) this.baseAttackDamage / 100 + level)) + 3 + level / 2;
        int healthIncrease = rand.nextInt(((int) this.maxHealth / 100 + level)) + 7 + level / 2;
        this.baseAttackDamage += attackIncrease;
        this.maxHealth += healthIncrease;
        this.currentHealth += healthIncrease;
        learnRandomAttack();
        if (printMessage){
            System.out.println("Level UP!");
        }
    }

    public void learnRandomAttack() {
        Attack attack = new Attack(Database.getRandomAttack(this.entityClass));
        if (this.attacks.containsKey(attack.getNameChant())) {
            this.attacks.get(attack.getNameChant()).upgradeDamage(0.1);
        } else {
            this.attacks.put(attack.getNameChant(), attack);
        }
    }

    public void learnSpecificAttack(Attack attack) {
        if(!attacks.containsKey(attack.getNameChant())){
            attacks.put(attack.getNameChant(),attack);
        }else{
            attacks.get(attack.getNameChant()).upgradeDamage(0.4);
        }

    }

    public void healFlat(double amountToHeal){
        this.currentHealth +=amountToHeal;
        if(this.currentHealth > this.maxHealth){
            this.currentHealth = this.maxHealth;
            System.out.println("You over-healed\n" +
                    "¯\\_(ツ)_/¯");
        }
    }

    public void healPercentOfMaxHP(double percent) {
        double healing =maxHealth * (percent / 100);
        this.currentHealth = this.currentHealth + healing;
        if(maxHealth < currentHealth){
            currentHealth = maxHealth;
        }
    }
    public void printStats() {
        System.out.println("--------------------------------");
        System.out.println("Name: " + name + " LV." + level);
        System.out.println("Class: " + entityClass.name().toLowerCase());
        System.out.println("Health: " + currentHealth + "/" + maxHealth);
        System.out.println("Base Attack Damage: " + baseAttackDamage);
        System.out.println("--------------------------------");
    }

    public boolean isAlive() {
        if (this.currentHealth > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isStunned(){
        if(statuses.contains(EntityStatus.STUNNED)){
            return true;
        }else{
            return false;
        }
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public double getBaseAttackDamage() {
        return baseAttackDamage;
    }

    public CharacterClass getEntityClass() {
        return entityClass;
    }

    public HashMap<String, Attack> getAttacks() {
        return attacks;
    }

    public void removeStatus(EntityStatus es){
        if(statuses.contains(es)){
            statuses.remove(es);
        }
    }
    public boolean addStatus(EntityStatus es){
        if(statuses.contains(es)){
            return false;
        }else{
            statuses.add(es);
            return true;
        }
    }
    public Attack getRandomAttack(){
        Random rand = new Random();
        Object[] values = attacks.values().toArray();
        return  ((Attack) values[rand.nextInt(values.length)]);
    }

    public void takeDamage(double dmg){
        this.currentHealth = this.currentHealth-dmg;
    }
}
