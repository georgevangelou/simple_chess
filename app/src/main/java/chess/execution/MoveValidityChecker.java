package chess.execution;

import chess.constants.BoardDimensions;
import com.google.common.base.Preconditions;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
public class MoveValidityChecker {
    private final ChessGame game;

    public MoveValidityChecker(final ChessGame game) {
        this.game = game;
    }

    public boolean isMoveValid(final PieceToPoint2DMove move) {
        Preconditions.checkNotNull(move);

//        move.getTargetPoint()

        return (pointIsInsideTheBoard(move) &&
                pointIsWithinReachOfPiece(move) &&
                pointIsIsNotOccupiedByFrientlyPawn() &&
                pathIsNotBlockedAndPieceIsNotKnight() &&
                kingWillNotBeInDangerAfterMove()
        );
    }


    private boolean pointIsInsideTheBoard(final PieceToPoint2DMove move) {
        final int x = move.getTargetPoint().getX();
        final int y = move.getTargetPoint().getY();
        return 0 < x && x < BoardDimensions.SIZE_X && 0 < y && y < BoardDimensions.SIZE_Y;
    }


    private boolean pointIsWithinReachOfPiece(final PieceToPoint2DMove move) {

        return true;
    }


    private boolean pointIsIsNotOccupiedByFrientlyPawn() {
        return true;
    }


    private boolean kingWillNotBeInDangerAfterMove() {
        return true;
    }


    private boolean pathIsNotBlockedAndPieceIsNotKnight() {
        return true;
    }
}
