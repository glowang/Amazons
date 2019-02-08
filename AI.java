package amazons;

import java.util.Iterator;

import static amazons.Piece.*;


/**
 * A Player that automatically generates moves.
 *
 * @author Mengyi Wang
 */
class AI extends Player {

    /**
     * A position magnitude indicating a win (for white if positive, black
     * if negative).
     */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /**
     * A magnitude greater than a normal value.
     */
    private static final int INFTY = Integer.MAX_VALUE;

    /**
     * A new AI with no piece or controller (intended to produce
     * a template).
     */
    AI() {
        this(null, null);
    }

    /**
     * A new AI playing PIECE under control of CONTROLLER.
     */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new AI(piece, controller);
    }

    @Override
    String myMove() {
        Move move = findMove();
        _controller.reportMove(move);
        if (move == null) {
            return null;
        }
        return move.toString();
    }

    /**
     * Return a move for me from the current position, assuming there
     * is a move.
     */
    private Move findMove() {
        Board b = new Board(board());
        if (_myPiece == WHITE) {
            findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        } else {
            findMove(b, maxDepth(b), true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /**
     * The move found by the last call to one of the ...FindMove methods
     * below.
     */
    private Move _lastFoundMove;

    /**
     * Find a move from position BOARD and return its value, recording
     * the move found in _lastFoundMove iff SAVEMOVE. The move
     * should have maximal value or have value > BETA if SENSE==1,
     * and minimal value or value < ALPHA if SENSE==-1. Searches up to
     * DEPTH levels.  Searching at level 0 simply returns a static estimate
     * of the board value and does not set _lastMoveFound.
     */
    private int findMove(Board board, int depth, boolean saveMove, int sense,
                         int alpha, int beta) {
        int bestsofar;
        Move bestmove = null;
        if (depth == 0 || board.getWinner() != EMPTY) {
            return staticScore(board);
        }
        if (sense == 1) {
            bestsofar = -INFTY;
        } else {
            bestsofar = INFTY;
        }
        Iterator<Move> childMoves = board.legalMoves();
        while (childMoves.hasNext()) {
            System.out.print("HASNEXT"); //TODO delete
            Move nextmove = childMoves.next();
            board.makeMove(nextmove);
            int val;
            if (sense == 1) {
                val = findMove(board, depth - 1, false, -1, alpha, beta);
                if (val >= bestsofar) {
                    bestmove = nextmove;
                    bestsofar = val;
                    if (val > alpha) {
                        alpha = val;
                    }
                    board.undo();
                    if (beta <= alpha) {
                        break;
                    }
                } else {
                    board.undo();
                }
            } else {
                val = findMove(board, depth - 1, false, 1, alpha, beta);
                if (val <= bestsofar) {
                    bestmove = nextmove;
                    bestsofar = val;
                    if (val < beta) beta = val;
                    board.undo();
                    if (beta <= alpha) {
                        break;
                    }
                } else {
                    board.undo();
                }
            }
        }
        if (saveMove) {
            _lastFoundMove = bestmove;
            //String move = _lastFoundMove.toString(); //TODO delete print statement
            //System.out.println(_myPiece + "||" + move); //TODO delete print statement
        }
        return bestsofar;
    }


    /**
     * Return a heuristically determined maximum search depth
     * based on characteristics of BOARD.
     * how far down do you wanna go
     */
    private int maxDepth(Board board) {
        int N = board.numMoves();
        if (N < 25) {
            return 1;
        } else if (N >= 25 && N <= 50) {
            return 2;
        } else return 3;
    }


    /**
     * Return a heuristic value for BOARD.
     * how do you minimize the reachable steps that
     */
    private int staticScore(Board board) {
        Piece winner = board.getWinner();
        if (winner == WHITE) {
            return WINNING_VALUE;
        }
        if (winner == BLACK) {
            return -WINNING_VALUE;
        }
        Iterator<Move> whitemoves = board().legalMoves(WHITE);
        Iterator<Move> blackmoves = board().legalMoves(BLACK);
        int whitecounter = 0;
        int blackcounter = 0;
        while (whitemoves.hasNext()) {
            whitemoves.next();
            whitecounter += 1;
        }
        while (blackmoves.hasNext()) {
            blackmoves.next();
            blackcounter += 1;
        }
        return whitecounter - blackcounter;
    }


}
