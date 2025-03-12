package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import static br.com.arbo.steamside.steam.client.localfiles.appinfo.KeyValues_h.TYPE_INT;
import static br.com.arbo.steamside.steam.client.localfiles.appinfo.KeyValues_h.TYPE_NONE;
import static br.com.arbo.steamside.steam.client.localfiles.appinfo.KeyValues_h.TYPE_NUMTYPES;
import static br.com.arbo.steamside.steam.client.localfiles.appinfo.KeyValues_h.TYPE_STRING;
import static br.com.arbo.steamside.steam.client.localfiles.appinfo.KeyValues_h.TYPE_UINT64;

import java.util.Optional;

import br.com.arbo.steamside.steam.client.localfiles.io.ByteBufferX;
import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;
import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor.Finished;
import br.com.arbo.steamside.steam.client.localfiles.vdf.Region;

/**
 https://github.com/ValveSoftware/source-sdk-2013/blob/master/src/tier1/KeyValues.cpp

 https://github.com/tralph3/Steam-Metadata-Editor/blob/5c6ec345417c48160ea9798d97643c6f0e82ba7d/src/appinfo.py#L85-L87
 */
public class KeyValues_cpp
{

	public static void readAsBinary(
		final ByteBufferX buffer, final String[] stringPool,
		final KeyValueVisitor visitor)
	{
		while (true)
		{
			final int position = buffer.position();
			final byte type = buffer.getUnsignedChar();
			if (type == TYPE_NUMTYPES)
			{
				break;
			}
			try
			{
				readNameValue(buffer, stringPool, visitor, type, position);
			}
			catch (final Finished e)
			{
				break;
			}
		}

	}

	private static void readNameValue(
			final ByteBufferX buffer, final String[] stringPool,
			final KeyValueVisitor visitor,
			final byte type, final int position
	) throws Finished
	{
		class RegionImpl implements Region
		{

			@Override
			public void accept(final KeyValueVisitor visitor)
			{
				readAsBinary(buffer, stringPool, visitor);
			}

			@Override
			public Optional<Region> region(String name)
			{
				throw new UnsupportedOperationException();
			}

		}

		final int index = buffer.read__uint32_t();
		final String name = stringPool[index];
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
			throw new UnexpectedType(type, name, position);
		}
	}

	public static final int KEYVALUES_TOKEN_SIZE = 4096;
}
