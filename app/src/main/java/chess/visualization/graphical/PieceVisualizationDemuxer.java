package chess.visualization.graphical;

import chess.resources.pieces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author George Evangelou
 * Created on: 2021-12-29
 */
public class PieceVisualizationDemuxer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PieceVisualizationDemuxer.class);

    public final String getFilenameForPiece(final Piece piece) {
        if (piece instanceof Rook) {
            return "C:\\Users\\George\\repositories\\simple_chess\\app\\src\\main\\resources\\images\\queen.png";
        } else if (piece instanceof Queen) {
            return "C:\\Users\\George\\repositories\\simple_chess\\app\\src\\main\\resources\\images\\queen.png";
        } else if (piece instanceof Bishop) {
            return "C:\\Users\\George\\repositories\\simple_chess\\app\\src\\main\\resources\\images\\queen.png";
        } else if (piece instanceof Pawn) {
            return "C:\\Users\\George\\repositories\\simple_chess\\app\\src\\main\\resources\\images\\queen.png";
        } else if (piece instanceof Knight) {
            return "C:\\Users\\George\\repositories\\simple_chess\\app\\src\\main\\resources\\images\\queen.png";
        } else if (piece instanceof King) {
            return "C:\\Users\\George\\repositories\\simple_chess\\app\\src\\main\\resources\\images\\queen.png";
        } else {
            LOGGER.warn("Unrecognized piece: " + piece.getClass());
            return "C:\\Users\\George\\repositories\\simple_chess\\app\\src\\main\\resources\\images\\queen.png";
        }
    }
}
