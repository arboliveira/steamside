package br.com.arbo.steamside.cloud.autoupload;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.cloud.Dontpad;
import br.com.arbo.steamside.settings.file.Save;
import br.com.arbo.steamside.settings.file.Save.Listener;

public class AutouploadingSteamsideData implements Listener {

	private static String read(File file) {
		try {
			return FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void upload(File file) {
		String content = read(file);
		new Dontpad().post(content);
	}

	@Inject
	public AutouploadingSteamsideData(Save save) {
		save.addListener(this);
	}

	@Override
	public void onSave(File file) {
		upload(file);
	}

}
