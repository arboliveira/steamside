package br.com.arbo.steamside.webui.wicket;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.steamside.continues.Continue;

public class ContinueNeedsImpl implements Continue.Needs {

	@Override
	public String name() {
		final String username = SystemUtils.USER_NAME;
		if ("PARENT_USERNAME".equals(username)) return "PARENT_CONTINUE";
		return "KID_CONTINUE";
	}

}
