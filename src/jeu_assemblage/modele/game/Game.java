package jeu_assemblage.modele.game;

import java.util.Map;
import java.util.Set;

import jeu_assemblage.ecouteur.ModeleEcoutable;
import jeu_assemblage.modele.player.Player;
import piece_puzzle.Coordonnee;
import piece_puzzle.PiecePuzzle;

public interface Game extends ModeleEcoutable {
    
    public Player getPlayer();

    public JAGrid getJAGrid();

    public GameSetting getGameSetting();

    public Map<PiecePuzzle, Coordonnee> getPositionOfPieces();

    public int getScore();
    
    public int getNbCurrentAction();

    public Set<Transfert> getValidTransferts();

    public void finish();

    public void setMethScore(boolean value);

    //public Set<Transfert> getValidMoves();

    public boolean canPieceBeRotated(PiecePuzzle piece, int angle);

    public void rotatePiece(PiecePuzzle piece, int angle);

    public boolean isValid(Transfert move);

    public void execute(Transfert move);

    public boolean isOver();

    public void updateScore();

    public Game copy();

}
