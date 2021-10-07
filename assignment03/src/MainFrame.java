import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This program implements a GUI to display the optimal travel route between a group of cities. The cities can be marked
 * on screen with a mouse click. Also, the marked cities can be moved to a new location by clicking and dragging it. The
 * program also supports additional functions to clear the screen and load/save the city data from/to a text file.
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-10-02
 */
public class MainFrame extends JFrame {
    private static final int DEFAULT_WINDOW_HEIGHT = 500;
    private static final int DEFAULT_WINDOW_WIDTH = 800;

    /**
     * Default constructor. Initializes the GUI components and their defines their responses to user actions. It also
     * sets the TSP class as an observer of the WorkSpace class, so that it can respond to addition/movement of a city
     * by re-evaluating the optimal route.
     */
    public MainFrame() {
        super("Travelling Salesman Path Plotting Tool");
        setLayout(new BorderLayout());

        WorkSpacePanel drawArea = new WorkSpacePanel();
        TSP tsp = new TSP();
        WorkSpace.getInstance().addObserver(tsp);
        add(drawArea, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem mItemNew = new JMenuItem("New");
        mItemNew.addActionListener(ev -> {
            WorkSpace.getInstance().clearAllCities();
            drawArea.repaint();
        });

        JMenuItem mItemLoad = new JMenuItem("Load");
        mItemLoad.addActionListener(ev -> {
            File selectedFile = displayFileSelectionDialog();
            if (selectedFile != null) {
                try { WorkSpace.getInstance().load(selectedFile); }
                catch (IOException e) {
                    String msg = String.format("Failed To Load Data From File\nException: %s", e);
                    JOptionPane.showMessageDialog(this, msg);
                }
                drawArea.repaint();
            }
        });

        JMenuItem mItemSave = new JMenuItem("Save");
        mItemSave.addActionListener(ev -> {
            File selectedFile = displayFileSaveDialog();
            if (selectedFile != null) {
                try { WorkSpace.getInstance().save(selectedFile); }
                catch (IOException e) {
                    String msg = String.format("Failed To Save Data To File\nException: %s", e);
                    JOptionPane.showMessageDialog(this, msg);
                }
            }
        });

        fileMenu.add(mItemNew);
        fileMenu.add(new JSeparator());
        fileMenu.add(mItemLoad);
        fileMenu.add(mItemSave);

        menuBar.add(fileMenu);

        add(menuBar, BorderLayout.NORTH);
    }

    /**
     * Entry point for the program. It creates an instance of the GUI, configures its physical attributes and displays
     * it on screen.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        mainFrame.setVisible(true);
    }

    private File displayFileSelectionDialog() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            return jFileChooser.getSelectedFile();
        else return null;
    }

    private File displayFileSaveDialog() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
            return jFileChooser.getSelectedFile();
        else return null;
    }
}
