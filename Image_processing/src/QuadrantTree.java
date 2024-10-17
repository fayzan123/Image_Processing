
public class QuadrantTree {
	
	QTreeNode root; // Root node of the quadrant tree
	
	// Constructor to build the quadrant tree
	public QuadrantTree(int[][] thePixels) {
		root = buildTree(thePixels, 0, 0, thePixels.length);
	}
	
	// Recursive method to build the quadrant tree
	private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
		int avgColor = Gui.averageColor(pixels, x, y, size);
		// Create a new tree node for this quadrant and set values
		QTreeNode node = new QTreeNode();
		node.setx(x); 
		node.sety(y); 
		node.setSize(size); 
		node.setColor(avgColor); 
		

		if (size > 1) {
			int halfSize = size / 2; 
			// Recursively build the child quadrants
			node.setChild(buildTree(pixels, x, y, halfSize), 0);
	        node.setChild(buildTree(pixels, x + halfSize, y, halfSize), 1);
	        node.setChild(buildTree(pixels, x, y + halfSize, halfSize), 2);
	        node.setChild(buildTree(pixels, x + halfSize, y + halfSize, halfSize), 3);
	        
	        // Set parent node for each child
	        for (int i = 0; i < 4; i++) {
	            node.getChild(i).setParent(node);
	        }
	    }
	
	    return node;
    }
		
	
	// Get the root node of the quadrant tree
	public QTreeNode getRoot() {
		return root;
	}
	
	
	// Get the list of pixels at a specific level in the quadrant tree
	public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
		if (theLevel == 0 || r.isLeaf()) {
			ListNode<QTreeNode> result = new ListNode<>(r);
			return result;
		}
	
		ListNode<QTreeNode> result = new ListNode<>(null);
		getPixelsListBuilder(r, theLevel, 0, result);
	
		return result;
	}
	
	
	// Helper method to build the list of pixels at a specific level
	private ListNode<QTreeNode> getPixelsListBuilder(QTreeNode node, int theLevel, int currentLevel, ListNode<QTreeNode> result) {
	    if (node == null || theLevel < 0) return result;
	    
	    if (node.isLeaf() || currentLevel == theLevel) {
	    	if (result.getData() == null) result.setData(node);
	    	else {
	    		ListNode<QTreeNode> newNode = new ListNode<>(node);
	    		result.setNext(newNode);
	    		result = newNode;
	    	}
	    	return result;
	    }
	    
	    for (QTreeNode child : node.children) {
	    	result = getPixelsListBuilder(child, theLevel, currentLevel + 1, result);
	    }
	    return result;
	}
	
	// Find nodes in the quadrant tree with colors similar to a given color at a specific level
	public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
		if (r == null) {
	        return new Duple(null, 0);
	    }
		
		if (r.isLeaf() || theLevel == 0) {
			if (Gui.similarColor(r.getColor(), theColor)) {
				ListNode<QTreeNode> list = new ListNode<>(r);
				return new Duple(list, 1);
			} else {
				return new Duple(null, 0);
			}
		}
		
		Duple parentDuple = null;
		
		// Recursively search for nodes with similar colors at the specified level
		for (int i = 0; i < 4; i++) {
			Duple childDuple = findMatching(r.getChild(i), theColor, theLevel - 1);
			parentDuple = mergeDuples(parentDuple, childDuple);
		}
		
		return parentDuple;
	}
	
	// Concatenate two lists of tree nodes
	private ListNode<QTreeNode> concatenateLists(ListNode<QTreeNode> list1, ListNode<QTreeNode> list2) {
		if (list1 == null) {
			return list2;
		}
		
		ListNode<QTreeNode> current = list1;
		
		while (current.getNext() != null) {
			current = current.getNext();
		}
		
		current.setNext(list2);
		return list1;
	}
	
	// Merge two Duples
	private Duple mergeDuples(Duple duple1, Duple duple2) {
		if (duple1 == null) {
	        return duple2;
	    }
	    if (duple2 == null) {
	        return duple1;
	    }
	    
	    ListNode<QTreeNode> mergedList = concatenateLists(duple1.getFront(), duple2.getFront());
	    
	    int totalCount = duple1.getCount() + duple2.getCount();
	    
	    return new Duple(mergedList, totalCount);
	}
	
	// Find a node in the quadrant tree at a specific level and containing a given point
	public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
		
		if (r == null || theLevel < 0) return null;
		
		if (r.contains(x,  y) && theLevel == 0) {
			return r;
		}
		
		if (!r.isLeaf() && theLevel > 0) {
			for (QTreeNode childNode : r.children) {
				QTreeNode result = findNode(childNode, theLevel - 1, x, y);
				
				if (result != null) {
					return result;
				}
			}
		}
		
		return null;
	}
}