package com.company.Tests;

import com.company.Tables.StrengthWeakness;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*==============================================================
Author: StrengthWeaknessTest
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

class StrengthWeaknessTest {

    @Test
    void whenContainsBerserkerIsStrong() {
        for (int i = 0; i < 7; i++) {
           String strength =  StrengthWeakness.getAdvantage(0,i);
           assertEquals("STRONG",strength);
        }
        for (int i = 0; i < 7; i++) {
            String strength =  StrengthWeakness.getAdvantage(i,0);
            assertEquals("STRONG",strength);
        }

    }
    @Test
    void saberIsWeakVersusArcher(){
        String strength = StrengthWeakness.getAdvantage(1,2);
        assertEquals("WEAK",strength);
    }
}