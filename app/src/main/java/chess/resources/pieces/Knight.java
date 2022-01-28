package chess.resources.pieces;

import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.game.ChessGame;
import chess.players.Player;
import chess.resources.immutables.Point2D;
import chess.space.movement.GammaAvailableMovesFinder;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public final class Knight extends Piece {

    public Knight(final Player owner, final Point2D position) {
        super("Knight", owner, ValuesOfPieces.KNIGHT, StringVisualRepresentationOfPieces.KNIGHT, position);
    }

    @Override
    public List<Point2D> getLawfulMoves(final ChessGame game) {
        Preconditions.checkNotNull(game);

        final GammaAvailableMovesFinder movesFinder = new GammaAvailableMovesFinder(this);
        return List.copyOf(movesFinder.getAvailableMoves(game));
    }
}
