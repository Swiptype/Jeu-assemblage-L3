package jeu_assemblage.modele.game;

import piece_puzzle.Coordonnee;
import piece_puzzle.PiecePuzzle;

public class Transfert {
    
    private PiecePuzzle piece;
    private Coordonnee coordStart;
    private Coordonnee coordEnd;

    public Transfert() {
        this.piece      = null;
        this.coordStart = null;
        this.coordEnd   = null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Transfert other = (Transfert) obj;
        if (piece != null ? !piece.equals(other.piece) : other.piece != null) {
            return false;
        }
        return coordEnd != null ? coordEnd.equals(other.coordEnd) : other.coordEnd == null;
    }

    public void clear() {
        this.piece      = null;
        this.coordStart = null;
        this.coordEnd   = null;
    }

    /**
     * Vérifie si l'objet Transfert a toutes ses données (pièce, coordonnée de départ et d'arrivée) remplies.
     *
     * @return true si toutes les données sont présentes, false sinon.
     */
    public boolean isFilled() {
        if (this.piece == null || this.coordStart == null || this.coordEnd == null) {
            return false;
        }
        return true;
    }

    /* Setter */
    public void setPiece(PiecePuzzle piece) {
        this.piece = piece;
    }

    public void setCoordStart(Coordonnee coordStart) {
        this.coordStart = coordStart;
    }

    public void setCoordEnd(Coordonnee coordEnd) {
        this.coordEnd = coordEnd;
    }

    /* Getter */
    public PiecePuzzle getPiece() {
        return piece;
    }

    public Coordonnee getCoordStart() {
        return coordStart;
    }

    public Coordonnee getCoordEnd() {
        return coordEnd;
    }
    
    

    @Override
    public String toString() {
        return  "Transfert[piece:"+ (this.piece != null ? this.piece : "") +
                ", Start"+ (this.coordStart != null ? this.coordStart : "[]") +
                ", End"+ (this.coordEnd != null ? this.coordEnd : "[]") +"]";
    }

    public Coordonnee getWantedCoord(Coordonnee centerPiece) {
        int diffX = centerPiece.getX() - this.coordStart.getX();
        int diffY = centerPiece.getY() - this.coordStart.getY();
        int x = this.coordEnd.getX() + diffX;
        int y = this.coordEnd.getY() + diffY;
        return new Coordonnee(x, y);
    }
}
