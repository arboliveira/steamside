package br.com.arbo.opersys.processes.seek.windows;

import java.util.TreeMap;

import org.jvnet.winp.WinProcess;

public class WinProcessX {

	WinProcessX(WinProcess exe)
	{
		this.exe = exe;
	}

	public void killRecursively()
	{
		exe.killRecursively();
	}

	public String username()
	{
		final TreeMap<String, String> env = exe.getEnvironmentVariables();
		return env.get("USERNAME");
	}

	private final WinProcess exe;

}
