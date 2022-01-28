package chess.resources.pieces;

import chess.game.ChessGame;
import chess.players.Player;
import chess.resources.immutables.Point2D;
import chess.resources.interfaces.Identifiable;
import chess.resources.interfaces.Movable;
import chess.resources.interfaces.Named;
import chess.resources.utilities.IdAutogenerator;
import chess.space.environment.Board2D;
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
    private final Player owner;
    private final long value;
    private final String visualRepresentation;
    private Point2D position;


    protected Piece(final String name, final Player owner, final long value, final String visualRepresentation, final Point2D position) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(owner);
        Preconditions.checkNotNull(visualRepresentation);
        Preconditions.checkNotNull(position);

        this.id = IdAutogenerator.generateId();
        this.name = name;
        this.owner = owner;
        this.value = value;
        this.visualRepresentation = visualRepresentation;
        this.position = position;
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


    public Player getOwner() {
        return owner;
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
