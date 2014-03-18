package br.com.arbo.steamside.cloud;

public class Uploader {

	@SuppressWarnings("static-method")
	public synchronized void upload(String content) {
		new Dontpad().post(content);
	}
}
