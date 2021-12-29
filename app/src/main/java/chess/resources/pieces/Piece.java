package chess.resources.pieces;

import chess.execution.ChessGame;
import chess.resources.immutables.Point2D;
import chess.resources.interfaces.Identifiable;
import chess.resources.interfaces.Movable;
import chess.resources.interfaces.Named;
import chess.resources.utilities.IdAutogenerator;
import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.List;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public abstract class Piece implements Identifiable, Named, Movable, Serializable {
    private final String id;
    private final String name;
    private final long value;
    private final String visualRepresentation;
    private Point2D position;

    protected Piece(final String name, final long value, final String visualRepresentation, final Point2D position) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(visualRepresentation);
        Preconditions.checkNotNull(position);

        this.id = IdAutogenerator.generateId();
        this.name = name;
        this.value = value;
        this.visualRepresentation = visualRepresentation;
        this.position = position;
    }


    protected Piece(final String name, final long value, final Point2D position) {
        this(name, value, "X", position);
    }


    @Override
    public String getId() {
        return this.id;
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


    public abstract List<Point2D> getLawfulMoves(final ChessGame game);
}
