import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
//Changed!!!
/// DO NOT CHANGE
/// DO NOT SUBMIT
class HashUtils
{
	/**
	 * Concatenates the given data and hashes it with SHA1
	 * @param dataList an ArrayList of byte arrays (byte[]) containing the data to be concatenated and hashed.
	 * @return 20 byte fixed-length hash value.
	 */
	static byte[] sha1Hash(ArrayList<byte[]> dataList)
	{
		MessageDigest crypt=null;
		try
		{
			crypt=MessageDigest.getInstance("SHA-1");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		if (crypt != null)
		{
			crypt.reset();
		}
		for (byte[] data : dataList)
		{
			if (crypt != null)
			{
				crypt.update(data);
			}
		}
		return crypt!=null ? crypt.digest() : new byte[0];
	}
}