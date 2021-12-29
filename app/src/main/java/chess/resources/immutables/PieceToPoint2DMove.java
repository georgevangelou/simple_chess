package chess.resources.immutables;

import chess.resources.immutables.configuration.ImmutablesConfiguration;
import chess.resources.pieces.Piece;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.io.Serializable;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
@ImmutablesConfiguration
@Value.Immutable
@JsonSerialize(as = ImmutablePieceToPoint2DMove.class)
@JsonDeserialize(as = ImmutablePieceToPoint2DMove.class)
public interface PieceToPoint2DMove extends Serializable {

    public Point2D getTargetPoint();

    public Piece getPiece();

    public static ImmutablePieceToPoint2DMove.Builder builder() {
        return ImmutablePieceToPoint2DMove.builder();
    }
}
