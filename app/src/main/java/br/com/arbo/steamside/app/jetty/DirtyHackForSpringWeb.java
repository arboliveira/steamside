package br.com.arbo.steamside.app.jetty;

import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.opersys.username.User;

public class DirtyHackForSpringWeb {

	public static class KidsModeExistingInstance implements KidsMode {

		public static KidsMode instance;

		@Override
		public boolean isKidsModeOn() {
			return instance.isKidsModeOn();
		}

		@Override
		public String getCategoryAllowedToBeSeen() {
			return instance.getCategoryAllowedToBeSeen();
		}

	}

	public static class UserExistingInstance implements User {

		public static User instance;

		@Override
		public String username() {
			return instance.username();
		}

	}

	public static class ExitExistingInstance implements Exit {

		public static Exit instance;

		@Override
		public void exit() {
			instance.exit();
		}

	}
}
