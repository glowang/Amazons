package amazons;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import static amazons.Square.*;
import static amazons.Piece.*;
import static amazons.Move.mv;


/**
 * The state of an Amazons Game.
 *
 * @author
 */
class Board {
    /**
     * The number of squares on a side of the board.
     */
    static final int SIZE = 10;

    /**
     * An empty iterator for initialization.
     */
    private static final Iterator<Square> NO_SQUARES =
            Collections.emptyIterator();

    private Iterator<Square> _squareiteraor;
    /**
     * Piece whose turn it is (BLACK or WHITE).
     */
    private Piece _turn;
    /**
     * Cached value of winner on this board, or EMPTY if it has not been
     * computed.
     */
    private Piece _winner;

    /**
     * Using a stack to keep track of all the moves made
     */
    private Stack<Move> _movetracker = new Stack<>();

    /**
     * Cached the number of moves (that have not been undone) for this
     * board.
     */
    private int _nummove = 0;

    /**
     * Initial positions of white and black pieces
     */
    private int[][] initwhites = {{0, 3}, {3, 0}, {6, 0}, {9, 3}};
    private int[][] initblacks = {{0, 6}, {3, 9}, {6, 9}, {9, 6}};

    private ArrayList<Square> allsquares;


    /**
     * Initializes a game board with SIZE squares on a side in the
     * initial position.
     */
    Board() {
        init();
    }

    /**
     * Initializes a copy of MODEL.
     */
    Board(Board model) {
        copy(model);
    }

    /**
     * Copies MODEL into me.
     */
    void copy(Board model) {
        //init();
        this._turn = model._turn;
        this._winner = model._winner;
        //this._squareiteraor = model._squareiteraor;
        //this._movetracker = model._movetracker;
        this.allsquares = new ArrayList<>();
        for (Square s : model.allsquares) {
            this.allsquares.add(s);
        }
    }

    /**
     * Clears the board to the initial position.
     * Initial position =  no pieces on the board?
     */
    void init() {
        _turn = WHITE;
        _winner = EMPTY;
        _squareiteraor = Square.iterator();
        allsquares = new ArrayList<>();
        ArrayList<Integer> initwhiteindices = new ArrayList<>();
        ArrayList<Integer> initblackindices = new ArrayList<>();
        while (_squareiteraor.hasNext()) {
            allsquares.add(_squareiteraor.next());
        }

        for (int[] pair : initwhites) {
            int pairindex = toIndex(pair[0], pair[1]);
            initwhiteindices.add(pairindex);
        }
        for (int[] pair : initblacks) {
            int pairindex = toIndex(pair[0], pair[1]);
            initblackindices.add(pairindex);
        }
        for (int index = 0; index <= 99; index += 1) {
            if (initwhiteindices.contains(index)) {
                this.put(WHITE, sq(index));
            } else if (initblackindices.contains(index)) {
                this.put(BLACK, sq(index));
            } else {
                this.put(EMPTY, sq(index));
            }
        }
    }

    /**
     * Return the Piece whose move it is (WHITE or BLACK).
     */
    Piece turn() {
        return _turn;
    }

    /**
     * Set the TURN to either BLACK or WHITE
     */
    void setTurn(Piece COLOR) {
        _turn = COLOR;
    }

    /**
     * Return the number of moves (that have not been undone) for this
     * board.
     */
    int numMoves() {
        return _nummove;
    }

    /**
     * Return the winner in the current position, or null if the game is
     * not yet finished.
     */

    Piece getWinner() {
        return _winner;
    }

    void setWinner() {
        Iterator blackremaining = new LegalMoveIterator(BLACK);
        Iterator whiteremaining = new LegalMoveIterator(WHITE);
        if (_turn == BLACK) {
            if (!blackremaining.hasNext()) {
                _winner = WHITE;
            } else {
                _winner = EMPTY;
            }
        } else if (_turn == WHITE) {
            if (!whiteremaining.hasNext()) {
                _winner = BLACK;
            } else {
                _winner = EMPTY;
            }
        }
        return;
    }

    /**
     * Return the contents the square at S.
     */
    final Piece get(Square s) {
        return s.piece();
    }

    /**
     * Return the contents of the square at (COL, ROW), where
     * <<<<<<< HEAD
     * 0 <= COL, ROW < 10.
     */

    final Piece get(int col, int row) {
        return get(Square.sq(col, row));
    }

    final Piece get(char col, int row) {
        int int_col = Square.getInt(col);
        return get(Square.sq(int_col, row));
    }

    /**
     * Return the contents of the square at COL ROW.
     */
    final Piece get(char col, char row) {
        int int_row = Character.getNumericValue(row) - 1;
        return get(col - 'a', int_row);
    }

