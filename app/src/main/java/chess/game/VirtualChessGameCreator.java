package chess.game;

import org.apache.commons.lang.SerializationUtils;

/**
 * @author George Evangelou
 * Created on: 2022-01-28
 */
public class VirtualChessGameCreator {
    private final ChessGame virtualGame;

    public VirtualChessGameCreator(final ChessGame chessGame) {
        this.virtualGame = (ChessGame) SerializationUtils.clone(chessGame);
    }


    public final ChessGame getVirtualGame() {
        return this.virtualGame;
    }
}
