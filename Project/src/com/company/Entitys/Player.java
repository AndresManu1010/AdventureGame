package com.company.Entitys;
/*==============================================================
Author: Player
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Enums.CharacterClass;

public class Player extends Entity {

    public Player(String name, double maxHealth, double baseAttackDamage, int level, CharacterClass entityClass) {
        super(name, maxHealth, baseAttackDamage, level,entityClass);
    }
}
