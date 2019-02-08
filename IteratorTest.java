package amazons;
import org.junit.Test;
import static org.junit.Assert.*;
import ucb.junit.textui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import static amazons.Square.*;
import static amazons.Move.*;
import static amazons.Piece.*;

/** Junit tests for our Board iterators.
 *  @author
 */
public class IteratorTest {

    /** Run the JUnit tests in this package. */
    public static void main(String[] ignored) {
        textui.runClasses(IteratorTest.class);
    }

    /** Tests reachableFromIterator to make sure it returns all reachable
     *  Squares. This method may need to be changed based on
     *   your implementation. */
    @Test
    public void testReachableFrom() {
        Board b = new Board();
        buildBoard(b, reachableFromTestBoard);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(5, 4), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            boolean bool = reachableFromTestSquares.contains(s);
            assertTrue(bool);
            numSquares += 1;
            squares.add(s);
        }
        System.out.println(numSquares);
        System.out.println(reachableFromTestSquares.size());
        assertEquals(reachableFromTestSquares.size(), numSquares);
        assertEquals(reachableFromTestSquares.size(), squares.size());
    }

    @Test
    public void testReachableFrom1() {
        Board b = new Board();
        buildBoard(b, reachableFromTestBoard1);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(5, 5), null);
        String collector = "";
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            boolean bool = reachableFromTestSquares1.contains(s);
            assertTrue(bool);
            numSquares += 1;
            squares.add(s);
        }
        System.out.println(collector.length());
        System.out.println(collector);
        assertEquals(reachableFromTestSquares1.size(), numSquares);
        assertEquals(reachableFromTestSquares1.size(), squares.size());
    }

    /** Tests legalMovesIterator to make sure it returns all legal Moves.
     *  This method needs to be finished and may need to be changed
     *  based on your implementation. */
    @Test
    public void testLegalMoves() {
        Board b = new Board();
        buildBoard(b,legalMoveTestBoard2); // FIXME
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            System.out.println(m);
            assertTrue(legalMove2TestSquares.contains(m));
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(7, numMoves); // FIXME
        assertEquals(legalMove2TestSquares, moves); // FIXME
        assertEquals(7, moves.size()); // 8 total possible positions for first move and 6 options for spear position

    }




    @Test
    public void testNumberOfLegalMoves1() {
        Board b = new Board();
        buildBoard(b,legalMoveTestBoard1); // FIXME
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            numMoves += 1;
            moves.add(m);
        }
//        assertEquals(48, numMoves); // FIXME
        assertEquals(48, numMoves); // 8 total possible positions for first move and 6 options for spear position

    }

    @Test
    public void testNumberOfLegalMoves2() {
        Board b = new Board();
        buildBoard(b,legalMoveTestBoard3); // FIXME
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            numMoves += 1;
            moves.add(m);
        }
//        assertEquals(48, numMoves); // FIXME
        assertEquals(0, moves.size()); // 8 total possible positions for first move and 6 options for spear position

    }

    @Test
    public void testNumberOfLegalMoves3() {
        Board b = new Board();
        buildBoard(b,legalMoveTestBoard_Init); // FIXME
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.BLACK);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            numMoves += 1;
            moves.add(m);
        }
