"use strict";

var Test_Cloud_html = Backbone.Model.extend(
{
	pageToTest: function()
	{
		return 'Cloud.html';
	},

	isPageLoaded: function()
	{
		return true;
	},

	addTests: function (pageLoader)
	{
		var that = this;
		var before = global.before;
		var describe = global.describe;
		var it = global.it;

		describe('Cloud', function(){

			before(function(done)
			{
				pageLoader.loadPage(that, done);
			});

			describe("Just started", function ()
			{
				it('random suggestion', function(done)
				{
					that.testRandomSuggestion(done);
					done();
				})
			});


			describe("Sync to the cloud", function ()
			{
				it('Yes please', function(done)
				{
					// TO DO
					done();
				})
			});

		});
	},

	testRandomSuggestion: function (done)
	{
		var expect = global.expect;

		var randomSuggestion = "RANDOM_SUGGESTION";

		var MockCloudModel = Backbone.View.extend(
		{
			randomSuggestion: function()
			{
				return randomSuggestion;
			}
		});

		var cloudView = new CloudView(
			{
				el: $('#CloudView'),
				cloudModel: new MockCloudModel()
			}
		).render();

		cloudView.rendered.done(function()
		{
			var $input = cloudView.$('#input-text-command-box');
			var val = $input.val();

			expect(val).to.equal(randomSuggestion);

			done();
		});

	}
});
