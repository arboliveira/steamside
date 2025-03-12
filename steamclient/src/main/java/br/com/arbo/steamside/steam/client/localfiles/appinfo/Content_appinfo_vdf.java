package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import static br.com.arbo.steamside.steam.client.localfiles.appinfo.KeyValues_cpp.KEYVALUES_TOKEN_SIZE;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.apache.log4j.Logger;

import br.com.arbo.steamside.steam.client.localfiles.hex.HexBytes;
import br.com.arbo.steamside.steam.client.localfiles.hex.HexInteger;
import br.com.arbo.steamside.steam.client.localfiles.hex.HexLong;
import br.com.arbo.steamside.steam.client.localfiles.io.ByteBufferX;
import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;

/**
 https://github.com/SteamDatabase/SteamAppInfo?tab=readme-ov-file#appinfovdf
 */
public class Content_appinfo_vdf
{

	public void accept(Content_appinfo_vdf_Visitor visitor,
		KeyValueVisitor keyValueVisitor)
	{
		read__uint32_t("MAGIC: 29 44 56 07");
		read__uint32_t("UNIVERSE: 1");

		final long stringTableOffset = read__int64_t("Offset to string table from start of the file");
		final int position = buffer.position();
		buffer.position((int)stringTableOffset);
		final int stringCount = read__uint32_t("Count of strings");
		final String[] stringPool = new String[stringCount];
		for (int i = 0; i < stringCount; i++) {
			stringPool[i] = buffer.read__null_terminated_string();
		}
		buffer.position(position);

		// ---- repeated app sections ----
		while (true)
		{
			int app_id = read__uint32_t("AppID");
			if (app_id == 0) break;
			go_app_id(app_id, stringPool, visitor, keyValueVisitor);
		}
	}

	public Content_appinfo_vdf(final FileInputStream f)
	{
		buffer = newBuffer(f);
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

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass());
	}

	private void go_app_id(
		int app_id, String[] stringPool,
		Content_appinfo_vdf_Visitor visitor,
		KeyValueVisitor keyValueVisitor
	)
	{
		visitor.onApp(app_id);

		read__uint32_t("size // until end of binary_vdf");
		read__uint32_t("infoState // mostly 2, sometimes 1 (may indicate prerelease or no info)");
		read__uint32_t("lastUpdated");
		read__uint64_t("picsToken");
		read__uint8_t(20, "SHA1 // of text appinfo vdf, as seen in CMsgClientPICSProductInfoResponse.AppInfo.sha");
		read__uint32_t("changeNumber");
		read__uint8_t(20, "SHA1 // of binary_vdf");

		// binary_vdf
		KeyValues_cpp.readAsBinary(buffer, stringPool, keyValueVisitor);

		visitor.onAppEnd();
	}

	private long read__int64_t(String info)
	{
		long int64_t = buffer.read__int64_t();

		getLogger().debug(new HexLog(new HexLong(int64_t), info));

		return int64_t;
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

	private static ByteBufferX newBuffer(final FileInputStream f)
	{
        return new ByteBufferX(
				getByteBuffer(f.getChannel()),
				KEYVALUES_TOKEN_SIZE * 2
		);
	}

	private static ByteBuffer getByteBuffer(FileChannel ch) {
		try
		{
			return ch.map(MapMode.READ_ONLY, 0L, ch.size());
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private final ByteBufferX buffer;

	public interface Content_appinfo_vdf_Visitor //extends KeyValueVisitor
	{

		void onApp(int app_id);

		void onAppEnd();
	}

}
