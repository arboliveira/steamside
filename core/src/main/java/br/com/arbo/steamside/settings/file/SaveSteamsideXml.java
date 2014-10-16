package br.com.arbo.steamside.settings.file;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXB;

import br.com.arbo.steamside.xml.SteamsideXml;

public class SaveSteamsideXml implements SaveFile {

	public SaveSteamsideXml(File_steamside_xml file_steamside_xml)
	{
		this.file_steamside_xml = file_steamside_xml;
	}

	public void addListener(Listener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void save(final SteamsideXml xml)
	{
		final File file = file_steamside_xml.steamside_xml();
		file.getParentFile().mkdirs();
		JAXB.marshal(xml, file);
		listeners.stream().forEach(l -> l.onSave(file));
	}

	public interface Listener {

		void onSave(File file);

	}

	private final File_steamside_xml file_steamside_xml;

	private final ArrayList<Listener> listeners = new ArrayList<>(1);

}
