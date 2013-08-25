package com.github.zinfidel.jarvis_march;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * @author Zach Friedland
 *
 */
public class JarvisMarch extends JFrame {

    private static final long serialVersionUID = -2988707585939084412L;
    
    private final int width = 650;
    private final int height = 400;

    /**
     * TODO: Document
     */
    public JarvisMarch() {
	super();

	// Set up the frame.
	setTitle("Jarvis' March Convex Hull Visualizer");
	setBounds(100, 100, width, height);
	setMinimumSize(new Dimension(width, height));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	// Set up the content frame.
	JPanel contentFrame = (JPanel) getContentPane();
	contentFrame.setLayout(new BorderLayout());
	contentFrame.setBorder(new EmptyBorder(5, 5, 5, 5));

	// Set up the geometry viewing pane.
	// TODO: Surrogate pane for now - replace with actual geometry pane later.
	JPanel PnlGeometry = new JPanel();
	PnlGeometry.setBackground(Color.WHITE);
	PnlGeometry.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	contentFrame.add(PnlGeometry, BorderLayout.CENTER);

	// Set up the controls panel.
	JPanel pnlControls = new JPanel(new FlowLayout());
	contentFrame.add(pnlControls, BorderLayout.SOUTH);
	
	// Set up point-count label, entry field, and generate button.
	JLabel lblNumberOfPoints = new JLabel("Number of Points:");
	pnlControls.add(lblNumberOfPoints);

	JFormattedTextField ftfPoints = new JFormattedTextField();
	ftfPoints.setColumns(3);
	ftfPoints.setText("3");
	pnlControls.add(ftfPoints);

	JButton btnGeneratePoints = new JButton("Generate Points");
	pnlControls.add(btnGeneratePoints);

	// Set up convex hull calculate button.
	JButton btnCalculateCH = new JButton("Calculate CH");
	pnlControls.add(btnCalculateCH);

	// Set up the iterate button.
	JButton btnIterate = new JButton("Iterate");
	btnIterate.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
	    }
	});
	pnlControls.add(btnIterate);

	// Set up the auto-run checkbox, delay field, and time units label.
	JCheckBox chckbxAuto = new JCheckBox("Auto");
	pnlControls.add(chckbxAuto);

	JFormattedTextField ftfDelay = new JFormattedTextField();
	ftfDelay.setColumns(4);
	pnlControls.add(ftfDelay);

	JLabel lblTimeUnits = new JLabel("ms");
	pnlControls.add(lblTimeUnits);

    }

    /**
     * Starts the program by displaying the GUI.
     * 
     * @param args Ignored.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		JarvisMarch frame = new JarvisMarch();
		frame.setVisible(true);
	    }
	});
    }
}
