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
			root.delete(key);
	}
	
	@Override
	public MerkleBNode createMBT()
	{
		// TODO Auto-generated method stub
		return null;
	}
}