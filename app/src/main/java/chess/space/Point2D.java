package chess.space;

import chess.resources.immutables.configuration.ImmutablesConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
@ImmutablesConfiguration
@Value.Immutable
@JsonSerialize(as = ImmutablePoint2D.class)
@JsonDeserialize(as = ImmutablePoint2D.class)
public interface Point2D extends Serializable {
    final String DELIMITER = ",";

    /**
     * Returns true if objects a,b are equal.
     *
     * @param a objects
     * @param b objects
     * @return a equal elements with b
     */
    public static boolean areEqual(
            final Point2D a,
            final Point2D b
    ) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        return (a.getX() == b.getX() &&
                a.getY() == b.getY()
        );
    }

    /**
     * @return a new {@link Point} at (0,0,0)
     */
    public static Point2D createZeroPoint() {
        final Point2D zeroPoint = Point2D.builder()
                .setX(0)
                .setY(0)
                .build();
        return zeroPoint;
    }

    public static Point2D from(final String str) {
        return builder()
                .setX(Integer.parseInt(str.split(DELIMITER)[0]))
                .setY(Integer.parseInt(str.split(DELIMITER)[1]))
                .build();
    }

    /**
     * @return
     */
    public static ImmutablePoint2D.Builder builder() {
        return ImmutablePoint2D.builder();
    }

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
    public default boolean isEqual(
            final Point2D anotherPoint
    ) {
        Objects.requireNonNull(anotherPoint);

        return (this.getX() == anotherPoint.getX() &&
                this.getY() == anotherPoint.getY()
        );
    }
}
