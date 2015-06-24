"use strict";

var Test_Steamside =
{
	testSteamside: function()
	{
		var tests =
			[
				new Test_Tag(),
				new Test_Cloud(),
				new Test_KidsHome(),
				new Test_Home(),
				null
			];
		var theTestRunner = new TestRunner();

		var mocha = global.mocha;
		mocha.setup('bdd');
		global.expect = chai.expect;
		global.assert = chai.assert;
		chai.Assertion.includeStack = true;
		var describe = global.describe;

		describe("Steamside", function()
		{
			for (var i = 0; i < tests.length; i++)
			{
				if (tests[i] != null)
					tests[i].addTests(theTestRunner);
			}
		});

		mocha.run();
	}
};

