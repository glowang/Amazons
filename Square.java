package amazons;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.lang.*;

import static amazons.Utils.*;

/** Represents a position on an Amazons board.  Positions are numbered
 *  from 0 (lower-left corner) to 99 (upper-right corner).  Squares
 *  are immutable and unique: there is precisely one square created for
 *  each distinct position.  Clients create squares using the factory method
 *  sq, not the constructor.  Because there is a unique Square object for each
 *  position, you can freely use the cheap == operator (rather than the
 *  .equals method) to compare Squares, and the program does not waste time
 *  creating the same square over and over again.
 *  @author Mengyi Wang
 */
final class Square {
    /** The regular expression for a square designation (e.g.,
     *  a3). For convenience, it is in parentheses to make it a
     *  group.  This subpattern is intended to be incorporated into
     *  other pattern that contain square designations (such as
     *  patterns for moves). */
    static final String SQ = "([a-j](?:[1-9]|10))";

    /** The cache of all created squares, by index. */
    private static final Square[] SQUARES =
            new Square[Board.SIZE * Board.SIZE];

    /** SQUARES viewed as a List. */
    private static final List<Square> SQUARE_LIST = Arrays.asList(SQUARES);

    /** My index position. */
    private final int _index;

    /** My row and column (redundant, since these are determined by _index). 0-indexed */
    private final int _row, _col;
    /** My String denotation. */
    private final String _str;
    /** The Piece that is placed on me right now */
    private Piece _mypiece = Piece.EMPTY;
    /** The mapping from characters to integers */
    static HashMap<Character,Integer> _char_to_int;
    /** The mapping from integers to characters */
    static HashMap<Integer,Character> _int_to_char;


    /** Return the Square with index INDEX. */
    private Square(int index) {
        chatToInt();
        intToChar();
        _index = index;
        _row = (index) % 10;
        _col = (index) / 10;  // _col should be denoted by chars, not ints
        _str = Character.toString(_int_to_char.get(_col)) + Integer.toString(_row+1);
    }

    static {
        for (int i = Board.SIZE * Board.SIZE - 1; i >= 0; i -= 1) {
            SQUARES[i] = new Square(i);
        }
    }
    /** Return my squares, collected by an arraylist*/
    Square[] squares() {
        return SQUARES;
    }

    /** Return my row position, where 0 is the bottom row.*/
    int row() {
        return _row;
    }

    /** Return my column position, where 0 is the leftmost column. */
    int col() {
        return _col;
    }

    /** Return my index position (0-99).  0 represents square a1, and 99
     *  is square j10. */
    int index() {
        return _index;
    }

    /** Return the piece that is stored in me */
    Piece piece() {
        return _mypiece;
    }

    void setPiece(Piece p) {
        _mypiece = p;
    }

    private void chatToInt() {
        HashMap<Character, Integer> map1 = new HashMap<>() {{
            put('a', 0);
            put('b', 1);
            put('c', 2);
            put('d', 3);
            put('e', 4);
            put('f', 5);
            put('g', 6);
            put('h', 7);
            put('i', 8);
            put('j', 9);
        }};
        _char_to_int = map1;
    }

    private void intToChar() {
        HashMap<Integer,Character> map2 = new HashMap<>() {{
            put(0,'a');
            put(1,'b');
            put(2,'c');
            put(3,'d');
            put(4,'e');
            put(5,'f');
            put(6,'g');
            put(7,'h');
            put(8,'i');
            put(9,'j');
        }};
        _int_to_char = map2;
    }

    /** use the mapping to get back an integer 1-10
     * representing letter from a to j. */
    static int getInt(char col) {
        return _char_to_int.get((char) col);
    }


    /** Return true iff THIS - TO is a valid queen move.
     * Check whether you have a valid path to get there
     * AND whether the distance between the two is different
     * */
    boolean isQueenMove(Square to) { //TODO how to check whether have a piece already?
        // case 1: didn't move at all
        if (this == to) {
            return false;
        }
        // case 2: moved out of bound, ensuring TO is a legal point
        if (to.row() < 0 || to.row() > 9 || to.col() < 0 || to.col() > 9) {
            return false;
        }
        int diffcol = Math.abs(this.col() - to.col());
        int diffrow = Math.abs(this.row() - to.row());
        // case 3: moved vertically at least 1 unit, at most 8 units
        if (diffcol == 0 && diffrow != 0 ) {
            return true;
        }
        // case 4: moved horizontally at least one unit, at most 8
        if (diffrow == 0 && diffcol != 0 ) {
            return true;
        }
        // case 5: diagonally
        if (diffcol != 0 && diffrow != 0 && diffcol == diffrow ) {
            return true;
        }
        return false;
    }

