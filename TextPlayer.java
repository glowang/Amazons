package amazons;

import static amazons.Move.isGrammaticalMove;
import static amazons.Move.mv;

/** A Player that takes input as text commands from the standard input.
 *  @author Mengyi Wang
 */
class TextPlayer extends Player {

    /** A new TextPlayer with no piece or controller (intended to produce
     *  a template). */
    TextPlayer() {
        this(null, null);
    }

    /** A new TextPlayer playing PIECE under control of CONTROLLER. */
    private TextPlayer(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new TextPlayer(piece, controller);
    }

    /** Return either a String denoting either a legal move for me
     *  or another command (which may be invalid).  Always returns the
     *  latter if board().turn() is not myPiece() or if board.winner()
     *  is not null. */
    @Override
    String myMove() {
        while (true) {
            String line = _controller.readLine();
            if (line == null) {
                return "quit";}
            else if (isGrammaticalMove(line) &&
                    !(mv(line) != null) &&
                    !_controller.board().isLegal(mv(line))) { // the latter half should be NULL
                    _controller.reportError("Invalid move. "
                                        + "Please try again."); // you report error when it is not a legal move
                continue;

            } else {
                return line; // you will get here if it is a legal move. you just let it pass

            }
        }
    }
}
