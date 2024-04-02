package jeu_assemblage.modele.player;

import jeu_assemblage.modele.game.Game;
import jeu_assemblage.modele.game.Transfert;

public interface Player {

    /**
     * La méthode permet de récupérer le mouvement que veut effectuer le joueur
     * @param jeu représente le jeu joué
     * @return un mouvement
     */
    public Transfert chooseMove(Game jeu);

}