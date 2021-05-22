package chess.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Reade input from console.
 *
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class ConsoleInputReader {
    public String getUserInput() {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (final IOException ioException) {
            return "";
        }
    }
}
