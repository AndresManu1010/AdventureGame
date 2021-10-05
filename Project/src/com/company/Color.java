package com.company;
/*==============================================================
Author: Color
Datum:  
ProjektName:    Project
Beschreibung: 
==============================================================*/

public class Color {
    // ANSI General Styling Codes
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String ANSI_BEFORE_TEXT = "\u001b[38;5;"; // Before level
    public static final String ANSI_BEFORE_BACKGROUND = "\u001b[48;5;";
    public static final String ANSI_AFTER = "m"; // After number 0-255
    // example:
    // System.out.print(ANSI_BEFORE_TEXT + 15 + ANSI_AFTER + "Hello World" + "RESET");
    // this prints Hello World in Color 15 (ex. red)

    ///////////////////////////////////////////////////////////////////////////
    // TEXT DECORATIONS ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public static final String BOLD = "\u001b[1m";
    public static final String UNDERLINE = "\u001b[4m";
    public static final String REVERSED = "\u001b[7m";



    // Console Color Codes
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE


    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // Method to display number codes
    public static void displayColorNumbers() {
        for (int i = 0; i <= 16; i++) {
            for (int j = 0; j <= 16; j++) {
                int code = i * 16 + j;
                String formattedCode = String.format("%4s", Integer.toString(code));
                System.out.print(ANSI_BEFORE_TEXT + code + ANSI_AFTER + formattedCode);
            }
            System.out.print("\n");
        }
        System.out.println(RESET);
    }
}