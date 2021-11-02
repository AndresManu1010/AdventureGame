package com.company.Managers;
/*==============================================================
Author: BattleManager
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Attacks.Attack;
import com.company.Color;
import com.company.Entitys.Entity;
import com.company.Enums.AttackType;
import com.company.Enums.EntityStatus;
import com.company.Tables.StrengthWeakness;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class BattleController {
    private Entity player;
    private Entity enemy;
    private AttackType lastPlayerAttackType = AttackType.NONE;
    private AttackType lastEnemyAttackType = AttackType.NONE;
    private AttackType currentPlayerAttackType = AttackType.NONE;
    private AttackType currentEnemyAttackType = AttackType.NONE;
    private int[] playerEffectTurns = {0, 0, 0};
    private int[] enemyEffectTurns = {0, 0, 0};

    /**
     * Starts the battle between the two Parameters
     * @param playerP will be controlled by the player
     * @param enemyP will be controlled by random chances
     */
    public void startBattle(Entity playerP, Entity enemyP) {
        setStartValues(playerP, enemyP);
        System.out.println("You are Facing:");
        System.out.println("Name:  " + enemy.getName());
        System.out.println("Class: " + enemy.getEntityClass());

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("|---------------------------------------------------|");
            dealDotDMG(player, true);
            if (player.isAlive()) {
                if (!player.isStunned()) {
                    playerAttack();
                } else {
                    player.removeStatus(EntityStatus.STUNNED);
                }
            }

            dealDotDMG(enemy, false);

            if (enemy.isAlive()) {
                if (!enemy.isStunned()) {
                    enemyAttack();
                } else {
                    enemy.removeStatus(EntityStatus.STUNNED);
                }
            } else {
                System.out.println(player.getName() + " has won the battle");
            }
            if (player.isAlive() == false) {
                System.out.println("You have lost the battle");
            }
            System.out.println("|-------------------------------------------------------------|");
        }
    }

    /**
     * Used to scan the
     * @return
     */
    private String scanPlayerAttack(){
        String userInput = "";
        Scanner scan = new Scanner(System.in);

        boolean validAttack = false;

        System.out.println(Color.GREEN + "Player health : " + player.getCurrentHealth() + Color.RESET);
        System.out.println(Color.GREEN + "Enemy health  : " + enemy.getCurrentHealth() + Color.RESET);
        System.out.println("-----------------------------------------------------");
        for (Map.Entry<String, Attack> at : player.getAttacks().entrySet()) {
            System.out.print(Color.CYAN + "Attack Name: " + at.getValue().getName() + Color.RESET);
            for (int i = 0; i < 24 - at.getValue().getName().length(); i++) {

                System.out.print(" ");

            }
            System.out.print(Color.RED + "DMG:");
            System.out.printf("%.1f", at.getValue().getDamage() + player.getBaseAttackDamage());
            System.out.println(Color.BLUE + " To Use: " + at.getKey());

            //   System.out.println("Attack Name: " + at.getValue().getName() + "\t DMG: " + at.getValue().getBaseDMG() + " To Use: " + at.getKey());
        }
        System.out.println(Color.RESET + "-----------------------------------------------------");

        System.out.print("Attack : ");
        while (!validAttack) {
            userInput = scan.nextLine();


            if (player.getAttacks().containsKey(userInput)) {
                validAttack = true;
            }
        }
        return userInput;
    }

    /**
     * used by the player to select his attack ot attack the enemy
     */
    private void playerAttack() {
    String userInput = scanPlayerAttack();
    double dmg;
        lastPlayerAttackType = currentPlayerAttackType;
        currentPlayerAttackType = player.getAttacks().get(userInput).getType();

        dmg = calcDMG((player.getAttacks().get(userInput).getDamage() + player.getBaseAttackDamage()), baseMultiplier(player.getName()),elementalReaction(true), classAdvantage(true));
        enemy.takeDamage(dmg);
        System.out.printf("You dealt %.0f DMG\n", dmg);
    }

    /**
     * Enemy chooses a attack randomly and deals damage to the player
     */
    private void enemyAttack() {
        Attack attack = enemy.getRandomAttack();
        double dmg = attack.getDamage();
        lastEnemyAttackType = currentEnemyAttackType;
        currentEnemyAttackType = attack.getType();
        System.out.println(Color.RED + enemy.getName() + " used " + attack.getName() + Color.RESET);

        dmg = calcDMG((dmg + enemy.getBaseAttackDamage()), baseMultiplier(enemy.getName()) ,elementalReaction(false),classAdvantage(false));
        player.takeDamage(dmg);
        System.out.printf("You took %.0f DMG\n", dmg);

    }

    /**
     * used to get a base multiplier
     * @param attackerName the attacks name will be used if it lands a critical hit
     * @return the multiplier
     */
    public double baseMultiplier(String attackerName){
       Random rand = new Random();
    double multiplier;
    double randNum = rand.nextInt(10) + 1;
    int lessDmgCheck = rand.nextInt(2);
    int crit = rand.nextInt(10) + 1;
    if (lessDmgCheck == 1) {
        multiplier = 1 - (randNum / 10) + 0.5;
    } else {
        multiplier = 1 + (randNum / 10);
    }
    if (crit == 10) {
        System.out.println(Color.RED + attackerName + " Landed a Critical hit" + Color.RESET);
        multiplier = multiplier * 1.5 + 0.5;
    }
    return multiplier;
}

    /**
     * Used to calculate the damage
     * @param dmg flat base damage
     * @param baseMultiplier a base multiplier
     * @param elementalReactionMultiplier a multiplier based on elemental reactions
     * @param classAdvantageMultiplier a multiplier based on advantages/disadvantages
     * @return
     */
    public double calcDMG(double dmg, double baseMultiplier,double elementalReactionMultiplier, double classAdvantageMultiplier) {
        double multiplier = 1;
        multiplier = multiplier * baseMultiplier * elementalReactionMultiplier * classAdvantageMultiplier;
        System.out.println(Color.YELLOW + "Multiplier : " + multiplier + Color.RESET);
        dmg = ((int) (dmg * multiplier));
        return dmg;
    }

    /**
     * Used to make elemental reactions
     * Based on an entity's last two attacks something might happen
     * @param isPlayerAttack if the elemental reaction is caused by the player or not
     * @return the multiplier
     */
    public double elementalReaction(boolean isPlayerAttack) {
        double elementalBonus = 1;
        AttackType lastAttackType;
        AttackType currentAttackType;
        boolean elementalReactionHappend = false;

        if (isPlayerAttack) {
            lastAttackType = lastPlayerAttackType;
            currentAttackType = currentPlayerAttackType;
            System.out.print(Color.BLUE);
        } else {
            lastAttackType = lastEnemyAttackType;
            currentAttackType = currentEnemyAttackType;
            System.out.print(Color.RED);
        }

        /*
https://fsymbols.com/text-art/
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
██████╗░░█████╗░███╗░░██╗██╗░░░██╗░██████╗  ██████╗░░█████╗░███╗░░░███╗░█████╗░░██████╗░███████╗
██╔══██╗██╔══██╗████╗░██║██║░░░██║██╔════╝  ██╔══██╗██╔══██╗████╗░████║██╔══██╗██╔════╝░██╔════╝
██████╦╝██║░░██║██╔██╗██║██║░░░██║╚█████╗░  ██║░░██║███████║██╔████╔██║███████║██║░░██╗░█████╗░░
██╔══██╗██║░░██║██║╚████║██║░░░██║░╚═══██╗  ██║░░██║██╔══██║██║╚██╔╝██║██╔══██║██║░░╚██╗██╔══╝░░
██████╦╝╚█████╔╝██║░╚███║╚██████╔╝██████╔╝  ██████╔╝██║░░██║██║░╚═╝░██║██║░░██║╚██████╔╝███████╗
╚═════╝░░╚════╝░╚═╝░░╚══╝░╚═════╝░╚═════╝░  ╚═════╝░╚═╝░░╚═╝╚═╝░░░░░╚═╝╚═╝░░╚═╝░╚═════╝░╚══════╝
         */

        if ((lastAttackType == AttackType.FIRE && currentAttackType == AttackType.FROST) || (lastAttackType == AttackType.FROST && currentAttackType == AttackType.FIRE)) {
            elementalBonus = 1.5;
            System.out.println("Frost Fire Reaction");
            elementalReactionHappend = true;
        }
        if ((lastAttackType == AttackType.FIRE && currentAttackType == AttackType.WATER) || (lastAttackType == AttackType.WATER && currentAttackType == AttackType.FIRE)) {
            elementalBonus = 1.5;
            System.out.println("Water Fire Reaction");
            elementalReactionHappend = true;
        }



                /*
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

██╗░░██╗███████╗░█████╗░██╗░░░░░
██║░░██║██╔════╝██╔══██╗██║░░░░░
███████║█████╗░░███████║██║░░░░░
██╔══██║██╔══╝░░██╔══██║██║░░░░░
██║░░██║███████╗██║░░██║███████╗
╚═╝░░╚═╝╚══════╝╚═╝░░╚═╝╚══════╝
         */
        if (lastAttackType == AttackType.LIGHT && currentAttackType == AttackType.LIGHT) {
            int heal = 10;
            if (isPlayerAttack) {
                player.healPercentOfMaxHP(heal);
                System.out.println(player.getName() + " healed " + (player.getMaxHealth() / heal));
            } else {
                enemy.healPercentOfMaxHP(heal);
                System.out.println(enemy.getName() + " healed " + (enemy.getMaxHealth() / heal));
            }
            elementalReactionHappend = true;
        }


        /*
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
██████╗░░█████╗░████████╗░██████╗
██╔══██╗██╔══██╗╚══██╔══╝██╔════╝
██║░░██║██║░░██║░░░██║░░░╚█████╗░
██║░░██║██║░░██║░░░██║░░░░╚═══██╗
██████╔╝╚█████╔╝░░░██║░░░██████╔╝
╚═════╝░░╚════╝░░░░╚═╝░░░╚═════╝░
         */
        if (lastAttackType == AttackType.POISON && currentAttackType == AttackType.POISON) {
            if (isPlayerAttack) {
                enemy.addStatus(EntityStatus.POISONED);
                enemyEffectTurns[1] += 5;
            } else {
                player.addStatus(EntityStatus.POISONED);

                playerEffectTurns[1] += 5;
            }
            elementalReactionHappend = true;
            System.out.println("Pure Poison");
        }

        if (lastAttackType == AttackType.FIRE && currentAttackType == AttackType.FIRE) {
            if (isPlayerAttack) {
                enemy.addStatus(EntityStatus.ONFIRE);

                enemyEffectTurns[0] += 5;
            } else {
                player.addStatus(EntityStatus.ONFIRE);

                playerEffectTurns[0] += 5;
            }
            elementalReactionHappend = true;
            System.out.println("Pure Fire");
        }

        if (lastAttackType == AttackType.FROST && currentAttackType == AttackType.FROST) {
            if (isPlayerAttack) {
                enemy.addStatus(EntityStatus.FROSTBURN);

                enemyEffectTurns[2] += 7;
            } else {
                player.addStatus(EntityStatus.FROSTBURN);

                playerEffectTurns[2] += 7;
            }
            elementalReactionHappend = true;
            System.out.println("Pure Frost");
        }

        /*
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
░██████╗██████╗░███████╗░█████╗░██╗░█████╗░██╗░░░░░  ███████╗███████╗███████╗███████╗░█████╗░████████╗░██████╗
██╔════╝██╔══██╗██╔════╝██╔══██╗██║██╔══██╗██║░░░░░  ██╔════╝██╔════╝██╔════╝██╔════╝██╔══██╗╚══██╔══╝██╔════╝
╚█████╗░██████╔╝█████╗░░██║░░╚═╝██║███████║██║░░░░░  █████╗░░█████╗░░█████╗░░█████╗░░██║░░╚═╝░░░██║░░░╚█████╗░
░╚═══██╗██╔═══╝░██╔══╝░░██║░░██╗██║██╔══██║██║░░░░░  ██╔══╝░░██╔══╝░░██╔══╝░░██╔══╝░░██║░░██╗░░░██║░░░░╚═══██╗
██████╔╝██║░░░░░███████╗╚█████╔╝██║██║░░██║███████╗  ███████╗██║░░░░░██║░░░░░███████╗╚█████╔╝░░░██║░░░██████╔╝
╚═════╝░╚═╝░░░░░╚══════╝░╚════╝░╚═╝╚═╝░░╚═╝╚══════╝  ╚══════╝╚═╝░░░░░╚═╝░░░░░╚══════╝░╚════╝░░░░╚═╝░░░╚═════╝░
         */

        if ((lastAttackType == AttackType.WATER && currentAttackType == AttackType.FROST) || (lastAttackType == AttackType.FROST && currentAttackType == AttackType.WATER)) {
            if (isPlayerAttack) {
                enemy.addStatus(EntityStatus.STUNNED);
            } else {
                player.addStatus(EntityStatus.STUNNED);
            }
            elementalReactionHappend = true;
            System.out.println("Water Frost Reaction");
            System.out.println("Freeze");
        }
        if ((lastAttackType == AttackType.LIGHT && currentAttackType == AttackType.ELECTRO) || (lastAttackType == AttackType.ELECTRO && currentAttackType == AttackType.LIGHT)) {
            if (isPlayerAttack) {
                enemy.addStatus(EntityStatus.STUNNED);
            } else {
                player.addStatus(EntityStatus.STUNNED);
            }
            elementalReactionHappend = true;
            System.out.println("Light Electro Reaction");
            System.out.println("Flash");
        }


        if (elementalReactionHappend) {
            if (isPlayerAttack) {
                currentPlayerAttackType = AttackType.NONE;
                lastPlayerAttackType = AttackType.NONE;
            } else {
                currentEnemyAttackType = AttackType.NONE;
                lastEnemyAttackType = AttackType.NONE;
            }
        }

        System.out.print(Color.RESET);
        return elementalBonus;
    }

    /**
     * Returns a double multiplier based on a table
     * @param isPlayerAttack to decide who the attacker and defender is
     * @return the multiplier
     */
    private double classAdvantage(boolean isPlayerAttack) {
        int attackerClass = 0;
        int defenderClass = 0;
        Entity attacker;
        Entity defender;
        if (isPlayerAttack) {
            attacker = player;
            defender = enemy;
        } else {
            attacker = enemy;
            defender = player;
        }

        switch (attacker.getEntityClass()) {
            case BERSERKER -> attackerClass = 0;
            case SABER -> attackerClass = 1;
            case ARCHER -> attackerClass = 2;
            case LANCER -> attackerClass = 3;
            case RIDER -> attackerClass = 4;
            case ASSASSIN -> attackerClass = 5;
            case CASTER -> attackerClass = 6;
        }

        switch (defender.getEntityClass()) {
            case BERSERKER -> defenderClass = 0;
            case SABER -> defenderClass = 1;
            case ARCHER -> defenderClass = 2;
            case LANCER -> defenderClass = 3;
            case RIDER -> defenderClass = 4;
            case ASSASSIN -> defenderClass = 5;
            case CASTER -> defenderClass = 6;
        }
        return switch (StrengthWeakness.getAdvantage(attackerClass, defenderClass)) {
            case "STRONG" -> 1.2;
            case "NORMAL" -> 1.0;
            case "WEAK" -> 0.8;
            default -> 1;
        };
    }

    /**
     * Used to call damaging methodes
     * @param e the entity
     * @param isPlayer if the entity is a player or not
     */
    private void dealDotDMG(Entity e, boolean isPlayer) {

        if (e.isPoisoned()) {
            e.takeCurrentHealthDamage(11, "poison");
            if (isPlayer) {
                playerEffectTurns[1] = playerEffectTurns[1] - 1;
                if (playerEffectTurns[1] == 0) {
                    e.removeStatus(EntityStatus.POISONED);
                }
            } else {
                enemyEffectTurns[1] = enemyEffectTurns[1] - 1;
                if (enemyEffectTurns[1] == 0) {
                    e.removeStatus(EntityStatus.POISONED);
                }
            }
        }
        if (e.isOnFire()) {
            e.takeMaxLifeDamage(6, "fire");
            if (isPlayer) {
                playerEffectTurns[0] = playerEffectTurns[0] - 1;
                if (playerEffectTurns[0] == 0) {
                    e.removeStatus(EntityStatus.ONFIRE);
                }
            } else {
                enemyEffectTurns[0] = enemyEffectTurns[0] - 1;
                if (enemyEffectTurns[0] == 0) {
                    e.removeStatus(EntityStatus.ONFIRE);
                }
            }
        }
        if (e.isFrostBurned()) {
            e.takeMaxLifeDamage(7, "sheer Cold");
            if (isPlayer) {
                playerEffectTurns[2] = playerEffectTurns[2] - 1;
                if (playerEffectTurns[2] == 0) {
                    e.removeStatus(EntityStatus.FROSTBURN);
                }
            } else {
                enemyEffectTurns[2] = enemyEffectTurns[2] - 1;
                if (enemyEffectTurns[2] == 0) {
                    e.removeStatus(EntityStatus.FROSTBURN);
                }
            }
        }
    }

    /**
     * resets the Battlecontroller and changes the player and enemy
     * @param player
     * @param enemy
     */
    public void setStartValues(Entity player, Entity enemy) {
        this.player = player;
        this.enemy = enemy;
        lastPlayerAttackType = AttackType.NONE;
        lastEnemyAttackType = AttackType.NONE;
        currentPlayerAttackType = AttackType.NONE;
        currentEnemyAttackType = AttackType.NONE;


        playerEffectTurns = new int[]{0, 0, 0};
        enemyEffectTurns = new int[]{0, 0, 0};

    }

}
