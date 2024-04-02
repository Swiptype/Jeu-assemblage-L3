package piece_puzzle;

import java.util.HashSet;

/**
 * Classe représentant une pièce de puzzle de type "T".
 * Cette pièce est en forme de T et peut être orientée à 0°, 90°, 180° et 270°.
 */
public class TPiece extends AbstractPiecePuzzle {

    /**
     * Constructeur de la classe TPiece.
     *
     * @param hauteur La hauteur de la pièce de type "T".
     * @param largeur La largeur de la pièce de type "T".
     */
    public TPiece(int hauteur, int largeur) {
        super(hauteur, largeur);

        // Initialise les ensembles de coordonnées pour les angles 0°, 90°, 180° et 270°.
        this.anglesSetOfCoords.put(0, new HashSet<>());
        this.anglesSetOfCoords.put(90, new HashSet<>());
        this.anglesSetOfCoords.put(180, new HashSet<>());
        this.anglesSetOfCoords.put(270, new HashSet<>());

        // Crée les ensembles de coordonnées pour chaque angle.
        this.createAnglesSetOfCoords();

        // Initialise l'ensemble de coordonnées actuel avec l'angle actuel.
        this.currentSetOfCoords = this.anglesSetOfCoords.get(this.angle);
    }

    @Override
    protected void createAnglesSetOfCoords() {
        // Crée les ensembles de coordonnées pour les angles 0°, 90°, 180° et 270°.
        int moitieHauteur = this.hauteur / 2;
        int resteHauteur = this.hauteur % 2;

        int moitieLargeur = this.largeur / 2;
        int resteLargeur = this.largeur % 2;

        for (int i = moitieHauteur * -1; i < (moitieHauteur + resteHauteur); i++) {
            for (int j = moitieLargeur * -1; j < (moitieLargeur + resteLargeur); j++) {

                if (j == 0) {
                    this.anglesSetOfCoords.get(0).add(new Coordonnee(i, j));
                    this.anglesSetOfCoords.get(180).add(new Coordonnee(i, j));
                    this.anglesSetOfCoords.get(90).add(new Coordonnee(j, i));
                    this.anglesSetOfCoords.get(270).add(new Coordonnee(j, i));
                }
                if (i == moitieHauteur * -1) {
                    this.anglesSetOfCoords.get(0).add(new Coordonnee(i, j));
                    this.anglesSetOfCoords.get(270).add(new Coordonnee(j, i));
                }
                if (i == (moitieHauteur + resteHauteur - 1)) {
                    this.anglesSetOfCoords.get(90).add(new Coordonnee(j, i));
                    this.anglesSetOfCoords.get(180).add(new Coordonnee(i, j));
                }
            }
        }
    }

    @Override
    public boolean rotation(int rAngle) {
        // Effectue la rotation uniquement pour les angles 0°, 90°, 180° et 270°.
        rAngle = rAngle % 360;
        if (rAngle == 90 || rAngle == 270) {
            int tmp = this.largeur;
            this.largeur = this.hauteur;
            this.hauteur = tmp;
            this.angle = rAngle;
            this.currentSetOfCoords = this.anglesSetOfCoords.get(this.angle);
            return true;
        } else if (rAngle == 0 || rAngle == 180) {
            this.angle = rAngle;
            this.currentSetOfCoords = this.anglesSetOfCoords.get(this.angle);
            return true;
        }
        return false;
    }

}
