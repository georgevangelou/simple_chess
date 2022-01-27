package chess.execution;

import chess.players.PlayerType;
import chess.visualization.console.BoardPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main class. It creates a Player-vs-Player {@link ChessGame} as an application.
 *
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class ChessGameApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChessGameApp.class);

    public static void main(final String[] args) {
        final ChessGame game = new ChessGame(PlayerType.human, PlayerType.human);
        final BoardPrinter boardPrinter = new BoardPrinter(game);

        while (!game.isFinished()) {
            LOGGER.info(boardPrinter.printBoard());
            game.nextPlayersTurn();
        }
        LOGGER.info("The game has ended.");
    }
}