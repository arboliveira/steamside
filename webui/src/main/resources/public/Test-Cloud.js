"use strict";

var Test_Cloud = Backbone.Model.extend(
{
	cloudView: null,

	randomSuggestion: "RANDOM_SUGGESTION",

	renderTestableView_promise: function()
	{
		var that = this;

		var MockCloudModel = Backbone.View.extend(
			{
				cloudEnabled: function()
				{
					return true;
				},

				dontpadUrl: function()
				{
					return that.randomSuggestion;
				},

				fetch: function()
				{
					return $.Deferred().resolve();
				}
			});

		return new CloudView(
			{
				cloudModel: new MockCloudModel(),
				backend: new Backend()
			}
		).render().whenRendered;
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
				that.renderTestableView_promise().done(function(view)
				{
					theTestRunner.replaceTestableUI(view.$el);
					that.cloudView = view;
					done();
				});
			});

			describe("Just started", function ()
			{
				it('displays initial values', function(done) {
					that.testInitialValues(done);
				})
			});


			describe("Sync to the cloud", function ()
			{
				it('saves', function(done) {
					that.testSave(done);
				})
			});

		});
	},

	testInitialValues: function (done)
	{
		var that = this;
		var expect = global.expect;

		var $input = that.cloudView.$('#input-text-command-box');
		var val = $input.val();

		expect(val).to.equal(that.randomSuggestion);

		done();
	},

	testSave: function (done) {
		var that = this;

		// TODO
		// that.cloudView.$("#SaveButton").click();

		done();
	}
});
