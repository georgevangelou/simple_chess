package chess.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleInputReader.class);

    public String getUserInput() {
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            final BufferedReader reader = new BufferedReader(inputStreamReader);
            return reader.readLine();
        } catch (final IOException ioException) {
            LOGGER.warn("ERROR: Invalid user input");
            return "";
        }
    }
}
