package br.com.arbo.steamside.steam.client.localfiles.appcache;

import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_INT;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_NONE;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_NUMTYPES;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_STRING;
import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.TYPE_UINT64;

import br.com.arbo.steamside.steam.client.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.vdf.KeyValueVisitor.Finished;
import br.com.arbo.steamside.steam.client.vdf.NotFound;
import br.com.arbo.steamside.steam.client.vdf.Region;

/**
https://github.com/ValveSoftware/source-sdk-2013/blob/master/mp/src/tier1/KeyValues.cpp
 */
public class KeyValues_cpp
{

	public static void readAsBinary(
		final ByteBufferX buffer,
		final KeyValueVisitor visitor)
	{
		while (true)
		{
			final byte type = buffer.getUnsignedChar();
			if (type == TYPE_NUMTYPES)
			{
				break;
			}
			try
			{
				readNameValue(buffer, visitor, type);
			}
			catch (final Finished e)
			{
				break;
			}
		}

	}

	private static void readNameValue(final ByteBufferX buffer,
		final KeyValueVisitor visitor, final byte type) throws Finished
	{
		class RegionImpl implements Region
		{

			@Override
			public void accept(final KeyValueVisitor visitor)
			{
				readAsBinary(buffer, visitor);
			}

			@Override
			public Region region(String name) throws NotFound
			{
				throw new UnsupportedOperationException();
			}

		}

		final String name = buffer.read__null_terminated_string();
		switch (type)
		{
		case TYPE_NONE:
			visitor.onSubRegion(name, new RegionImpl());
			break;
		case TYPE_STRING:
			final String string = buffer.read__null_terminated_string();
			visitor.onKeyValue(name, string);
			break;
		case TYPE_INT:
			final int uint32 = buffer.read__uint32_t();
			visitor.onKeyValue(name, String.valueOf(uint32));
			break;
		case TYPE_UINT64:
			final long uint64 = buffer.read__uint64_t();
			visitor.onKeyValue(name, String.valueOf(uint64));
			break;
		default:
			throw new UnexpectedType(type, name);
		}
	}

	public static final int KEYVALUES_TOKEN_SIZE = 4096;
}
