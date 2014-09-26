"use strict";

var TestRunner = Backbone.Model.extend(
{
	loadAndTest: function (testsuite)
	{
		this.loadPageToTest(testsuite.pageToTest());
		testsuite.runTests();
	},

	loadPageToTest: function (pageToTest)
	{
		$.ajax({url:pageToTest, success: function(page) {
			$('#page-to-test').append($(page));
		}, dataType: 'html'});
	}
});