    /**
     * Set square S to P.
     */
    final void put(Piece p, Square s) {
        s.setPiece(p);
    }

    /**
     * Set square (COL, ROW) to P.
     */
    final void put(Piece p, int col, int row) {
        put(p, Square.sq(col, row));
        _winner = EMPTY;
    }

    /**
     * Set square (COL, ROW) to P.
     */
    final void put(Piece p, char col, int row) {
        int int_col = Square.getInt(col);
        put(p, Square.sq(int_col, row));
        _winner = EMPTY;
    }

    /**
     * Set square COL ROW to P.
     */
    final void put(Piece p, char col, char row) {
        int int_row = Character.getNumericValue(row);
        put(p, col - 'a' + 1, int_row);
        _winner = EMPTY;
    }

    /**
     * Return true iff FROM - TO is an unblocked queen move on the current
     * board, ignoring the contents of ASEMPTY, if it is encountered.
     * For this to be true, FROM-TO must be a queen move and the
     * squares along it, other than FROM and ASEMPTY, must be
     * empty. ASEMPTY may be null, in which case it has no effect.
     */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {
        if (to == null) {
            return false;
        }

        if (!from.isQueenMove(to)) {
            return false;
        }

        if (to.piece() != EMPTY && !to.equals(asEmpty)) {
            return false;
        }

        int direction = from.direction(to);
        int diffcol = Math.abs(to.col() - from.col());
        int diffrow = Math.abs(to.row() - from.row());
        int numsteps = Math.max(diffcol, diffrow);
        for (int i = 1; i <= numsteps; i += 1) {
            Square neighbor = from.queenMove(direction, i);
            if (!neighbor.piece().equals(EMPTY)) {
                if (!neighbor.equals(asEmpty))
                    return false;
            }
        }

        return true;
    }


    boolean isLegal(Square from) {
        return get(from) == _turn;
    }


    /** Return true iff FROM-TO is a valid first part of move, ignoring
     *  spear throwing. */
    boolean isLegal(Square from, Square to) {
        return isLegal(from) && isUnblockedMove(from, to, null);
    }

    /** Return true iff FROM-TO(SPEAR) is a legal move in the current
     *  position. */
    boolean isLegal(Square from, Square to, Square spear) {
        if (!isLegal(from)) {
            return false;
        }

        if (spear == from) {
            if (!isLegal(from, to)) {
                return false;
            }
            if (!isUnblockedMove(from, to, from)) {
                return false;
            }
        }

        if (spear != from) {
            if (spear.piece() != EMPTY) {
                return false;
            }
            if (!isUnblockedMove(from, to, spear) || !isUnblockedMove(to, spear, from)) {
                return false;
            }
        }
        return true;
    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        if (move == null) return false;
        return isLegal(move.from(),
                move.to(),
                move.spear());
    }

    /**
     * Move FROM-TO(SPEAR), assuming this is a legal move.
     */
    void makeMove(Square from, Square to, Square spear) {
        this.put(from.piece(), to);
        from.setPiece(EMPTY);
        if (spear == null) {
            System.out.println("You must also throw a spear >.<!!!");
            return;
        }
        spear.setPiece(SPEAR);
        Move m = mv(from, to, spear);
        _nummove += 1;
        _movetracker.push(m);
        _turn = _turn.opponent();

    }

    /**
     * Move according to MOVE, assuming it is a legal move.
     */
    void makeMove(Move move) {
        if (move != null) {
            Square from = move.from();
            Square to = move.to();
            Square sp = move.spear();
            makeMove(from, to, sp);
            _movetracker.push(move);
            _nummove += 1;
        }
    }

    /**
     * Undo one move.  Has no effect on the initial board.
     */
    void undo() {
        if (!_movetracker.empty()) {
            Move oldMove = _movetracker.pop();
            //System.out.println(oldMove);
            Square old_from = oldMove.from();
            Square old_to = oldMove.to();
            Square spear = oldMove.spear();

            spear.setPiece(EMPTY);
            old_from.setPiece(old_to.piece());
            old_to.setPiece(EMPTY);

            _turn = _turn.opponent();
            _nummove -= 1;
        }
    }

