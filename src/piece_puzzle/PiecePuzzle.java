package piece_puzzle;

import java.util.Set;

/**
 * Interface représentant une pièce de puzzle générique.
 * Les pièces sont caractérisées par leur capacité à effectuer des rotations et à fournir des informations sur leurs dimensions et leurs positions.
 */
public interface PiecePuzzle {

    /**
     * Effectue une rotation de la pièce selon l'angle spécifié.
     *
     * @param angle L'angle de rotation de la pièce.
     * @return true si la rotation est réussie, false sinon.
     */
    public boolean rotation(int angle);

    /**
     * Effectue une rotation vers la gauche de la pièce.
     */
    public void rotateLeft();

    /**
     * Effectue une rotation vers la droite de la pièce.
     */
    public void rotateRight();

    /**
     * Obtient la largeur de la pièce.
     *
     * @return La largeur de la pièce.
     */
    public int getLargeur();

    /**
     * Obtient la hauteur de la pièce.
     *
     * @return La hauteur de la pièce.
     */
    public int getHauteur();

    /**
     * Obtient l'angle actuel de la pièce.
     *
     * @return L'angle actuel de la pièce.
     */
    public int getAngle();

    /**
     * Vérifie si la case à la position spécifiée est vide.
     *
     * @param x La coordonnée x de la case.
     * @param y La coordonnée y de la case.
     * @return true si la case est vide, false sinon.
     */
    public boolean isCaseEmpty(int x, int y);

    /**
     * Obtient l'ensemble de coordonnées de la pièce sans son centre.
     *
     * @return L'ensemble de coordonnées de la pièce sans son centre.
     */
    public Set<Coordonnee> getSetOfCoordsWithoutCenter();

    /**
     * Obtient l'ensemble de coordonnées de la pièce avec un centre spécifié.
     *
     * @param center Le centre des coordonnées.
     * @return L'ensemble de coordonnées de la pièce avec le centre spécifié.
     */
    public Set<Coordonnee> getSetOfCoordsWithCenter(Coordonnee center);
}
