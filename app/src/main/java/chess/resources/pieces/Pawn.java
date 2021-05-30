package chess.resources.pieces;

import chess.constants.StringVisualRepresentationOfPieces;
import chess.constants.ValuesOfPieces;
import chess.execution.ChessGame;
import chess.players.Player;
import chess.players.PlayerColor;
import chess.resources.immutables.Point2D;
import chess.space.movement.DiagonallyAvailableMovesFinder;
import chess.space.movement.VerticallyAvailableMovesFinder;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public final class Pawn extends Piece {

    public Pawn(final Point2D position) {
        super("Pawn", ValuesOfPieces.PAWN, StringVisualRepresentationOfPieces.PAWN, position);
    }


    @Override
    public List<Point2D> getLawfulMoves(final ChessGame game) {
        Preconditions.checkNotNull(game);

        final Player playerOwningPiece = game.getPlayerOwningPiece(this.getId());
        final boolean playerIsBlack = playerOwningPiece.getPlayerColor().equals(PlayerColor.black);
        // Different players' pawns move in different direction.
        VerticallyAvailableMovesFinder.Direction direction = VerticallyAvailableMovesFinder.Direction.toBottom;
        int yInitial = 1;
        if (playerIsBlack) {
            direction = VerticallyAvailableMovesFinder.Direction.toTop;
            yInitial = 6;
        }

        int maxSteps = 1;
        if (this.getPosition().getY() == yInitial) {
            maxSteps = 2;
        }

        final DiagonallyAvailableMovesFinder.Direction directionRight = playerIsBlack ? DiagonallyAvailableMovesFinder.Direction.toTopRight : DiagonallyAvailableMovesFinder.Direction.toBottomRight;
        final DiagonallyAvailableMovesFinder.Direction directionLeft = playerIsBlack ? DiagonallyAvailableMovesFinder.Direction.toTopLeft : DiagonallyAvailableMovesFinder.Direction.toBottomLeft;

        final VerticallyAvailableMovesFinder verticalMoveFinder = new VerticallyAvailableMovesFinder(game, this, maxSteps, direction);
        final DiagonallyAvailableMovesFinder rightDiagonalMovesFinderForAttacks = new DiagonallyAvailableMovesFinder(game, this, 1, directionRight);
        final DiagonallyAvailableMovesFinder leftDiagonalMovesFinderForAttacks = new DiagonallyAvailableMovesFinder(game, this, 1, directionLeft);


        // VerticallyAvailableMovesFinder may return position containing enemy piece, but Pawn cannot attach vertically.
        //  Thus, any such case must be removed.
        final List<Point2D> verticalPositionsForThisPawnWithoutPositionsContainingOtherPiece =
                verticalMoveFinder.getAvailableMoves()
                        .stream().filter(a -> (game.getBoard().getPiece(a) == null))
                        .collect(Collectors.toList());

        final List<Point2D> rightDiagonalPositionsForThisPawnOnlyWithPositionsContainingOtherPiece =
                rightDiagonalMovesFinderForAttacks.getAvailableMoves()
                        .stream().filter(a -> (game.getBoard().getPiece(a) != null))
                        .collect(Collectors.toList());

        final List<Point2D> leftDiagonalPositionsForThisPawnOnlyWithPositionsContainingOtherPiece =
                leftDiagonalMovesFinderForAttacks.getAvailableMoves()
                        .stream().filter(a -> (game.getBoard().getPiece(a) != null))
                        .collect(Collectors.toList());

        final List<Point2D> accessiblePositions = new ArrayList<>();
        accessiblePositions.addAll(verticalPositionsForThisPawnWithoutPositionsContainingOtherPiece);
        accessiblePositions.addAll(rightDiagonalPositionsForThisPawnOnlyWithPositionsContainingOtherPiece);
        accessiblePositions.addAll(leftDiagonalPositionsForThisPawnOnlyWithPositionsContainingOtherPiece);
        return List.copyOf(accessiblePositions);
    }
}
