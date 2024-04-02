package jeu_assemblage.view;

import javax.swing.*;
import javax.swing.border.Border;

import jeu_assemblage.modele.game.Cell;
import jeu_assemblage.modele.game.Game;

import java.awt.*;

public class GridPanel extends JPanel {
    
    private int height, width;
    private Game game;

    public GridPanel(Game game) {
        this.game = game;

        this.height = this.game.getGameSetting().getHauteur();
        this.width  = this.game.getGameSetting().getLargeur();

        this.setLayout(new GridLayout(this.height, this.width));

        Border lineborder = BorderFactory.createLineBorder(Color.black, 1); 
        this.setBorder(lineborder);

        this.initGridPanel();
    }

    private void initGridPanel() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Cell cell = this.game.getJAGrid().getCell(i, j);
                GridCell gridCell = new GridCell(game, cell);
                this.add(gridCell);
            }
        }
    }

    public void update(JFrame frame) {
        this.removeAll();

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Cell cell = this.game.getJAGrid().getCell(i, j);
                GridCell gridCell = new GridCell(game, cell);
                this.add(gridCell);
            }
        }

        frame.revalidate();
        frame.repaint();
    }
}
