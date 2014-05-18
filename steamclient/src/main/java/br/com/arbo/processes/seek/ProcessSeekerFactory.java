package br.com.arbo.processes.seek;

import org.apache.commons.lang3.SystemUtils;

import br.com.arbo.processes.seek.linux.ProcessSeekerLinux;
import br.com.arbo.processes.seek.windows.ProcessSeekerWindows;

public class ProcessSeekerFactory {

	public static ProcessSeeker build() {
		if (SystemUtils.IS_OS_LINUX)
			return ProcessSeekerLinux.withNoHeaders();
		if (SystemUtils.IS_OS_MAC_OSX)
			return new ProcessSeekerLinux();
		if (SystemUtils.IS_OS_WINDOWS)
			return new ProcessSeekerWindows();
		throw new UnsupportedOperationException();
	}

}