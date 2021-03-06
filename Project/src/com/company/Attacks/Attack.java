package com.company.Attacks;

import com.company.Enums.AttackType;

public class Attack {
    private String name;
    private String nameChant;
    private double damage;
    private AttackType type;


    public Attack(String name, String nameChant, double damage, AttackType type) {
        this.name = name;
        this.nameChant = nameChant;
        this.damage = damage;
        this.type = type;
    }

    public Attack(Attack copyThis) {
        this.damage = copyThis.damage;
        this.name = copyThis.name;
        this.nameChant = copyThis.nameChant;
        this.type = copyThis.type;
    }

    /**
     * Increases the damage the Attack deals
     * @param dmgMultiplier the multiplier in percent ex: 50% = 0.5
     * @return returns the new damage variable
     */
    public double upgradeDamage(double dmgMultiplier){
        this.damage= this.damage * (1+ dmgMultiplier) + 2;
        return this.damage;
    }


    public String getName() {
        return name;
    }

    public String getNameChant() {
        return nameChant;
    }

    public double getDamage() {
        return damage;
    }

    public AttackType getType() {
        return type;
    }
}
