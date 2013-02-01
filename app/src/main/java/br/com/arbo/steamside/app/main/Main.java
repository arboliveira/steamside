package br.com.arbo.steamside.app.main;

import java.net.URL;

import br.com.arbo.steamside.app.jetty.Jetty;

public class Main {

	public static void main(String[] args) throws Exception {
		URL url = Jetty.start();
		// TODO Callback
		System.out.println(url);
	}
}
