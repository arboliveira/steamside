package br.com.arbo.steamside.settings.file;

import java.io.FileNotFoundException;

public class FileNotFound_steamside_xml extends RuntimeException
{

	public FileNotFound_steamside_xml(FileNotFoundException e)
	{
		super(e);
	}

}