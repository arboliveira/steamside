"use strict";

var MockCloudModel = CloudModel.extend(
{
	fetch: function()
	{
		return $.Deferred().resolve();
	},

	save: function()
	{
		return $.Deferred().resolve();
	}
});


var Test_Cloud = Backbone.Model.extend(
{
	viewBeingTested: null,

	randomSuggestion: "RANDOM_SUGGESTION",

	initialize: function()
	{
		var that = this;

		var model = new MockCloudModel();
		model.cloudEnabled_set(true);
		model.dontpadUrl_set(that.randomSuggestion);

		this.viewBeingTested = new CloudView(
			{
				model: model,
				backend: new Backend()
			}
		).render();
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
				theTestRunner.replaceTestableUI(that.viewBeingTested.$el);

				Insisting.assertRetry({
					done: done,
					condition: function()
					{
						expect($("#CloudView")).to.have.length(1);
					}
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

		var $input = that.viewBeingTested.$('#input-text-command-box');
		var val = $input.val();

		expect(val).to.equal(that.randomSuggestion);

		done();
	},

	testSave: function (done) {
		var that = this;

		that.viewBeingTested.$("#SaveButton").click();

		expect($(".tooltipster-content")).to.have.length(1);

		done();
	}
});
