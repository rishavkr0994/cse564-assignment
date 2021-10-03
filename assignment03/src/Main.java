import javax.swing.*;

public class Main extends JFrame {
    private static final int DEFAULT_WINDOW_HEIGHT = 500;
    private static final int DEFAULT_WINDOW_WIDTH = 800;

    public Main()
    {
        super("TSP");
        TSP tsp = new TSP();
        WorkSpace workSpace = new WorkSpace();
        workSpace.addObserver(tsp);
        WorkSpacePanel drawArea = new WorkSpacePanel();
        add(drawArea);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        main.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        main.setVisible(true);
    }
}