    /**
     * Return an Iterator over the Squares that are reachable by an
     * unblocked queen move from FROM. Does not pay attention to what
     * piece (if any) is on FROM, nor to whether the game is finished.
     * Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     * feature is useful when looking for Moves, because after moving a
     * piece, one wants to treat the Square it came from as empty for
     * purposes of spear throwing.)
     */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /**
     * Return an Iterator over all legal moves on the current board.
     */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }

    /**
     * Return an Iterator over all legal moves on the current board for
     * SIDE (regardless of whose turn it is).
     */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /**
     * An iterator used by reachableFrom.
     */
    private class ReachableFromIterator implements Iterator<Square> {

        /**
         * Iterator of all squares reachable by queen move from FROM,
         * treating ASEMPTY as empty.
         */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = -1;
            _steps = 0;
            _asEmpty = asEmpty;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _dir < 8;
        }

        @Override
        public Square next() {
            Square onemove = _from.queenMove(_dir, _steps);
            toNext();
            return onemove;
        }

        /**
         * Advance _dir and _steps, so that the next valid Square is
         * _steps steps in direction _dir from _from.
         */
        private void toNext() {
            _steps += 1;
            while (_dir < 8 && !isUnblockedMove(_from, _from.queenMove(_dir, _steps), _asEmpty)) {
                _dir++;
                _steps = 1;
            } // if your current set up is an unblocked move, move forward
        }

        /**
         * Starting square.
         */
        private Square _from;
        /**
         * Current direction.
         */
        private int _dir;
        /**
         * Current distance.
         */
        private int _steps;
        /**
         * Square treated as empty.
         */
        private Square _asEmpty;
    }

    /**
     * An iterator used by legalMoves.
     */
    private class LegalMoveIterator implements Iterator<Move> {

        /**
         * All legal moves for SIDE (WHITE or BLACK).
         */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThrows = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _fromPiece = side;
            validStartSquareIterator();
            toNext();
        }

        private void validStartSquareIterator() {
            ArrayList<Square> validSquares = new ArrayList<>();
            while (_startingSquares.hasNext()) {
                Square sq = _startingSquares.next();
                if (sq.piece() == _fromPiece) {
                    validSquares.add(sq);
                }
            }
            Iterator<Square> validsqiterator = validSquares.iterator();
            _validsqs = validSquares;
            _validStartSqIterator = validsqiterator;
        }

        @Override
        public boolean hasNext() {
            return _spear != null;
        }

        @Override
        public Move next() {
            Move m = mv(_start,
                    _nextSquare,
                    _spear);
            toNext();
            return m;
        }

        /**
         * Advance so that the next valid Move is
         * _start-_nextSquare(sp), where sp is the next value of
         * _spearThrows.
         */
        private void toNext() {
            if (!_spearThrows.hasNext()) {
                if (!_pieceMoves.hasNext()) {
                    if (!_validStartSqIterator.hasNext()) {
                        _spear = null;
                        return;
                    } else {
                        _start = _validStartSqIterator.next();
                        _pieceMoves = new ReachableFromIterator(_start, null);
                        if (_pieceMoves.hasNext()) {
                            _nextSquare = _pieceMoves.next();
                            _spearThrows = new ReachableFromIterator(_nextSquare, _start);
                            _spear = _spearThrows.next();
                        } else {
                            toNext();
                        }
                    }
                } else {
                    _nextSquare = _pieceMoves.next();
                    _spearThrows = new ReachableFromIterator(_nextSquare, _start);
                    _spear = _spearThrows.next();
                }
            } else {
                _spear = _spearThrows.next();
            }
        }

        /**
         * Color of side whose moves we are iterating.
         */
        private Piece _fromPiece;
        /**
         * Current starting square.
         */
        private Square _start;
        /**
         * ALl remaining starting squares to consider. Usage is mainly to give _validSqIterator empty squares to pick from
         */
        private Iterator<Square> _startingSquares;
        /**
         * Remaining valid starting squares, with the correct color, to consider.
         */
        private Iterator<Square> _validStartSqIterator;
        /**
         * Current piece's new position.
         */
        private Square _nextSquare;
        /**
         * Current piece's thrown spear.
         */
        private Square _spear;
        /**
         * Remaining moves from _start to consider.
         */
        private Iterator<Square> _pieceMoves;
        /**
         * Remaining spear throws from _piece to consider.
         */
        private Iterator<Square> _spearThrows;
        private ArrayList<Square> _validsqs;
        private int _numvalidsqs;
    }

    @Override
    public String toString() {
        String boardview = "";
        for (int r = 9; r >= 0; r -= 1) {
            for (int c = 0; c <= 9; c += 1) {
                if (c == 0) {
                    boardview += "   ";
                    Piece p = get(sq(c, r));
                    boardview += p.toString();
                    boardview += " ";

                }
                if (c > 0 && c < 9) {
                    Piece p = get(sq(c, r));
                    boardview += p.toString();
                    boardview += " ";
                }
                if (c == 9) {
                    Piece p = get(sq(c, r));
                    boardview += p.toString();
                    boardview += "\n";
                }
            }
        }
        return boardview;
    }


}
