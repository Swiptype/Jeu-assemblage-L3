package jeu_assemblage.modele.game;

import piece_puzzle.Coordonnee;
import piece_puzzle.PiecePuzzle;

public class Cell {
    
    private PiecePuzzle piece;
    private Coordonnee coord;

    public Cell(Coordonnee coord, PiecePuzzle piece) {
        this.coord = coord;
        this.piece = piece;
    }
    public Cell(Coordonnee coord) {
        this(coord,null);
    }

    public Coordonnee getCoordonnee() {
        return this.coord;
    }

    public PiecePuzzle getPiece() {
        return piece;
    }

    public void setPiece(PiecePuzzle piece) {
        this.piece = piece;
    }

}
