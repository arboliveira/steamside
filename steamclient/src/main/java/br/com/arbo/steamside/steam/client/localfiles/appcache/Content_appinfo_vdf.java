package br.com.arbo.steamside.steam.client.localfiles.appcache;

import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_cpp.KEYVALUES_TOKEN_SIZE;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.apache.log4j.Logger;

import br.com.arbo.steamside.steam.client.vdf.KeyValueVisitor;

public class Content_appinfo_vdf
{

	private static ByteBufferX newBuffer(final FileInputStream f)
	{
		final FileChannel ch = f.getChannel();
		final MappedByteBuffer map;
		try
		{
			map = ch.map(MapMode.READ_ONLY, 0L, ch.size());
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		return new ByteBufferX(map, KEYVALUES_TOKEN_SIZE * 2);
	}

	public void accept(final Content_appinfo_vdf_Visitor visitor)
	{
		read__uint32_t("version: 0x07564427");
		read__uint32_t("universe: enum EUniverse");
		while (true)
		{
			int app_id = read__uint32_t("app_id");
			if (app_id == 0) break;
			go_app_id(app_id, visitor);
		}
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass());
	}

	private void go_app_id(int app_id, Content_appinfo_vdf_Visitor visitor)
	{
		visitor.onApp(app_id);

		read__uint32_t("data_size");
		read__uint32_t("infoState");
		read__uint32_t("last_updated");
		read__uint64_t("accessToken");
		read__uint8_t(20, "checksum: sha[20]");
		read__uint32_t("change_number");

		KeyValues_cpp.readAsBinary(buffer, visitor);

		visitor.onAppEnd();
	}

	private int read__uint32_t(String info)
	{
		int uint32_t = buffer.read__uint32_t();

		getLogger().debug(new HexLog(new HexInteger(uint32_t), info));

		return uint32_t;
	}

	private long read__uint64_t(String info)
	{
		long uint64_t = buffer.read__uint64_t();

		getLogger().debug(new HexLog(new HexLong(uint64_t), info));

		return uint64_t;
	}

	private byte[] read__uint8_t(int size, String info)
	{
		byte[] data = new byte[size];

		for (int i = 1; i <= size; i++)
			data[i - 1] = buffer.read__uint8_t();

		getLogger().debug(new HexLog(new HexBytes(data), info));

		return data;
	}

	public Content_appinfo_vdf(final FileInputStream f)
	{
		buffer = newBuffer(f);
	}

	private final ByteBufferX buffer;

	public interface Content_appinfo_vdf_Visitor extends KeyValueVisitor
	{

		void onApp(int app_id);

		void onAppEnd();
	}

	static class HexLog
	{

		@Override
		public String toString()
		{
			return hex.toString() + " | " + info;
		}

		HexLog(Object hex, String info)
		{
			this.hex = hex;
			this.info = info;
		}

		private final Object hex;

		private final String info;

	}

}
