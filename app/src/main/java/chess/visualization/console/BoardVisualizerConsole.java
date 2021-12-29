package chess.visualization.console;

import chess.execution.ChessGame;
import chess.resources.pieces.Piece;
import chess.visualization.BoardVisualizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prints the current board state in console.
 *
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class BoardVisualizerConsole implements BoardVisualizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoardVisualizerConsole.class);
    private final ChessGame chessGame;
    private final String REPRESENTATION_OF_EMPTY_BLOCK = "_";
    private final String SPACING = "   ";
    private final int SIZE_MULTIPLIER = 2;


    public BoardVisualizerConsole(final ChessGame chessGame) {
        this.chessGame = chessGame;
    }


    /**
     * @return current state of {@link this#chessGame}
     */
    public void showBoardView() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n").append("-").append(SPACING);
        for (int x = 0; x < this.chessGame.getBoard().getLength(); x++) {
            stringBuilder.append(x).append(SPACING);
        }
        stringBuilder.append("\n");
        for (int y = 0; y < this.chessGame.getBoard().getLength(); y++) {
            stringBuilder.append(y).append(SPACING);
            for (int x = 0; x < this.chessGame.getBoard().getLength(); x++) {
                final Piece piece = this.chessGame.getBoard().getPiece(x, y);
                if (piece != null) {
                    final String pieceVisualRepresentationNormal = piece.getVisualRepresentation();
                    final String pieceVisualRepresentationDependingOnPlayer =
                            this.chessGame.getPlayerWhite().pieceBelongsToPlayer(piece.getId()) ?
                                    pieceVisualRepresentationNormal.toUpperCase() :
                                    pieceVisualRepresentationNormal.toLowerCase();
                    stringBuilder.append(pieceVisualRepresentationDependingOnPlayer);
                } else {
                    stringBuilder.append(REPRESENTATION_OF_EMPTY_BLOCK);
                }
                stringBuilder.append(SPACING);
            }
            stringBuilder.append("\n");
        }

        final StringBuilder stringBuilderResized = new StringBuilder();
        for (int i = 0; i < stringBuilder.length(); i++) {
            for (int duplicate = 0; duplicate < SIZE_MULTIPLIER; duplicate++) {
                stringBuilderResized.append(stringBuilder.charAt(i));
            }
        }
        LOGGER.info(stringBuilderResized.toString());
    }
}
