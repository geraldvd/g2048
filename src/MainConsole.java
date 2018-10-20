import com.haaivda.g2048.QLearning.QTable;
import com.haaivda.g2048.engine.Move;

class MainConsole {
    public static void main(String[] args) {
        QTable qTable = new QTable();
        qTable.qLearning(10000);
        System.out.println("Learning finished");

        qTable.playExploitationGame();
    }
}
