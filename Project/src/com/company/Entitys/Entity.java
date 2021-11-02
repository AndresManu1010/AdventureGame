package com.company.Entitys;

import com.company.Attacks.Attack;
import com.company.Database.Database;
import com.company.Enums.CharacterClass;
import com.company.Enums.EntityStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The entity class is used to store Entity's.
 * Meaning it stores enemies and the player.
 */
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

    /**
     * The levelUp methode increases the player level,
     * increases health and attack
     * the entity learns a randomAttack
     *
     * @param printMessage decides if a short level Up message gets printed
     */
    public void levelUp(boolean printMessage) {
        Random rand = new Random();
        this.level++;
        int attackIncrease = rand.nextInt(((int) this.baseAttackDamage / 100 + level)) + 1 + level / 2;
        int healthIncrease = rand.nextInt(((int) this.maxHealth / 100 + level)) + 7 + level / 2;
        this.baseAttackDamage += attackIncrease;
        this.maxHealth += healthIncrease;
        this.currentHealth += healthIncrease;
        learnRandomAttack();
        if (printMessage){
            System.out.println("Level UP!");
        }
    }

    /**
     * the entity learns a new attack randomly
     * IF the entity already has that attack, it gets upgraded, increasing its damage
     */
    public void learnRandomAttack() {
        Attack attack = new Attack(Database.getRandomAttack(this.entityClass));
        if (this.attacks.containsKey(attack.getNameChant())) {
            this.attacks.get(attack.getNameChant()).upgradeDamage(0.1);
        } else {
            this.attacks.put(attack.getNameChant(), attack);
        }
    }
    /**
     * the entity learns a new attack
     * IF the entity already has that attack, it gets upgraded increasing its damage
     */
    public void learnSpecificAttack(Attack attack) {
        if(!attacks.containsKey(attack.getNameChant())){
            attacks.put(attack.getNameChant(),attack);
        }else{
            attacks.get(attack.getNameChant()).upgradeDamage(0.4);
        }
    }

    /**
     * The entity heals a set amount of its current health
     * @param amountToHeal the amount that the entity heals
     */
    public void healFlat(double amountToHeal){
        this.currentHealth +=amountToHeal;
        if(this.currentHealth > this.maxHealth){
            this.currentHealth = this.maxHealth;
            System.out.println("You over-healed\n" +
                    "¯\\_(ツ)_/¯");
        }
    }

    /**
     * the entity heals a percentage of its max health
     * @param percent the percentage that will be healed
     */
    public void healPercentOfMaxHP(double percent) {
        double healing =maxHealth * (percent / 100);
        this.currentHealth = this.currentHealth + healing;
        if(maxHealth < currentHealth){
            currentHealth = maxHealth;
        }
    }

    /**
     * Prints some information about the entity's stats
     */
    public void printStats() {
        System.out.println("--------------------------------");
        System.out.println("Name: " + name + " LV." + level);
        System.out.println("Class: " + entityClass.name().toLowerCase());
        System.out.println("Health: " + currentHealth + "/" + maxHealth);
        System.out.println("Base Attack Damage: " + baseAttackDamage);
        System.out.println("--------------------------------");
    }

    /**
     * Removes the parameter from the statuses
     * @param es the status that should be removed
     */
    public void removeStatus(EntityStatus es){
        if(statuses.contains(es)){
            statuses.remove(es);
        }
    }

    /**
     * Adds a status to the entity
     * @param es the status that should be added
     * @return if the adding was successful
     */
    public boolean addStatus(EntityStatus es){
        if(statuses.contains(es)){
            return false;
        }else{
            statuses.add(es);
            return true;
        }
    }

    /**
     * returns a random attack
     * @return the random attack
     */
    public Attack getRandomAttack(){
        Random rand = new Random();
        Object[] values = attacks.values().toArray();
        return  ((Attack) values[rand.nextInt(values.length)]);
    }

    /**
     * The entity takes a set amount of damage
     * @param dmg
     */
    public void takeDamage(double dmg){
        this.currentHealth = this.currentHealth-dmg;
    }

    /**
     * The entity takes a amount of damage based on its current health
     * @param percent the percentage of the current health that should be dealt as damage
     * @param source a source ex:Fire,Poison
     */
    public void takeCurrentHealthDamage(double percent,String source){
        double damage =Math.abs(this.currentHealth *(percent/100)) ;
        System.out.println(name + " took "+damage+" dmg from "+ source );
        this.currentHealth = this.currentHealth - damage;
    }
    /**
     * The entity takes a amount of damage based on its maximum health
     * @param percent the percentage of the maximum health that should be dealt as damage
     * @param source a source ex:Fire,Poison
     */
    public void takeMaxLifeDamage(double percent,String source){
        double damage =Math.abs( this.maxHealth *(percent/100));
        System.out.println(name + " took "+damage+" dmg from "+ source );
        this.currentHealth = this.currentHealth - damage;
    }

    /**
     * Returns of the Entity is alive(current health is above 0)
     * @return if the entity is alive or not
     */
    public boolean isAlive() {
        if (this.currentHealth > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return if the entity is stunned or not
     */
    public boolean isStunned(){
        if(statuses.contains(EntityStatus.STUNNED)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * @return if the entity is On Fire or not
     */
    public boolean isOnFire(){
        if(statuses.contains(EntityStatus.ONFIRE)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * @return if the entity is Frost burned or not
     */
    public boolean isFrostBurned(){
        if(statuses.contains(EntityStatus.FROSTBURN)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * @return if the entity is Poisoned or not
     */
    public boolean isPoisoned(){
        if(statuses.contains(EntityStatus.POISONED)){
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


}
