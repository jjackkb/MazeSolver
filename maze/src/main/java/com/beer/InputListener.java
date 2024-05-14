package com.beer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.*;

public class InputListener extends JPanel {
   enum Dir {
      LEFT("Left", KeyEvent.VK_LEFT, -1, 0),
      RIGHT("Right", KeyEvent.VK_RIGHT, 1, 0),
      UP("Up", KeyEvent.VK_UP, 0, -1),
      DOWN("Down", KeyEvent.VK_DOWN, 0, 1);

      private String name;
      private int keyCode;
      private int deltaX;
      private int deltaY;
      private Dir(String name, int keyCode, int deltaX, int deltaY) {
         this.name = name;
         this.keyCode = keyCode;
         this.deltaX = deltaX;
         this.deltaY = deltaY;
      }
      public String getName() {
         return name;
      }
      public int getKeyCode() {
         return keyCode;
      }
      public int getDeltaX() {
        return deltaX;
     }
     public int getDeltaY() {
        return deltaY;
     }  
   }

   public static final int TIMER_DELAY = 50;
   public static final int DELTA_X = 1;
   public static final int DELTA_Y = DELTA_X;
   private static final String PRESSED = "pressed";
   private static final String RELEASED = "released";
   private Map<Dir, Boolean> dirMap = new EnumMap<>(Dir.class);
   private Timer animationTimer = new Timer(TIMER_DELAY, new ControlListener());
   private static Maze maze;

   public InputListener(Maze game) {
    maze = game;
      for (Dir dir : Dir.values()) {
         dirMap.put(dir, Boolean.FALSE);
      }
      setKeyBindings();
      animationTimer.start();
   }

   private void setKeyBindings() {
      int condition = WHEN_IN_FOCUSED_WINDOW;
      InputMap inputMap = getInputMap(condition);
      ActionMap actionMap = getActionMap();

      for (Dir dir : Dir.values()) {
         KeyStroke keyPressed = KeyStroke.getKeyStroke(dir.getKeyCode(), 0, false);
         KeyStroke keyReleased = KeyStroke.getKeyStroke(dir.getKeyCode(), 0, true);

         inputMap.put(keyPressed, dir.toString() + PRESSED);
         inputMap.put(keyReleased, dir.toString() + RELEASED);

         actionMap.put(dir.toString() + PRESSED, new DirAction(dir, PRESSED));
         actionMap.put(dir.toString() + RELEASED, new DirAction(dir, RELEASED));
      }

   }

   private class ControlListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int newX = maze.getX();
            int newY = maze.getY();
            for (Dir dir : Dir.values()) {
                if (dirMap.get(dir)) {
                    newX += dir.getDeltaX() * DELTA_X;
                    newY += dir.getDeltaY() * DELTA_Y;
                    }
                }
            if (newX < 0 || newY < 0) {
                return;
            }
            if (newX >= maze.win.getNumSquaresX() || newY >= maze.win.getNumSquaresY()) {
                return;
            }
            
            maze.setX(newX);
            maze.setY(newY);
            maze.win.repaint();
        }
   }

   private class DirAction extends AbstractAction {

      private String pressedOrReleased;
      private Dir dir;

      public DirAction(Dir dir, String pressedOrReleased) {
         this.dir = dir;
         this.pressedOrReleased = pressedOrReleased;
      }

      @Override
      public void actionPerformed(ActionEvent evt) {
         if (pressedOrReleased.equals(PRESSED)) {
            dirMap.put(dir, Boolean.TRUE);
         } else if (pressedOrReleased.equals(RELEASED)) {
            dirMap.put(dir, Boolean.FALSE);
         }
      }
   }
}