package dev.stephenpearson.ui;

import javax.swing.*;

public class Main extends JFrame {
	
    public Main() {
        setTitle("Pi Motion Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        add(new JPanel());
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
