package com.github.zinfidel.jarvis_march.algorithm;

/**
 * Specifies that the geometry specified for some use is degenerate for that
 * use, such as two vertices when a closed polygon is needed.
 */
public class DegenerateGeometryException extends RuntimeException {

    private static final long serialVersionUID = -2721285151267111196L;

    public DegenerateGeometryException() {
	super();
    }

    /** {@InheritDoc} */
    public DegenerateGeometryException(String arg0) {
	super(arg0);
    }

    /** {@InheritDoc} */
    public DegenerateGeometryException(Throwable arg0) {
	super(arg0);
    }

    /** {@InheritDoc} */
    public DegenerateGeometryException(String arg0, Throwable arg1) {
	super(arg0, arg1);
    }

    /** {@InheritDoc} */
    public DegenerateGeometryException(String arg0, Throwable arg1,
	                               boolean arg2, boolean arg3) {
	super(arg0, arg1, arg2, arg3);
    }

}
