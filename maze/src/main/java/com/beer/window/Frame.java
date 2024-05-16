package com.beer.window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

public class Frame extends JFrame{

    protected Dimension dimension;
    protected JPanel panel = new JPanel();
    protected ArrayList<Component> components = new ArrayList<>();
    protected final Font FONT = new Font("Helvetica", Font.BOLD, 14);
    public Frame(String name, int width, int height, FrameLayout frameLayout) {
        super(name);
        dimension = new Dimension(width, height);

        panel.setLayout(frameLayout);
        
        add(panel);
        setVisible(true);
    }

    //getters
    public Dimension getDimension() {
        return dimension;
    }

    //setters
    public void addComponent(Component comp) {
        components.add(comp);
    }   
}