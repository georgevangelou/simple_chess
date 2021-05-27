package chess.resources.pieces;

import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.execution.ChessGame;
import chess.players.Player;
import chess.players.PlayerColor;
import chess.space.environment.Board2D;
import chess.space.environment.Point2D;
import chess.space.movement.VerticallyAvailableMovesFinder;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public final class Pawn extends Piece {

    public Pawn(final Point2D position) {
        super("Pawn", ValuesOfPieces.PAWN, StringVisualRepresentationOfPieces.PAWN, position);
    }


    @Override
    public List<Point2D> getAccessiblePositionsIgnoringCollisions(final ChessGame game) {
        Preconditions.checkNotNull(game);
        // TODO: x+-1 if able to attack

        final Player playerOwningPiece = game.getPlayerOwningPiece(this.getId());

        // Different players' pawns move in different direction.
        VerticallyAvailableMovesFinder.Direction direction = VerticallyAvailableMovesFinder.Direction.toBottom;
        int yInitial = 1;
        if (playerOwningPiece.getPlayerColor().equals(PlayerColor.black)) {
            direction = VerticallyAvailableMovesFinder.Direction.toTop;
            yInitial = 6;
        }

        int maxSteps = 1;
        if (this.getPosition().getY() == yInitial) {
            maxSteps = 2;
        }

        final VerticallyAvailableMovesFinder moveFinder = new VerticallyAvailableMovesFinder(game, this, maxSteps, direction);
        return List.copyOf(new ArrayList<>(moveFinder.getAvailableMoves()));
    }
}
