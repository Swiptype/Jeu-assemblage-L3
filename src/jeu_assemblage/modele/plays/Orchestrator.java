package jeu_assemblage.modele.plays;

import jeu_assemblage.modele.game.Game;
import jeu_assemblage.modele.game.Transfert;

public class Orchestrator {
    
    private Game jeu;

    public Orchestrator(Game jeu) {
        this.jeu = jeu;
    }

    public void play() {
    	while (!this.jeu.isOver()) {
            Transfert move = jeu.getPlayer().chooseMove(jeu);
            if (jeu.isValid(move)) {
                jeu.execute(move);
            }
        }    	
    	
        System.out.println("Vous avez fini avec un score de " + jeu.getScore());
    }
}
