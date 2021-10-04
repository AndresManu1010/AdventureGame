package com.company;

import com.company.Database.DatabaseReader;
import com.company.Managers.GameController;

public class Main {

    public static void main(String[] args) {
        GameController gc = new GameController();
        gc.startNewGame();
    }
}
