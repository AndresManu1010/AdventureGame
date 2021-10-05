package com.company;

import com.company.Database.DatabaseReader;
import com.company.Managers.GameController;
import com.company.Other.Command;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Command.cls();
        GameController gc = new GameController();
        gc.start();



    }
}