//        assertEquals(48, numMoves); // FIXME
        assertEquals(2176, moves.size()); // 8 total possible positions for first move and 6 options for spear position
    }

    @Test
    public void testNumberOfLegalMoves4() {
        Board b = new Board();
        buildBoard(b,legalMoveTestBoard4); // FIXME
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(moves,legalmoves4);
//        assertEquals(48, numMoves); // FIXME
        assertEquals(10, moves.size()); // 8 total possible positions for first move and 6 options for spear position
    }

    private void buildBoard(Board b, Piece[][] target) {
        for (int col = 0; col < Board.SIZE; col++) {
            for (int row = Board.SIZE - 1; row >= 0; row--) {
                Piece piece = target[Board.SIZE - row - 1][col];
                b.put(piece, Square.sq(col, row));
            }
        }
        System.out.println(b);
    }

    static final Piece E = Piece.EMPTY;

    static final Piece W = Piece.WHITE;

    static final Piece B = Piece.BLACK;

    static final Piece S = Piece.SPEAR;

    static final Piece[][] reachableFromTestBoard =
            {
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, W, W },
                    { E, E, E, E, E, E, E, S, E, S },
                    { E, E, E, S, S, S, S, E, E, S },
                    { E, E, E, S, E, E, E, E, B, E },
                    { E, E, E, S, E, W, E, E, B, E },
                    { E, E, E, S, S, S, B, W, B, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
            };


    static final Set<Square> reachableFromTestSquares =
            new HashSet<>(Arrays.asList(
                    Square.sq(5, 5),
                    Square.sq(4, 5),
                    Square.sq(4, 4),
                    Square.sq(6, 4),
                    Square.sq(7, 4),
                    Square.sq(6, 5),
                    Square.sq(7, 6),
                    Square.sq(8, 7)));

    static final Piece[][] reachableFromTestBoard1 =
            {
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, W, W },
                    { E, E, E, E, E, E, E, S, E, S },
                    { E, E, E, S, S, S, S, E, E, S },
                    { E, E, E, S, E, W, E, E, B, E },
                    { E, E, E, S, E, E, E, E, B, E },
                    { E, E, E, S, S, S, B, W, B, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
            };


    static final Set<Square> reachableFromTestSquares1 =
            new HashSet<>(Arrays.asList(
                    sq(4,5),
                    sq(4,4),
                    sq(5,4),
                    sq(6,4),
                    sq(6,5),
                    sq(7,5)
                    ));

    static final Piece[][] legalMoveTestBoard1 =
            {
                    { W, W, W, W, W, W, W, W, W, W },
                    { W, W, W, W, W, W, W, W, W, W },
                    { W, W, W, W, W, W, W, W, W, W },
                    { W, W, W, S, S, S, S, S, W, W },
                    { B, B, B, S, E, E, E, S, B, B },
                    { B, B, B, S, E, W, E, S, B, B },
                    { B, B, B, S, E, E, E, S, B, B },
                    { W, W, W, S, S, S, S, S, W, W },
                    { W, W, W, W, W, W, W, W, W, W },
                    { W, W, W, W, W, W, W, W, W, W },
            };
    static final Piece[][] legalMoveTestBoard2 =
            {
                    { W, W, W, W, W, W, W, W, W, W },
                    { W, W, W, W, W, W, W, W, W, W },
                    { W, W, W, W, W, W, W, W, W, W },
                    { W, W, W, S, S, S, S, S, W, W },
                    { B, B, B, S, S, E, E, S, B, B },
                    { B, B, B, S, E, W, S, S, B, B },
                    { B, B, B, S, S, S, S, W, B, B },
                    { W, W, W, S, S, S, S, S, W, W },
                    { W, W, W, W, W, W, W, W, W, W },
                    { W, W, W, W, W, W, W, W, W, W },
            };

    static final Piece[][] legalMoveTestBoard3 =
            {
                    { W, W, W, W, W, W, W, W, W, W },
                    { S, S, S, S, S, S, S, S, S, S },
                    { E, E, E, E, E, E, E, E, E, E },
                    { S, S, S, S, S, S, S, S, S, S },
                    { B, B, B, S, S, E, E, S, B, B },
                    { S, S, S, S, S, S, S, S, S, S },
                    { B, B, B, S, S, S, S, S, B, B },
                    { E, E, E, E, E, E, E, E, E, E },
                    { S, S, S, S, S, S, S, S, S, S },
                    { W, W, W, W, W, W, W, W, W, W },
            };


    static final Piece[][] legalMoveTestBoard_Init =
            {
                    { E, E, E, B, E, E, B, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { B, E, E, E, E, E, E, E, E, B },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { W, E, E, E, E, E, E, E, E, W },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, W, E, E, W, E, E, E },
            };


    static final Piece[][] legalMoveTestBoard4 =
            {       { E, E, E, E, E, S, S, S, S, W },
                    { E, E, E, E, E, S, W, S, S, E },
                    { E, E, E, E, E, S, S, S, S, S },
                    { E, E, E, S, S, S, S, E, E, S },
                    { E, E, E, S, E, E, S, E, B, E },
                    { E, E, E, S, E, W, S, S, B, E },
                    { E, E, E, S, S, S, B, W, B, E },
                    { E, E, E, E, E, E, S, S, S, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
            };

    static Set<Move> legalmoves4 =
            new HashSet<>(Arrays.asList(
                    Move.mv("f5-f6(f5)"),
                    Move.mv("f5-f6(e6)"),
                    Move.mv("f5-f6(e5)"),
                    Move.mv("f5-e5(f5)"),
                    Move.mv("f5-e5(f6)"),
                    Move.mv("f5-e5(e6)"),
                    Move.mv("f5-e6(f5)"),
                    Move.mv("f5-e6(e5)"),
                    Move.mv("f5-e6(f6)"),
                    Move.mv("j10-j9(j10)")
            ));
    /**
     * W = (5,4)
     * Sq 1  = (4,4)
     * Sq 2  = (5,5)
     * Sq 3 =  (6,5)
     * Possible move combination(from-to(sp)):
     * W-1-2
     * W-1-W
     * W-2-1
     * W-2-W
     * W-2-3
     * W-3-2
     * W-3-W
     * */
    static final Set<Move> legalMove2TestSquares =
            new HashSet<Move>(Arrays.asList(
                   mv(sq(5,4),sq(4,4),sq(5,5)), //lol
                    mv(sq(5,4),sq(4,4),sq(5,4)),
                    mv(sq(5,4),sq(5,5),sq(4,4)), //lol
                    mv(sq(5,4),sq(5,5),sq(5,4)),
                    mv(sq(5,4),sq(5,5),sq(6,5)), //lol
                    mv(sq(5,4),sq(6,5),sq(5,5)), //lol
                    mv(sq(5,4),sq(6,5),sq(5,4))
                    ));


}

