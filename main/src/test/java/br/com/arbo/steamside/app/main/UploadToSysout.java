package br.com.arbo.steamside.app.main;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.Logger;

import br.com.arbo.steamside.cloud.CloudUpload;
import br.com.arbo.steamside.cloud.Unavailable;

public class UploadToSysout implements CloudUpload
{

	@Override
	public HttpResponse upload(HttpPost post) throws Unavailable
	{
		getLogger().info("Not really uploading! Just printing to console...");

		throw new Unavailable(null);
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass());
	}

}
