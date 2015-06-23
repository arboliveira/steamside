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
	assertRetry: function(options)
	{
		var condition = options.condition;
		var done = options.done;
		var success = options.success;
		var failure = options.failure;

		if (success == undefined)
		{
			success = function() { done(); };
		}

		if (failure == undefined)
		{
			failure = function(error) { done(error); throw error; };
		}

		Insisting.seen(condition, success, failure);
	},

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
