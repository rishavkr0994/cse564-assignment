import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int DEFAULT_WINDOW_HEIGHT = 500;
    private static final int DEFAULT_WINDOW_WIDTH = 800;

    public MainFrame() {
        super("TSP");
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem mItemNew = new JMenuItem("New");
        JMenuItem mItemLoad = new JMenuItem("Load");
        JMenuItem mItemSave = new JMenuItem("Save");

        fileMenu.add(mItemNew);
        fileMenu.add(mItemLoad);
        fileMenu.add(mItemSave);

        menuBar.add(fileMenu);

        add(menuBar, BorderLayout.NORTH);

        WorkSpacePanel drawArea = new WorkSpacePanel();
        TSP tsp = new TSP();
        WorkSpace.getInstance().addObserver(tsp);
        add(drawArea, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        mainFrame.setVisible(true);
    }
}
