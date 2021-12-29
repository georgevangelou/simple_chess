package chess.resources.interfaces;

import org.immutables.criteria.Criteria;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public interface Identifiable {

    /**
     * The unique id of this object. This is a unique id of every {@link Identifiable} instance.
     * There should never be two {@link Identifiable} with the same ID.
     *
     * @return
     */
    @Criteria.Id
    public String getId();
}