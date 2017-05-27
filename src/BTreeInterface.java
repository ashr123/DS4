/// DO NOT CHANGE
/// DO NOT SUBMIT
interface BTreeInterface
{
	/**
	 * @return the root BNode of the tree.
	 */
	BNode getRoot();
	
	/**
	 * @return the t constant of the tree
	 */
	int getT();
	
	/**
	 * Search a block by its key.
	 * @param key
	 * @return the block if found, null otherwise.
	 */
	Block search(int key);
	
	/**
	 * Insert a new block
	 * @param b
	 */
	void insert(Block b);
	
	/**
	 * Delete a block by its key.
	 * @param key
	 */
	void delete(int key);
	
	/**
	 * Computes the complete Merkle-B-Tree of this B-Tree
	 * @return the root of MBT.
	 */
	MerkleBNode createMBT();
}