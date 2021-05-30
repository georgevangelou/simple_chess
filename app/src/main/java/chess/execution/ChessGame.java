package chess.execution;

import chess.players.*;
import chess.resources.pieces.Piece;
import chess.space.environment.Board2D;
import chess.utilities.HumanMoveReaderAndExecutor;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Implements a Chess instance.
 *
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class ChessGame implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChessGame.class);
    private final Player playerWhite;
    private final Player playerBlack;
    private final Board2D board;
    private Player playerNow;
    private boolean isFinished = false;


    public ChessGame(final PlayerType playerWhiteType, final PlayerType playerBlackType) {
        Preconditions.checkNotNull(playerWhiteType);
        Preconditions.checkNotNull(playerBlackType);

        this.board = new Board2D();

        // TODO: Passing 'this' as a parameter while 'this' is not initialized yet, is bad.
        final HumanMoveReaderAndExecutor humanMoveReaderAndExecutor = new HumanMoveReaderAndExecutor(this);

        playerWhite = playerWhiteType.equals(PlayerType.human) ?
                new HumanPlayer(PlayerColor.white, humanMoveReaderAndExecutor) :
                new ComputerPlayer(PlayerColor.white);

        playerBlack = playerBlackType.equals(PlayerType.human) ?
                new HumanPlayer(PlayerColor.black, humanMoveReaderAndExecutor) :
                new ComputerPlayer(PlayerColor.black);

        playerNow = playerWhite;
        this.board.fillBoardWithPieces(playerWhite, playerBlack);
    }


    public void nextPlayersTurn() {
        LOGGER.info("Player " + playerNow.getPlayerColor() + " (" + playerNow.getType() + ") now plays.");

        if (playerNow.getPossibleMoves(this).size() == 0) {
            // Stalemate
            endGame(null);
        } else if (!playerNow.isKingSafe(this)) {
            // King is in check
            LOGGER.warn("CHECK: The King of Player " + playerNow.getPlayerColor() + " (" + playerNow.getType() + ") is threatened!");
            boolean moveThatSavesKingExists = false;
            for (final PieceToPoint2DMove move : playerNow.getPossibleMoves(this)) {
                if (new KingIsSafeChecker(true).willKingBeSafeAfterMove(this, move)) {
                    moveThatSavesKingExists = true;
                }
            }
            if (!moveThatSavesKingExists) {
                // King cannot escape check. Game ends.
                endGame(getPlayerNotPlayingNow());
            } else {
                // King can escape check. Game continues.
                playerNow.play(this);
                changePlayer();
            }
        } else {
            // King is not in check.
            playerNow.play(this);
            changePlayer();
        }
    }


    public void changePlayer() {
        playerNow = (playerNow == playerBlack) ? playerWhite : playerBlack; // Change player at the end of the turn.
    }


    public Player getPlayerWhite() {
        return this.playerWhite;
    }


    public Player getPlayerBlack() {
        return this.playerBlack;
    }


    public Player getPlayerNow() {
        return this.playerNow;
    }


    public Player getPlayerNotPlayingNow() {
        return (this.playerNow == this.playerBlack) ? this.playerWhite : this.playerBlack;
    }


    public Board2D getBoard() {
        return board;
    }


    /**
     * @param pieceId matching a {@link Piece#getId()}
     * @return which {@link Player} owns this {@link Piece}
     */
    public Player getPlayerOwningPiece(final String pieceId) {
        if (this.playerWhite.pieceBelongsToPlayer(pieceId)) {
            return this.playerWhite;
        } else {
            return this.playerBlack;
        }
    }


    /**
     * TODO: Check if there is a stalemate.
     *
     * @return
     */
    public boolean isFinished() {
        return this.isFinished;
    }


    private void endGame(final Player winner) {
        if (winner == null) {
            LOGGER.warn("GAME ENDED: Stalemate.");
            return;
        }
        LOGGER.warn("GAME ENDED:  Player " + winner.getPlayerColor() + " (" + winner.getType() + ") won!");
        this.isFinished = true;
    }
}
