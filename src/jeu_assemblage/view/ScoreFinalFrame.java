package jeu_assemblage.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jeu_assemblage.modele.game.Game;

public class ScoreFinalFrame extends JFrame{
    
    private Game game;

    public ScoreFinalFrame(Game game) {
        super("Fin de parti");
        this.game = game;

        this.setLayout(new BorderLayout());

        JPanel scorePanel = new JPanel();
        JLabel scoreLabel = new JLabel((this.game == null? "" : "Votre score final est de " + this.game.getScore() + " !")); 
        scorePanel.add(scoreLabel);

        JButton quitButton = new JButton("Quitter");

        JFrame thisFrame = this;
        quitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.dispose();
            }
            
        });

        this.add(scorePanel, BorderLayout.CENTER);
        this.add(quitButton, BorderLayout.SOUTH);

        this.setSize(200, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
