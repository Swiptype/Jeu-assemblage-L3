package jeu_assemblage.modele.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import jeu_assemblage.modele.player.HumanWithScan;
import jeu_assemblage.modele.player.Player;
import jeu_assemblage.view.ViewOrchestrator;
import piece_puzzle.IPiece;
import piece_puzzle.LPiece;
import piece_puzzle.OPiece;
import piece_puzzle.PiecePuzzle;
import piece_puzzle.TPiece;
import piece_puzzle.UPiece;

public class GameSetting {
    
    private int hauteur;
    private int largeur;

    public final static int MAX_LARGEUR_PIECE = 5;
    public final static int MAX_HAUTEUR_PIECE = 5;

    public final static int MAX_HAUTEUR_GRID = 15;
    public final static int MAX_LARGEUR_GRID = 25;

    private boolean unlimitedAction;
    private int nbAction;
    
    private List<PiecePuzzle> pieces;

    public GameSetting(int hauteur, int largeur, boolean unlimitedAction, int nbAction) {
        this.hauteur = hauteur;
        this.largeur = largeur;

        this.pieces = new ArrayList<>();

        this.unlimitedAction = unlimitedAction;
        this.nbAction = nbAction;
    }
    public GameSetting(int hauteur, int largeur) {
        this(hauteur, largeur, true, 0);
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public boolean isActionUnlimited() {
        return this.unlimitedAction;
    }

    public int getNBAction() {
        return this.nbAction;
    }

    public List<PiecePuzzle> getPieces() {
        return pieces;
    }

    public boolean canPieceBeAdded(PiecePuzzle piece) {
        if (piece.getHauteur() > MAX_HAUTEUR_PIECE) {
            return false;
        }
        if (piece.getLargeur() > MAX_LARGEUR_PIECE) {
            return false;
        }
        if (pieces.contains(piece)) {
            return false;
        }
        return true;
    }

    public void addNewPiecePuzzle(PiecePuzzle piece) {
        if (this.canPieceBeAdded(piece)) {
            this.pieces.add(piece);
        } else {
            throw new IllegalArgumentException("La piece ne remplit pas les contraintes");
        } 
    }


    public static GameSetting getGameSetting1_15x25() {
        GameSetting gameSetting = new GameSetting(15, 25);
        gameSetting.addNewPiecePuzzle(new OPiece(5, 4));
        gameSetting.addNewPiecePuzzle(new LPiece(3, 2));
        gameSetting.addNewPiecePuzzle(new IPiece(2, 1));
        gameSetting.addNewPiecePuzzle(new TPiece(3, 3));
        gameSetting.addNewPiecePuzzle(new IPiece(1, 1));
        gameSetting.addNewPiecePuzzle(new UPiece(4, 5));
        gameSetting.addNewPiecePuzzle(new UPiece(3, 3));
        gameSetting.addNewPiecePuzzle(new IPiece(3, 1));
        gameSetting.addNewPiecePuzzle(new LPiece(3, 2));
        gameSetting.addNewPiecePuzzle(new TPiece(2, 3));
        gameSetting.addNewPiecePuzzle(new IPiece(3, 1));
        gameSetting.addNewPiecePuzzle(new LPiece(2, 3));
        gameSetting.addNewPiecePuzzle(new LPiece(4, 2));
        gameSetting.addNewPiecePuzzle(new IPiece(3, 1));
        return gameSetting;
    }

    public static GameSetting getGameSetting1_15x25_20Actions() {
        GameSetting gameSetting = new GameSetting(15, 25, false, 20);
        gameSetting.addNewPiecePuzzle(new OPiece(5, 4));
        gameSetting.addNewPiecePuzzle(new LPiece(3, 2));
        gameSetting.addNewPiecePuzzle(new IPiece(2, 1));
        gameSetting.addNewPiecePuzzle(new TPiece(3, 3));
        gameSetting.addNewPiecePuzzle(new IPiece(1, 1));
        gameSetting.addNewPiecePuzzle(new UPiece(4, 5));
        gameSetting.addNewPiecePuzzle(new UPiece(3, 3));
        gameSetting.addNewPiecePuzzle(new IPiece(3, 1));
        gameSetting.addNewPiecePuzzle(new LPiece(3, 2));
        gameSetting.addNewPiecePuzzle(new TPiece(2, 3));
        gameSetting.addNewPiecePuzzle(new IPiece(3, 1));
        gameSetting.addNewPiecePuzzle(new LPiece(2, 3));
        gameSetting.addNewPiecePuzzle(new LPiece(4, 2));
        gameSetting.addNewPiecePuzzle(new IPiece(3, 1));
        return gameSetting;
    }

    public static GameSetting getGameSetting2_15x25() {
        GameSetting gameSetting = new GameSetting(15, 25);
        
        gameSetting.addNewPiecePuzzle(new UPiece(4, 5));
        gameSetting.addNewPiecePuzzle(new TPiece(2, 3));
        gameSetting.addNewPiecePuzzle(new UPiece(2, 3));
        gameSetting.addNewPiecePuzzle(new IPiece(3, 1));
        gameSetting.addNewPiecePuzzle(new LPiece(3, 5));
        gameSetting.addNewPiecePuzzle(new UPiece(5, 5));
        gameSetting.addNewPiecePuzzle(new TPiece(4, 3));
        gameSetting.addNewPiecePuzzle(new UPiece(4, 3));
        gameSetting.addNewPiecePuzzle(new OPiece(5, 4));
        gameSetting.addNewPiecePuzzle(new LPiece(3, 2));
        gameSetting.addNewPiecePuzzle(new IPiece(2, 1));
        gameSetting.addNewPiecePuzzle(new LPiece(2, 4));
        gameSetting.addNewPiecePuzzle(new IPiece(2, 1));
        gameSetting.addNewPiecePuzzle(new OPiece(4, 3));
        gameSetting.addNewPiecePuzzle(new IPiece(4, 1));
        gameSetting.addNewPiecePuzzle(new UPiece(5, 3));
        gameSetting.addNewPiecePuzzle(new TPiece(5, 3));
        gameSetting.addNewPiecePuzzle(new LPiece(5, 3));
        gameSetting.addNewPiecePuzzle(new LPiece(5, 3));
        gameSetting.addNewPiecePuzzle(new IPiece(4, 1));
        gameSetting.addNewPiecePuzzle(new OPiece(4, 3));
        gameSetting.addNewPiecePuzzle(new IPiece(5, 1));
        return gameSetting;
    }

    /* Creation de random gameSetting */

    private enum Piece {
        IPiece,
        TPiece,
        LPiece,
        UPiece,
        OPiece
    }

    private static Piece getRandomPiece(Random random) {
        List<Piece> enumPieces = new ArrayList<>(
            List.of(
                Piece.IPiece,
                Piece.TPiece,
                Piece.LPiece,
                Piece.UPiece,
                Piece.OPiece
            )
        );
        int randomIndex = random.nextInt(enumPieces.size());
        return enumPieces.get(randomIndex);
    }

    /**
     * Génère une configuration aléatoire de jeu pour un puzzle de taille 15x25.
     *
     * Cette méthode crée une nouvelle configuration de jeu en ajoutant aléatoirement des pièces
     * de puzzle dans un espace défini. Elle assure que les pièces ajoutées respectent les limites
     * de taille et de quantité prédéfinies pour chaque type de pièce.
     *
     * @param random Un objet Random pour générer des valeurs aléatoires.
     * @param nbAction Le nombre d'actions (mouvements de pièces) à intégrer dans la configuration.
     * @return Un objet GameSetting représentant la configuration aléatoire générée du puzzle.
     */
    public static GameSetting getRandomGameSetting15x25(Random random, int nbAction) {
        //Parametre de base du jeu
        GameSetting gameSetting = new GameSetting(
            MAX_HAUTEUR_GRID, 
            MAX_LARGEUR_GRID, 
            false, 
            MAX_HAUTEUR_GRID*MAX_LARGEUR_GRID
        );

        /* Cela correspond aux limites de la grille, ils limiteront la creation de piece */
        int limitRow = MAX_HAUTEUR_GRID/MAX_HAUTEUR_PIECE;
        int limitColomn = ((MAX_LARGEUR_GRID/MAX_LARGEUR_PIECE)-2) * MAX_LARGEUR_PIECE - 3;
        int limitPiece = limitRow * (MAX_LARGEUR_GRID/3); //nombre max de piece

        //On definit une limit d'apparition pour chaque piece
        Map<Piece,Double> limitForAPiece = new HashMap<>();
        limitForAPiece.put(Piece.IPiece, limitPiece*0.35);
        limitForAPiece.put(Piece.TPiece, limitPiece*0.2);
        limitForAPiece.put(Piece.LPiece, limitPiece*0.3);
        limitForAPiece.put(Piece.UPiece, limitPiece*0.1);
        limitForAPiece.put(Piece.OPiece, limitPiece*0.05);

        int randHeight;
        int width , randWidth;

        while (limitRow > 0) {
            //On choisit une piece aleatoire
            Piece ePiece = GameSetting.getRandomPiece(random);

            //Si la piece a atteint son nombre maximum d'apparition alors on recommence une nouvelle boucle
            if (limitForAPiece.get(ePiece) <= 0) {
                continue;
            }

            switch (ePiece) {

                case IPiece:
                    /* On récupère une hauteur aléatoire entre 1 et 5 */
                    randHeight = 1 + random.nextInt(MAX_HAUTEUR_PIECE);
                    /* On récupère une largeur de 1 */
                    width = 1;
                    /* On regarde si il y a assez de place ! */
                    if (width <= limitColomn) {
                        /* On crée et ajoute la nouvelle piece I */
                        gameSetting.addNewPiecePuzzle(new IPiece(randHeight, width));
                        /* On réduit ensuite le champs des possibles */
                        limitColomn -= width;
                        limitForAPiece.put(ePiece, limitForAPiece.get(ePiece)-1);
                    }
                    break;
            
                case TPiece :
                    /* On récupère une hauteur aléatoire entre 2 et 5 */
                    randHeight = 2 + random.nextInt(MAX_HAUTEUR_PIECE-1);
                    /* On récupère une largeur impaire aléatoire entre 3 et 5 */
                    randWidth  = 3 + random.nextInt(MAX_LARGEUR_PIECE-2);
                    if (randWidth%2 == 0) randWidth += 1;
                    /* On regarde si il y a assez de place ! */
                    if (randWidth <= limitColomn) {
                        /* On crée et ajoute la nouvelle piece T */
                        gameSetting.addNewPiecePuzzle(new TPiece(randHeight, randWidth));
                        /* On réduit ensuite le champs des possibles */
                        limitColomn -= randWidth;
                        limitForAPiece.put(ePiece, limitForAPiece.get(ePiece)-1);
                    }
                    break;

                case LPiece :
                    /* On récupère une hauteur aléatoire entre 2 et 5 */
                    randHeight = 2 + random.nextInt(MAX_HAUTEUR_PIECE-1);
                    /* On récupère une largeur aléatoire entre 2 et 5 */
                    randWidth  = 2 + random.nextInt(MAX_LARGEUR_PIECE-1);
                    /* On regarde si il y a assez de place ! */
                    if (randWidth <= limitColomn) {
                        /* On crée et ajoute la piece nouvelle L */
                        gameSetting.addNewPiecePuzzle(new LPiece(randHeight, randWidth));
                        /* On réduit ensuite le champs des possibles */
                        limitColomn -= randWidth;
                        limitForAPiece.put(ePiece, limitForAPiece.get(ePiece)-1);
                    }
                    break;

                case UPiece :
                    /* On récupère une hauteur aléatoire entre 2 et 5 */
                    randHeight = 2 + random.nextInt(MAX_HAUTEUR_PIECE-1);
                    /* On récupère une largeur aléatoire entre 3 et 5 */
                    randWidth  = 3 + random.nextInt(MAX_LARGEUR_PIECE-2);
                    /* On regarde si il y a assez de place ! */
                    if (randWidth <= limitColomn) {
                        /* On crée et ajoute la piece nouvelle U */
                        gameSetting.addNewPiecePuzzle(new UPiece(randHeight, randWidth));
                        /* On réduit ensuite le champs des possibles */
                        limitColomn -= randWidth;
                        limitForAPiece.put(ePiece, limitForAPiece.get(ePiece)-1);
                    }
                    break;
                default: //OPiece
                    /* On récupère une hauteur aléatoire entre 3 et 5 */
                    randHeight = 4 + random.nextInt(MAX_HAUTEUR_PIECE-3);
                    /* On récupère une largeur aléatoire entre 3 et 5 */
                    randWidth  = 3 + random.nextInt(MAX_LARGEUR_PIECE-2);
                    /* On regarde si il y a assez de place ! */
                    if (randWidth <= limitColomn) {
                        /* On crée et ajoute la piece nouvelle O */
                        gameSetting.addNewPiecePuzzle(new OPiece(randHeight, randWidth));
                        /* On réduit ensuite le champs des possibles */
                        limitColomn -= randWidth;
                        limitForAPiece.put(ePiece, limitForAPiece.get(ePiece)-1);
                    }
                    break;
            }

            if (limitColomn <= 0) {
                limitColomn = ((MAX_LARGEUR_GRID/MAX_LARGEUR_PIECE)-1) * MAX_LARGEUR_PIECE - 3;
                limitRow--;
            }

        }

        return gameSetting;
    }
    public static GameSetting getRandomGameSetting15x25(Random random) {
        return GameSetting.getRandomGameSetting15x25(random, Integer.MAX_VALUE);
    }


    public static void main(String[] args) {

        Random random = new Random();
        int seed = random.nextInt();
        //seed = 1871814381;
        System.out.println("Seed : "+ seed);
        random.setSeed(seed);

        Player player = new HumanWithScan("Alexis", new Scanner(System.in));
        Game game = new JeuAssemblage(player, GameSetting.getRandomGameSetting15x25(random));
        //Game game = new JeuAssemblage(player, GameSetting.getGameSetting2_15x25());
        new ViewOrchestrator(game);
    }

}
