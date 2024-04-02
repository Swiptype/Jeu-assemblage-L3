package jeu_assemblage.modele.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import piece_puzzle.*;

public class JAGrid {

    private int hauteur, largeur;

    private List<List<Cell>> grid;

    private Set<Coordonnee> coordWithoutPiece;
    private Set<Coordonnee> coordWithPiece;

    /**
     * Constructeur de la classe JAGrid.
     * Initialise une grille de jeu avec les dimensions spécifiées.
     * @param hauteur La hauteur de la grille.
     * @param largeur La largeur de la grille.
     */
    public JAGrid(int hauteur, int largeur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.grid = new ArrayList<>();

        this.coordWithoutPiece = new HashSet<>();
        this.coordWithPiece = new HashSet<>();

        this.initGrid();

    }

    /**
     * Initialise la grille de jeu en créant les cellules pour chaque coordonnée (i,j).
     * Cette méthode est appelée lors de la construction de l'objet JAGrid.
     */
    private void initGrid() {
        for (int i = 0; i < this.hauteur; i++) {
            List<Cell> rowList = new ArrayList<>();
            for (int j = 0; j < this.largeur; j++) {
                Coordonnee coord = new Coordonnee(i, j);
                rowList.add(new Cell(coord));
                //
                this.coordWithoutPiece.add(coord);
            }
            grid.add(rowList);
        }
    }

    /**
     * Initialise et place les pièces du jeu sur la grille.
     * @param game L'instance du jeu contenant les paramètres et les pièces à placer.
     * @throws IllegalArgumentException Si les pièces ne peuvent pas être placées.
     */
    public void initAddPiece(Game game) {
        GameSetting gameSetting = game.getGameSetting();

        int x = GameSetting.MAX_HAUTEUR_PIECE/2;
        int y = gameSetting.getPieces().get(0).getLargeur()/2 
              + gameSetting.getPieces().get(0).getLargeur()%2;

        for (int iPiece = 0; iPiece < gameSetting.getPieces().size(); iPiece++) {
            /* Si on a atteint le maximum pour cette ligne, on passe à la suivante */
            if (y + gameSetting.getPieces().get(iPiece).getLargeur() >= GameSetting.MAX_LARGEUR_GRID) {
                y = gameSetting.getPieces().get(iPiece).getLargeur()/2 
                  + gameSetting.getPieces().get(iPiece).getLargeur()%2; 
                x += GameSetting.MAX_HAUTEUR_PIECE;
                /* Si il n'y a pas assez de place */
                if (x >= GameSetting.MAX_HAUTEUR_GRID) {
                    throw new IllegalArgumentException("Les pièces ne peuvent pas être placées !");
                }
            } 
            
            /* On crée la coordonnée pour la piece à placer */
            Coordonnee coord = new Coordonnee(x, y);

            /* On récupère la pièce à placer */
            PiecePuzzle piece = gameSetting.getPieces().get(iPiece);
 
            /* Si elle peut être placée, on la place */
            Coordonnee testCoordY = new Coordonnee(x, y-1);
            if (y > 2 && piece.getLargeur()>3 && this.canPieceBePlaced(piece, testCoordY)) {
                this.placePiece(piece, testCoordY);
                game.getPositionOfPieces().put(piece, testCoordY);
                y--;
            }
            else if (this.canPieceBePlaced(piece, coord)) {
                this.placePiece(piece, coord);
                game.getPositionOfPieces().put(piece, coord);
            }

            /* On prépare pour la nouvelle piece */
            int minWidth = piece.getLargeur()/2 
                         + piece.getLargeur()%2;
            int minStartWidth = (
                iPiece+1 < gameSetting.getPieces().size() 
                    ? gameSetting.getPieces().get(iPiece+1).getLargeur()/2 //+ gameSetting.getPieces().get(iPiece+1).getLargeur()%2
                    : 1
            );
            y += minWidth + minStartWidth;
        }
    }

