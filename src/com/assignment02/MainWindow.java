package com.assignment02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description Text
 *
 * @author Zhuoran Li, Rishav Kumar
 * @version 1.0
 * @since 2021-09-11
 */
public class MainWindow extends JFrame {
    private static final int DEFAULT_WINDOW_HEIGHT = 500;
    private static final int DEFAULT_WINDOW_WIDTH = 800;

    private final JLabel distanceValueLabel;
    private final JRadioButton dummyRouteRadioButton, shortestRouteRadioButton;

    private final Canva canva;

    private TSP tsp = null;

    /**
     * Description Text
     */
    public MainWindow() {
        super("Travelling Salesman Path Plotting Tool");
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel distanceHeaderLabel = new JLabel();
        distanceHeaderLabel.setText("Total Distance:");
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.0; constraints.weighty = 0.0;
        constraints.gridx = 0; constraints.gridy = 0; constraints.gridwidth = 2;
        constraints.insets = new Insets(5,10,0,10);
        add(distanceHeaderLabel, constraints);

        distanceValueLabel = new JLabel();
        distanceValueLabel.setText("0.00 Units");
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.0; constraints.weighty = 0.0;
        constraints.gridx = 0; constraints.gridy = 0; constraints.gridwidth = 2;
        constraints.insets = new Insets(5,100,0,10);
        add(distanceValueLabel, constraints);

        ActionListener routeRadioButtonActionListener = e -> {
            if (tsp != null) {
                try {
                    refreshPlot();
                    showMessageDialog("Route is plotted successfully !");
                } catch (Exception exception) {
                    showMessageDialog("Failed to generate plot !");
                }
            }
        };

        dummyRouteRadioButton = new JRadioButton("Dummy Route");
        dummyRouteRadioButton.setFocusPainted(false);
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 0.0; constraints.weighty = 0.0;
        constraints.gridx = 0; constraints.gridy = 0; constraints.gridwidth = 2;
        constraints.insets = new Insets(5,0,0,230);
        dummyRouteRadioButton.addActionListener(routeRadioButtonActionListener);
        add(dummyRouteRadioButton, constraints);

        shortestRouteRadioButton = new JRadioButton("Shortest Route (Greedy Algorithm)");
        shortestRouteRadioButton.setFocusPainted(false);
        shortestRouteRadioButton.setSelected(true);
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 0.0; constraints.weighty = 0.0;
        constraints.gridx = 0; constraints.gridy = 0; constraints.gridwidth = 2;
        constraints.insets = new Insets(5,0,0,10);
        shortestRouteRadioButton.addActionListener(routeRadioButtonActionListener);
        add(shortestRouteRadioButton, constraints);

        ButtonGroup routeTypeButtonGroup = new ButtonGroup();
        routeTypeButtonGroup.add(shortestRouteRadioButton);
        routeTypeButtonGroup.add(dummyRouteRadioButton);

        canva = new Canva();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0; constraints.weighty = 1.0;
        constraints.gridx = 0; constraints.gridy = 1; constraints.gridwidth = 2;
        constraints.insets = new Insets(5,10,5,10);
        add(canva, constraints);

        JButton loadDataButton = new JButton("Load Data");
        loadDataButton.setFocusPainted(false);
        loadDataButton.setPreferredSize(new Dimension(0, 30));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5; constraints.weighty = 0.0;
        constraints.gridx = 0; constraints.gridy = 2; constraints.gridwidth = 1;
        constraints.insets = new Insets(0,10,10,2);
        loadDataButton.addActionListener(e -> {
            File selectedFile = displayFileSelectionDialog();
            if (selectedFile != null) {
                try {
                    generatePlot(selectedFile);
                    showMessageDialog("Route is plotted successfully !");
                } catch (Exception exception) {
                    showMessageDialog("Failed to generate plot !");
                }
            }
        });
        add(loadDataButton, constraints);

        JButton clearDataButton = new JButton("Clear Data");
        clearDataButton.setFocusPainted(false);
        clearDataButton.setPreferredSize(new Dimension(0, 30));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5; constraints.weighty = 0.0;
        constraints.gridx = 1; constraints.gridy = 2; constraints.gridwidth = 1;
        constraints.insets = new Insets(0,2,10,10);
        clearDataButton.addActionListener(e -> {
            tsp = null;
            plotRoute(null, null);
        });
        add(clearDataButton, constraints);
    }

    /**
     * Description Text
     * @param args
     */
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void generatePlot(File file) throws Exception {
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
        ArrayList<Route> routeList;
        if (shortestRouteRadioButton.isSelected())
            routeList = tsp.calculateShortestRoute();
        else routeList = tsp.calculateDummyRoute();
        plotRoute(tsp.getCityList(), routeList);
    }

    private void refreshPlot() {
        ArrayList<Route> routeList;
        if (shortestRouteRadioButton.isSelected())
            routeList = tsp.calculateShortestRoute();
        else routeList = tsp.calculateDummyRoute();
        plotRoute(tsp.getCityList(), routeList);
    }

    private void plotRoute(ArrayList<City> cityList, ArrayList<Route> routeList) {
        canva.setCityList(cityList);
        canva.setRouteList(routeList);

        double totalDistance = 0.0;
        if (routeList != null) {
            for (Route route : routeList)
                totalDistance += route.getDist();
        }
        distanceValueLabel.setText(String.format("%.2f Units",  totalDistance));
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
