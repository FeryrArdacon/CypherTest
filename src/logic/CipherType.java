package logic;

public class CipherType {
	private String algor = "";
	private String cypherAlgor = "";
	private short bits = 0;

	public CipherType(String algor, String cypherAlgor, short bits) {
		this.algor = algor;
		this.cypherAlgor = cypherAlgor;
		this.bits = bits;
	}

	public String getAlgor() {
		return this.algor;
	}

	public short getBits() {
		return this.bits;
	}

	public String getCypherAlgor() {
		return cypherAlgor;
	}

	@Override
	public String toString() {
		return this.algor + (this.cypherAlgor.equals("") ? " " : " (" + this.cypherAlgor + ") ") + this.bits + " Bit";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((algor == null) ? 0 : algor.hashCode());
		result = prime * result + bits;
		result = prime * result + ((cypherAlgor == null) ? 0 : cypherAlgor.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CipherType)) {
			return false;
		}
		CipherType other = (CipherType) obj;
		if (algor == null) {
			if (other.algor != null) {
				return false;
			}
		} else if (!algor.equals(other.algor)) {
			return false;
		}
		if (bits != other.bits) {
			return false;
		}
		if (cypherAlgor == null) {
			if (other.cypherAlgor != null) {
				return false;
			}
		} else if (!cypherAlgor.equals(other.cypherAlgor)) {
			return false;
		}
		return true;
	}

}
