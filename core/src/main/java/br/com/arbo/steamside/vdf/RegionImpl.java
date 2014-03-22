package br.com.arbo.steamside.vdf;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

import br.com.arbo.steamside.vdf.KeyValueVisitor.Finished;

public class RegionImpl implements Region {

	RegionImpl(final ReaderFactory rf) {
		this.parent = rf;
	}

	@Override
	public void accept(final KeyValueVisitor visitor)
	{
		final Reader r = newReaderFromParent();
		accept(visitor, r);
	}

	public RegionImpl region(final String name) throws NotFound
	{
		class Find implements KeyValueVisitor {

			@Override
			public void onKeyValue(final String k, final String v)
				throws Finished
			{
				// Do nothing
			}

			@Override
			public void onSubRegion(final String k, final Region r)
				throws Finished
			{
				if (k.equalsIgnoreCase(name)) {
					found = (RegionImpl) r;
					throw new Finished();
				}
				skipPastEndOfRegion();
			}

			RegionImpl found;

		}

		final Find find = new Find();
		accept(find);
		if (find.found != null) return find.found;
		throw NotFound.name(name);
	}

	void accept(final KeyValueVisitor visitor, final Reader reader)
	{
		Tokenize tokenize = new Tokenize(reader);
		tokenize.tokenize(visitor);
	}

	Reader newReaderFromParent()
	{
		return parent.newReaderPositionedInside();
	}

	void skipPastEndOfRegion()
	{
		class DoNothing implements KeyValueVisitor {

			@Override
			public void onKeyValue(final String k, final String v)
				throws Finished
			{
				// 
			}

			@Override
			public void onSubRegion(final String k, final Region r)
				throws Finished
			{
				skipPastEndOfRegion();
			}

		}
		accept(new DoNothing());
	}

	class RegionReaderFactory implements ReaderFactory {

		@Override
		public Reader newReaderPositionedInside()
		{
			return newReaderFromParent();
		}

	}

	class Tokenize {

		Tokenize(Reader reader) {
			tokenizer = StreamTokenizerBuilder.build(reader);
		}

		void tokenize(final KeyValueVisitor visitor)
		{
			try {
				while (true)
					advance(visitor);
			}
			catch (final Finished ok) {
				//
			}
			catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void advance(final KeyValueVisitor visitor)
			throws IOException, Finished
		{
			nextToken();
			final String key = tokenizer.sval;

			if (key == null) finish();

			nextToken();
			final String value = tokenizer.sval;

			if (value != null) {
				visitor.onKeyValue(key, value);
				return;
			}

			// no value? it's a named sub region

			if (tokenizer.ttype != '{') throw new IllegalStateException();

			final RegionImpl sub = new RegionImpl(new RegionReaderFactory());
			visitor.onSubRegion(key, sub);
		}

		private void finish() throws Finished
		{
			final int ttype = tokenizer.ttype;
			if (ttype == '}' || ttype == StreamTokenizer.TT_EOF)
				throw new Finished();
			throw new IllegalStateException();
		}

		private void nextToken() throws IOException
		{
			tokenizer.nextToken();
		}

		private final StreamTokenizer tokenizer;

	}

	private final ReaderFactory parent;
}
