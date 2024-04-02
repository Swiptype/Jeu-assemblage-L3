package jeu_assemblage.modele.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import jeu_assemblage.ecouteur.AbstractModeleEcoutable;
import jeu_assemblage.modele.player.Player;
import piece_puzzle.Coordonnee;
import piece_puzzle.PiecePuzzle;

public class JeuAssemblage extends AbstractModeleEcoutable implements Game{

    private Player player;
    private GameSetting gameSetting;
    private JAGrid grid;

    private Map<PiecePuzzle, Coordonnee> positionOfPieces;
    private Map<PiecePuzzle, Coordonnee> undo;

    private boolean finish;

    private int score;
    private boolean MAXSQUARE;
    private int nbCurrentAction;

    /**
     * Construit une nouvelle instance du jeu JeuAssemblage.
     *
     * @param player Le joueur participant au jeu.
     * @param gameSetting Les paramètres du jeu, incluant les dimensions de la grille et les règles du jeu.
     */    
    public JeuAssemblage(Player player, GameSetting gameSetting) {
        this.player = player;
        this.gameSetting = gameSetting;
        this.positionOfPieces = new HashMap<>();
        this.grid = new JAGrid(gameSetting.getHauteur(), gameSetting.getLargeur());
        this.score = 0;
        this.finish = false;
        this.nbCurrentAction = 0;

        this.grid.initAddPiece(this);   
        this.updateScore();
        this.MAXSQUARE = true;
    }
    
    /**
     * Récupère le joueur actuel.
     *
     * @return Le joueur qui joue la partie.
     */

    @Override
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Récupère la grille utilisée dans le jeu.
     *
     * @return L'objet JAGrid représentant la grille du jeu.
     */

    @Override
    public JAGrid getJAGrid() {
        return this.grid;
    }

    /**
     * Récupère les paramètres du jeu.
     *
     * @return L'objet GameSetting contenant les paramètres de la partie en cours.
     */

    @Override
    public GameSetting getGameSetting() {
        return this.gameSetting;
    }
    
    /**
     * Récupère les positions de toutes les pièces du puzzle sur la grille.
     *
     * @return Une map de PiecePuzzle à leur Coordonnee respective sur la grille.
     */

    @Override
    public Map<PiecePuzzle, Coordonnee> getPositionOfPieces() {
        return this.positionOfPieces;
    }

    /**
     * Récupère le score actuel du jeu.
     *
     * @return Le score en tant qu'entier.
     */

    @Override
    public int getScore() {
        return this.score;
    }
    
    public boolean getMethScore() {
        return this.MAXSQUARE;
    }
    public void setMethScore(boolean value) {
        this.MAXSQUARE = value;
    }
    
    /**
     * Récupère le nombre d'actions effectuées jusqu'à présent dans le jeu.
     *
     * @return Le nombre d'actions en tant qu'entier.
     */

    @Override
    public int getNbCurrentAction() {
        return this.nbCurrentAction;
    }

    /**
     * Récupère toutes les options de transfert de pièces valides disponibles dans l'état actuel du jeu.
     *
     * @return Un ensemble d'objets Transfert représentant les mouvements valides.
     */

    @Override
    public Set<Transfert> getValidTransferts() {

        Set<Transfert> validTransferts = new HashSet<>();

        //On recupere toutes les coordonnees avec et sans piece
        Set<Coordonnee> coordWithoutPiece = this.grid.getCoordWithoutPiece();
        Set<Coordonnee> coordWithPiece = this.grid.getCoordWithPiece();
        
        //Pour chaque coordonnee avec une piece, on regarde si on peut placer la piece dans une coordonnee sans piece
        for (Coordonnee coordStart : coordWithPiece) {
            PiecePuzzle piece = this.grid.getCell(coordStart).getPiece();
            if(piece != null) {
            int previousAngle = piece.getAngle();

            for (Coordonnee coordEnd : coordWithoutPiece) {

                Transfert transfert = new Transfert();
                transfert.setPiece(piece);
                transfert.setCoordStart(coordStart);
                transfert.setCoordEnd(coordEnd);

                if (this.isValid(transfert)) {
                
                    validTransferts.add(transfert);
                }

            }
            piece.rotation(previousAngle);
        }
        }
        return validTransferts;
    }
    
