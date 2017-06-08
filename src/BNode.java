import java.util.ArrayList;

//SUBMIT
class BNode implements BNodeInterface
{
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private final int t;
	private int numOfBlocks;
	private boolean isLeaf;
	private ArrayList<Block> blocksList=new ArrayList<>();
	private ArrayList<BNode> childrenList=new ArrayList<>();
	
	/**
	 * Constructor for creating a node with a single child.<br>
	 * Useful for creating a new root.
	 */
	BNode(int t, BNode firstChild)
	{
		this(t, false, 0);
		getChildrenList().add(firstChild);
	}
	
	/**
	 * Constructor for creating a <b>leaf</b> node with a single block.
	 */
	BNode(int t, Block firstBlock)
	{
		this(t, true, 1);
		getBlocksList().add(firstBlock);
	}
	
	@SuppressWarnings("WeakerAccess")
	public BNode(int t, boolean isLeaf, int numOfBlocks)
	{
		this.t=t;
		this.isLeaf=isLeaf;
		this.numOfBlocks=numOfBlocks;
	}
	
	// For testing purposes.
	@SuppressWarnings("unused")
	public BNode(int t, int numOfBlocks, boolean isLeaf, ArrayList<Block> blocksList,
	             ArrayList<BNode> childrenList)
	{
		this(t, isLeaf,  numOfBlocks);
		this.blocksList=blocksList;
		this.childrenList=childrenList;
	}
	
	@Override
	public int getT()
	{
		return t;
	}
	
	@Override
	public int getNumOfBlocks()
	{
		return numOfBlocks;
	}
	
	@Override
	public boolean isLeaf()
	{
		return isLeaf;
	}
	
	@Override
	public ArrayList<Block> getBlocksList()
	{
		return blocksList;
	}
	
	@Override
	public ArrayList<BNode> getChildrenList()
	{
		return childrenList;
	}
	
	@Override
	public boolean isFull()
	{
		return getNumOfBlocks()==2*getT()-1;
	}
	
	@Override
	public boolean isMinSize()
	{
		return getNumOfBlocks()==getT()-1;
	}
	
	@Override
	public boolean isEmpty()
	{
		return getNumOfBlocks()==0;
	}
	
	@Override
	public int getBlockKeyAt(int indx)
	{
		return getBlockAt(indx).getKey();
	}
	
	@Override
	public Block getBlockAt(int indx)
	{
		return getBlocksList().get(indx);
	}
	
	@Override
	public BNode getChildAt(int indx)
	{
		return getChildrenList().get(indx);
	}
	
