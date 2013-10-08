package com.github.zinfidel.jarvis_march;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import javax.swing.Timer;

import com.github.zinfidel.jarvis_march.algorithm.JarvisMarcher;
import com.github.zinfidel.jarvis_march.algorithm.PointGenerator;
import com.github.zinfidel.jarvis_march.geometry.Model;
import com.github.zinfidel.jarvis_march.geometry.Point;
import com.github.zinfidel.jarvis_march.visualization.GeometryPanel;

/**
 * @author Zach Friedland
 *
 */
public class JarvisMarch extends JFrame {

    private static final long serialVersionUID = -2988707585939084412L;
    private static final int width = 640; // TODO Specify as starting width?
    private static final int height = 480;
    
    private GeometryPanel geoPanel;
    
    private Model model = new Model();

    /**
     * TODO: Document
     */
    public JarvisMarch() {
	super();
	initGUI();
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

    /**
     * Initializes the Swing GUI elements of this frame and sets this
     * instance's geometry panel field.
     */
    private void initGUI() {
	// Set up the frame.
	setTitle("Jarvis' March Convex Hull Visualizer");
	setBounds(100, 100, width, height);
	setMinimumSize(new Dimension(width, height));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Set up the content frame.
	JPanel contentFrame = (JPanel) getContentPane();
	contentFrame.setLayout(new BorderLayout());
	contentFrame.setBorder(new EmptyBorder(5, 5, 0, 5));

	// Set up the geometry viewing pane.
	GeometryPanel pnlGeometry = new GeometryPanel();
	// TODO: TESTING CODE!
	pnlGeometry.model = model;
	contentFrame.add(pnlGeometry, BorderLayout.CENTER);
	geoPanel = pnlGeometry; // Set the member variable.

	// Set up the controls panel.
	JPanel pnlControls = new JPanel(new FlowLayout());
	contentFrame.add(pnlControls, BorderLayout.SOUTH);

	// Set up point-count label, entry field, and generate button.
	JLabel lblNumberOfPoints = new JLabel("Number of Points:");
	pnlControls.add(lblNumberOfPoints);

	JFormattedTextField ftfPoints = new JFormattedTextField(getNumFormat());
	ftfPoints.setColumns(4);
	ftfPoints.setValue(new Integer(4));
	pnlControls.add(ftfPoints);

	JButton btnGeneratePoints = new JButton("Generate Points");
	btnGeneratePoints.addActionListener(new GeneratePoints(ftfPoints));
	pnlControls.add(btnGeneratePoints);

	// Set up convex hull calculate button.
	JButton btnCalculateCH = new JButton("Calculate CH");
	btnCalculateCH.addActionListener(new CalculateCH());
	pnlControls.add(btnCalculateCH);

	// Set up the iterate button.
	JButton btnIterate = new JButton("Iterate");
	btnIterate.addActionListener(new IterativelyCalculateCH());
	pnlControls.add(btnIterate);

	// Set up the auto-run checkbox, delay field, and time units label.
	JCheckBox chckbxAuto = new JCheckBox("Auto");
	pnlControls.add(chckbxAuto);

	JFormattedTextField ftfDelay = new JFormattedTextField();
	ftfDelay.setColumns(4);
	ftfDelay.setText("100");
	pnlControls.add(ftfDelay);

	JLabel lblTimeUnits = new JLabel("ms");
	pnlControls.add(lblTimeUnits);
    }
    
    
    /*
     * Action Listeners
     */
    
    /**
     * Generates points in the model and updates the visualization. The number
     * of points generated is retreived from the points formatted field.
     */
    private class GeneratePoints implements ActionListener {
	
	private JFormattedTextField ftfPoints;
	
	public GeneratePoints(JFormattedTextField ftfPoints) {
	    this.ftfPoints = ftfPoints;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    // Clear points, establish boundaries.
	    model.clearPoints();
	    Point bounds = new Point(geoPanel.getWidth(),
		                     geoPanel.getHeight());
	    
	    // Create the random points.
	    for (int n = 0; n < (int) ftfPoints.getValue(); n++) {
		model.addPoint(PointGenerator.random(bounds));
	    }
	    
	    // Update the drawing.
	    geoPanel.repaint();
	}
    }
    
    // TODO Document
    private class CalculateCH implements ActionListener {

	// TODO: Gray out option if no points on screen.
	@Override
	public void actionPerformed(ActionEvent e) {
	    JarvisMarcher marcher = new JarvisMarcher(model);
	    marcher.solve();
	    geoPanel.repaint();
	}
    }
    
    // TODO: This all probably needs to be reimplemented in some background way.
    private class IterativelyCalculateCH implements ActionListener {
	
	private JarvisMarcher marcher = null;
	Timer timer = null;

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    
	    marcher = new JarvisMarcher(model);
	    geoPanel.marcher = marcher;

	    timer = new Timer(25, new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		    if (!marcher.iterate()) timer.stop();
		    geoPanel.repaint();
		}    
	    });
	    
	    timer.start();

	}
	
    }
        
    /*
     * Utility
     */

    /**
     * Formatter for the integer fields that allows for 4 digits.
     * 
     * @return The formatter.
     */
    private NumberFormatter getNumFormat() {
	// Integer format with 4 digits.
	NumberFormat format = NumberFormat.getIntegerInstance();
	format.setMaximumIntegerDigits(4);

	// Formatter that produces integer values.
	NumberFormatter formatter = new NumberFormatter(format);
	formatter.setMinimum(3);
	formatter.setMaximum(9999);
	formatter.setValueClass(Integer.class);

	return formatter;
    }

}
