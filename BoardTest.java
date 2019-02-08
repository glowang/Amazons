package amazons;
import net.sf.saxon.functions.Empty;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static amazons.Square.sq;
import static amazons.Board.*;
import static amazons.Move.mv;
import static amazons.Piece.EMPTY;
import static amazons.Piece.WHITE;
import static amazons.Piece.BLACK;
import static amazons.Piece.SPEAR;
public class BoardTest {
    @Test
    public void testIsUnblock(){
//        Board myboard = new Board();
//        Square start = sq(1,1);
//        start.setPiece(Piece.WHITE);
//        Square end = sq(4,4);
//        Square neighbor1 = sq(2,2);
//        neighbor1.setPiece(EMPTY);
//        Square neighbor2 = sq(1,1);
//        assertEquals(true,myboard.isUnblockedMove(start,end,start));

//
        Board myboard2 = new Board();
        Square start2 = sq(1,1);
        start2.setPiece(WHITE);
        Square end2 = sq(1,5);
        Square n2 = sq(1,2);
        n2.setPiece(EMPTY);
        Square n3 = sq(1,3);
        n3.setPiece(EMPTY);
        Square n4 = sq(1,4);
        n4.setPiece(EMPTY);
        assertEquals(true,myboard2.isUnblockedMove(start2,end2,start2));
//
//        Board myboard3 = new Board();
//        Square start3 = sq(1,1);
//        start3.setPiece(WHITE);
//        Square end3 = sq(1,5);
//        Square N2 = sq(1,2);
//        N2.setPiece(EMPTY);
//        Square N3 = sq(1,3);
//        N3.setPiece(EMPTY);
//        Square N4 = sq(1,4);
//        N4.setPiece(EMPTY);
//        assertEquals(false,myboard3.isUnblockedMove(start3,end3,start3));
    }

    @Test
    public void testGetter() {
        Board myboard = new Board();
        Square start = sq(1,1);
        start.setPiece(WHITE);
        Square end = sq(1,4);
        end.setPiece(BLACK);
        Square anotherpoint = sq(2,2);
        anotherpoint.setPiece(SPEAR);

        assertEquals(start.piece(),myboard.get('b','2'));
        assertEquals(end.piece(),myboard.get('b','5'));
        assertEquals(anotherpoint.piece(),myboard.get('c','3'));
    }

    @Test
    public void testIsLegal() {
        Board myboard = new Board();
        myboard.init();
        myboard.setTurn(Piece.WHITE);
        // false if trying to place something on top of the initial chess pieces
        Square from1 = sq(1,1);
        Square from2 = sq(3,9);
        from1.setPiece(EMPTY);
        assertFalse(myboard.isLegal(from1)); // you cannot move sth that is not there
        assertFalse(myboard.isLegal(from2));

        // case 2: return false if FROM.piece( ) is EMPTY
        Square badsq2 = sq(8,9);
        assertEquals(false, myboard.isLegal(badsq2));

        // case 3: return false if trying to move a SPEAR
        Square badsq3 = sq(8,9);
        badsq3.setPiece(SPEAR);
        assertEquals(false,myboard.isLegal(badsq3));

        // case 4: return false if the FROM square does NOT exist( )
        Square goodsq1 = sq(9,9);
        goodsq1.setPiece(EMPTY);
        assertEquals(false,myboard.isLegal(goodsq1));
    }

    @Test
    public void IslegalMove() {
        Board myb = new Board();
        myb.init(); // default _turn is WHITE
        assertFalse(myb.isLegal(sq(3,0),sq(3,9)));
        Square one  = sq(3,0);
        Square two  = sq(3,8);
        Square three  = sq(3,0);
        assertTrue(myb.isLegal(one,two,three));
        assertFalse(myb.isLegal(one,two,sq(9,9)));
        Square four  = sq(0,4);
        System.out.println(four.toString());
        four.setPiece(WHITE);
        Square five  = sq(4,4);
        Square six  = sq(6,4);
        six.setPiece(BLACK);
        assertFalse(myb.isLegal(four,five,six));


        //System.out.println(myb.toString());
        //Square a = sq(4,9);
        //assertTrue(myb.isLegal(sq(6,9),a));
    }

    @Test
    public void testInit() {
        Board myb = new Board();
        myb.init();
        System.out.println(myb.toString());
    }

    @Test
    public void testMakeMoves() {
        Board b = new Board();
        b.init();

        Square one = sq(5,5);
        Square two = sq(9,9);
        Square three = sq(1,1);
        Square four = sq(9,5);
        one.setPiece(WHITE);
        three.setPiece(SPEAR);

        b.setTurn(BLACK);

        Move m1 = mv(one,two,three);
        assertFalse(b.isLegal(m1));
        Move m2 = mv(two,four,two);
        assertFalse(b.isLegal(m2));
        Move m3 = mv(one,two,four);
        assertFalse(b.isLegal(m3));

        Move m4 = mv(sq(6,9),sq(6,5),sq(6,9)); // (6,9) is a black piece generated during init()
        assertTrue(b.isLegal(m4));
        b.makeMove(m4);
        assertTrue(sq(6,9).piece() == SPEAR);
        assertTrue(sq(6,5).piece() == BLACK);
        assertTrue(WHITE == b.turn()); // after black has moved, white's turn rn

        Move m6 = mv(sq(3,0),sq(3,8),sq(2,8));
        assertTrue(b.isLegal(m6));
        b.makeMove(m6);
        assertTrue(BLACK == b.turn());

    }


}