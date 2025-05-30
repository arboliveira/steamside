package br.com.arbo.steamside.steam.client.localfiles.appinfo;

import br.com.arbo.steamside.steam.client.localfiles.io.SteamLocalFileUtils;
import br.com.arbo.steamside.steam.client.localfiles.vdf.KeyValueVisitor;

public class Data_appinfo_vdf_from_File_appinfo_vdf
{

	public static Data_appinfo_vdf hydrate(File_appinfo_vdf file_appinfo_vdf)
	{
		return SteamLocalFileUtils.doWithFile(
			file_appinfo_vdf.appinfo_vdf(),
			in -> {
				InMemory_appinfo_vdf inMemory_appinfo_vdf =
					new InMemory_appinfo_vdf();

				ContentVisitor contentVisitor = new ContentVisitor(
					(appid, appinfo) -> inMemory_appinfo_vdf
						.put(appid, appinfo));

				KeyValueVisitor keyValueVisitor = contentVisitor;

				new Content_appinfo_vdf(in).accept(
					contentVisitor, keyValueVisitor);

				return inMemory_appinfo_vdf;
			});
	}

}
