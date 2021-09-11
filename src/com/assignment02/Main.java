package com.assignment02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Main extends JFrame implements ActionListener {
    Canva canva;

    Main() {
        super("TSP");
        canva = new Canva();
        //JButton button = new JButton("upload Data");
        setLayout(new BorderLayout());
        add(canva,BorderLayout.CENTER);
        //add(button, BorderLayout.SOUTH);
        //button.addActionListener(this);
    }

    public static void main(String[] args) throws IOException {
        TSP tsp = null;
        Main app = new Main();
        app.setSize(500,500);
        app.setVisible(true);
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //TSP tsp = true ? new TSPSymmetric() : new TSPAsymmetric();
        if (args.length == 0)
            throw new IllegalArgumentException("file path is not provided");
        String path = args[0];
        String lineText = null;
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((lineText = br.readLine()) != null)
        {
            if (lineText.equals("TYPE : TSP"))
            {
                tsp = new TSPSymmetric();
                break;
            }else if (lineText.equals("TYPE: ATSP"))
            {
                tsp = new TSPAsymmetric();
                break;
            }
        }
        tsp.readFile(path);
        //tsp.calculateShortestPath();
        ArrayList<Route> result = tsp.calculateShortestPath();
        app.paint(result);
    }

    public void paint(ArrayList<Route> result) {
        // plot the result on canva
        canva.drawLine(getGraphics(),result);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
