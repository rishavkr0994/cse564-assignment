import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private static final int DEFAULT_WINDOW_HEIGHT = 500;
    private static final int DEFAULT_WINDOW_WIDTH = 800;

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
