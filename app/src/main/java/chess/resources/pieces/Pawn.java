package chess.resources.pieces;

import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.execution.ChessGame;
import chess.players.AbstractPlayer;
import chess.players.PlayerColor;
import chess.space.Point2D;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public final class Pawn extends AbstractPiece {

    public Pawn(final Point2D position) {
        super("Pawn", ValuesOfPieces.PAWN, StringVisualRepresentationOfPieces.PAWN, position);
    }


    @Override
    public List<Point2D> getAccessiblePositionsIgnoringCollisions(final ChessGame game) {
        Preconditions.checkNotNull(game);
        // TODO: Implement how to chose y+1 or y-1 depending on the player
        // TODO: +2 if at y=1/y=length-2
        // TODO: x+-1 if able to attack

        final AbstractPlayer player = game.getPlayerOwningPiece(this.getId());
        final List<Point2D> accessiblePositions = new ArrayList<>();

        // Different players' pawns move in different direction.
        int moveModifier = 1;
        if (player.getPlayerColor().equals(PlayerColor.black)) {
            moveModifier = -1;
        }

        final Point2D singleMove = Point2D.builder()
                .setX(this.getPosition().getX())
                .setY(this.getPosition().getY() + moveModifier)
                .build();
        if (game.getBoard().isWithinBoard(singleMove)) {
            accessiblePositions.add(singleMove);
        }

        if (this.getPosition().getY() == 1) {
            final Point2D doubleMove = Point2D.builder()
                    .setX(this.getPosition().getX())
                    .setY(this.getPosition().getY() + 2 * moveModifier)
                    .build();
            accessiblePositions.add(doubleMove);
        }
        return List.copyOf(accessiblePositions);
    }
}
