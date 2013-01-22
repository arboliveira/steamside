package br.com.arbo.steamside.vdf;

import static org.junit.Assert.fail;

import org.junit.Test;

@SuppressWarnings("static-method")
public class VdfLockTest {

	@Test
	public void steamProcessFailsWhenWeLockTheFile() {
		// file is locked
		// Steam fails to modify vdf while it is locked
		// operation is atomic
		// file is released
		// Steam modifies file without reloading and wrecks it?!
		// offer to restart Steam after vdf is modified
		fail();
	}

}
