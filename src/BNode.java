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
	private ArrayList<Block> blocksList;
	private ArrayList<BNode> childrenList;
	
	/**
	 * Constructor for creating a node with a single child.<br>
	 * Useful for creating a new root.
	 */
	public BNode(int t, BNode firstChild)
	{
		this(t, false, 0);
		childrenList.add(firstChild);
	}
	
	/**
	 * Constructor for creating a <b>leaf</b> node with a single block.
	 */
	public BNode(int t, Block firstBlock)
	{
		this(t, true, 1);
		blocksList.add(firstBlock);
	}
	
	public BNode(int t, boolean isLeaf, int numOfBlocks)
	{
		this.t=t;
		this.isLeaf=isLeaf;
		this.numOfBlocks=numOfBlocks;
		blocksList=new ArrayList<>();
		childrenList=new ArrayList<>();
	}
	
	// For testing purposes.
	public BNode(int t, int numOfBlocks, boolean isLeaf,
	             ArrayList<Block> blocksList, ArrayList<BNode> childrenList)
	{
		this.t=t;
		this.numOfBlocks=numOfBlocks;
		this.isLeaf=isLeaf;
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
		return numOfBlocks==2*t-1;
	}
	
	@Override
	public boolean isMinSize()
	{
		return numOfBlocks==t-1;
	}
	
	@Override
	public boolean isEmpty()
	{
		return numOfBlocks==0;
	}
	
	@Override
	public int getBlockKeyAt(int indx)
	{
		return blocksList.get(indx).getKey();
	}
	
	@Override
	public Block getBlockAt(int indx)
	{
		return blocksList.get(indx);
	}
	
	@Override
	public BNode getChildAt(int indx)
	{
		return childrenList.get(indx);
	}
	
	@Override
	public int hashCode()
	{
		final int prime=31;
		int result=1;
		result=prime*result+(blocksList==null ? 0 : blocksList.hashCode());
		result=prime*result+(childrenList==null ? 0 : childrenList.hashCode());
		result=prime*result+(isLeaf ? 1231 : 1237);
		result=prime*result+numOfBlocks;
		result=prime*result+t;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this==obj)
			return true;
		if (obj==null)
			return false;
		if (getClass()!=obj.getClass())
			return false;
		BNode other=(BNode)obj;
		if (blocksList==null)
		{
			if (other.blocksList!=null)
				return false;
		}
		else
			if (!blocksList.equals(other.blocksList))
				return false;
		if (childrenList==null)
		{
			if (other.childrenList!=null)
				return false;
		}
		else
			if (!childrenList.equals(other.childrenList))
				return false;
		return isLeaf==other.isLeaf && numOfBlocks==other.numOfBlocks && t==other.t;
	}
	
	@Override
	public String toString()
	{
		return "BNode [t="+t+", numOfBlocks="+numOfBlocks+", isLeaf="
		       +isLeaf+", blocksList="+blocksList+", childrenList="
		       +childrenList+"]";
	}
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	
	
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
	public void insertNonFull(Block d)
	{
		int i=getNumOfBlocks()-1;//Line 1
		if (isLeaf())//Line 2
		{
			while (i>=0 && d.getKey()<getBlockKeyAt(i))//Line 3
			{
				getBlocksList().set(i+1, getBlockAt(i));//Line 4
				i--;//Line 5
			}
			getBlocksList().set(i+1, d);//Line 6
			numOfBlocks++;//Line 7
		}
		else//Line 9
		{
			while (i>=0 && d.getKey()<getBlockKeyAt(i))//Line 10
				i--;//Line 11
			i++;//Line 12
			if (getChildAt(i).getNumOfBlocks()==2*getT()-1)//Line 14
			{
				splitChild(i);//Line 15
				if (d.getKey()>getBlockKeyAt(i))//Without a line number
					i++;//Line 16
			}
			getChildAt(i).insertNonFull(d);//Line 17
		}
	}
	
	@Override
	public void delete(int key)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public MerkleBNode createHashNode()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Splits the child node at childIndex into 2 nodes.
	 * @param childIndex the child to be split
	 */
	public void splitChild(int childIndex)
	{
		BNode y=getChildAt(childIndex);//Line 1
		BNode z=new BNode(getT(), y.isLeaf(), getT()-1);//Lines 2-4
		for (int i=0; i<getT()-1; i++)//Line 5
			z.getBlocksList().set(i, y.getBlockAt(i+getT()));//Line 6
		if (!y.isLeaf())//Line 7
			for (int i=0; i<getT(); i++)//Line 8
				z.getChildrenList().set(i, y.getChildAt(i+getT()));//Line 9
		for (int i=getNumOfBlocks(); i>=childIndex+1; i--)//Line 10
			getChildrenList().set(i+1, getChildrenList().get(i));//11
		getChildrenList().set(childIndex+1, z);//Line 12
		for (int i=getNumOfBlocks()-1; i>=childIndex; i++)//Line 13
			getBlocksList().set(i+1, getBlocksList().get(i));//Line 14
		getBlocksList().set(childIndex, y.getBlockAt(getT()));//Line 15
		numOfBlocks++;//Line 16
		y.numOfBlocks=getT()-1;//Line 17
	}
	
	/**
	 * @param childIndx
	 * @return {@code true} iff the child node at {@code childIndx-1} exists and has more than {@code t-1} blocks.
	 */
	private boolean childHasNonMinimalLeftSibling(int childIndx)
	{
		return childIndx>=1 && getChildAt(childIndx-1)!=null && getChildAt(childIndx-1).getNumOfBlocks()>getT()-1;
	}
	
	/**
	 * @param childIndx
	 * @return {@code true} iff the child node at {@code childIndx+1} exists and has more than {@code t-1} blocks.
	 */
	private boolean childHasNonMinimalRightSibling(int childIndx)
	{
		return childIndx+1<=getNumOfBlocks() && getChildAt(childIndx+1)!=null && getChildAt(childIndx+1).getNumOfBlocks()>getT()-1;
	}
	
	/**
	 * Verifies the child node at childIndx has at least t blocks.<br>
	 * If necessary a shift or merge is performed.
	 * @param childIndx
	 */
	private void shiftOrMergeChildIfNeeded(int childIndx)
	{
		//TODO shiftOrMergeChildIfNeeded
		if (childHasNonMinimalLeftSibling(childIndx))
		{
			shiftFromLeftSibling(childIndx);
			return;
		}
		if (childHasNonMinimalRightSibling(childIndx))
		{
			shiftFromRightSibling(childIndx);
			return;
		}
		mergeChildWithSibling(childIndx);
	}
	
	/**
	 * Add additional block to the child node at childIndx, by shifting from left sibling.
	 * @param childIndx
	 */
	private void shiftFromLeftSibling(int childIndx)
	{
		//TODO shiftFromLeftSibling
	}
	
	/**
	 * Add additional block to the child node at childIndx, by shifting from right sibling.
	 * @param childIndx
	 */
	private void shiftFromRightSibling(int childIndx)
	{
		//TODO shiftFromRightSibling
	}
	
	/**
	 * Merges the child node at childIndx with its left or right sibling.
	 * @param childIndx
	 */
	private void mergeChildWithSibling(int childIndx)
	{
		//TODO mergeChildWithSibling
		if (childIndx>=1 && getChildAt(childIndx-1)!=null)
		{
			mergeWithLeftSibling(childIndx);
			return;
		}
		mergeWithRightSibling(childIndx);
	}
	
	/**
	 * Merges the child node at childIndx with its left sibling.<br>
	 * The left sibling node is removed.
	 * @param childIndx
	 */
	private void mergeWithLeftSibling(int childIndx)
	{
		//TODO mergeWithLeftSibling
	}
	
	/**
	 * Merges the child node at childIndx with its right sibling.<br>
	 * The right sibling node is removed.
	 * @param childIndx
	 */
	private void mergeWithRightSibling(int childIndx)
	{
		//TODO mergeWithRightSibling
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
			i=i.getChildAt(getNumOfBlocks());
		return i.getBlockAt(getNumOfBlocks()-1);
	}
}