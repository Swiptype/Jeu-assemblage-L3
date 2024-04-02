package piece_puzzle;

import java.util.HashSet;

/**
 * Classe représentant une pièce de puzzle de type "I".
 * Cette pièce est rectangulaire et peut être orientée à 0° et 90°.
 */
public class IPiece extends AbstractPiecePuzzle {

    /**
     * Constructeur de la classe IPiece.
     *
     * @param hauteur La hauteur de la pièce de type "I".
     * @param largeur La largeur de la pièce de type "I".
     */
    public IPiece(int hauteur, int largeur) {
        super(hauteur, largeur);

        // Initialise les ensembles de coordonnées pour les angles 0° et 90°.
        this.anglesSetOfCoords.put(0, new HashSet<>());
        this.anglesSetOfCoords.put(90, new HashSet<>());

        // Crée les ensembles de coordonnées pour chaque angle.
        this.createAnglesSetOfCoords();

        // Initialise l'ensemble de coordonnées actuel avec l'angle actuel.
        this.currentSetOfCoords = this.anglesSetOfCoords.get(this.angle);
    }

    @Override
    protected void createAnglesSetOfCoords() {
        // Crée les ensembles de coordonnées pour les angles 0° et 90°.
        int moitieHauteur = this.hauteur / 2;
        int resteHauteur = this.hauteur % 2;

        int moitieLargeur = this.largeur / 2;
        int resteLargeur = this.largeur % 2;

        for (int i = moitieHauteur * -1; i < (moitieHauteur + resteHauteur); i++) {
            for (int j = moitieLargeur * -1; j < (moitieLargeur + resteLargeur); j++) {
                this.anglesSetOfCoords.get(0).add(new Coordonnee(i, j));
            }
        }

        for (int i = moitieLargeur * -1; i < (moitieLargeur + resteLargeur); i++) {
            for (int j = moitieHauteur * -1; j < (moitieHauteur + resteHauteur); j++) {
                this.anglesSetOfCoords.get(90).add(new Coordonnee(i, j));
            }
        }
    }

    @Override
    public boolean rotation(int rAngle) {
        // Effectue la rotation uniquement pour les angles 0° et 90°.
        rAngle = rAngle % 180;
        if (rAngle == 90) {
            int tmp = this.largeur;
            this.largeur = this.hauteur;
            this.hauteur = tmp;
            this.angle = rAngle;
            this.currentSetOfCoords = this.anglesSetOfCoords.get(this.angle);
            return true;
        } else if (rAngle == 0) {
            this.angle = rAngle;
            this.currentSetOfCoords = this.anglesSetOfCoords.get(this.angle);
            return true;
        }
        return false;
    }
}
