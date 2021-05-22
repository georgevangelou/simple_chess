package chess.resources.interfaces;

import chess.resources.utilities.IdAutogenerator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.immutables.criteria.Criteria;
import org.immutables.value.Value;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public interface Identifiable {
    /**
     * @param items
     * @param <T>
     * @return
     */
    public static <T extends Identifiable> Map<String, T> toMap(final Collection<T> items) {
        Objects.requireNonNull(items);
        return items.stream().collect(Collectors.toMap(Identifiable::getId, Function.identity()));
    }

    /**
     * The unique id of this object
     * This is a unique id of every {@link Identifiable} instance.
     * There should never be two {@link Identifiable} with the same ID.
     *
     * @return
     */
    @JsonProperty("_id")
    @Criteria.Id
    @Value.Default
    public default String getId() {
        final String newID = IdAutogenerator.generateId();

        try {
            final Method setIdMethod = this.getClass().getMethod("setId", String.class);
            if (setIdMethod != null) {
                setIdMethod.invoke(this, newID);
            }
        } catch (final Exception e) {
            //Ignore on purpose.
            //LOGGER.trace(ExceptionUtils.getStackTrace(e));
        }
        return newID;
    }
}