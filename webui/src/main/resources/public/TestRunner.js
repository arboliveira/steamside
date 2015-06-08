"use strict";

var TestRunner = Backbone.Model.extend(
{
	replaceTestableUI: function (el) {
		var testbed = $('#page-to-test');
		testbed.empty();
		testbed.append(el);
	}

});

var Insisting =
{
	seen: function (condition, success, failure, timeout, interval)
	{
		if (!timeout) timeout = 1000;
		if (!interval) interval = 40;

		var quit = false;

		setTimeout(
			function()
			{
				quit = true;
			},
			timeout);

		var check = function()
		{
			try
			{
				condition();
				success();
				return;
			}
			catch(e)
			{
				if (e instanceof chai.AssertionError)
				{
					if (quit)
					{
						failure(e);
						return;
					}
				}
				else {
					throw e;
				}
			}

			setTimeout(check, interval);
		};
		check();
	}
};
