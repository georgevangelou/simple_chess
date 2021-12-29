package chess.visualization.graphical;

import chess.execution.ChessGame;
import chess.resources.pieces.Piece;
import chess.visualization.BoardVisualizer;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Collection;


/**
 * @author George Evangelou
 * Created on: 2021-12-29
 */
public class BoardVisualizerGraphical extends JFrame implements MouseListener, MouseMotionListener, BoardVisualizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoardVisualizerGraphical.class);
    private final int WIDTH = 700;
    private final int HEIGHT = 700;
    private final int PADDING = 20;
    private final int CELL_SIZE = (HEIGHT - 2 * PADDING) / 8;

    JLayeredPane layeredPane;
    JPanel graphicalChessBoard;
    JLabel graphicalChessPiece;
    int xAdjustment;
    int yAdjustment;
    private final ChessGame chessGame;

    public BoardVisualizerGraphical(final ChessGame chessGame) throws IOException {
        Preconditions.checkNotNull(chessGame);

        this.chessGame = chessGame;
        Dimension boardSize = new Dimension(WIDTH, HEIGHT);

        //  Use a Layered Pane for this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane
        graphicalChessBoard = new JPanel();
        layeredPane.add(graphicalChessBoard, JLayeredPane.DEFAULT_LAYER);
        graphicalChessBoard.setLayout(new GridLayout(8, 8));
        graphicalChessBoard.setPreferredSize(boardSize);
        graphicalChessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            graphicalChessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground(i % 2 == 0 ? Color.black : Color.white);
            else
                square.setBackground(i % 2 == 0 ? Color.white : Color.black);
        }
    }


    public void showBoardView() {
        //Add a few pieces to the board
        PieceVisualizationDemuxer pieceVisualizationDemuxer = new PieceVisualizationDemuxer();
        ImageResizer imageResizer = new ImageResizer();

        final Collection<Piece> pieces = this.chessGame.getBoard().getIdsPieces().values();
        LOGGER.info(pieces.toString());
        for (Piece piece : pieces) {
            final int x = piece.getPosition().getX();
            final int y = piece.getPosition().getY();
            final String filename = pieceVisualizationDemuxer.getFilenameForPiece(piece);
            LOGGER.info(filename);

            try {
                ImageIcon imageIcon = new ImageIcon(imageResizer.resize(filename, CELL_SIZE, CELL_SIZE));
                JLabel graphicalPiece = new JLabel(imageIcon);
                JPanel panel = (JPanel) graphicalChessBoard.getComponent(x + 8 * y);
                panel.add(graphicalPiece);
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
            }

        }
    }


    public void mousePressed(MouseEvent e) {
        graphicalChessPiece = null;
        Component c = graphicalChessBoard.findComponentAt(e.getX(), e.getY());

        if (c instanceof JPanel)
            return;

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        graphicalChessPiece = (JLabel) c;
        graphicalChessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        graphicalChessPiece.setSize(graphicalChessPiece.getWidth(), graphicalChessPiece.getHeight());
        layeredPane.add(graphicalChessPiece, JLayeredPane.DRAG_LAYER);
    }

    //Move the chess piece around

    public void mouseDragged(MouseEvent me) {
        if (graphicalChessPiece == null) return;
        graphicalChessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    //Drop the chess piece back onto the chess board

    public void mouseReleased(MouseEvent e) {
        if (graphicalChessPiece == null) return;

        graphicalChessPiece.setVisible(false);
        Component c = graphicalChessBoard.findComponentAt(e.getX(), e.getY());

        if (c instanceof JLabel) {
            Container parent = c.getParent();
            parent.remove(0);
            parent.add(graphicalChessPiece);
        } else {
            Container parent = (Container) c;
            parent.add(graphicalChessPiece);
        }

        graphicalChessPiece.setVisible(true);
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public static BoardVisualizerGraphical createGraphicBoard(final ChessGame chessGame) throws IOException {
        BoardVisualizerGraphical board = new BoardVisualizerGraphical(chessGame);
        board.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        board.pack();
        board.setResizable(true);
        board.setLocationRelativeTo(null);
        board.setVisible(true);
        return board;
    }
}