    /** Definitions of direction for queenMove.  DIR[k] = (dcol, drow)
     *  means that to going one step from (col, row) in direction k,
     *  brings us to (col + dcol, row + drow). */
    private static final int[][] DIR = {
        { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 },
        { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }
    };

    /** Return the Square that is STEPS>0 squares away from me in direction
     *  DIR, or null if there is no such square.
     *  DIR = 0 for north, 1 for northeast, 2 for east, etc., up to 7 for 
     *  northwest. If DIR has another value, return null. Thus, unless the 
     *  result is null the resulting square is a queen move away from me. */
    Square queenMove(int dir, int steps) {
        if (dir < 0 || dir > 7) {
            //System.out.println("Direction" + dir);
            return null;
        }
        if (DIR[dir] == null) {
            //System.out.println("Invalid direction.");
            //System.out.println("Direction is NULL");
            return null;
        }
        if (steps == 0) {
            //System.out.println("step = 0");
            return null;
        }
        int[] directions = DIR[dir];
        int newcol = steps * directions[0] + this.col();
        int newrow = steps * directions[1] + this.row();

        if (newcol > 9 || newrow > 9 || newcol < 0 || newrow <0) {
            // System.out.println(newcol + "||" + newrow);
            //System.out.println("You are moving out of the board! Reduce your steps.");
            return null;
        }
        return sq(newcol,newrow);
    }

    int directionHelper (int pos) {
        if (pos < 0) {
            return -1;
        }
        else if (pos > 0) {
            return 1;
        }
        else return 0;
    }

    /** Return the direction (an int as defined in the documentation
     *  for queenMove) of the queen move THIS-TO. */
    int direction(Square to) {
        assert isQueenMove(to);
        int diffrow = to.row() - this.row();
        int diffcol = to.col() - this.col();
        int drow = directionHelper(diffrow);
        int dcol = directionHelper(diffcol);
        int[] myDirection = new int[]{dcol,drow};
        for (int i = 0; i < 8; i += 1) {
            if (Arrays.equals(myDirection,DIR[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return _str;
    }

    /** Return true iff COL ROW is a legal square. */
    static boolean exists(int col, int row) {
        return row >= 0 && col >= 0 && row < Board.SIZE && col < Board.SIZE; // NOTE: updated to <= instead of < bc of my numbering system
    }

    /** Helper method to convert a coordinate (0,4) to an index value (4) */
    static int toIndex (int col, int row) {
        return col*10 + row;
    }

    /** Return the (unique) Square denoting COL ROW. */
    static Square sq(int col, int row) {
        if (!exists(col, row)) {
            throw error("row or column out of bounds");
        }
        //char coll = (char) col;
        int index = toIndex(col,row); // pass in internal index values
        return sq(index);
    }

    /** Return the (unique) Square denoting the position with index INDEX. */
    static Square sq(int index) {
        if (SQUARES[index] == null) {
            System.out.println("Square doesn't exist");
            return null;
        }
        return SQUARES[index];
    }

    /** Return the (unique) Square denoting the position COL ROW, where
     *  COL ROW is the standard text format for a square (e.g., a4). */
    static Square sq(String col, String row) {
        int col_int = getInt(col.charAt(0));
        int row_int = Integer.valueOf(row);
        return sq(col_int,row_int - 1);
    }

    /** Return the (unique) Square denoting the position in POSN, in the
     *  standard text format for a square (e.g. a4). POSN must be a
     *  valid square designation. */
    static Square sq(String posn) {
        assert posn.matches(SQ);
        int col = getInt(posn.charAt(0));
        int row = Integer.valueOf(posn.substring(1)) - 1;
        return sq(col,row);
    }

    /** Return an iterator over all Squares. */
    static Iterator<Square> iterator() {
        return SQUARE_LIST.iterator();
    }

}
