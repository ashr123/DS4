import java.util.ArrayList;

/// DO NOT CHANGE
/// DO NOT SUBMIT
public interface BNodeInterface
{
	int getT();
	
	int getNumOfBlocks();
	
	boolean isLeaf();
	
	ArrayList<Block> getBlocksList();
	
	ArrayList<BNode> getChildrenList();
	
	int getBlockKeyAt(int indx);
	
	Block getBlockAt(int indx);
	
	BNode getChildAt(int indx);
	
	/**
	 * True if and only if the numOfBlocks is maximal, (2t-1).
	 */
	boolean isFull();
	
	/**
	 * True if and only if the numOfBlocks is minimal, (t-1).
	 */
	boolean isMinSize();
	
	/**
	 * True if and only if the numOfBlocks is 0.
	 */
	boolean isEmpty();
	
	/**
	 * Search a block by its key, in the subtree rooted by this node.
	 * @param key
	 * @return the block if found, null otherwise.
	 */
	Block search(int key);
	
	/**
	 * Insert a new block to the subtree rooted by this node.<br>
	 * Assuming this node is not full.
	 * @param b the new block.
	 */
	void insertNonFull(Block d);
	
	/**
	 * Delete a block by its key, in the subtree rooted by this node.
	 * @param key
	 * @return the block if found, null otherwise.
	 */
	void delete(int key);
	
	/**
	 * @return the corresponding Merkle-B-Tree node of this BNode.
	 */
	MerkleBNode createHashNode();
}