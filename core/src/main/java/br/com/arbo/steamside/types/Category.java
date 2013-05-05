package br.com.arbo.steamside.types;

public class Category {

	public final String category;

	public Category(final String category) {
		this.category = category;
	}

	public interface Visitor {

		void visit(Category each);
	}

	@Override
	public String toString() {
		return category;
	}
}
