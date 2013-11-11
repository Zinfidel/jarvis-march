jarvis-march
============

Implementation of the Jarvis' March (Gift-Wrapping) algorithm for finding the convex hull of a set of points.

Usage
-----
All of the controls in the program are located below the main drawing panel.

### Generating Points

Enter the number of points to generate into the appropriately labeled text field, and then click the "Generate Points" button. The number of points to be generated must be > 3 and < 9999.

### Solving

After generating a point cloud, simply click the "Calculate CH" button and the convex hull will be solved using the Jarvis' March algorithm.

### Solving Iteratively

After generating a point cloud, click on the "Iterate" button to perform one step of the Jarvis' March algorithm on the point cloud. This is useful to examine how the algorithm works. To continuously solve (animation!), tick the "Auto" checkbox next to the "Iterate" button, and enter a number in the textbox to the right. This number should be the number of milliseconds between each iteration of the continuous solve. Click the "Iterate" button when satisfied.
