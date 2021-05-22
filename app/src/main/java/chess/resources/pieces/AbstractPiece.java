package chess.resources.pieces;

import chess.execution.ChessGame;
import chess.resources.interfaces.Identifiable;
import chess.resources.interfaces.Movable;
import chess.resources.interfaces.Named;
import chess.space.Point2D;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public abstract class AbstractPiece implements Named, Identifiable, Movable {
    private final  String name;
    private final long value;
    private final String visualRepresentation;
    private Point2D position;

    protected AbstractPiece(final String name, final long value, final String visualRepresentation, final Point2D position) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(visualRepresentation);
        Preconditions.checkNotNull(position);

        this.name = name;
        this.value = value;
        this.visualRepresentation = visualRepresentation;
        this.position = position;
    }


    protected AbstractPiece(final String name, final long value, final Point2D position) {
        this(name, value, "X", position);
    }

    public String getName() {
        return this.name;
    }


    public long getValue() {
        return this.value;
    }


    public Point2D getPosition() {
        return this.position;
    }


    public void setPosition(final Point2D position) {
        this.position = position;
    }


    public String getVisualRepresentation() {
        return this.visualRepresentation;
    }


    public abstract List<Point2D> getAccessiblePositionsIgnoringCollisions(final ChessGame game);
}
