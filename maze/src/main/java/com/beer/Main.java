package com.beer;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new Window(50, 50, 15, 15, 0.1);
         }
      });
    }
}