// SUBMIT
class BTree implements BTreeInterface
{
	private final int t;
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private BNode root;
	
	/**
	 * Construct an empty tree.
	 */
	public BTree(int t)
	{
		this.t=t;
		//root=null;
	}
	
	// For testing purposes.
	public BTree(int t, BNode root)
	{
		this(t);
		this.root=root;
	}
	
	@Override
	public BNode getRoot()
	{
		return root;
	}
	
	@Override
	public int getT()
	{
		return t;
	}
	
	@Override
	public int hashCode()
	{
		final int prime=31;
		int result=1;
		result=prime*result+(root==null ? 0 : root.hashCode());
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
		BTree other=(BTree)obj;
		if (root==null)
		{
			if (other.root!=null)
				return false;
		}
		else
			if (!root.equals(other.root))
				return false;
		return t==other.t;
	}
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	
	@Override
	public Block search(int key)
	{
		return getRoot()!=null ? getRoot().search(key) : null;
	}
	
	@Override
	public void insert(Block b)
	{
		if (b==null)
			return;
		if (getRoot()==null)
		{
			root=new BNode(getT(), b);
			return;
		}
		if (getRoot().getNumOfBlocks()==2*getT()-1)//Line 2
		{
			root=new BNode(getT(), getRoot());//Lines 3-7
			getRoot().splitChild(0);//Line 8
			getRoot().insertNonFull(b);//Line 9
		}
		else//Line 10
			getRoot().insertNonFull(b);//Line 11
	}
	
	@Override
	public void delete(int key)
	{
		if (root!=null)
		{
			if (getRoot().getNumOfBlocks()==1 && !getRoot().isLeaf() &&
			    getRoot().getChildAt(0).getNumOfBlocks()==1 &&
			    getRoot().getChildAt(1).getNumOfBlocks()==1)
				if (getRoot().childHasNonMinimalRightSibling(0))
				{
					getRoot().mergeWithRightSibling(0);
					root=getRoot().getChildAt(0);
				}
				else
				{
					getRoot().mergeWithRightSibling(0);
					root=getRoot().getChildAt(0);
				}
			root.delete(key);
//			if (getRoot().getNumOfBlocks()==0)
//				return;
//			int last=getRoot().getBlockKeyAt(getRoot().getNumOfBlocks()-1);
//			if (getRoot().getNumOfBlocks()>=2*getT()-1)
//			{
//				Block block=getRoot().getBlockAt(getRoot().getNumOfBlocks()-1);
//				root=new BNode(getT(), getRoot());
//				getRoot().splitChild(0);
//				if (key!=last && getRoot().getChildAt(1).getBlockKeyAt(getRoot().getChildAt(1).getNumOfBlocks()-1)!=last)
//				{
//					getRoot().getChildAt(1).getBlocksList().add(block);
//					getRoot().getChildAt(1).setNumOfBlocks(getRoot().getChildAt(1)
//					                                                .getNumOfBlocks()+1);
//				}
//			}
		}
	}
	
	@Override
	public MerkleBNode createMBT()
	{
		if(root!=null)
			return root.createHashNode();
		return null;
	}
}