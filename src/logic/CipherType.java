package logic;

public class CipherType
{
	private String algor = "";
	private short bits = 0;
	
	public CipherType(String algor, short bits)
	{
		this.algor = algor;
		this.bits = bits;
	}
	
	public String getAlgor()
	{
		return this.algor;
	}
	
	public short getBits()
	{
		return this.bits;
	}
	
	@Override
	public String toString()
	{
		return this.algor + " " + this.bits + " Bit";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.algor == null) ? 0 : this.algor.hashCode());
		result = prime * result + this.bits;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CipherType other = (CipherType) obj;
		if (this.algor == null)
		{
			if (other.algor != null)
				return false;
		} else if (!this.algor.equals(other.algor))
			return false;
		if (this.bits != other.bits)
			return false;
		return true;
	}
	
}
