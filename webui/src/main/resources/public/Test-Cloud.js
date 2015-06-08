"use strict";

var Test_Cloud = Backbone.Model.extend(
{
	renderTestableUIPromise: function()
	{
		var pageToTest = 'Cloud.html';
		return $.ajax({
				url: pageToTest, dataType: 'html' }
		).then(function(page) {
				return $(page);	});
	},

	addTests: function (theTestRunner)
	{
		var that = this;
		var before = global.before;
		var describe = global.describe;
		var it = global.it;

		describe('Cloud', function(){

			before(function(done)
			{
				that.renderTestableUIPromise().done(function(el)
				{
					theTestRunner.replaceTestableUI(el);
					done();
				});
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

		cloudView.whenRendered.done(function()
		{
			var $input = cloudView.$('#input-text-command-box');
			var val = $input.val();

			expect(val).to.equal(randomSuggestion);

			done();
		});

	}
});
