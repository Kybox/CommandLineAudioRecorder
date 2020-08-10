package fr.kybox.utils;

import java.util.Scanner;

/**
 * Author : yann@kybox.fr
 **/
public class UserInput {

    public static Integer getInt() {
        return new Scanner(System.in).nextInt();
    }

    public static String getLine() {
        return new Scanner(System.in).nextLine();
    }
}
