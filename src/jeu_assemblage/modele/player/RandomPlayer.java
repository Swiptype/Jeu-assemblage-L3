package jeu_assemblage.modele.player;

import java.util.List;
import java.util.Random;

import jeu_assemblage.modele.game.Game;
import jeu_assemblage.modele.game.Transfert;
import piece_puzzle.Coordonnee;
import piece_puzzle.PiecePuzzle;

public class RandomPlayer implements Player {
    
    private Random random;

    public RandomPlayer(Random random) {
        this.random = random;
    }

    private PiecePuzzle getRandomPieces(List<PiecePuzzle> pieces) {
        int randomInt = this.random.nextInt(pieces.size());
        return pieces.get(randomInt);
    }

    @Override
    public Transfert chooseMove(Game jeu) {
        //System.out.println(jeu.getJAGrid());

        int height = jeu.getGameSetting().getHauteur();
        int width = jeu.getGameSetting().getLargeur();
        PiecePuzzle piece = this.getRandomPieces(jeu.getGameSetting().getPieces());

        int x1 = random.nextInt(height);
        int y1 = random.nextInt(width);
        int x2 = random.nextInt(height);
        int y2 = random.nextInt(width);

        Transfert transfert = new Transfert();
        transfert.setPiece(piece);
        transfert.setCoordStart(new Coordonnee(x1, y1));
        transfert.setCoordEnd(new Coordonnee(x2, y2));

        if (jeu.isValid(transfert)) {
            return transfert;
        } else {
            return chooseMove(jeu);
        }
    }



}