    public Set<Transfert> getOptimalTransferts() {

        Set<Transfert> validTransferts = new HashSet<>();

        Set<Coordonnee> coordWithoutPiece = this.grid.getCoordWithoutPiece();
        Set<Coordonnee> coordWithPiece = this.grid.getCoordWithPiece();
        
        for (Coordonnee coordStart : coordWithPiece) {
        	
            PiecePuzzle piece = this.grid.getCell(coordStart).getPiece();
            if(piece != null) {
            int previousAngle = piece.getAngle();
            for (Coordonnee coordEnd : coordWithoutPiece) {
                Transfert transfert = new Transfert();
                transfert.setPiece(piece);
                transfert.setCoordStart(coordStart);
                transfert.setCoordEnd(coordEnd);
                if (this.isValid(transfert)) {
                	if(this.getScore() < this.executeS(transfert).getScore())
                    validTransferts.add(transfert);
                }

            }
            piece.rotation(previousAngle);
        }
        }
        return validTransferts;
    }

    /**
     * Marque la fin du jeu et déclenche toutes les actions ou mises à jour finales.
     */
    @Override
    public void finish() {
        this.finish = true;
        this.fireChange();
    }

    /**
     * Met à jour le score en fonction de l'état actuel du jeu.
     * deux configurations sont disponible pour le calcul du score soit on cherche le plus grands carré ou comme dans le sujet le plus petit rectanble (approche naive pour la recherche du plus petit)
     */
    @Override
    public void updateScore() {
    	if(!this.MAXSQUARE) {
        int minArea = Integer.MAX_VALUE;

        for (int i = 0; i < this.gameSetting.getHauteur(); i++) {
            for (int j = 0; j < this.gameSetting.getLargeur(); j++) {
                for (int k = i; k < this.gameSetting.getHauteur(); k++) {
                    for (int l = j; l < this.gameSetting.getLargeur(); l++) {
                        if (isRectangleValid(i, j, k, l)) {
                            int area = (k - i + 1) * (l - j + 1);
                            minArea = Math.min(minArea, area);
                        }
                    }
                }
            }
        }

        this.score = (minArea == Integer.MAX_VALUE) ? 0 : minArea;
    	}
    	
    	else {
    		int[][] matrice = new int[this.gameSetting.getHauteur()][this.gameSetting.getLargeur()];
    	    // Initialisation de la première ligne et de la première colonne
    	    for (int i = 0; i < this.gameSetting.getHauteur(); i++) {
    	        matrice[i][0] = (this.grid.getCell(i, 0).getPiece() == null ? 0 : 1);
    	    }
    	    for (int j = 0; j < this.gameSetting.getLargeur(); j++) {
    	        matrice[0][j] = (this.grid.getCell(0, j).getPiece() == null ? 0 : 1);
    	    }
    	    // Calcul des hauteurs pour chaque cellule
    	    for (int i = 1; i < this.gameSetting.getHauteur(); i++) {
    	        for (int j = 1; j < this.gameSetting.getLargeur(); j++) {
    	            if (this.grid.getCell(i,j).getPiece() != null) {
    	                matrice[i][j] = matrice[i - 1][j] + 1;
    	            } else {
    	                matrice[i][j] = 0;
    	            }
    	        }
    	    }
    	    int maxArea = 0;
    	    // Calcul de l'aire du plus grand rectangle pour chaque ligne
    	    for (int i = 0; i < this.gameSetting.getHauteur(); i++) {
    	        maxArea = Math.max(maxArea, getMaxAreaInRow(matrice[i]));
    	    }

    	    this.score = maxArea; 
    	}
    	
    }

    private boolean isRectangleValid(int dbtligne, int dbtCol, int finligne, int finCol) {
        if (!isRectangleFilled(dbtligne, dbtCol, finligne, finCol)) {
            return false;
        }
        if (!areBordersEmpty(dbtligne, dbtCol, finligne, finCol)) {
            return false;
        }
        return true;
    }

