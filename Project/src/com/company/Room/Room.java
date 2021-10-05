package com.company.Room;

import com.company.Entitys.Entity;
import com.company.Items.Item;

import java.util.ArrayList;

public class Room {
    private Entity enemy;
    private ArrayList<Item> items;
    private boolean isBossRoom;
    private int x;
    private int y;

    public Room(Entity enemy, ArrayList<Item> items, boolean isBossRoom, int x, int y) {
        this.enemy = enemy;
        this.items = items;
        this.isBossRoom = isBossRoom;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Entity getEnemy() {
        return enemy;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    public void removeItems(){
        this.items =  new ArrayList<>();
    }
}
