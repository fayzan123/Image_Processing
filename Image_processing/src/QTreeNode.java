
public class QTreeNode {
	int x, y; // Initalize coords, colour, size, parent, children
	int size; 
	int color; 
	QTreeNode parent; 
	QTreeNode[] children; 
	
	// Constructor to initialize a QTreeNode without children
	public QTreeNode() {
		parent = null;
		children = new QTreeNode[4]; // Initialize array for four children
		x = 0;
		y = 0;
		size = 0;
		color = 0;
	}
	
	// Constructor to initialize a QTreeNode with specified parameters
	public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
		parent = null;
		children = theChildren;
		x = xcoord;
		y = ycoord;
		size = theSize;
		color = theColor;
	}
	
	// Method to check if the quadrant contains a given point
	public boolean contains(int xcoord, int ycoord) {
		int xspan = x + size - 1;
		int yspan = y + size - 1;
		return (xcoord >= x && xcoord <= xspan && ycoord >= y && ycoord <= yspan);
	}
	
	// Getter methods
	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getColor() {
		return color;
	}
	
	public QTreeNode getParent() {
		return parent;
	}
	
	// Method to get a child node by index
	public QTreeNode getChild(int index) throws QTreeException {
		if (children == null || index < 0 || index > 3) {
			throw new QTreeException(""); // Throw exception if index is out of bounds
		}
		
		return children[index];
	}
	
	// Setter methods
	public void setx(int newx) {
		x = newx;
	}
	
	public void sety(int newy) {
		y = newy;
	}
	
	public void setSize(int newSize) {
		size = newSize;
	}
	
	public void setColor(int newColor) {
		color = newColor;
	}
	
	public void setParent(QTreeNode newParent) {
		this.parent = newParent;
	}
	
	// Method to set a child node at a specific index
	public void setChild(QTreeNode newChild, int index) {
		if (children == null || index < 0 || index > 3) {
			throw new QTreeException(""); // Throw exception if index is out of bounds
		}
		children[index] = newChild;
	}
	
	// Method to check if the node is a leaf (has no children)
	public boolean isLeaf() {
		return children == null || children.length == 0;
	}
}