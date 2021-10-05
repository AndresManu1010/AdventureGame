package com.company.Items;
/*==============================================================
Author: Skillbook
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

import com.company.Attacks.Attack;
import com.company.Enums.AttackType;
import com.company.Enums.ItemRarity;

public class SkillBook extends Item{
    private Attack attack;
    public SkillBook(ItemRarity rarity, String name, String attackName, String chant, double baseDMG, AttackType type) {
        super(rarity, name);
        attack = new Attack(attackName,chant,baseDMG,type);
    }

    public SkillBook(SkillBook skillBook) {
        super(skillBook.getRarity(),skillBook.getName());
        attack= new Attack(skillBook.getAttack());
    }

    public Attack getAttack() {
        return attack;
    }
}

