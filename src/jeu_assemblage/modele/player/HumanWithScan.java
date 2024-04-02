package jeu_assemblage.modele.player;

import java.util.Scanner;

import jeu_assemblage.modele.game.Game;
import jeu_assemblage.modele.game.Transfert;
import piece_puzzle.Coordonnee;
import piece_puzzle.PiecePuzzle;

public class HumanWithScan implements Player{

    private String nom;
    private Scanner scanner;

    public HumanWithScan(String nom, Scanner scanner) {
        this.nom = nom;
        this.scanner = scanner;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public Transfert chooseMove(Game jeu) {
        System.out.println(jeu.getJAGrid());
        System.out.println("Votre score : " + jeu.getScore());
        System.out.println("Est ce que vous voulez en finir ici ? (0:Non)(1:Oui)");
        boolean finish = scanner.nextInt() == 1;
        if (finish) {
            jeu.finish();
            return null;
        }

        System.out.println("Veuillez choisir une piece !\nPour cela, donnez un x puis un y.");
        int xStart = scanner.nextInt();
        int yStart = scanner.nextInt();

        PiecePuzzle piece = jeu.getJAGrid().getCell(xStart, yStart).getPiece();

        if (piece == null) {
            System.out.println("Il n'y a pas de pièce à cet endroit !");
            return chooseMove(jeu);
        }

        System.out.println("Voulez vous tourner la pièce ou la déplacer ?\n(0:déplacer)(1:tourner)");
        boolean tourner = scanner.nextInt() == 1;

        if (tourner) {
            System.out.println("Veuillez choisir un angle !\n(0 - 90 - 180 - 270)");
            int angle = scanner.nextInt();
            angle = (angle%90 != 0 ? 0 : angle);
            if (jeu.canPieceBeRotated(piece, angle)) {
                jeu.rotatePiece(piece, angle);
            }
            return chooseMove(jeu);
        }

        Transfert transfert = new Transfert();
        transfert.setPiece(piece);
        transfert.setCoordStart(new Coordonnee(xStart, yStart));
        
        System.out.println("Veuillez choisir une destination !\nPour cela, donnez un x puis un y.");
        int xEnd = scanner.nextInt();
        int yEnd = scanner.nextInt();
        transfert.setCoordEnd(new Coordonnee(xEnd, yEnd));

        if (jeu.isValid(transfert)) {
            System.out.println("Etes vous sûr de "+ transfert +" ?\n(0:Non)(1:Oui)");
            boolean confirm = scanner.nextInt() == 1;
            if (confirm) {
                return transfert;
            } else {
                System.out.println("Transfert a été annulé");
                return chooseMove(jeu);
            }
        } else {
            System.out.println("Transfert n'est pas valide");
            return chooseMove(jeu);
        }
        
    }
    
}
