package piece_puzzle;

/**
 * Classe représentant des coordonnées (x, y) dans un espace bidimensionnel.
 */
public class Coordonnee {

    /**
     * La coordonnée x.
     */
    protected int x;

    /**
     * La coordonnée y.
     */
    protected int y;

    /**
     * Constructeur de la classe Coordonnee.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     */
    public Coordonnee(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne la coordonnée x.
     *
     * @return La coordonnée x.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Retourne la coordonnée y.
     *
     * @return La coordonnée y.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Obtient les coordonnées résultantes après une translation spécifiée.
     *
     * @param xT Le déplacement en x.
     * @param yT Le déplacement en y.
     * @return Les nouvelles coordonnées après la translation.
     */
    public Coordonnee getResultTranslate(int xT, int yT) {
        return new Coordonnee(this.x + xT, this.y + yT);
    }

    /**
     * Obtient les coordonnées résultantes après une translation spécifiée.
     *
     * @param cT Les coordonnées de translation.
     * @return Les nouvelles coordonnées après la translation.
     */
    public Coordonnee getResultTranslate(Coordonnee cT) {
        return this.getResultTranslate(cT.getX(), cT.getY());
    }

    @Override
    public int hashCode() {
        // Implémentation basique, peut être ajustée si nécessaire.
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Coordonnee other = (Coordonnee) obj;
        return x == other.getX() && y == other.getY();
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }
}
