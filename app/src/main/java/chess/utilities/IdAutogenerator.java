package chess.utilities;

import java.util.UUID;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class IdAutogenerator {
    private IdAutogenerator() {
        throw new AssertionError("Do not instantiate");
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
