package com.company.Tables;
/*==============================================================
Author: StrengthWeakness
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

public class StrengthWeakness {
    static private String[][] strengthTable = {
            //[Attacker][Defender]
            //0 = berserker
            //1 = Saber
            //2 = Archer
            //3 = Lancer
            //4 = Rider
            //5 = Assassin
            //6 = Caster
            {"STRONG", "STRONG", "STRONG", "STRONG", "STRONG", "STRONG", "STRONG"},
            {"STRONG", "NORMAL", "WEAK", "STRONG", "NORMAL", "NORMAL", "NORMAL"},
            {"STRONG", "STRONG", "NORMAL", "WEAK", "NORMAL", "NORMAL", "NORMAL"},
            {"STRONG", "WEAK", "STRONG", "NORMAL", "NORMAL", "NORMAL", "NORMAL"},
            {"STRONG", "NORMAL", "NORMAL", "NORMAL", "NORMAL", "WEAK", "STRONG"},
            {"STRONG", "NORMAL", "NORMAL", "NORMAL", "STRONG", "NORMAL", "WEAK"},
            {"STRONG", "NORMAL", "NORMAL", "NORMAL", "WEAK", "STRONG", "NORMAL"}
    };

    static public String getAdvantage(int attacker,int defender){
        return strengthTable[attacker][defender];
    }

}
