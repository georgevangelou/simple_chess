package chess.utilities;

import chess.execution.PieceToPoint2DMove;
import chess.players.AbstractPlayer;
import chess.resources.pieces.AbstractPiece;
import chess.space.Board2D;
import chess.space.Point2D;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class HumanMoveReaderAndExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HumanMoveReaderAndExecutor.class);
    private final String DELIMITED = " ";
    private final Board2D board;
    //    private final AbstractPlayer player;
    private final ConsoleInputReader consoleInputReader;

    public HumanMoveReaderAndExecutor(final Board2D board) {
        Preconditions.checkNotNull(board);
//        Preconditions.checkNotNull(player);

        this.board = board;
//        this.player = player;
        this.consoleInputReader = new ConsoleInputReader();
    }

    private PieceToPoint2DMove parsePointsAndMovePiece(final String userInput, final AbstractPlayer player) {
        Preconditions.checkNotNull(userInput);

        if (!userInput.contains(DELIMITED)) {
            return null;
        }

        final String[] splitInput = userInput.split(DELIMITED);
        final String startPointString = splitInput[0];
        final String targetPointString = splitInput[1];

        final Point2D startPoint = Point2D.from(startPointString);
        final Point2D targetPoint = Point2D.from(targetPointString);

        final AbstractPiece pieceChosen = this.board.getPiece(startPoint);
        if (pieceChosen == null) {
            LOGGER.warn("No piece selected. Try again: ");
            return null;
//        } else if (player.getPieces().containsKey(pieceChosen.getId())) {
        } else if (true) {
            pieceChosen.setPosition(targetPoint);
            return PieceToPoint2DMove.builder()
                    .setTargetPoint(targetPoint)
                    .setPiece(pieceChosen)
                    .build();
        } else {
            LOGGER.info("Piece [" + pieceChosen.getId() + "], pieces [" + player.getPieces().keySet() + "]");

            LOGGER.warn("Wrong piece. Try again: ");
            return null;
        }
    }

    public void readExecuteMove(final AbstractPlayer player) {
        while (this.parsePointsAndMovePiece(consoleInputReader.getUserInput(), player) == null) {
//            LOGGER.warn("Please retry... ");
        }
    }
}
