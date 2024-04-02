package jeu_assemblage.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import jeu_assemblage.modele.game.Cell;
import jeu_assemblage.modele.game.Game;
import jeu_assemblage.modele.game.Transfert;
import piece_puzzle.*;

public class GridCell extends JButton implements ActionListener {
    
    private Game game;
    private Color color;
    private Cell cell;

    public GridCell(Game game, Cell cell) {
        super("");
        this.game = game;
        this.cell = cell;
        this.color = (cell == null ? Color.WHITE : GridCell.choseColor(cell.getPiece()));

        int cellWidth = 50;
        this.setPreferredSize(new Dimension(cellWidth, cellWidth));

        Border lineborder = BorderFactory.createLineBorder(Color.black, 1); 
        this.setBorder(lineborder);

        this.setBackground(this.color);

        this.addActionListener(this);
        this.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                PiecePuzzle piece = cell.getPiece();
                if (piece != null) {
                    int angleBefore = piece.getAngle();
                    int angleAfter;
                    boolean possible;
                    if (notches < 0) { // gauche
                        angleAfter = (angleBefore == 0 ? 270 : angleBefore - 90);
                        possible = game.canPieceBeRotated(piece, angleAfter);
                        if (possible) {
                            game.rotatePiece(piece, angleAfter);
                        }
                    } else { //droite
                        angleAfter = (angleBefore + 90)%360;
                        possible = game.canPieceBeRotated(piece, angleAfter);
                        if (possible) {
                            game.rotatePiece(piece, angleAfter);
                        }                        
                    }
                }
                
            }
            
        });
    }
    public GridCell(Game game) {
        this(game, null);
    }

    public Color getColor() {
        return color;
    }

    public Cell getCell() {
        return cell;
    }

    public static Color choseColor(PiecePuzzle piece) {
        if      (piece instanceof OPiece) return Color.RED;
        else if (piece instanceof IPiece) return Color.BLUE;
        else if (piece instanceof LPiece) return Color.GREEN;
        else if (piece instanceof TPiece) return Color.ORANGE;
        else if (piece instanceof UPiece) return Color.MAGENTA;
        else return Color.WHITE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Si le jeu est fini, il ne sert à rien de jouer
        if (!game.isOver()) {

            //on recupere l'outil de tranfert
            Transfert transfert = ViewOrchestrator.TRANSFERT_TOOL;

            //Si on n'a pas de coordonnées de départ, on définit la coordonnées de cette case comme point de départ
            if (transfert.getCoordStart() == null) {
                //Si il n'y a pas de piece, il n'y a rien à déplacer.
                if (this.game.getJAGrid().getCell(this.cell.getCoordonnee()).getPiece() != null) {
                    transfert.setCoordStart(this.cell.getCoordonnee());
                    transfert.setPiece(this.cell.getPiece());
                    this.setText("X");
                }
            }
            //Si on n'a pas de coordonnées de fin, on définit la coordonnées de cette case comme point de fin
            else if (transfert.getCoordEnd() == null) {
                transfert.setCoordEnd(this.cell.getCoordonnee());
                if (transfert.isFilled() && this.game.isValid(transfert)) {
                    this.game.execute(transfert);
                } 
                transfert.clear();
            } else {
                transfert.clear();
            }
        }
    }

}
