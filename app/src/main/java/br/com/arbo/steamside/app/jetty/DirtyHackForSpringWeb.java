package br.com.arbo.steamside.app.jetty;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.exit.Exit;

public class DirtyHackForSpringWeb {

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
