package br.com.arbo.steamside.app.instance;

public interface DetectSteamside {

	public enum Situation {
		AlreadyRunningForThisUser, RunningOnDifferentUser, NotHere
	}

	Situation detect(final int p);

}
