import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * This program implements a GUI to display the travel route between a group of cities. It supports user
 * clear, save and load the city list by JMenu.
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-10-02
 */
public class MainFrame extends JFrame {
    private static final int DEFAULT_WINDOW_HEIGHT = 500;
    private static final int DEFAULT_WINDOW_WIDTH = 800;

    /**
     * Default constructor. Initializes the GUI components and their defines their responses to user actions.
     */
    public MainFrame() {
        super("TSP");
        setLayout(new BorderLayout());

        WorkSpacePanel drawArea = new WorkSpacePanel();
        TSP tsp = new TSP();
        WorkSpace.getInstance().addObserver(tsp);
        add(drawArea, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem mItemNew = new JMenuItem("New");
        mItemNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                WorkSpace.getInstance().clearAllCities();
                drawArea.repaint();
            }
        });

        JMenuItem mItemLoad = new JMenuItem("Load");
        mItemLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                File selectedFile = displayFileSelectionDialog();
                try {
                    WorkSpace.getInstance().load(selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                drawArea.repaint();
            }
        });

        JMenuItem mItemSave = new JMenuItem("Save");
        mItemSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                File selectedFile = displayFileSaveDialog();
                try {
                    WorkSpace.getInstance().save(selectedFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        fileMenu.add(mItemNew);
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
