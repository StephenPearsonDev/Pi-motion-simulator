package dev.stephenpearson.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import dev.stephenpearson.model.SimulationState;
import dev.stephenpearson.simulation.SimulationEngine;


public class HelpScreen {
    

    public static void show(Window parent, SimulationState state, SimulationEngine simulator) {
  
        state.setRunning(false);
        state.setShowHelp(true);
        
        JDialog helpDialog = new JDialog(parent, "Help");
        helpDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        helpDialog.setSize(500, 400);
        helpDialog.setLocationRelativeTo(parent);
        
        String explanation =
            "This simulation demonstrates a geometric representation of Pi's irrational nature.\n\n"
          + "We plot the complex sum e^(i·θ) + e^(i·π·θ), where θ increments over time.\n"
          + "Because π is irrational, the angles never line up exactly, creating a spiral.\n\n"
          + "Controls:\n"
          + " • Start/Pause toggles simulation\n"
          + " • Restart resets everything (and starts again)\n"
          + " • New Segment Color picks a new bright color\n"
          + " • Speed +/- changes speed level (1..20)\n"
          + " • Reset Zoom sets zoom & pan to default\n"
          + " • FPS changes how often we repaint (30, 60, 120)\n"
          + " • Lock to: Center, Middle, Outer point\n"
          + " • Increase/Decrease line thickness (1..3)\n"
          + " • Toggle Double Buffering on/off\n\n"
          + "Pan: hold CTRL or middle mouse and drag\n"
          + "Zoom: mouse wheel\n\n"
          + "Close this window to resume simulation.";
        
        JTextArea helpText = new JTextArea(explanation);
        helpText.setFont(new Font("SansSerif", Font.PLAIN, 16));
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(helpText);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        helpDialog.add(scrollPane, BorderLayout.CENTER);
        
        helpDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                state.setShowHelp(false);
                state.setRunning(true);
            }
        });
        
        helpDialog.setVisible(true);
    }
}