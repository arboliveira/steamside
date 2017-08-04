package br.com.arbo.steamside.cloud;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

public interface CloudUpload
{

	HttpResponse upload(HttpPost post) throws Unavailable;

}
