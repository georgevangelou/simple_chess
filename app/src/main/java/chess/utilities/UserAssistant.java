package chess.utilities;

import chess.players.HumanPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-30
 */
public class UserAssistant {
    private static final Logger LOGGER = LoggerFactory.getLogger("User Assistant");

    public void displayControls() {
        LOGGER.info("" +
                "\n-----------------------------------------------HELP ------------------------------------------------------------" +
                "\nIn order to control your pieces, you need to write down 2 points: the starting point and the target point." +
                "\nThis is done by writing down the X,Y coordinates of the respective points, as noted on the board." +
                "\n\nFirst, the starting point is written down, with its coordinates split by a comma ','." +
                "\nThe target point is written down in the same way, but it must be separated by the former one by a space ' '" +
                "\nFor example, in order to move a piece from point{x=1,y=2} to point{x=3,y=3}, one must write '1,2 3,3' in the command line" +
                "\nand then press ENTER to register the command." +
                "\n\nIf the move is lawful, then it will be immediately executed. Otherwise," +
                "\nan error message with provide information on what went wrong (e.g. invalid input, unlawful move. etc.)" +
                "\n----------------------------------------------------------------------------------------------------------------");
    }
}
