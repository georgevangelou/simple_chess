package chess.utilities;

import chess.execution.ChessGame;
import chess.logic.MoveValidityChecker;
import chess.logic.PieceDestroyer;
import chess.players.Player;
import chess.resources.immutables.PieceToPoint2DMove;
import chess.resources.immutables.Point2D;
import chess.resources.pieces.Piece;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class HumanMoveReaderAndExecutor implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HumanMoveReaderAndExecutor.class);
    private final String DELIMITED = " ";
    private final ChessGame chessGame;
    private final ConsoleInputReader consoleInputReader;
    private final MoveValidityChecker moveValidityChecker;


    public HumanMoveReaderAndExecutor(final ChessGame chessGame) {
        Preconditions.checkNotNull(chessGame);

        this.chessGame = chessGame;
        this.consoleInputReader = new ConsoleInputReader();
        this.moveValidityChecker = new MoveValidityChecker(chessGame);
    }


    private PieceToPoint2DMove parseAndInspectMove(final String userInput, final Player player) {
        Preconditions.checkNotNull(userInput);

        if (!userInput.contains(DELIMITED)) {
            LOGGER.warn("ERROR: Invalid user input");
            return null;
        }

        final Point2D startPoint;
        final Point2D targetPoint;
        try {
            final String[] splitInput = userInput.split(DELIMITED);
            final String startPointString = splitInput[0];
            final String targetPointString = splitInput[1];

            startPoint = Point2D.from(startPointString);
            targetPoint = Point2D.from(targetPointString);
        } catch (final Exception e) {
            LOGGER.warn("ERROR: Invalid user input");
            return null;
        }


        final Piece pieceChosen = this.chessGame.getBoard().getPiece(startPoint);
        if (pieceChosen == null) {
            LOGGER.warn("ERROR: No piece selected. Try again: ");
            return null;
        } else if (!player.getPieces().containsKey(pieceChosen.getId())) {
            LOGGER.warn("ERROR: Wrong player's piece selected. Try again: ");
            return null;

        }

        final PieceToPoint2DMove pieceToPoint2DMove = PieceToPoint2DMove.builder()
                .setTargetPoint(targetPoint)
                .setPiece(pieceChosen)
                .build();

        return this.moveValidityChecker.isMoveValid(pieceToPoint2DMove) ? pieceToPoint2DMove : null;
    }


    public long readExecuteMove(final Player player) {
        final UserAssistant userAssistant = new UserAssistant();
        PieceToPoint2DMove pieceToPoint2DMove = null;
        do {
            final String HELP_COMMAND = "help";
            LOGGER.info("Please input your move. For help, enter '" + HELP_COMMAND + "':");
            final String userInput = consoleInputReader.getUserInput();
            if (userInput.equalsIgnoreCase(HELP_COMMAND)) {
                userAssistant.displayControls();
            } else {
                pieceToPoint2DMove = this.parseAndInspectMove(userInput, player);
            }
        } while (pieceToPoint2DMove == null);

        final PieceDestroyer pieceDestroyer = new PieceDestroyer(this.chessGame);
        final long changeInPoints = pieceDestroyer.destroyPieceIfExistsInPosition(pieceToPoint2DMove, false);
        pieceToPoint2DMove.getPiece().setPosition(pieceToPoint2DMove.getTargetPoint());
        return changeInPoints;
    }
}
