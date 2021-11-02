package com.company.Tests;

import com.company.Entitys.Entity;
import com.company.Enums.CharacterClass;
import com.company.Managers.BattleController;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*==============================================================
Author: BattleControllerTest
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

class BattleControllerTest {
BattleController bc = new BattleController();
    @Before
    void initialize(){
        Entity player = new Entity("Player",100,30,5, CharacterClass.SABER);
        Entity enemy = new Entity("Enemy",100,30,5, CharacterClass.SABER);
        bc.setStartValues(player,enemy);
    }
    @Test
    void calcDMGTestTrue(){
   assertEquals(bc.calcDMG(5,1,1,1),5); ;
    }
    @Test
    void calcDMGTestFalse(){
        assertNotEquals(bc.calcDMG(5,1,2,1),5); ;
    }

}