	@Override
	public int hashCode()
	{
		final int prime=31;
		int result=1;
		result=prime*result+(getBlocksList()==null ? 0 : getBlocksList().hashCode());
		result=prime*result+(getChildrenList()==null ? 0 : getBlocksList().hashCode());
		result=prime*result+(isLeaf() ? 1231 : 1237);
		result=prime*result+getNumOfBlocks();
		result=prime*result+getT();
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this==obj)
			return true;
		if (obj==null || getClass()!=obj.getClass())
			return false;
		if (getBlocksList()==null && ((BNode)obj).getBlocksList()!=null)
			return false;
		else
			if (getBlocksList()!=null && !getBlocksList().equals(((BNode)obj).getBlocksList()))
				return false;
		if (getChildrenList()==null && ((BNode)obj).getChildrenList()!=null)
			return false;
		else
			if (getChildrenList()!=null && !getChildrenList().equals(((BNode)obj).getChildrenList()))
				return false;
		return isLeaf()==((BNode)obj).isLeaf() && getNumOfBlocks()==((BNode)obj).getNumOfBlocks() &&
		       getT()==((BNode)obj).getT();
	}
	
	@SuppressWarnings("SingleCharacterStringConcatenation")
	@Override
	public String toString()
	{
		return "BNode [t="+getT()+", numOfBlocks="+getNumOfBlocks()+", isLeaf="+isLeaf()+
		       ", blocksList="+getBlocksList()+", childrenList="+getChildrenList()+"]";
	}
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	
	@SuppressWarnings("TailRecursion")
	@Override
	public Block search(int key)
	{
		int i=0;//Line 1
		while (i<getNumOfBlocks() && key>getBlockKeyAt(i))//Line 2
			i++;//Line 3
		if (i<getNumOfBlocks() && key==getBlockKeyAt(i))//Line 4
			return getBlockAt(i);//Line 5
		else
			if (isLeaf())//Line 6
				return null;//Line 7
			else//Line 8
				return getChildAt(i).search(key);//Line 10
	}
	
	@Override
	public void insertNonFull(Block b)
	{
		int i=getNumOfBlocks()-1;//Line 1
		if (isLeaf())//Line 2
		{
			while (i>=0 && b.getKey()<getBlockKeyAt(i))//Line 3
			{
				if (i==getNumOfBlocks()-1)
					getBlocksList().add(getBlockAt(getNumOfBlocks()-1));//Line 4
				else
					getBlocksList().set(i+1, getBlockAt(i));//Line 4
				i--;//Line 5
			}
			if (i==getNumOfBlocks()-1)
				getBlocksList().add(i+1, b);//Line 6
			else
				getBlocksList().set(i+1, b);//Line 6
			numOfBlocks++;//Line 7
		}
		else//Line 9
		{
			while (i>=0 && b.getKey()<getBlockKeyAt(i))//Line 10
				i--;//Line 11
			i++;//Line 12
			if (getChildAt(i).isFull())//Line 14
			{
				splitChild(i);//Line 15
				if (b.getKey()>getBlockKeyAt(i))//Without a line number
					i++;//Line 16
			}
			getChildAt(i).insertNonFull(b);//Line 17
		}
	}
	
	@Override
	public void delete(int key)
	{
		int i=0;
		for (; i<getNumOfBlocks() && getBlockKeyAt(i)<=key; i++)
			if (getBlockKeyAt(i)==key)
				if (isLeaf())//Case 1
				{
					getBlocksList().remove(i);
					numOfBlocks--;
					return;
				}
				else
				{
					if (!getChildAt(i).isMinSize())//Case 2
					{
						Block predecessor=getChildAt(i).getMaxKeyBlock();
						getChildAt(i).delete(predecessor.getKey());
						getBlocksList().set(i, predecessor);
						return;
					}
					if (getChildAt(i).isMinSize() && !getChildAt(i+1).isMinSize())//Case 3
					{
						Block successor=getChildAt(i+1).getMinKeyBlock();
						getChildAt(i+1).delete(successor.getKey());
						getBlocksList().set(i, successor);
						return;
					}
					if (getChildAt(i).isMinSize() && getChildAt(i+1).isMinSize())//Case 4
					{
						mergeWithRightSibling(i);
						getChildAt(i).delete(key);
						return;
					}
				}
		if (!isLeaf())
			getChildAt(shiftOrMergeChildIfNeeded(i) && i!=0 ? i-1 : i).delete(key);
	}
	
	@Override
	public MerkleBNode createHashNode()
	{
		ArrayList<byte[]> hashInput;
		if (isLeaf())
		{
			hashInput=new ArrayList<>(getNumOfBlocks());
			for (Block block : getBlocksList())
				hashInput.add(block.getData());
			return new MerkleBNode(HashUtils.sha1Hash(hashInput));
		}
		
		hashInput=new ArrayList<>(getNumOfBlocks()*2+1);
		ArrayList<MerkleBNode> merkleChildrenList=new ArrayList<>(getNumOfBlocks());
		for (BNode child : getChildrenList())//Builds array of Merkle children by in-order scanning
			merkleChildrenList.add(child.createHashNode());
		for (int i=0; i<getNumOfBlocks(); i++)
		{
			hashInput.add(merkleChildrenList.get(i).getHashValue());
			hashInput.add(getBlockAt(i).getData());
		}
		hashInput.add(merkleChildrenList.get(getNumOfBlocks()).getHashValue());
		
		return new MerkleBNode(HashUtils.sha1Hash(hashInput), merkleChildrenList);
	}
	
	/**
	 * Splits the child node at childIndex into 2 nodes.
	 * @param childIndex the child to be split
	 */
	void splitChild(int childIndex)
	{
		getChildrenList().add(childIndex+1, new BNode(getT(), getChildAt(childIndex).isLeaf(),
		                                              getT()-1));//Lines 2-4, 10-12
		getChildAt(childIndex+1).getBlocksList()
		                        .addAll(getChildAt(childIndex)
				                                .getBlocksList()
				                                .subList(getT(), 2*getT()-1));//Lines 5, 6
		if (!getChildAt(childIndex).isLeaf())//Line 7
		{
			getChildAt(childIndex+1).getChildrenList().addAll(getChildAt(childIndex)
					                           .getChildrenList()
					                           .subList(getT(), 2*getT()));//Lines 8, 9
			getChildAt(childIndex).getChildrenList()
			                      .removeAll(getChildAt(childIndex)
					                                 .getChildrenList()
					                                 .subList(getT(), 2*getT()));//Lines 8, 9
		}
		getBlocksList().add(childIndex, getChildAt(childIndex).getBlockAt(getT()-1));//Lines 13-15
		getChildAt(childIndex).getBlocksList()
		                      .removeAll(getChildAt(childIndex)
				                                 .getBlocksList()
				                                 .subList(getT()-1,
				                                          getChildAt(childIndex)
						                                          .getNumOfBlocks()));//Lines 13-15
		numOfBlocks++;//Line 16
		getChildAt(childIndex).numOfBlocks=getT()-1;//Line 17
	}
	
	/**
	 * @param childIndx the child to be checked
	 * @return {@code true} iff the child node at {@code childIndx-1} exists and has more than {@code t-1} blocks.
	 */
	private boolean childHasNonMinimalLeftSibling(int childIndx)
	{
		return childIndx>0 && !getChildAt(childIndx-1).isMinSize();
	}
	
	/**
	 * @param childIndx the child to be checked
	 * @return {@code true} iff the child node at {@code childIndx+1} exists and has more than {@code t-1} blocks.
	 */
	private boolean childHasNonMinimalRightSibling(int childIndx)
	{
		return childIndx<getNumOfBlocks() && !getChildAt(childIndx+1).isMinSize();
	}
	
	/**
	 * Verifies the child node at {@code childIndx} has at least {@code t} blocks.<br>
	 * If necessary a shift or merge is performed.
	 * @param childIndx the child to be shifted or merged to, if needed
	 */
	private boolean shiftOrMergeChildIfNeeded(int childIndx)
	{
		if (getChildAt(childIndx).isMinSize())
		{
			if (childHasNonMinimalLeftSibling(childIndx))
			{
				shiftFromLeftSibling(childIndx);
				return false;
			}
			if (childHasNonMinimalRightSibling(childIndx))
			{
				shiftFromRightSibling(childIndx);
				return false;
			}
			mergeChildWithSibling(childIndx);
			return true;
		}
		return false;
	}
	
	/**
	 * Add additional block to the child node at {@code childIndx}, by shifting from left sibling.
	 * @param childIndx the index to be shifted to from left sibling
	 */
	private void shiftFromLeftSibling(int childIndx)
	{
		getChildAt(childIndx).getBlocksList().add(0, getBlockAt(childIndx-1));
		getChildAt(childIndx).numOfBlocks++;
		
		if (!getChildAt(childIndx-1).isLeaf())
			getChildAt(childIndx).getChildrenList().add(0, getChildAt(childIndx-1)
					                                               .getChildrenList()
					                                               .remove(getChildAt(childIndx-1)
							                                                       .getNumOfBlocks()));
		
		getBlocksList().set(childIndx-1, getChildAt(childIndx-1).getBlocksList()
		                                                        .remove(getChildAt(childIndx-1)
				                                                                .getNumOfBlocks()-1));
		
		getChildAt(childIndx-1).numOfBlocks--;
	}
	
	/**
	 * Add additional block to the child node at {@code childIndx}, by shifting from right sibling.
	 * @param childIndx the index to be shifted to from right sibling
	 */
	private void shiftFromRightSibling(int childIndx)
	{
		getChildAt(childIndx).getBlocksList().add(getBlockAt(childIndx));
		getChildAt(childIndx).numOfBlocks++;
		
		if (!getChildAt(childIndx+1).isLeaf())
			getChildAt(childIndx).getChildrenList().add(getChildAt(childIndx+1).getChildrenList()
			                                                                   .remove(0));
		
		getBlocksList().set(childIndx, getChildAt(childIndx+1).getBlocksList().remove(0));
		
		getChildAt(childIndx+1).numOfBlocks--;
	}
	
	/**
	 * Merges the child node at {@code childIndx} with its left or right sibling.
	 * @param childIndx the index to be merged with
	 */
	private void mergeChildWithSibling(int childIndx)
	{
		if (childIndx>0)
			mergeWithLeftSibling(childIndx);
		else
			mergeWithRightSibling(childIndx);
	}
	
	/**
	 * Merges the child node at {@code childIndx} with its left sibling.<br>
	 * The left sibling node is removed.
	 * @param childIndx the index to be merged with
	 */
	private void mergeWithLeftSibling(int childIndx)
	{
		getChildAt(childIndx-1).getBlocksList().add(getBlocksList().remove(childIndx-1));
		
		//Adds to the childIndxth-1 child the blocks of the childIndxth child
		getChildAt(childIndx-1).getBlocksList().addAll(getChildAt(childIndx).getBlocksList());
		getChildAt(childIndx-1).numOfBlocks+=getChildAt(childIndx).getNumOfBlocks()+1;
		
		//Adds to the childIndxth-1 child the childes of the childIndxth child
		if (!getChildAt(childIndx).isLeaf())
		{
			getChildAt(childIndx-1).isLeaf=false;
			getChildAt(childIndx-1).getChildrenList().addAll(getChildAt(childIndx).getChildrenList());
		}
		
		//Deletes the childIndx child
		getChildrenList().remove(childIndx);
		numOfBlocks--;
	}
	
	/**
	 * Merges the child node at {@code childIndx} with its right sibling.<br>
	 * The right sibling node is removed.
	 * @param childIndx the index to be merged with
	 */
	void mergeWithRightSibling(int childIndx)
	{
		getChildAt(childIndx+1).getBlocksList().add(0, getBlocksList().remove(childIndx));
		
		//Adds to the childIndxth+1 child the blocks of the childIndxth child
		getChildAt(childIndx+1).getBlocksList().addAll(0, getChildAt(childIndx).getBlocksList());
		getChildAt(childIndx+1).numOfBlocks+=getChildAt(childIndx).getNumOfBlocks()+1;
		
		//Adds to the childIndxth+1 child the childes of the childIndxth child
		if (!getChildAt(childIndx).isLeaf())
		{
			getChildAt(childIndx+1).isLeaf=false;
			getChildAt(childIndx+1).getChildrenList().addAll(0, getChildAt(childIndx)
					                                                    .getChildrenList());
		}
		
		//Deletes the childIndx child
		getChildrenList().remove(childIndx);
		numOfBlocks--;
	}
	
	/**
	 * Finds and returns the block with the min key in the subtree.
	 * @return min key block
	 */
	private Block getMinKeyBlock()
	{
		BNode i=this;
		while (!i.isLeaf())
			i=i.getChildAt(0);
		return i.getBlockAt(0);
	}
	
	/**
	 * Finds and returns the block with the max key in the subtree.
	 * @return max key block
	 */
	private Block getMaxKeyBlock()
	{
		BNode i=this;
		while (!i.isLeaf())
			i=i.getChildAt(i.getNumOfBlocks());
		return i.getBlockAt(i.getNumOfBlocks()-1);
	}
}