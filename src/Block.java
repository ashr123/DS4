import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

/// DO NOT CHANGE
/// DO NOT SUBMIT
/**
 * A Block is a part of a larger file, to be stored in B-Tree nodes.
 */
class Block
{
	static final int BLOCK_SIZE=200;
	
	private int key;
	private byte[] data;
	
	Block(int key, byte[] data)
	{
		this.key=key;
		this.data=data;
	}
	
	/**
	 * Generates a sequence of blocks with random data. With keys running from
	 * fromKey to toKey, inclusive.
	 * @param fromKey
	 * @param toKey
	 * @return ArrayList of blocks
	 */
	static ArrayList<Block> blockFactory(int fromKey, int toKey)
	{
		SecureRandom random=new SecureRandom();
		ArrayList<Block> blocks=new ArrayList<>(toKey-fromKey+1);
		for (int i=fromKey; i<=toKey; i++)
		{
			BigInteger bi=new BigInteger(BLOCK_SIZE*8, random);
			byte[] data=bi.toByteArray();
			blocks.add(new Block(i, data));
		}
		return blocks;
	}
	
	int getKey()
	{
		return key;
	}
	
	byte[] getData()
	{
		return data;
	}
	
	@Override
	public int hashCode()
	{
		final int prime=31;
		int result=1;
		result=prime*result+Arrays.hashCode(data);
		result=prime*result+key;
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
		Block other=(Block)obj;
		return key==other.key && Arrays.equals(data, other.data);
	}
	
	@Override
	public String toString()
	{
		return "Block [key="+key+"]";
		//return "Block [key=" + key + ", data=" + Arrays.toString(data) + "]";
	}
}