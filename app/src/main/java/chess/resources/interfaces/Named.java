package chess.resources.interfaces;

/**
 * @author Georgios Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public interface Named {

    /**
     * A human friendly name. While this should not be used for identification, it is better to be unique and clear.
     *
     * @return
     */
    public String getName();
}
