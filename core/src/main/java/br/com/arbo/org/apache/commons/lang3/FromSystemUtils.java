package br.com.arbo.org.apache.commons.lang3;

import java.io.File;

import org.apache.commons.lang3.SystemUtils;

public class FromSystemUtils implements UserHome {

	@Override
	public File getUserHome() {
		return SystemUtils.getUserHome();
	}

}
