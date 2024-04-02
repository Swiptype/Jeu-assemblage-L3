package jeu_assemblage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import jeu_assemblage.modele.game.Game;
import jeu_assemblage.modele.game.GameSetting;
import jeu_assemblage.modele.game.JeuAssemblage;
import jeu_assemblage.modele.player.RandomPlayer;

public class MenuFrame implements ActionListener {
    
    private JFrame frame;

    private GameSetting gameSetting;

    private JTextField seedField;

    private JRadioButton radButGS1;
    private JRadioButton radButGS2;
    private JRadioButton radButGSRandom;

    public MenuFrame() {
        this.frame = new JFrame("Choix du GameSetting !");

        this.gameSetting = GameSetting.getRandomGameSetting15x25(new Random());

        this.frame.setLayout(new BorderLayout());

        this.radButGS1 = new JRadioButton("GameSetting 1");
        this.radButGS2 = new JRadioButton("GameSetting 2");
        this.radButGSRandom = new JRadioButton("Random GameSetting");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.radButGS1);
        buttonGroup.add(this.radButGS2);
        buttonGroup.add(this.radButGSRandom);

        this.seedField = new JTextField();

        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(new GridLayout(5,1));
        choicePanel.add(this.radButGS1);
        choicePanel.add(this.radButGS2);
        choicePanel.add(this.radButGSRandom);
        choicePanel.add(new JLabel("Seed : "));
        choicePanel.add(this.seedField);
        

        JButton validButton = new JButton("Valider");
        validButton.addActionListener(this);

        this.frame.add(choicePanel, BorderLayout.CENTER);
        this.frame.add(validButton, BorderLayout.SOUTH);

        this.frame.setSize(350, 200);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.radButGSRandom.isSelected() && this.seedField.getText().matches("[+-]?\\d*(\\.\\d+)?") && this.seedField.getText() != "1000") {
            System.out.println("Seed : "+ this.seedField.getText());
            this.gameSetting = GameSetting.getRandomGameSetting15x25(new Random(Integer.valueOf(this.seedField.getText())));
        } else if (this.radButGS1.isSelected()) {
            this.gameSetting = GameSetting.getGameSetting1_15x25();
        } else if (this.radButGS2.isSelected()) {
            this.gameSetting = GameSetting.getGameSetting2_15x25();
        }
        this.frame.dispose();
        Game game = new JeuAssemblage(new RandomPlayer(new Random()), this.gameSetting);
        new ViewOrchestrator(game);
    }

    public GameSetting getGameSetting() {
        return this.gameSetting;
    }

    public static void main(String[] args) {
        new MenuFrame();
        
    }

}
