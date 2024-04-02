package jeu_assemblage;

import jeu_assemblage.modele.game.Game;
import jeu_assemblage.modele.game.GameSetting;
import jeu_assemblage.modele.game.JeuAssemblage;
import jeu_assemblage.modele.player.*;
import jeu_assemblage.modele.plays.Orchestrator;

public class MainScan {

    public static void main(String[] args) {
        
        //Player player = new HumanWithScan("Alexis", new Scanner(System.in));
        //Player player = new RandomPlayer(new Random(100));
        Player player = new ComputerPlayer();
        Game game = new JeuAssemblage(player, GameSetting.getGameSetting1_15x25_20Actions());
        

        new Orchestrator(game).play();

    }
    
}
