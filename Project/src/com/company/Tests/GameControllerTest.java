package com.company.Tests;

import com.company.Database.Database;
import com.company.Enums.CharacterClass;
import com.company.Managers.GameController;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.*;

/*==============================================================
Author: GameControllerTest
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

class GameControllerTest {
    GameController gc = new GameController();


    @Test
    void createNewPlayerWorksIfDatabaseLoaded() {
        Database.loadData();
        String name = "Tester";
        CharacterClass c = CharacterClass.SABER;
        gc.createNewPlayer(name, c);
        assertEquals(name, gc.getPlayer().getName());
        assertEquals(c, gc.getPlayer().getEntityClass());
        Database.reset();
    }




    @Test
    void levelIsZeroAtBeginning() {
        gc = new GameController();
        assertEquals(0, gc.getLevel());
    }
}