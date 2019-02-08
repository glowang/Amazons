package amazons;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static amazons.Square.sq;

public class SquareTest {
    /** Create a new Square*/
    @Test
    public void test_sq() {
        Square mySquare1 = Square.sq("c3");
        assertEquals(22, mySquare1.index());
        Square mySquare2 = Square.sq("j","2");
        assertEquals(91,mySquare2.index());
        Square mySquare3 = Square.sq("j","10");
        assertEquals(99,mySquare3.index());
    }

    @Test
    public void testGetInt() {
        //Square mySquare = Square.sq("a1");
        assertEquals(9, Square.getInt('j'));
        assertEquals(0, Square.getInt('a'));
        assertEquals(2, Square.getInt('c'));
    }
    @Test
    public void testQueenMove() {
        Square queen1 = Square.sq(5,3);
        Square nq1 = queen1.queenMove(2,1);
        assertEquals(6,nq1.col());
        assertEquals(3,nq1.row());

        Square queen5 = Square.sq(7,6);
        Square nq5 = queen5.queenMove(3,2);
        assertEquals(4,nq5.row());
        assertEquals(9,nq5.col());

        Square queen3 = Square.sq(1,1);
        Square nq3 = queen3.queenMove(3,2);
        assertEquals(null,nq3);

        Square queen4 = Square.sq(8,8);
        Square nq4 = queen4.queenMove(1,3);
        assertEquals(null,nq4);


    }
    @Test
    public void testIsQueenMove() {
        Square self = Square.sq(1,1);
        Square to1 = Square.sq(9,9);
        assertEquals(true,self.isQueenMove(to1));

        Square to2 = Square.sq(1,2);
        assertEquals(true,self.isQueenMove(to2));

        Square to4 = Square.sq(5,9);
        assertEquals(false,self.isQueenMove(to4));

        Square to5 = Square.sq(9,5);
        assertEquals(false,self.isQueenMove(to5));

        Square to6 = Square.sq(9,9);
        assertEquals(true,self.isQueenMove(to6));

        Square to7 = Square.sq(1,9);
        assertEquals(true,self.isQueenMove(to7));
    }

    @Test
    public void testDirection() {
        Square self = Square.sq(5,5);
        Square to1 = Square.sq(3,5);
        assertEquals(6,self.direction(to1));

        Square to2 = Square.sq(5,8);
        assertEquals(0,self.direction(to2));

        Square to3 = Square.sq(6,6);
        assertEquals(1,self.direction(to3));

        Square to4 = Square.sq(5,1);
        assertEquals(4,self.direction(to4));

        Square to5 = Square.sq(4,4);
        assertEquals(5,self.direction(to5));

        Square to6 = Square.sq(0,5);
        assertEquals(6,self.direction(to6));

        Square to7 = Square.sq(3,7);
        assertEquals(7,self.direction(to7));

        Square to8 = Square.sq(9,5);
        assertEquals(2,self.direction(to8));

    }
    public static void main(String[] args) {System.exit(ucb.junit.textui.runClasses(SquareTest.class));}}

