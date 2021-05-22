package chess.utilities;

import chess.execution.ChessGame;
import chess.execution.MoveValidityChecker;
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
    private final ConsoleInputReader consoleInputReader;
    private final MoveValidityChecker moveValidityChecker;

    public HumanMoveReaderAndExecutor(final ChessGame chessGame) {
        Preconditions.checkNotNull(chessGame);

        this.board = chessGame.getBoard();
        this.consoleInputReader = new ConsoleInputReader();
        this.moveValidityChecker = new MoveValidityChecker(chessGame);
    }

    private PieceToPoint2DMove parseAndInspectMove(final String userInput, final AbstractPlayer player) {
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
        } else if (!player.getPieces().containsKey(pieceChosen.getId())) {
            LOGGER.warn("Wrong piece selected. Try again: ");
            return null;

        }

        final PieceToPoint2DMove pieceToPoint2DMove = PieceToPoint2DMove.builder()
                .setTargetPoint(targetPoint)
                .setPiece(pieceChosen)
                .build();

        return this.moveValidityChecker.isMoveValid(pieceToPoint2DMove) ? pieceToPoint2DMove : null;
    }

    public void readExecuteMove(final AbstractPlayer player) {
        PieceToPoint2DMove pieceToPoint2DMove;
        do {
            LOGGER.info("Play: ");
            final String userInput = consoleInputReader.getUserInput();
            pieceToPoint2DMove = this.parseAndInspectMove(userInput, player);
//            if (pieceToPoint2DMove == null) {
//                LOGGER.info("Please try again: ");
//            }
        } while (pieceToPoint2DMove == null);

        pieceToPoint2DMove.getPiece().setPosition(pieceToPoint2DMove.getTargetPoint());
    }
}
