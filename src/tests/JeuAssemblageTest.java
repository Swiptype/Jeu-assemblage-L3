import junit.*;
import org.junit.*;
import junit.framework.*;

import java.util.Scanner;
import jeu_assemblage.modele.game.GameSetting;
import jeu_assemblage.modele.game.JeuAssemblage;
import jeu_assemblage.modele.game.Transfert;
import jeu_assemblage.modele.player.HumanWithScan;
import jeu_assemblage.modele.player.Player;
import piece_puzzle.*;

class JeuAssemblageTest {

    private JeuAssemblage jeu;

    @BeforeEach
    void setUp() {
        Player player = new HumanWithScan("gael",new Scanner(System.in)); 
        GameSetting gameSetting = new GameSetting(15, 25); 
        PiecePuzzle piece = new OPiece(5,4);
        gameSetting.addNewPiecePuzzle(piece);
        jeu = new JeuAssemblage(player, gameSetting);
    }

    @Test
    void testInitialization() {
        assertNotNull(jeu.getPlayer());
        assertNotNull(jeu.getJAGrid());
        assertNotNull(jeu.getGameSetting());
        assertNotNull(jeu.getPositionOfPieces());
        assertEquals(1, jeu.getScore());
        assertFalse(jeu.isOver());
    }

    @Test
    void testExecuteValidMove() {
    	PiecePuzzle piece = jeu.getGameSetting().getPieces().get(0);
        Coordonnee startCoord = new Coordonnee(0, 0); 
        Coordonnee endCoord = new Coordonnee(1, 1); 
        Transfert move = new Transfert();
        move.setPiece(piece);
        move.setCoordStart(startCoord);
        move.setCoordEnd(endCoord);

        //assertDoesNotThrow(() -> jeu.execute(move));
        jeu.execute(move);
        System.out.println(jeu.getPositionOfPieces());
        //assertTrue(jeu.getPositionOfPieces().containsKey(new Coordonnee(2,2)));
        assertEquals(new Coordonnee(3,3), jeu.getPositionOfPieces().get(piece));
        assertTrue(jeu.getJAGrid().getCell(endCoord).getPiece() == piece);
        assertTrue(jeu.getScore() > 0);
    }

    @Test
    void testExecuteInvalidMove() {
    	PiecePuzzle piece = jeu.getGameSetting().getPieces().get(0);
        Coordonnee startCoord = new Coordonnee(0, 0); 
        Coordonnee endCoord = new Coordonnee(10, 10); 
        Transfert move = new Transfert();
        move.setPiece(piece);
        move.setCoordStart(startCoord);
        move.setCoordEnd(endCoord);

      //  assertThrows(IllegalArgumentException.class, () -> jeu.execute(move));

        assertTrue(jeu.getPositionOfPieces().containsKey(piece));
        assertTrue(jeu.getScore() == 1); 
    }


}
