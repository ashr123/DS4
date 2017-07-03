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
	@SuppressWarnings("WeakerAccess")
	public BTree(int t)
	{
		this.t=t;
	}
	
	// For testing purposes.
	@SuppressWarnings({"unused", "WeakerAccess"})
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
		result=prime*result+(getRoot()==null ? 0 : getRoot().hashCode());
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
		if (getRoot()==null && ((BTree)obj).getRoot()!=null)
			return false;
		else
			if (getRoot()!=null && !getRoot().equals(((BTree)obj).getRoot()))
				return false;
		return getT()==((BTree)obj).getT();
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
		if (getRoot().isFull())//Line 2
		{
			root=new BNode(getT(), getRoot());//Lines 3-7
			getRoot().splitChild(0);//Line 8
		}
		getRoot().insertNonFull(b);//Line 9-11
	}
	
	@Override
	public void delete(int key)
	{
		if (getRoot()==null)
			return;
		if (getRoot().getNumOfBlocks()==1 && !getRoot().isLeaf() && getRoot().getChildAt(0).isMinSize() &&
		    getRoot().getChildAt(1).isMinSize())
		{
			getRoot().mergeWithRightSibling(0);
			root=getRoot().getChildAt(0);
		}
		getRoot().delete(key);
		if (getRoot().isEmpty())
			root=null;
	}
	
	@Override
	public MerkleBNode createMBT()
	{
		return getRoot()!=null ? getRoot().createHashNode() : null;
	}
}