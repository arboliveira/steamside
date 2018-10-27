package br.com.arbo.steamside.steam.client.localfiles.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Function;

public class SteamLocalFileUtils
{

	public static <T> T doWithFile(File file, Function<FileInputStream, T> fn)
	{
		try (FileInputStream in = new FileInputStream(file))
		{
			return fn.apply(in);
		}
		catch (FileNotFoundException e)
		{
			throw new SteamNotInstalled(e);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

}
