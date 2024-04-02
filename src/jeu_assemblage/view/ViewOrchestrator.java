package jeu_assemblage.view;

import javax.swing.*;

import jeu_assemblage.ecouteur.EcouteurModele;
import jeu_assemblage.modele.game.Game;
import jeu_assemblage.modele.game.Transfert;

import java.awt.*;

public class ViewOrchestrator implements EcouteurModele {

    public static Transfert TRANSFERT_TOOL = new Transfert();
    
    private JFrame frame;
    private Game game;
    private GridPanel gridPanel;

    private JPanel scorePanel;
    private JLabel scoreLabel;

    public ViewOrchestrator(Game game) {
        this.game = game;
        this.game.ajoutEcouteur(this);

        this.gridPanel = new GridPanel(this.game);

        this.scoreLabel = new JLabel("Votre score : "+ game.getScore() + (this.game.getGameSetting().isActionUnlimited() ? "" : " Nombre de transferts restants : " + (this.game.getGameSetting().getNBAction() - this.game.getNbCurrentAction())));

        this.frame = new JFrame("Jeu Assemblage");
        this.frame.setLayout(new BorderLayout());
        
        this.scorePanel = new JPanel();
        scorePanel.add(this.scoreLabel);
        this.frame.add(scorePanel, BorderLayout.NORTH);
        this.frame.add(this.gridPanel, BorderLayout.CENTER);
        this.frame.add(new FinishButton(game), BorderLayout.SOUTH);

        //Param fenetre
        int frameWidth  = this.game.getGameSetting().getHauteur() * 50;
        int frameHeight = this.game.getGameSetting().getLargeur() * 50;
        this.frame.setSize(frameHeight, frameWidth);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    @Override
    public void modeleMAJ(Object src) {
        //System.out.println("Need MAJ !");
        if (!this.game.isOver()) {
            this.scoreLabel.setText("Votre score : "+ game.getScore() + (this.game.getGameSetting().isActionUnlimited() ? "" : " Nombre de transferts restants : " + (this.game.getGameSetting().getNBAction() - this.game.getNbCurrentAction())));
            this.scorePanel.revalidate();
            this.scorePanel.repaint();
            this.gridPanel.update(frame);
        } else {
            this.frame.dispose();
            new ScoreFinalFrame(this.game); 
        }
    }

}
