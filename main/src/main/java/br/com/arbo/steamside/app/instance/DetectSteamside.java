package br.com.arbo.steamside.app.instance;

public interface DetectSteamside {

	Situation detect(int port);

	public enum Situation {
		AlreadyRunningForThisUser, RunningOnDifferentUser, NotHere
	}

}
