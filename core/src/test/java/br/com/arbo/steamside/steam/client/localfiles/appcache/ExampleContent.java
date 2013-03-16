package br.com.arbo.steamside.steam.client.localfiles.appcache;

import static br.com.arbo.steamside.steam.client.localfiles.appcache.KeyValues_h.KEYVALUES_TOKEN_SIZE;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import br.com.arbo.steamside.vdf.KeyValueVisitor;
import br.com.arbo.steamside.vdf.examples.DumpVdfStructure;

public class ExampleContent {

	private final Visitor visitor;

	public ExampleContent(
			final Visitor visitor,
			final KeyValueVisitor keyvalueVisitor) {
		this.visitor = visitor;
		this.keyvalueVisitor = keyvalueVisitor;
	}

	public static void main(final String[] args) throws IOException {
		class AppOut implements Visitor {

			@Override
			public void onApp(final int app_id) {
				System.out.println("====" + app_id + "====");
			}

			@Override
			public void onSection(final byte section_number) {
				System.out.println(">>>>" + section_number + ">>>>");
			}

		}
		new ExampleContent(new AppOut(), new DumpVdfStructure()).go();
	}

	private void go() throws IOException {
		final FileInputStream f =
				new FileInputStream(File_appinfo_vdf.appinfo_vdf());
		try {
			go2(f);
		} finally {
			f.close();
		}
	}

	private void go2(final FileInputStream f) throws IOException {
		final FileChannel ch = f.getChannel();
		buffer = new ByteBufferX(
				ch.map(MapMode.READ_ONLY, 0L, ch.size()),
				KEYVALUES_TOKEN_SIZE);
		go3();
	}

	private void go3() {
		buffer.read__uint32_t(); // 0x07564426
		buffer.read__uint32_t(); // enum EUniverse
		while (true) {
			final int app_id = buffer.read__uint32_t();
			if (app_id == 0) break;
			go_app_id(app_id);
		}
	}

	private void go_app_id(final int app_id) {
		visitor.onApp(app_id);

		buffer.read__uint32_t(); // data_size
		buffer.read__uint32_t(); // unknown1
		buffer.read__uint32_t(); // last_updated
		buffer.read__uint64_t(); // unknown2;
		for (int i = 1; i <= 20; i++)
			buffer.read__uint8_t(); // unknown3[20];
		buffer.read__uint32_t(); // change_number;

		while (true) {
			final byte section_number = buffer.read__uint8_t(); // enum EAppInfoSection
			if (section_number == 0) break;

			visitor.onSection(section_number);

			KeyValues_cpp.readAsBinary(buffer, keyvalueVisitor);
		}
	}

	interface Visitor {

		void onApp(int app_id);

		void onSection(byte section_number);
	}

	private ByteBufferX buffer;
	private final KeyValueVisitor keyvalueVisitor;
}
