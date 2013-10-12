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
 * @author Zach Friedland
 *
 */
public class JarvisMarch extends JFrame {

    // TODO: Document all this?
    private static final long serialVersionUID = -2988707585939084412L;
    private static final int width = 640;
    private static final int height = 480;
    
    /** The current Jarvis marcher. Do not set manually! Use setMarcher(). */
    private JarvisMarcher marcher = null;
    
    private final Model model = new Model();
    private GeometryPanel geoPanel;
    

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

	// Text field containing number of points to generate.
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


    // TODO: This all probably needs to be reimplemented in some background way.
    private class IterativelyCalculateCH implements ActionListener {
	
	Timer timer = null;

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    
	    setMarcher(new JarvisMarcher(model));

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
