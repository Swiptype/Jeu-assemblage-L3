package jeu_assemblage.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import jeu_assemblage.modele.game.Game;

public class FinishButton extends JButton implements ActionListener {
    
    private Game game;

    public FinishButton(Game game) {
        super("Finir la partie");
        this.game = game;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.game.finish();
    }

}
