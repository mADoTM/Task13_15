package ru.vsu.cs.dolzhenkoms;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GUIMain gui = new GUIMain();
        Game game = new Game(7,7);

    }

    private static void printField(GameCell[][] field) {
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field[i].length; j++) {
                if(field[i][j].getStatus() == GameCellStatus.DEFAULT) {
                    System.out.print(field[i][j].getValue());
                }
                if(field[i][j].getStatus() == GameCellStatus.BOMB) {
                    System.out.print("*");
                }
                if(field[i][j].getStatus() == GameCellStatus.PLAYER) {
                    System.out.print("P");
                }
                if(field[i][j].getStatus() == GameCellStatus.DEAD) {
                    System.out.print("D");
                }
                if(field[i][j].getStatus() == GameCellStatus.OPENED) {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }

    private static int readInt(String str) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите " + str + " - ");
        return scn.nextInt();
    }
}
