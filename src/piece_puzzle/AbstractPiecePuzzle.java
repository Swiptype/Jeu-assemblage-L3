package piece_puzzle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe abstraite représentant une pièce de puzzle générique.
 * Cette classe définit les caractéristiques communes et les opérations de base
 * pour les pièces de puzzle.
 */
public abstract class AbstractPiecePuzzle implements PiecePuzzle {

    /* Variables */

    /**
     * La hauteur de la pièce de puzzle.
     */
    protected int hauteur;

    /**
     * La largeur de la pièce de puzzle.
     */
    protected int largeur;

    /**
     * L'angle de rotation actuel de la pièce de puzzle.
     */
    protected int angle;

    /**
     * Map contenant les ensembles de coordonnées pour chaque angle possible de la pièce.
     */
    protected Map<Integer, Set<Coordonnee>> anglesSetOfCoords;

    /**
     * L'ensemble de coordonnées actuel de la pièce.
     */
    protected Set<Coordonnee> currentSetOfCoords;

    /* Constructeur */

    /**
     * Constructeur de la classe AbstractPiecePuzzle.
     *
     * @param hauteur La hauteur de la pièce de puzzle.
     * @param largeur La largeur de la pièce de puzzle.
     */
    public AbstractPiecePuzzle(int hauteur, int largeur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.angle = 0;
        this.anglesSetOfCoords = new HashMap<>();
        this.currentSetOfCoords = new HashSet<>();
    }

    /* Méthode abstraite */

    /**
     * Méthode abstraite à implémenter dans les sous-classes pour créer les ensembles
     * de coordonnées pour chaque angle possible de la pièce.
     */
    protected abstract void createAnglesSetOfCoords();

    /**
     * Effectue la rotation de la pièce selon l'angle spécifié.
     *
     * @param angle L'angle de rotation.
     * @return true si la rotation a réussi, false sinon.
     */
    public abstract boolean rotation(int angle);

    /**
     * Effectue une rotation vers la gauche de la pièce.
     */
    public void rotateLeft() {
        if (this.angle == 0) {
            this.rotation(270);
        } else {
            this.rotation(this.angle - 90);
        }
    }

    /**
     * Effectue une rotation vers la droite de la pièce.
     */
    public void rotateRight() {
        this.rotation(this.angle + 90);
    }

    /**
     * Retourne la largeur de la pièce de puzzle.
     *
     * @return La largeur de la pièce.
     */
    public int getLargeur() {
        return this.largeur;
    }

    /**
     * Retourne la hauteur de la pièce de puzzle.
     *
     * @return La hauteur de la pièce.
     */
    public int getHauteur() {
        return this.hauteur;
    }

    /**
     * Retourne l'angle actuel de la pièce de puzzle.
     *
     * @return L'angle de rotation.
     */
    public int getAngle() {
        return this.angle;
    }

    /**
     * Vérifie si la case à la position spécifiée est vide.
     *
     * @param x La coordonnée x de la case.
     * @param y La coordonnée y de la case.
     * @return true si la case est vide, false sinon.
     * @throws IllegalArgumentException Si les paramètres x ou y sont invalides.
     */
    public boolean isCaseEmpty(int x, int y) {
        if (x > this.hauteur || y > this.largeur) {
            throw new IllegalArgumentException("Parametre invalide soit x > hauteur ou y > largeur");
        }
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Parametre invalide soit x < 0 ou y < 0");
        }

        int modifX = (this.largeur <= this.hauteur ? x - this.hauteur/2 : y - this.largeur/2);
        int modifY = (this.largeur <= this.hauteur ? y - this.largeur/2 : x - this.hauteur/2);

        return !this.currentSetOfCoords.contains(new Coordonnee(modifX,modifY));
    }

    /**
     * Retourne l'ensemble de coordonnées actuel de la pièce sans le centre.
     *
     * @return L'ensemble de coordonnées.
     */
    public Set<Coordonnee> getSetOfCoordsWithoutCenter() {
        return this.currentSetOfCoords;
    }

    /**
     * Retourne l'ensemble de coordonnées actuel de la pièce avec un centre spécifié.
     *
     * @param center Le centre des coordonnées.
     * @return L'ensemble de coordonnées avec le centre spécifié.
     */
    public Set<Coordonnee> getSetOfCoordsWithCenter(Coordonnee center) {
        Set<Coordonnee> coords = new HashSet<>();
        for (Coordonnee coordonnee : this.currentSetOfCoords) {
            int x = coordonnee.getX() + center.getX();
            int y = coordonnee.getY() + center.getY();
            coords.add(new Coordonnee(x,y));
        }
        return coords;
    }
    /*
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AbstractPiecePuzzle other = (AbstractPiecePuzzle) obj;

        if (angle != other.angle) {
            return false;
        }

        return (currentSetOfCoords != null ? currentSetOfCoords.equals(other.currentSetOfCoords) : other.currentSetOfCoords == null);
    }
    */
}
