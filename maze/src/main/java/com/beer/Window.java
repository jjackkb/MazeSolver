package com.beer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Window extends JFrame {
    private JPanel panel;
    private GroupLayout layout;
    private Font textFont;
    private JLabel games;
    private JLabel moves;
    private JButton resetButton;
    private Game game;
    public Window(Game newGame) {
        super("Maze"); 
        game = newGame;
        textFont = new Font("Helvetica", Font.BOLD, 14);
        panel = new JPanel();
        layout = new GroupLayout(panel);
        resetButton = new JButton("reset");
        moves = new JLabel();
        games = new JLabel();
        game.grid = new Grid(game);
    }

    protected void start() {
        panel.setLayout(layout);
        moves.setFont(textFont);
        games.setFont(textFont);
        resetButton.setPreferredSize(new Dimension(80, 35)); 
        setSize(game.window_width, game.window_height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup() //horizontal main group
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER) //center main group
            .addComponent(game.grid) 
            .addGroup(layout.createSequentialGroup() //sub group for games and moves
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(games))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(moves)))
            .addComponent(resetButton)));
        layout.setVerticalGroup(layout.createSequentialGroup() //vertical main group
            .addComponent(game.grid)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER) //sub group for games and moves
                .addComponent(moves)
                .addComponent(games))
            .addComponent(resetButton));

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                game.maze.genMap();
            }});
        resetButton.addActionListener(new ActionListener() { //reset button listener
            public void actionPerformed(ActionEvent e){  
                game.maze.genMap();
            }}); 

        add(panel);
        setVisible(true);
    }
    public void updateMoves() {
        game.win.moves.setText("Move: " + game.movesCount);
        game.movesCount++;
    }
    public void updateGames() {
        games.setText("Maze: " + game.gameCount);
        updateMoves();
    }
}

class Grid extends JPanel {
    private Game game;
    public Grid(Game newGame) {
        super();
        game = newGame;
        setPreferredSize(new Dimension(game.grid_width, game.grid_height));
        setMinimumSize(new Dimension(game.grid_width, game.grid_height));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(253,255,252));//background
        g.fillRect(game.cell_width, game.cell_height, game.grid_width, game.grid_height);
        g.setColor(new Color(76,185,68));//start cell
        g.fillRect((game.cell_width + (game.start.x * game.cell_width)), (game.cell_height + (game.start.y * game.cell_height)), game.cell_width, game.cell_height);
        g.setColor(new Color(171,52,40));//end cell
        g.fillRect((game.cell_width + (game.end.x * game.cell_width)), (game.cell_height + (game.end.y * game.cell_height)), game.cell_width, game.cell_height);
        
        for (Point fillCell : game.maze.disabledCells) { 
            int cellX = game.cell_width + (fillCell.x * game.cell_width);
            int cellY = game.cell_height + (fillCell.y * game.cell_height);
            g.setColor(Color.BLACK);//disabled cells
            g.fillRect(cellX, cellY, game.cell_width, game.cell_height);
        }
        for (Point fillCell : game.maze.visitedCells) { 
            if (!game.start.equals(fillCell)) {
                int cellX = game.cell_width + (fillCell.x * game.cell_width);
                int cellY = game.cell_height + (fillCell.y * game.cell_height);
                g.setColor(new Color(244,158,76));//visited cells
                g.fillRect(cellX, cellY, game.cell_width, game.cell_height);
            }
        }
        
        g.setColor(new Color(42,43,42));//line color
        g.drawRect(game.cell_width, game.cell_height, game.grid_width, game.grid_height);
        for (int i = game.cell_width; i <= game.grid_width; i += game.cell_width) { //horizontal
            g.drawLine(i, game.cell_height, i, game.grid_height + game.cell_height);
        }
        for (int i = game.cell_height; i <= game.grid_height; i += game.cell_height) { //vertical
            g.drawLine(game.cell_width, i, game.grid_width + game.cell_height, i); 
        }
    }
}