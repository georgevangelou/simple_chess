package chess.space.environment;

import chess.resources.immutables.configuration.ImmutablesConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import org.immutables.value.Value;

import java.io.Serializable;
import java.util.Objects;

/**
 * Similar functionality with java.awt.geom.{@link java.awt.geom.Point2D}, but it is immutable.
 *
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
@ImmutablesConfiguration
@Value.Immutable
@JsonSerialize(as = ImmutablePoint2D.class)
@JsonDeserialize(as = ImmutablePoint2D.class)
public interface Point2D extends Serializable {
    final String DELIMITER = ",";


    public int getX();


    public int getY();


    /**
     * Returns true if objects a,anotherPoint are equal.
     *
     * @param anotherPoint objects
     * @return a equal elements with anotherPoint
     */
    @Value.Default
    @JsonIgnore
    public default boolean isEquivalent(
            final Point2D anotherPoint
    ) {
        Objects.requireNonNull(anotherPoint);

        return (this.getX() == anotherPoint.getX() &&
                this.getY() == anotherPoint.getY()
        );
    }


    public static Point2D from(final String str) throws NumberFormatException {
        Preconditions.checkNotNull(str);
        return builder()
                .setX(Integer.parseInt(str.split(DELIMITER)[0]))
                .setY(Integer.parseInt(str.split(DELIMITER)[1]))
                .build();
    }


    public static ImmutablePoint2D.Builder from(final Point2D point2D) {
        Preconditions.checkNotNull(point2D);
        return builder().from(point2D);
    }


    public static ImmutablePoint2D.Builder builder() {
        return ImmutablePoint2D.builder();
    }
}
