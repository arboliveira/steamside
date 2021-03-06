package br.com.arbo.steamside.cloud.autoupload;

import javax.inject.Inject;

import br.com.arbo.steamside.settings.file.File_steamside_xml;
import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.settings.file.SaveSteamsideXml;
import br.com.arbo.steamside.xml.SteamsideXml;

public class AutoUpload implements SaveFile {

	@Inject
	public AutoUpload(
			File_steamside_xml file_steamside_xml,
			ParallelUpload parallel)
	{
		SaveSteamsideXml save = new SaveSteamsideXml(file_steamside_xml);
		save.addListener(parallel::submit);
		this.save = save;
	}

	@Override
	public void save(SteamsideXml xml)
	{
		this.save.save(xml);
	}

	private final SaveFile save;

}
