package br.com.arbo.steamside.cloud.autoupload;

import javax.inject.Inject;

import br.com.arbo.steamside.settings.file.SaveSteamsideXml;

public class AutoUpload {

	@Inject
	public AutoUpload(SaveSteamsideXml save, ParallelUpload parallel) {
		save.addListener(file -> parallel.submit(file));
	}
}
