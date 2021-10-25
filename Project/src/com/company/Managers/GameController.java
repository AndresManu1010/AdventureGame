package com.company.Managers;
/*==============================================================
Author: GameController
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Database.Database;
import com.company.Entitys.Entity;
import com.company.Entitys.Player;
import com.company.Enums.CharacterClass;
import com.company.Items.Item;
import com.company.Other.Command;
import com.company.Other.Scanner;
import com.company.Room.Room;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameController {
    private HashMap<Integer, HashMap<String, Room>> levels = new HashMap<>();
    private int level = 0;
    private Player player;
    private double enemyBaseHealth = 100;
    private double enemyBaseAttack = 10;
    private Room currentRoom;
    private BattleController bc = new BattleController();

    public void start() {

        Database.loadData();
        inputToCreateNewPlayer();
        while (player.isAlive()) {
            startNewLevel();
        }
    }

    public void startNewLevel() {
        this.level++;
        levels.put(level, new HashMap<>());
        createNewRoom(level, 0, 0);
        this.currentRoom = levels.get(level).get("0;0");
        System.out.println("Started level:" + level);
        runGame();
    }

    private void runGame() {
        boolean defeatedBoss = false;
        Scanner scan = new Scanner();
        String userInput;
        while (!defeatedBoss && player.isAlive()) {
            printPossibilities();
            userInput = scan.scanString();
            Command.cls();
            switch (userInput) {
                case "w":
                    move();
                    break;
                case "f":
                 defeatedBoss =   battle();
                 if(defeatedBoss){
                    return;
                 }
                    break;
                case "i":
                    player.printBackpack();
                    break;
                case "s":
                    player.printStats();
                    break;
                case "p":
                    pickupItems();
                    break;
                case "h":
                    player.useHealingPotion();
                    break;
                case "sb":
                    player.useSkillBook();
                    break;
            }
        }

    }

    private void inputToCreateNewPlayer() {
        String name;
        CharacterClass cClass = CharacterClass.SABER;
        String sCLass;
        Boolean classNotDecidec = true;
        Scanner scan = new Scanner();
        System.out.println("Enter your name");
        name = scan.scanString();
        Command.cls();
        while (classNotDecidec) {
            System.out.println("Which class do you want to play as?");
            System.out.println("Saber\nArcher\nLancer\n" +
                    "Caster\nAssassin\nRider\n" +
                    "Berserker");
            sCLass = scan.scanString();
            Command.cls();
            classNotDecidec = false;
            switch (sCLass) {
                case "Saber" -> cClass = CharacterClass.SABER;
                case "Archer" -> cClass = CharacterClass.ARCHER;
                case "Lancer" -> cClass = CharacterClass.LANCER;
                case "Caster" -> cClass = CharacterClass.CASTER;
                case "Assassin" -> cClass = CharacterClass.ASSASSIN;
                case "Rider" -> cClass = CharacterClass.RIDER;
                case "Berserker" -> cClass = CharacterClass.BERSERKER;
                default -> classNotDecidec = true;
            }
        }

        createNewPlayer(name, cClass);

    }

    public void createNewPlayer(String name, CharacterClass c) {
        this.player = new Player(name, 100, 10, 5, c);
    }

    private void createNewRoom(int level, int x, int y) {
        if (!levels.get(level).containsKey(x + ";" + y)) {
            Random rand = new Random();
            boolean isBossRoom;
            if (rand.nextInt(20) + 1 == 20) {
                isBossRoom = true;
            } else {
                isBossRoom = false;
            }
            Room newRoom = new Room(createRandomEnemy(isBossRoom), Database.getXRandomItems(rand.nextInt(3) + 1, isBossRoom), isBossRoom, x, y);
            if (!levels.containsKey(level)) {
                levels.put(level, new HashMap<>());
            }
            levels.get(level).put(x + ";" + y, newRoom);
        }
    }

    private Entity createRandomEnemy(boolean isBoss) {
        Random rand = new Random();
        String[] nameList = {"Lydan",
                "Syrin",
                "Ptorik",
                "Joz",
                "Varog",
                "Gethrod",
                "Feron",
                "Hezra",
                "Ophni",
                "Colborn",
                "Fintis",
                "Gatlin",
                "Jinto",
                "Hagalbar",
                "Krinn",
                "Lenox",
                "Revvyn",
                "Hodus",
                "Dimian",
                "Paskel",
                "Kontas",
                "Weston",
                "Azamarr",
                "Jather",
                "Tekren",
                "Jareth",
                "Adon",
                "Zaden",
                "Eune",
                "Graff"
        };
        double baseHealth = enemyBaseHealth+level*level-level;
        double baseAttack = enemyBaseAttack+level*level-level;
        CharacterClass c;
        //Bosses have 50% stronger base stats a regular enemy
        if (isBoss) {
            baseHealth = baseHealth * 1.5;
            baseAttack = baseAttack * 1.5;
        }
        switch (rand.nextInt(7) + 1) {
            case 1:
                c = CharacterClass.SABER;
                break;
            case 2:
                c = CharacterClass.ARCHER;
                break;
            case 3:
                c = CharacterClass.LANCER;
                break;
            case 4:
                c = CharacterClass.ASSASSIN;
                break;
            case 5:
                c = CharacterClass.CASTER;
                break;
            case 6:
                c = CharacterClass.RIDER;
                break;
            case 7:
                c = CharacterClass.BERSERKER;
                break;
            default:
                c = CharacterClass.SABER;
        }

        return new Entity(nameList[rand.nextInt(nameList.length)], baseHealth, baseAttack, this.player.getLevel() + 1, c);
    }

    private void printPossibilities() {
        System.out.println("w -> Walk to a different Room ");
        System.out.println("f -> Fight the enemy in the current room");
        System.out.println("i -> Look at inventory");
        System.out.println("s -> Prints your stats");
        System.out.println("p -> Pick up items on the ground after a fight");
        System.out.println("h -> use A healing potion");
        System.out.println("sb -> use A skill book");
    }

    public Player getPlayer() {
        return player;
    }

    public int getLevel() {
        return level;
    }

    private void move() {
        Command.cls();
        if (currentRoom.getEnemy().isAlive()) {
            System.out.println("You need to defeat the enemy in this room first");
        } else {
            String userInput = "";
            Scanner scan = new Scanner();
            System.out.println("Wher do you want to go?\n" +
                    "N = north\n" +
                    "E = east\n" +
                    "S = south\n" +
                    "W = west");
            while (!userInput.equals("N") && !userInput.equals("W") && !userInput.equals("E") && !userInput.equals("S")) {
                userInput = scan.scanString();
            }
            System.out.println("Go" + userInput);
            int newX = 0;
            int newY = 0;
            switch (userInput) {
                case "N":
                    newX = currentRoom.getX() + 1;
                    newY = currentRoom.getY();
                    break;
                case "S":
                    newX = currentRoom.getX() - 1;
                    newY = currentRoom.getY();
                    break;
                case "E":
                    newX = currentRoom.getX();
                    newY = currentRoom.getY() + 1;
                    break;
                case "W":
                    newX = currentRoom.getX();
                    newY = currentRoom.getY() - 1;
                    break;
            }
            createNewRoom(level, newX, newY);
            this.currentRoom = levels.get(level).get(newX + ";" + newY);
        }
    }

    public boolean battle() {
        Command.cls();
        if (currentRoom.getEnemy().isAlive()) {
            if (currentRoom.isBossRoom()) {
                System.out.println("You are going to fight a boss!");
            }
            bc.startBattle(player, currentRoom.getEnemy());
            if (player.isAlive()) {
                int levelDifference = currentRoom.getEnemy().getLevel() - player.getLevel();
                if (levelDifference < 0) {
                    levelDifference = 0;
                }
                player.gainEXP(currentRoom.getEnemy().getLevel() + levelDifference);
                System.out.println("You gained: " + (currentRoom.getEnemy().getLevel() + levelDifference) + " EXP!");
                if (currentRoom.isBossRoom()) {
                    return true;
                }else{
                    return false;
                }
            }
        } else {
            System.out.println("You have defeated the enemy already");
        }
        return false;
    }

    public void pickupItems() {
        Command.cls();
        if (!currentRoom.getEnemy().isAlive()) {
            for (Item i : currentRoom.getItems()) {
                player.addToBackpack(i);
            }
            currentRoom.removeItems();
        } else {
            System.out.println("You need to defeat the enemy first");
        }
    }
}
