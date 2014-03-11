package br.com.arbo.steamside.types;

public class CollectionName {

	public final String value;

	public CollectionName(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public boolean equalsCollectionName(CollectionName name) {
		return value.equals(name.value);
	}

}
