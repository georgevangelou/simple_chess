package chess.suppliers;

import chess.logic.MoveValidityChecker;
import com.google.common.base.Supplier;

import java.io.Serializable;

public final class MoveValidityCheckerSupplier implements Supplier<MoveValidityChecker>, Serializable {
    private MoveValidityChecker moveValidityChecker = null;

    public MoveValidityCheckerSupplier() {
    }


    public void setMoveValidityChecker(final MoveValidityChecker moveValidityChecker) {
        this.moveValidityChecker = moveValidityChecker;
    }

    @Override
    public MoveValidityChecker get() {
        return this.moveValidityChecker;
    }
}
