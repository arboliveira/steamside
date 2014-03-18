package br.com.arbo.steamside.cloud.autoupload;

import javax.inject.Inject;

import br.com.arbo.steamside.settings.file.Save;

public class AutoUpload {

	@Inject
	public AutoUpload(Save save, AutouploadingSteamsideData listener) {
		save.addListener(listener);
	}
}
