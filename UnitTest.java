package amazons;

import org.junit.Test;

import static amazons.Piece.*;
import static amazons.Square.*;
import static amazons.Move.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ucb.junit.textui;

/** The suite of all JUnit tests for the amazons package.
 *  @author
 */
public class UnitTest {

    /** Run the JUnit tests in this package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    /** Tests basic correctness of put and get on the initialized board. */
    @Test
    public void testBasicPutGet() {
        Board b = new Board();
        b.put(BLACK, Square.sq(3, 5));
        assertEquals(b.get(3, 5), BLACK);
        b.put(WHITE, Square.sq(9, 9));
        assertEquals(b.get(9, 9), WHITE);
        b.put(EMPTY, Square.sq(3, 5));
        assertEquals(b.get(3, 5), EMPTY);
    }

    /** Tests proper identification of legal/illegal queen moves. */
    @Test
    public void testIsQueenMove() {
        assertFalse(Square.sq(1, 5).isQueenMove(Square.sq(1, 5)));
        assertFalse(Square.sq(1, 5).isQueenMove(Square.sq(2, 7)));
        assertFalse(Square.sq(0, 0).isQueenMove(Square.sq(5, 1)));
        //assertTrue(Square.sq(1, 1).isQueenMove(Square.sq(9, 9)));
        assertTrue(Square.sq(2, 7).isQueenMove(Square.sq(8, 7)));
        assertTrue(Square.sq(3, 0).isQueenMove(Square.sq(3, 4)));
        assertTrue(Square.sq(7, 9).isQueenMove(Square.sq(0, 2)));
    }

    @Test
    public void testCopyBoard() {
        Board source = new Board();
        System.out.println(source.toString());
        source.put(WHITE,sq(3,3));
        System.out.println(source.toString());
        source.put(WHITE,sq(4,4));
        System.out.println(source.toString());
        Board copy = new Board(source);
        System.out.println(copy.toString());
        //assertEquals(copy.get(3,3),WHITE);
        //System.out.println(copy.toString());
        //assertEquals(copy.get(4,4),WHITE);
        //System.out.println(copy.toString());
    }
    /** Tests toString for initial board state and a smiling board state. :) */
    @Test
    public void testToString() {
        Board b = new Board();
        assertEquals(INIT_BOARD_STATE, b.toString());
        makeSmile(b);
        assertEquals(SMILE, b.toString());
    }

    private void makeSmile(Board b) {
        b.put(EMPTY, Square.sq(0, 3));
        b.put(EMPTY, Square.sq(0, 6));
        b.put(EMPTY, Square.sq(9, 3));
        b.put(EMPTY, Square.sq(9, 6));
        b.put(EMPTY, Square.sq(3, 0));
        b.put(EMPTY, Square.sq(3, 9));
        b.put(EMPTY, Square.sq(6, 0));
        b.put(EMPTY, Square.sq(6, 9));
        for (int col = 1; col < 4; col += 1) {
            for (int row = 6; row < 9; row += 1) {
                b.put(SPEAR, Square.sq(col, row));
            }
        }
        b.put(EMPTY, Square.sq(2, 7));
        for (int col = 6; col < 9; col += 1) {
            for (int row = 6; row < 9; row += 1) {
                b.put(SPEAR, Square.sq(col, row));
            }
        }
        b.put(EMPTY, Square.sq(7, 7));
        for (int lip = 3; lip < 7; lip += 1) {
            b.put(WHITE, Square.sq(lip, 2));
        }
        b.put(WHITE, Square.sq(2, 3));
        b.put(WHITE, Square.sq(7, 3));
    }

    static final String INIT_BOARD_STATE =
                    "   - - - B - - B - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   B - - - - - - - - B\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   W - - - - - - - - W\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - W - - W - - -\n";

    static final String SMILE =
                    "   - - - - - - - - - -\n" +
                    "   - S S S - - S S S -\n" +
                    "   - S - S - - S - S -\n" +
                    "   - S S S - - S S S -\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - W - - - - W - -\n" +
                    "   - - - W W W W - - -\n" +
                    "   - - - - - - - - - -\n" +
                    "   - - - - - - - - - -\n";




    @Test
    public void testSmiilyBoard() {
        Board smile = new Board();
        buildBoard(smile,smilyBoard); // FIXME
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = smile.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(1942, moves.size()); // 8 total possible positions for first move and 6 options for spear position
    }

//    @Test
//    public void placePiece() {
//        Board playself = new Board();
//        playself.put()
//    }
    @Test
    public void testPlaySelfBoard() {
        Board smile = new Board();
        buildBoard(smile,playSelfBoard);
        smile.setTurn(BLACK);
        Move problemMove = mv("c8-c9(c7)");
        assertTrue(smile.isLegal(problemMove));
    }

    @Test
    public void testIsLegal() {
        Board b = new Board();
        b.put(WHITE,sq(3,3));
        System.out.println(b.toString());
        assertFalse(b.isLegal(sq(3,0),sq(3,3)));
        assertTrue(b.isLegal(sq(3,3),sq(3,5),sq(3,3)));
    }


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
    public void testIsUnblockedMove() {
        Board b = new Board();
        buildBoard(b,playSelfBoard);
        assertTrue(b.isUnblockedMove(sq("c8"),sq("c9"),sq("c7")));
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
    public void testWrongMoves() {
        Board empty = new Board();
        Move m = mv("d1-e4(e5)");
        boolean checkm = m == null;
        boolean legal = empty.isLegal(m);
        System.out.println("legal");
        empty.makeMove(mv("d1-e4(e5)"));


//        empty.makeMove(mv("d10-d9(e9)"));
//        empty.makeMove(mv("d1-e4(e5)"));
//        empty.makeMove(mv("d1-d4(g5)"));
//        empty.makeMove(mv("a4-a7(b7)"));
//        empty.makeMove(mv("j4-j8(i8)"));
//        empty.makeMove(mv("d1-d3(d10)"));
//        empty.makeMove(mv("d1-a1(a5)"));
//        empty.makeMove(mv("g1-g6(d9)"));

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

        static final Piece[][] smilyBoard =
            {
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, S, S, S, E, E, S, S, S, E },
                    { E, S, E, S, E, E, S, E, S, E },
                    { E, S, S, S, E, E, S, S, S, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, W, E, E, E, E, W, E, E },
                    { E, E, E, W, W, W, W, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
                    { E, E, E, E, E, E, E, E, E, E },
            };

        static final Piece[][] playSelfBoard =
                {
                        { E, E, E, E, S, E, E, E, E, E },
                        { E, E, E, E, E, E, E, E, E, E },
                        { E, E, B, S, E, E, E, E, E, E },
                        { E, S, E, S, S, S, E, E, E, B },
                        { S, E, E, W, E, S, S, E, E, E },
                        { E, B, E, E, E, E, S, E, S, E },
                        { E, E, W, B, E, E, E, E, E, W },
                        { E, E, S, E, E, W, E, S, E, E },
                        { E, E, E, S, E, E, E, E, E, E },
                        { E, E, E, E, E, E, S, E, E, E },
                }

    ;


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




