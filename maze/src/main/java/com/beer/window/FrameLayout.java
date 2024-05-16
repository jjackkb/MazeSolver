package com.beer.window;

import com.beer.Game;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

public class FrameLayout extends GroupLayout {

    private Game game;
    private Stat games = new Stat("Maze: ", game.getGames());
    private Stat moves = new Stat("Move: ", game.getMoves());
    private FrameButton reset = new FrameButton("reset", game.getResetActionListener(), new Dimension(80, 35));

    public FrameLayout(Frame frame, Game game) {
        super(frame);

        reset.setPreferredSize(new Dimension(80, 35)); 

        setAutoCreateGaps(true);
        setAutoCreateContainerGaps(true);
        setHorizontalGroup();
        setVerticalGroup();
    }
    
    private void setHorizontalGroup() {
        setHorizontalGroup(createSequentialGroup()
            .addGroup(createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(game.getGrid())
                .addGroup(createSequentialGroup()
                    .addGroup(createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(games))
                    .addGroup(createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(moves)))
                    .addComponent(reset)));
    }

    private void setVerticalGroup() {
        setVerticalGroup(
            createSequentialGroup()
                .addComponent(game.getGrid())
                .addGroup(createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(moves)
                    .addComponent(games))
                .addComponent(reset));
    }
}

class Stat extends JLabel {

    private Object obj;
    private String text;
    public Stat(String message, Object object) {
        obj = object;
        text = message;

        setText(text + object.toString());
    }

    public void update() {
        setText(text + obj.toString());
    }

    //getters
    public Object getObject() {
        return obj;
    }
    public String getText() {
        return text;
    }

    //setters
    public void setObject(Object newObject) {
        obj = newObject;
        update();
    }
    public void setText(String newText) {
        text = newText;
        update();
    }
}

class FrameButton extends JButton {
    
    private String text;
    private ActionListener action;
    private Dimension dimension;

    public FrameButton(String message, ActionListener actionObject, Dimension size) {
        text = message;
        action = actionObject;

        addActionListener(actionObject);
        setDimension(size);
    }

    //setter
    public void setDimension(Dimension size) {
        dimension = size;
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    //getter
    public String getText() {
        return text;
    }
    public ActionListener getActionListener() {
        return action;
    }
    public Dimension getDimension() {
        return dimension;
    }
}