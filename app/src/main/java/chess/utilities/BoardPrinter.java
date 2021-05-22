package chess.utilities;

import chess.resources.pieces.AbstractPiece;
import chess.space.Board2D;
import chess.space.Point2D;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class BoardPrinter {
    private final Board2D board;
    private final String REPRESENTATION_OF_EMPTY_BLOCK = "_";
    private final String SPACING = "   ";

    public BoardPrinter(final Board2D board) {
        this.board = board;
    }

    public String printBoard() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n").append("-").append(SPACING);
        for (int x = 0; x < this.board.getSizeX(); x++) {
            stringBuilder.append(x).append(SPACING);
        }
        stringBuilder.append("\n");
        for (int y = 0; y < this.board.getSizeX(); y++) {
            stringBuilder.append(y).append(SPACING);
            for (int x = 0; x < this.board.getSizeX(); x++) {
                final Point2D pointOnBoard = Point2D.builder().setX(x).setY(y).build();
                final AbstractPiece piece = this.board.getPiece(pointOnBoard);
                if (piece != null) {
                    stringBuilder.append(piece.getVisualRepresentation());
                } else {
                    stringBuilder.append(REPRESENTATION_OF_EMPTY_BLOCK);
                }
                stringBuilder.append(SPACING);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
