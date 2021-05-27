package chess.execution;

import chess.players.HumanPlayer;
import chess.players.Player;
import chess.players.PlayerColor;
import chess.resources.pieces.King;
import chess.resources.pieces.Piece;
import chess.space.environment.Board2D;
import chess.utilities.HumanMoveReaderAndExecutor;
import chess.utilities.KingIsSafeChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Captures the state of a Chess game instance.
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


    public ChessGame() {
        this.board = new Board2D();

        // TODO: Passing 'this' as a parameter while 'this' is not initialized yet, is bad.
        final HumanMoveReaderAndExecutor humanMoveReaderAndExecutor = new HumanMoveReaderAndExecutor(this);
        playerWhite = new HumanPlayer(PlayerColor.white, humanMoveReaderAndExecutor);
        playerBlack = new HumanPlayer(PlayerColor.black, humanMoveReaderAndExecutor);
        playerNow = playerWhite.getPlayerColor().equals(PlayerColor.white) ? playerWhite : playerBlack; // If player1 is white, they start.

        this.board.fillBoardWithPieces(playerWhite, playerBlack);
    }


    public void nextPlayersTurn() {
        LOGGER.info("Player " + playerNow.getPlayerColor() + " (" + playerNow.getType() + ") now plays.");

        if (!playerNow.isKingSafe(this)) {
            LOGGER.warn("CHECK: The King of Player " + playerNow.getPlayerColor() + " (" + playerNow.getType() + ") is threatened!");
            for (final PieceToPoint2DMove move : playerNow.getPossibleMoves(this)) {
                if (new KingIsSafeChecker(true).willKingBeSafeAfterMove(this, move)) {
                    break;
                }
            }
            endGame(getPlayerNotPlayingNow());
        } else if (playerNow.getPossibleMoves(this).size() == 0) {
            endGame(null);
        } else {
            playerNow.play(this);
            playerNow = (playerNow == playerBlack) ? playerWhite : playerBlack; // Change player at the end of the turn.
        }
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


    private void endGame(final Player playerWon) {
        if (playerWon==null) {
            LOGGER.warn("GAME ENDED: Stalemate.");
            return;
        }
        LOGGER.warn("GAME ENDED:  Player " + playerWon.getPlayerColor() + " (" + playerWon.getType() + ") won!");
        this.isFinished = true;
    }
}
