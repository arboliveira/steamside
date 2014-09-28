"use strict";

var TestRunner = Backbone.Model.extend(
{
	loadAndTest: function (testsuite)
	{
		this.loadPageToTest(testsuite.pageToTest());

		var is_loaded = function () {
			return testsuite.isPageLoaded();
		};

		var ok_loaded = function () {
			testsuite.runTests();
		};

		var no_loaded = function() {
			throw new Error('page never loaded?!');
		};

		Insisting.seen(is_loaded, ok_loaded, no_loaded, 3000);
	},

	loadPageToTest: function (pageToTest)
	{
		$.ajax(
			{
				url: pageToTest,
				success: function(page)
					{
						var testbed = $('#page-to-test');
						testbed.empty();
						testbed.append($(page));
					},
				dataType: 'html'
			});
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
			if (quit) {
				failure();
				return;
			}

			if (condition())
			{
				success();
				return;
			}

			setTimeout(check, interval);
		};
		check();
	}
};