    /**
     * Vérifie si une pièce peut être placée à un emplacement spécifique sur la grille.
     * @param piece La pièce à placer.
     * @param center Le centre de coordonnées où placer la pièce.
     * @return true si la pièce peut être placée, false sinon.
     */
    public boolean canPieceBePlaced(PiecePuzzle piece, Coordonnee center) {
        //Correspond à toutes les coordonnées nécessaires pour la piece
        Set<Coordonnee> coords = piece.getSetOfCoordsWithCenter(center); 
        
        //On parcours chaque coordonnée et on verifie qu'elle soit soit vide
        for (Coordonnee coordonnee : coords) {
            if (!this.isCellEmpty(coordonnee) || this.offGrid(coordonnee)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retire une pièce de la grille.
     * @param piece La pièce à retirer.
     * @param center Le centre de coordonnées de la pièce à retirer.
     */
    public void placePiece(PiecePuzzle piece, Coordonnee center) {
        if (!this.canPieceBePlaced(piece, center)) {
            throw new IllegalArgumentException("La pièce ne pouvait pas être placée");
        }

        //On change toutes les coordonnées representees par la piece et on garde les coordonnees avec une piece ou sans
        Set<Coordonnee> coords = piece.getSetOfCoordsWithCenter(center);
        for (Coordonnee coordonnee : coords) {
            this.getCell(coordonnee).setPiece(piece);
            //
            this.coordWithoutPiece.remove(coordonnee);
            this.coordWithPiece.add(coordonnee);
        }
    }

    /**
     * Vérifie si une coordonnée est hors de la grille.
     * @param x La coordonnée x à vérifier.
     * @param y La coordonnée y à vérifier.
     * @return true si la coordonnée est hors de la grille, false sinon.
     */
    public void retirePiece(PiecePuzzle piece, Coordonnee center) {
        Set<Coordonnee> coordsBefore = piece.getSetOfCoordsWithCenter(center);
       // System.out.println(coordsBefore.size());
        for (Coordonnee coordonnee : coordsBefore) {
            this.getCell(coordonnee).setPiece(null);
            //
            this.coordWithPiece.remove(coordonnee);
            this.coordWithoutPiece.add(coordonnee);
        }
    }


/**
 * Vérifie si une coordonnée est hors de la grille.
 * @param coord La coordonnée à vérifier.
 * @return true si la coordonnée est hors de la grille, false sinon.
 */
    public boolean offGrid(int x, int y) {
        if (0 > x || x >= this.hauteur) return true;
        if (0 > y || y >= this.largeur) return true;
        return false;
    }
    
   
    public boolean offGrid(Coordonnee coord) {
        return this.offGrid(coord.getX(),coord.getY());
    }

    /**
     * Récupère la cellule située à un emplacement spécifique sur la grille.
     * @param row La rangée de la cellule.
     * @param col La colonne de la cellule.
     * @return L'objet Cell correspondant à l'emplacement spécifié.
     */
    public Cell getCell(int row, int col) {
        return grid.get(row).get(col);
    }
    /**
     * Récupère la cellule située à une coordonnée spécifique.
     * @param coord La coordonnée de la cellule à récupérer.
     * @return L'objet Cell à la coordonnée spécifiée.
     */
    public Cell getCell(Coordonnee coord) {
        return this.getCell(coord.getX(), coord.getY());
    }
    /**
     * Vérifie si une cellule spécifiée par ses coordonnées est vide.
     * @param row La rangée de la cellule.
     * @param col La colonne de la cellule.
     * @return true si la cellule est vide, false sinon.
     */
    public boolean isCellEmpty(int row, int col) {
        return grid.get(row).get(col).getPiece() == null;
    }
    /**
     * Vérifie si une cellule à une coordonnée donnée est vide.
     * @param coord La coordonnée de la cellule à vérifier.
     * @return true si la cellule est vide, false sinon.
     */
    public boolean isCellEmpty(Coordonnee coord) {
        return this.isCellEmpty(coord.getX(), coord.getY());
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    /**
     * Retourne l'ensemble des coordonnées sans pièce sur la grille.
     * @return Un ensemble de coordonnées sans pièce.
     */
    public Set<Coordonnee> getCoordWithoutPiece() {
        return coordWithoutPiece;
    }

    public Set<Coordonnee> getCoordWithPiece() {
        return coordWithPiece;
    }

    @Override
    public String toString() {
        String representationString = "   ";
        for (int i = 0; i < this.getLargeur(); i++) {
            if (i/10 == 0) {
                representationString += "0";
            }
            representationString += i + " ";
        }
        representationString += "\n";
        int cpt = 0;
        for (List<Cell> rowList : grid) {
            String row = "";
            if (cpt/10 == 0) {
                row += "0";
            }
            row += cpt + " ";
            for (Cell cell : rowList) {
                row += (cell.getPiece()==null ? "-  " : "o  ");
            }
            row += "\n"; representationString += row;
            cpt++;
        }
        return representationString;
    }
    
}
