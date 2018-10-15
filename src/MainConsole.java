import com.haaivda.g2048.engine.Board;
import com.haaivda.g2048.engine.Move;

public class MainConsole {
    public static void main(String[] args) {
        Board board = Board.createInitialBoard();
        System.out.println(board.toString() + "\n");

        for(int i = 0; i < 10; i++) {
            board = board.makeMove(Move.DOWN);
            System.out.println(board.toString() + "\n");
        }
    }
}