    private boolean isRectangleFilled(int dbtligne, int dbtCol, int finligne, int finCol) {
        for (int i = dbtligne; i <= finligne; i++) {
            for (int j = dbtCol; j <= finCol; j++) {
                if (this.grid.getCell(i, j).getPiece() == null ) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean areBordersEmpty(int dbtligne, int dbtCol, int finligne, int finCol) {
        // Vérifier les cellules au-dessus et en dessous du rectangle
        for (int j = dbtCol - 1; j <= finCol + 1; j++) {
            if (j >= 0 && j < this.gameSetting.getLargeur()) {
                if (dbtligne > 0 && this.grid.getCell(dbtligne - 1, j).getPiece() != null) {
                    return false;
                }
                if (finligne < this.gameSetting.getHauteur() - 1 && this.grid.getCell(finligne + 1, j).getPiece() != null) {
                    return false;
                }
            }
        }

        // Vérifier les cellules à gauche et à droite du rectangle
        for (int i = dbtligne - 1; i <= finligne + 1; i++) {
            if (i >= 0 && i < this.gameSetting.getHauteur()) {
                if (dbtCol > 0 && this.grid.getCell(i, dbtCol - 1).getPiece() != null) {
                    return false;
                }
                if (finCol < this.gameSetting.getLargeur() - 1 && this.grid.getCell(i, finCol + 1).getPiece() != null) {
                    return false;
                }
            }
        }

        return true;
    }
    
    private int getMaxAreaInRow(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int i = 0;

        while (i < heights.length) {
            // Si la pile est vide ou que la barre actuelle est plus haute que le sommet de la pile
            if (stack.isEmpty() || heights[i] >= heights[stack.peek()]) {
                stack.push(i);
                i++;
            } else {
                // Calculer l'aire avec la hauteur de la barre au sommet de la pile
                int top = stack.pop();
                int area = heights[top] * (stack.isEmpty() ? i : i - stack.peek() - 1);
                maxArea = Math.max(maxArea, area);
            }
        }

        // Calculer l'aire pour les barres restantes dans la pile
        while (!stack.isEmpty()) {
            int top = stack.pop();
            int area = heights[top] * (stack.isEmpty() ? i : i - stack.peek() - 1);
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }

    /**
     * Vérifie si un mouvement donné est valide dans l'état actuel du jeu.
     *
     * @param move L'objet Transfert représentant le mouvement à valider.
     * @return true si le mouvement est valide, false sinon.
     */

    @Override
    public boolean isValid(Transfert move) {
        if (move == null || !move.isFilled()) return false;

        if (this.grid.offGrid(move.getCoordStart()) || this.grid.offGrid(move.getCoordEnd())) {
            return false;
        }

        if (!this.getPositionOfPieces().containsKey(move.getPiece())) {
            return false;
        }

        Coordonnee previousCoord = this.getPositionOfPieces().get(move.getPiece());
        Coordonnee wantedCoord = move.getCoordEnd();
        wantedCoord = wantedCoord.getResultTranslate(previousCoord.getX() - move.getCoordStart().getX(), previousCoord.getY() - move.getCoordStart().getY());

        Set<Coordonnee> coords = move.getPiece().getSetOfCoordsWithCenter(wantedCoord);
        for (Coordonnee coordonnee : coords) {
            if (this.grid.offGrid(coordonnee) || !(this.grid.isCellEmpty(coordonnee) || this.grid.getCell(coordonnee).getPiece() == move.getPiece())) {
             //   System.out.println("JeuAssemblage isValid("+ move +")");
                //System.out.println(previousCoord);
               // System.out.println(coords);
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si une pièce du puzzle peut être tournée à un angle spécifié.
     *
     * @param piece La pièce du puzzle à tourner.
     * @param angle L'angle de rotation en degrés.
     * @return true si la rotation est possible, false sinon.
     */

    @Override
    public boolean canPieceBeRotated(PiecePuzzle piece, int angle) {
        if (angle == piece.getAngle()) {
            return true;
        }
        if (piece == null || angle%90 != 0) {
            return false;
        }
        int previousAngle = piece.getAngle();
        Coordonnee centerPiece = this.positionOfPieces.get(piece);

        piece.rotation(angle);
        Set<Coordonnee> coords = piece.getSetOfCoordsWithCenter(centerPiece);
        piece.rotation(previousAngle);

        for (Coordonnee coordonnee : coords) {
            if (this.grid.getCell(coordonnee).getPiece() != null && this.grid.getCell(coordonnee).getPiece() != piece) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tourne une pièce du puzzle à un angle spécifié.
     *
     * @param piece La pièce du puzzle à tourner.
     * @param angle L'angle de rotation en degrés.
     * @throws IllegalArgumentException si la rotation n'est pas possible.
     */

    @Override
    public void rotatePiece(PiecePuzzle piece, int angle) {
        if (!this.canPieceBeRotated(piece, angle)) {
            throw new IllegalArgumentException("La rotation n'était pas possible !");
        }
        Coordonnee centerPiece = this.getPositionOfPieces().get(piece);
        
        Set<Coordonnee> coords = piece.getSetOfCoordsWithCenter(centerPiece);
        for (Coordonnee coordonnee : coords) {
            this.grid.getCell(coordonnee).setPiece(null);
        }

        piece.rotation(angle);
        coords = piece.getSetOfCoordsWithCenter(centerPiece);
        for (Coordonnee coordonnee : coords) {
            this.grid.getCell(coordonnee).setPiece(piece);
        }
        this.fireChange();
    }

    /**
     * Exécute un mouvement de transfert de pièce donné.
     *
     * @param move L'objet Transfert représentant le mouvement à exécuter.
     * @throws IllegalArgumentException si le mouvement est invalide.
     */
    @Override
    public void execute(Transfert move) {
        if (!this.isValid(move)) {
            throw new IllegalArgumentException("Le transfert était impossible !");
        }
        this.undo = this.positionOfPieces;
        Coordonnee previousCoord = this.positionOfPieces.get(move.getPiece());
        this.grid.retirePiece(move.getPiece(), previousCoord);

        Coordonnee wantedCoord = move.getCoordEnd();
        wantedCoord = wantedCoord.getResultTranslate(previousCoord.getX() - move.getCoordStart().getX(), previousCoord.getY() - move.getCoordStart().getY());

        this.grid.placePiece(move.getPiece(), wantedCoord);
        this.getPositionOfPieces().put(move.getPiece(), wantedCoord);

        //AfterEffect
        this.nbCurrentAction++;
        if (!this.gameSetting.isActionUnlimited() && this.gameSetting.getNBAction() == this.nbCurrentAction) this.finish = true; 
        this.updateScore();
        this.fireChange();
    }
    /**
     * Annule le dernier mouvement 
     *
     * 
     * @throws IllegalStateException si pas eu de dernier mouvement .
     */
    public void undo() {
        if (this.undo == null || this.undo.isEmpty()) {
            throw new IllegalStateException("Aucun mouvement à annuler.");
        }

        // Restaurer les positions des pièces
        for (Map.Entry<PiecePuzzle, Coordonnee> entry : this.undo.entrySet()) {
            PiecePuzzle piece = entry.getKey();
            Coordonnee coord = entry.getValue();

            // Retire la pièce de sa position actuelle
            Coordonnee currentCoord = this.positionOfPieces.get(piece);
            if (currentCoord != null) {
                this.grid.retirePiece(piece, currentCoord);
            }

            // Place la pièce à sa position précédente
            this.grid.placePiece(piece, coord);
            this.positionOfPieces.put(piece, coord);
        }

        // Mise à jour du jeu après l'annulation
        this.nbCurrentAction--;
        this.updateScore();
        this.fireChange();

        // Réinitialiser la sauvegarde de l'état précédent
        this.undo = new HashMap<>();
    }

    /**
     * Vérifie si le jeu est terminé.
     *
     * @return true si le jeu est fini, false sinon.
     */
    
    @Override
    public boolean isOver() {
        return this.finish;
    }
    
    public Game executeS(Transfert mvt) {
        GameSetting newGameSetting = new GameSetting(this.gameSetting.getHauteur(), this.gameSetting.getLargeur());
        for (PiecePuzzle piece : this.gameSetting.getPieces()) {
            newGameSetting.addNewPiecePuzzle(piece);
        }
        Game newInst = new JeuAssemblage(player, gameSetting);
        for (int i = 0; i < this.gameSetting.getHauteur(); i++) {
            for (int j = 0; j < this.gameSetting.getLargeur(); j++) {
                newInst.getJAGrid().getCell(i, j).setPiece(this.getJAGrid().getCell(i, j).getPiece());
            }
        }
        newInst.getPositionOfPieces().clear();
        newInst.getPositionOfPieces().putAll(this.positionOfPieces);
        newInst.execute(mvt);
        return newInst;

    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JeuAssemblage other = (JeuAssemblage) obj;

        if (positionOfPieces.size() != other.positionOfPieces.size()) return false;

        for (Map.Entry<PiecePuzzle, Coordonnee> entry : positionOfPieces.entrySet()) {
            PiecePuzzle piece = entry.getKey();
            Coordonnee expectedCoord = entry.getValue();
            Coordonnee actualCoord = other.positionOfPieces.get(piece);

            if (!Objects.equals(expectedCoord, actualCoord)) return false;
        }

        return true;
    }

    @Override
    public Game copy() {
        GameSetting newGameSetting = new GameSetting(this.gameSetting.getHauteur(), this.gameSetting.getLargeur());
        for (PiecePuzzle piece : this.gameSetting.getPieces()) {
            newGameSetting.addNewPiecePuzzle(piece);
        }
        Game newInst = new JeuAssemblage(player, gameSetting);
        for (int i = 0; i < this.gameSetting.getHauteur(); i++) {
            for (int j = 0; j < this.gameSetting.getLargeur(); j++) {
                newInst.getJAGrid().getCell(i, j).setPiece(this.getJAGrid().getCell(i, j).getPiece());
            }
        }
        newInst.getPositionOfPieces().clear();
        newInst.getPositionOfPieces().putAll(this.positionOfPieces);
        return newInst;
    }
    
    
    
  
    
}
