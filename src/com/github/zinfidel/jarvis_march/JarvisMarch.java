package com.github.zinfidel.jarvis_march;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import javax.swing.Timer;

import com.github.zinfidel.jarvis_march.algorithm.*;
import com.github.zinfidel.jarvis_march.geometry.Model;
import com.github.zinfidel.jarvis_march.geometry.Point;
import com.github.zinfidel.jarvis_march.visualization.GeometryPanel;

/**
 * A class that provides a graphical representation of solving a convex hull for
 * an arbitrary cloud of points. This class can generate a cloud of points
 * (according to a Guassian distribution), then solve it all at once, one step
 * at a time, or continuously.
 */
public class JarvisMarch extends JFrame {

    private static final long serialVersionUID = -2988707585939084412L;
    private static final int width = 640;
    private static final int height = 480;
    
    /** The current Jarvis marcher. Do not set manually! Use setMarcher(). */
    private JarvisMarcher marcher = null;
    
    private final Model model = new Model();
    private GeometryPanel geoPanel;
    private JPanel controlsPanel;
    

    /**
     * Constructs the GUI and sets up the data elements.
     */
    public JarvisMarch() {
	super();
	initGUI();
	
	geoPanel.setModel(model); // Set the model to be rendered.
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
	contentFrame.add(pnlGeometry, BorderLayout.CENTER);
	geoPanel = pnlGeometry; // Set the member variable.

	// Set up the controls panel.
	JPanel pnlControls = new JPanel(new FlowLayout());
	contentFrame.add(pnlControls, BorderLayout.SOUTH);
	controlsPanel = pnlControls;

	// Set up points label, entry field, and generate button.
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
	pnlControls.add(btnIterate);

	// Set up the auto-run checkbox, delay field, and time units label.
	JCheckBox chckbxAuto = new JCheckBox("Auto");
	pnlControls.add(chckbxAuto);

	JFormattedTextField ftfDelay = new JFormattedTextField(getNumFormat());
	ftfDelay.setColumns(4);
	ftfDelay.setValue(new Integer(100));
	pnlControls.add(ftfDelay);

	JLabel lblTimeUnits = new JLabel("ms");
	pnlControls.add(lblTimeUnits);

	// Add the iterate button's action listener.
	btnIterate.addActionListener(
		new IterativelyCalculateCH(chckbxAuto, ftfDelay));
    }


    /*
     * Action Listeners
     */


    /**
     * Generates points in the model and updates the visualization. The number
     * of points generated is retreived from the points formatted field.
     */
    private class GeneratePoints implements ActionListener {

	// Text field containing number of points to generate.
	private JFormattedTextField ftfPoints;

	public GeneratePoints(JFormattedTextField ftfPoints) {
	    this.ftfPoints = ftfPoints;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    // Clear points, clear existing marchers, establish boundaries.
	    model.clearPoints();
	    marcher = null;
	    Point bounds = new Point(geoPanel.getWidth(),
		    geoPanel.getHeight());

	    // Create the random points.
	    for (int n = 0; n < (int) ftfPoints.getValue(); n++) {
		model.addPoint(PointGenerator.normalRandom(bounds));
	    }

	    // Update the drawing.
	    geoPanel.repaint();
	}
    }


    /**
     * Runnable that solves the current problem in its entirety all at once.
     */
    private class CalculateCH implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    try {
		// Initialize a marcher, solve the problem, then display it.
		setMarcher(new JarvisMarcher(model));
		marcher.solve();
		geoPanel.repaint();

	    } catch (DegenerateGeometryException ex) {
		// Display an error dialog.
		JOptionPane.showMessageDialog(
			JarvisMarch.this,
			ex.getMessage(),
			"Degenerate Geometry",
			JOptionPane.ERROR_MESSAGE);
	    }
	}
    }


    /**
     * Actionlistener that either iterates one step of the Jarvis March
     * algorithm, or continuously iterates until the convex hull is solved.
     */
    private class IterativelyCalculateCH implements ActionListener {
	
	// The "auto" checkbox and iteration delay field.
	private JCheckBox autoChkBox = null;
	private JFormattedTextField ftfDelay= null;
	
	// Used if the auto checkbox is ticked.
	private Timer timer = null;

	public IterativelyCalculateCH(JCheckBox autoChkBox,
				      JFormattedTextField ftfDelay) {
	    this.autoChkBox = autoChkBox;
	    this.ftfDelay = ftfDelay;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    
	    if (JarvisMarch.this.marcher == null)
        	    setMarcher(new JarvisMarcher(model));
	    
	    // If the auto checkbox is ticked, start a background timer and
	    // continuously iterate. Otherwise iterate once.
	    if (autoChkBox.isSelected()) {
		int delay = (Integer)ftfDelay.getValue();
		toggleControls(false);
		
		timer = new Timer(delay, new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {

			// Iterate until the iterator returns false (done), then
			// toggle the controls back on.
			try {
			    if (!marcher.iterate()) {
				timer.stop();
				toggleControls(true);
			    }
			    geoPanel.repaint();
			    
			// Stop the timer, enable controls, and display an error
			// message if an exception is thrown.
			} catch (DegenerateGeometryException ex) {
			    timer.stop();
			    toggleControls(true);

			    // Display an error dialog.
			    JOptionPane.showMessageDialog(
				    JarvisMarch.this,
				    ex.getMessage(),
				    "Degenerate Geometry",
				    JOptionPane.ERROR_MESSAGE);
			}
		    }    
		});

		timer.start();

	    }

	    // Just iterate once.
	    else {
		try {
		    marcher.iterate();
		    geoPanel.repaint();
		} catch (DegenerateGeometryException ex) {
		    // Display an error dialog.
		    JOptionPane.showMessageDialog(
			    JarvisMarch.this,
			    ex.getMessage(),
			    "Degenerate Geometry",
			    JOptionPane.ERROR_MESSAGE);
		}
	    }
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

    
    /**
     * Toggles the enable state of all of the controls on the GUI.
     * 
     * @param state True to enable the controls, false to disable them.
     */
    private void toggleControls(boolean state) {
	for(Component comp : controlsPanel.getComponents()) {
	    comp.setEnabled(state);
	}
    }


    /**
     * Sets the current marcher being used to solve the convex hull. This method
     * ensures that the geometry panel is synchronized with the new marcher.
     * 
     * @param marcher The new Jarvis Marcher.
     */
    private void setMarcher(JarvisMarcher marcher) {
	this.marcher = marcher;
	geoPanel.setMarcher(marcher);
    }
}
