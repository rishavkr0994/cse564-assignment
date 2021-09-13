package com.assignment02;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Some Text Here
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-09-12
 */
public class MainWindow extends JFrame {
    private static final int DEFAULT_WINDOW_HEIGHT = 500;
    private static final int DEFAULT_WINDOW_WIDTH = 800;

    private final Canva canva;

    public MainWindow() {
        super("Travelling Salesman Path Plotting Tool");
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        canva = new Canva();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0; constraints.weighty = 1.0;
        constraints.gridx = 0; constraints.gridy = 0; constraints.gridwidth = 2;
        constraints.insets = new Insets(10,10,5,10);
        add(canva, constraints);

        JButton loadDataButton = new JButton("Load Data");
        loadDataButton.setFocusPainted(false);
        loadDataButton.setPreferredSize(new Dimension(0, 30));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5; constraints.weighty = 0.0;
        constraints.gridx = 0; constraints.gridy = 1; constraints.gridwidth = 1;
        constraints.insets = new Insets(0,10,10,2);
        add(loadDataButton, constraints);
        loadDataButton.addActionListener(e -> {
            File selectedFile = displayFileSelectionDialog();
            if (selectedFile != null) {
                try {
                    generatePlot(selectedFile);
                    showMessageDialog("Route is plotted successfully !");
                } catch (Exception exception) {
                    showMessageDialog("Failed to generate plot from selected file");
                }
            }
        });

        JButton clearDataButton = new JButton("Clear Data");
        clearDataButton.setFocusPainted(false);
        clearDataButton.setPreferredSize(new Dimension(0, 30));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5; constraints.weighty = 0.0;
        constraints.gridx = 1; constraints.gridy = 1; constraints.gridwidth = 1;
        constraints.insets = new Insets(0,2,10,10);
        add(clearDataButton, constraints);
        clearDataButton.addActionListener(e -> plotRoute(null,null));
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void generatePlot(File file) throws Exception {
        TSP tsp = null;

        String lineText;
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((lineText = br.readLine()) != null) {
            List<String> tokens = Arrays.stream(lineText.split(":"))
                                        .map(String::trim).collect(Collectors.toList());

            if (tokens.size() == 2 && tokens.get(0).equals("TYPE")) {
                if (tokens.get(1).equals("TSP"))
                    tsp = new TSPSymmetric();
                else if (tokens.get(1).equals("ATSP"))
                    tsp = new TSPAsymmetric();
                break;
            }
        }

        if (tsp == null)
            throw new IOException("Invalid File");

        tsp.parseTextFile(file);
        ArrayList<Route> routeList = tsp.calculateShortestRoute();
        plotRoute(tsp.getCityList(), routeList);
    }

    private void plotRoute(ArrayList<City> cityList, ArrayList<Route> routeList) {
        canva.setCityList(cityList);
        canva.setRouteList(routeList);
    }

    private File displayFileSelectionDialog() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            return jFileChooser.getSelectedFile();
        else return null;
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
