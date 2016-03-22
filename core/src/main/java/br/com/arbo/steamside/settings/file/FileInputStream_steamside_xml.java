package br.com.arbo.steamside.settings.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileInputStream_steamside_xml
{

	public static FileInputStream newFileInputStream(
		File_steamside_xml file_steamside_xml)
		throws FileNotFound_steamside_xml
	{
		try
		{
			return new FileInputStream(file_steamside_xml.steamside_xml());
		}
		catch (FileNotFoundException e)
		{
			throw new FileNotFound_steamside_xml(e);
		}
	}

}
