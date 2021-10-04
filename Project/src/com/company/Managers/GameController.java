package com.company.Managers;
/*==============================================================
Author: GameController
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Database.Database;
import com.company.Entitys.Player;
import com.company.Enums.CharacterClass;
import com.company.Other.Scanner;
import com.company.Room.Room;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
   ArrayList<Room> level = new ArrayList<>();
    Player player;

    public void startNewGame() {
        Database.loadData();

        createPlayer();

    }
    private void createPlayer() {
        String name;
        CharacterClass cClass = CharacterClass.SABER;
        String sCLass;
        Boolean classNotDecidec = true;
        Scanner scan = new Scanner();
        System.out.println("Enter your name");
        name = scan.scanString();
        while (classNotDecidec) {
            System.out.println("Which class do you want to play as?");
            System.out.println("Saber,Archer,Lancer\n" +
                    "Caster,Assassin,Rider\n" +
                    "Berserker");
            sCLass = scan.scanString();
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

        this.player = new Player(name, 100, 10, 5,cClass);

    }

    private void createNewRoom(){

    }
}
