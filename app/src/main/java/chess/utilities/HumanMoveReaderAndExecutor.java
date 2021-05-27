package chess.utilities;

import chess.execution.ChessGame;
import chess.execution.MoveValidityChecker;
import chess.execution.PieceToPoint2DMove;
import chess.players.Player;
import chess.resources.pieces.Piece;
import chess.space.environment.Board2D;
import chess.space.environment.Point2D;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
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

        final String startPointString;
        final String targetPointString;
        try {
            final String[] splitInput = userInput.split(DELIMITED);
            startPointString = splitInput[0];
            targetPointString = splitInput[1];
        } catch (final Exception e) {
            LOGGER.warn("ERROR: Invalid user input");
            return null;
        }

        final Point2D startPoint = Point2D.from(startPointString);
        final Point2D targetPoint = Point2D.from(targetPointString);

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
        PieceToPoint2DMove pieceToPoint2DMove = null;
        do {
            LOGGER.info("Please input your move (e.g. 3,4 5,5):");
            final String userInput = consoleInputReader.getUserInput();
            pieceToPoint2DMove = this.parseAndInspectMove(userInput, player);
        } while (pieceToPoint2DMove == null);

        final long changeInPoints = destroyPieceIfPreexistentInPosition(pieceToPoint2DMove);
        pieceToPoint2DMove.getPiece().setPosition(pieceToPoint2DMove.getTargetPoint());
        return changeInPoints;
    }


    /**
     * If an {@link Piece} resides at the {@link Point2D} of interest, remove it from {@link Player} and {@link Board2D}.
     *
     * @param pieceToPoint2DMove
     */
    private long destroyPieceIfPreexistentInPosition(final PieceToPoint2DMove pieceToPoint2DMove) {
        Preconditions.checkNotNull(pieceToPoint2DMove);

        final Piece preexistingPiece = this.chessGame.getBoard().getPiece(pieceToPoint2DMove.getTargetPoint());
        if (preexistingPiece != null) {
            this.chessGame.getBoard().removePiece(preexistingPiece.getId());
            final Player playerWhosePieceWasTheKiller = this.chessGame.getPlayerOwningPiece(pieceToPoint2DMove.getPiece().getId());
            final Player playerWhosePieceWasDestroyed = this.chessGame.getPlayerOwningPiece(preexistingPiece.getId());
            playerWhosePieceWasDestroyed.destroyPiece(preexistingPiece.getId());
            LOGGER.warn("PIECE CAPTURED: " + preexistingPiece.getName() + " [" + playerWhosePieceWasDestroyed.getPlayerColor() + "] was captured by " + pieceToPoint2DMove.getPiece().getName() + " [" + playerWhosePieceWasTheKiller.getPlayerColor() + "].");
            LOGGER.info("POINTS: White: " + chessGame.getPlayerWhite().getCurrentPoints() + ", Black: " + chessGame.getPlayerBlack().getCurrentPoints());
            return preexistingPiece.getValue();
        }
        return 0;
    }
}
