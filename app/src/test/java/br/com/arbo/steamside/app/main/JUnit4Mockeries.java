package br.com.arbo.steamside.app.main;

import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;

public class JUnit4Mockeries {

	public static JUnit4Mockery threadsafe() {
		return new JUnit4Mockery()/* @formatter:off */ { { 	/* @formatter:on */
				setThreadingPolicy(new Synchroniser());
			}
		};
	}
}
