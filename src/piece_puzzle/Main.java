package piece_puzzle;

/**
 * La classe Main est le point d'entrée principal pour le programme de puzzle.
 * Elle initialise une grille, crée des pièces de puzzle de type "O" avec différentes rotations,
 * les ajoute à la grille, et affiche la grille mise à jour à chaque étape.
 */
public class Main {

    /**
     * Initialise une grille vide avec les dimensions spécifiées.
     *
     * @param h La hauteur de la grille.
     * @param l La largeur de la grille.
     * @return Une grille vide sous forme de tableau de chaînes de caractères.
     */
    public static String[][] initGrid(int h, int l) {
        String[][] grid = new String[h][l];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                grid[i][j] = "  ";
            }
        }
        return grid;
    }

    /**
     * Affiche le contenu d'une grille.
     *
     * @param grid La grille à afficher.
     */
    public static void showGrid(String[][] grid) {
        StringBuilder textToShow = new StringBuilder();
        for (String[] strings : grid) {
            for (String character : strings) {
                textToShow.append(character);
            }
            textToShow.append("\n");
        }

        System.out.println(textToShow);
    }

    /**
     * Ajoute les coordonnées d'une pièce de puzzle à une grille à une position spécifiée.
     *
     * @param grid  La grille à mettre à jour.
     * @param piece La pièce de puzzle à ajouter à la grille.
     * @param atPosition La position à laquelle ajouter la pièce.
     */
    public static void addPieceToGrid(String[][] grid, PiecePuzzle piece, Coordonnee atPosition) {
        for (Coordonnee coordPiece : piece.getSetOfCoordsWithCenter(atPosition)) {
            grid[coordPiece.getX()][coordPiece.getY()] = "1 ";
        }
    }

    /**
     * Le point d'entrée principal pour le programme.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans ce programme).
     */
    public static void main(String[] args) {
        int h = 8;
        int l = 8;

        int hG = h * 2;
        int lG = l * 2;

        // Initialise une grille affichable.
        String[][] grilleShow = initGrid(hG, lG);

        // Crée quatre pièces de puzzle de type "O" avec différentes rotations.
        PiecePuzzle piece1 = new OPiece(4, 5);
        PiecePuzzle piece2 = new OPiece(4, 5);
        PiecePuzzle piece3 = new OPiece(4, 5);
        PiecePuzzle piece4 = new OPiece(4, 5);

        // Rotation de la première pièce et ajout à la grille.
        piece1.rotation(0);
        addPieceToGrid(grilleShow, piece1, new Coordonnee(4, 4));
        System.out.println(piece1.getSetOfCoordsWithoutCenter());
        System.out.println(piece1.getSetOfCoordsWithoutCenter().size());
        showGrid(grilleShow);

        // Rotation de la deuxième pièce et ajout à la grille.
        piece2.rotation(90);
        addPieceToGrid(grilleShow, piece2, new Coordonnee(4, 10));
        System.out.println(piece2.getSetOfCoordsWithoutCenter());
        System.out.println(piece2.getSetOfCoordsWithoutCenter().size());
        showGrid(grilleShow);

        // Rotation de la troisième pièce et ajout à la grille.
        piece3.rotation(180);
        addPieceToGrid(grilleShow, piece3, new Coordonnee(10, 4));
        System.out.println(piece3.getSetOfCoordsWithoutCenter());
        System.out.println(piece3.getSetOfCoordsWithoutCenter().size());
        showGrid(grilleShow);

        // Rotation de la quatrième pièce et ajout à la grille.
        piece4.rotation(270);
        addPieceToGrid(grilleShow, piece4, new Coordonnee(10, 10));
        System.out.println(piece4.getSetOfCoordsWithoutCenter());
        System.out.println(piece4.getSetOfCoordsWithoutCenter().size());
        showGrid(grilleShow);
    }
}
