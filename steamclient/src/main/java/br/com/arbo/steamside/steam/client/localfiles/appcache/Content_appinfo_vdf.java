package br.com.arbo.steamside.steam.client.localfiles.appcache;

import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_cpp.KEYVALUES_TOKEN_SIZE;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import br.com.arbo.steamside.steam.client.vdf.KeyValueVisitor;

public class Content_appinfo_vdf {

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

	public Content_appinfo_vdf(final FileInputStream f)
	{
		buffer = newBuffer(f);
	}

	public void accept(final Content_appinfo_vdf_Visitor visitor)
	{
		buffer.read__uint32_t(); // 0x07564426
		buffer.read__uint32_t(); // enum EUniverse
		while (true)
		{
			final int app_id = buffer.read__uint32_t();
			if (app_id == 0) break;
			go_app_id(app_id, visitor);
		}
	}

	private void go_app_id(final int app_id,
			final Content_appinfo_vdf_Visitor visitor)
	{
		visitor.onApp(app_id);

		buffer.read__uint32_t(); // data_size
		buffer.read__uint32_t(); // unknown1
		buffer.read__uint32_t(); // last_updated
		buffer.read__uint64_t(); // unknown2;
		for (int i = 1; i <= 20; i++)
			buffer.read__uint8_t(); // unknown3[20];
		buffer.read__uint32_t(); // change_number;

		while (true)
		{
			final byte section_number = buffer.read__uint8_t(); // enum EAppInfoSection
			if (section_number == 0) break;

			visitor.onSection(section_number);

			KeyValues_cpp.readAsBinary(buffer, visitor);
		}

		visitor.onAppEnd();
	}

	public interface Content_appinfo_vdf_Visitor extends KeyValueVisitor {

		void onApp(int app_id);

		void onAppEnd();

		void onSection(byte section_number);
	}

	private final ByteBufferX buffer;

}
