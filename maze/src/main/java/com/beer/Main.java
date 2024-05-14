package com.beer;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            Maze game = new Maze(50, 50, 15, 15);

            game.startPlay();
         }
      });
    }
}