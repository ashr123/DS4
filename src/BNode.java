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
	BNode(int t, BNode firstChild)
	{
		this(t, false, 0);
		childrenList.add(firstChild);
	}
	
	/**
	 * Constructor for creating a <b>leaf</b> node with a single block.
	 */
	BNode(int t, Block firstBlock)
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
	public BNode(int t, int numOfBlocks, boolean isLeaf, ArrayList<Block> blocksList,
	             ArrayList<BNode> childrenList)
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
	
	@SuppressWarnings("SingleCharacterStringConcatenation")
	@Override
	public String toString()
	{
		return "BNode [t="+t+", numOfBlocks="+numOfBlocks+", isLeaf="+isLeaf+", blocksList="+blocksList
		       +", childrenList="+childrenList+"]";
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
	public void insertNonFull(Block d)
	{
		int i=getNumOfBlocks()-1;//Line 1
		if (isLeaf())//Line 2
		{
			while (i>=0 && d.getKey()<getBlockKeyAt(i))//Line 3
			{
				if (i==getNumOfBlocks()-1)
					getBlocksList().add(getBlockAt(getNumOfBlocks()-1));//Line 4
				else
					getBlocksList().set(i+1, getBlockAt(i));//Line 4
				i--;//Line 5
			}
			if (i==getNumOfBlocks()-1)
				getBlocksList().add(i+1, d);//Line 6
			else
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
		int i=0;
		for (; i<getNumOfBlocks() && getBlockAt(i).getKey()<=key; i++)
			if (getBlockAt(i).getKey()==key)
				if (isLeaf())//Case 1
				{
					getBlocksList().remove(i);
					numOfBlocks--;
					return;
				}
				else
				{
					if (getChildAt(i).getNumOfBlocks()>=getT())//Case 2
					{
						Block predecessor=getChildAt(i).getMaxKeyBlock();
						getChildAt(i).delete(predecessor.getKey());
						getBlocksList().set(i, predecessor);
						return;
					}
					if (getChildAt(i).getNumOfBlocks()==getT()-1 && getChildAt(i+1)
							                                                .getNumOfBlocks()>=getT())//Case 3
					{
						Block successor=getChildAt(i+1).getMinKeyBlock();
						getChildAt(i+1).delete(successor.getKey());
						getBlocksList().set(i, successor);
						return;
					}
					if (getChildAt(i).getNumOfBlocks()==getT()-1 && getChildAt(i+1)
							                                                .getNumOfBlocks()==getT()-1)//Case 4
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
			for (Block block : blocksList)
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
		BNode y=getChildAt(childIndex);//Line 1
		BNode z=new BNode(getT(), y.isLeaf(), getT()-1);//Lines 2-4
		for (int i=0; i<getT()-1; i++)//Line 5
			z.getBlocksList().add(y.getBlockAt(i+getT()));//Line 6
		if (!y.isLeaf())//Line 7
			for (int i=0; i<getT(); i++)//Line 8
				z.getChildrenList().add(y.getChildrenList().remove(getT()));//Line 9
		getChildrenList().add(childIndex+1, z);//Lines 10-12
		getBlocksList().add(childIndex, y.getBlockAt(getT()-1));//Lines 13-15
		for (int i=getT()-1; i<y.getNumOfBlocks(); i++)
			y.getBlocksList().remove(getT()-1);
		numOfBlocks++;//Line 16
		y.numOfBlocks=getT()-1;//Line 17
	}
	
	/**
	 * @param childIndx the child to be checked
	 * @return {@code true} iff the child node at {@code childIndx-1} exists and has more than {@code t-1} blocks.
	 */
	private boolean childHasNonMinimalLeftSibling(int childIndx)
	{
		return childIndx>=1 && !isLeaf() && getChildAt(childIndx-1)!=null && getChildAt(childIndx-1)
				                                                        .getNumOfBlocks()>getT()-1;
	}
	
	/**
	 * @param childIndx the child to be checked
	 * @return {@code true} iff the child node at {@code childIndx+1} exists and has more than {@code t-1} blocks.
	 */
	private boolean childHasNonMinimalRightSibling(int childIndx)
	{
		return childIndx+1<=getNumOfBlocks() && !isLeaf() && getChildAt(childIndx+1)!=null &&
		       getChildAt(childIndx+1).getNumOfBlocks()>getT()-1;
	}
	
	/**
	 * Verifies the child node at {@code childIndx} has at least {@code t} blocks.<br>
	 * If necessary a shift or merge is performed.
	 * @param childIndx the child to be shifted or merged to, if needed
	 */
	private boolean shiftOrMergeChildIfNeeded(int childIndx)
	{
		if (getChildAt(childIndx).getNumOfBlocks()<getT())
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
		{
			getChildAt(childIndx).getChildrenList().add(0, getChildAt(childIndx-1)
					                                            .getChildAt(getChildAt(childIndx-1)
							                                                           .getNumOfBlocks()));
			getChildAt(childIndx-1).getChildrenList().remove(getChildAt(childIndx-1)
					                                                 .getNumOfBlocks());
		}
		
		getBlocksList().set(childIndx-1, getChildAt(childIndx-1)
				                                 .getBlockAt(getChildAt(childIndx-1)
						                                             .getNumOfBlocks()-1));
		getChildAt(childIndx-1).getBlocksList().remove(getChildAt(childIndx-1).getNumOfBlocks()-1);
		
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
		{
			getChildAt(childIndx).getChildrenList().add(getChildAt(childIndx+1).getChildAt(0));
			getChildAt(childIndx+1).getChildrenList().remove(0);
		}
		
		getBlocksList().set(childIndx, getChildAt(childIndx+1).getBlockAt(0));
		getChildAt(childIndx+1).getBlocksList().remove(0);
		
		getChildAt(childIndx+1).numOfBlocks--;
	}
	
	/**
	 * Merges the child node at {@code childIndx} with its left or right sibling.
	 * @param childIndx the index to be merged with
	 */
	private void mergeChildWithSibling(int childIndx)
	{
		if (childIndx>=1 && !isLeaf() && getChildAt(childIndx-1)!=null)
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
		getChildAt(childIndx-1).getBlocksList().add(getBlockAt(childIndx-1));
		
		//Adds to the childIndxth-1 child the blocks of the childIndxth child
		getChildAt(childIndx-1).getBlocksList().addAll(getChildAt(childIndx).getBlocksList());
		getChildAt(childIndx-1).numOfBlocks+=getChildAt(childIndx).getNumOfBlocks()+1;
		
		//Adds to the childIndxth-1 child the childes of the childIndxth child
		if (!getChildAt(childIndx).isLeaf())
		{
			if (getChildAt(childIndx-1).isLeaf())
				getChildAt(childIndx-1).isLeaf=false;
			getChildAt(childIndx-1).getChildrenList().addAll(getChildAt(childIndx).getChildrenList());
		}
		
		//Deletes the childIndx-1 block
		getBlocksList().remove(childIndx-1);
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
		if (isLeaf())
			return;
		getChildAt(childIndx+1).getBlocksList().add(0, getBlockAt(childIndx));
		
		//Adds to the childIndxth+1 child the blocks of the childIndxth child
		getChildAt(childIndx+1).getBlocksList().addAll(0, getChildAt(childIndx).getBlocksList());
		getChildAt(childIndx+1).numOfBlocks+=getChildAt(childIndx).getNumOfBlocks()+1;
		
		//Adds to the childIndxth+1 child the childes of the childIndxth child
		if (!getChildAt(childIndx).isLeaf())
		{
			if (getChildAt(childIndx+1).isLeaf())
				getChildAt(childIndx+1).isLeaf=false;
			getChildAt(childIndx+1).getChildrenList().addAll(0, getChildAt(childIndx)
					                                                    .getChildrenList());
		}
		
		//Deletes the childIndx block
		getBlocksList().remove(childIndx);
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