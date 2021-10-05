package com.company.Other;
/*==============================================================
Author: Command
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

public class Command {
    static public void cls(){
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }catch (Exception e){}
    }
}
