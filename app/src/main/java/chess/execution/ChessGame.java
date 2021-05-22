package chess.execution;

import chess.players.AbstractPlayer;
import chess.players.HumanPlayer;
import chess.players.PlayerColor;
import chess.space.Board2D;
import chess.utilities.BoardPrinter;
import chess.utilities.HumanMoveReaderAndExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class ChessGame {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChessGame.class);
    private final AbstractPlayer player1;
    private final AbstractPlayer player2;
    private final Board2D board;
    private final BoardPrinter boardPrinter;
    private AbstractPlayer playerNow;


    public ChessGame() {
        this.board = new Board2D();
        this.boardPrinter = new BoardPrinter(board);

        final HumanMoveReaderAndExecutor humanMoveReaderAndExecutor = new HumanMoveReaderAndExecutor(this.getBoard());
        player1 = new HumanPlayer(PlayerColor.white, humanMoveReaderAndExecutor);
        player2 = new HumanPlayer(PlayerColor.black, humanMoveReaderAndExecutor);
        playerNow = player1.getPlayerColor().equals(PlayerColor.white) ? player1 : player2; // If player1 is white, they start.

        this.board.fillBoardWithPieces(player1, player2);
    }


    public void nextPlayersTurn() {
        LOGGER.info("Player [" + playerNow.getPlayerColor() + "] now plays:");
        playerNow.play();
        playerNow = (playerNow == player2) ? player1 : player2; // Change player at the end of the turn.
    }


    public AbstractPlayer getPlayer1() {
        return player1;
    }


    public AbstractPlayer getPlayer2() {
        return player2;
    }


    public Board2D getBoard() {
        return board;
    }


    public BoardPrinter getBoardPrinter() {
        return boardPrinter;
    }
}
