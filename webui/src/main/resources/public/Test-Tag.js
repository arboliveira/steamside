"use strict";

var Test_Tag = Backbone.Model.extend({

	addTests: function (theTestRunner) {
		var that = this;
		var before = global.before;
		var describe = global.describe;
		var it = global.it;

		describe('Tag', function(){

			before(function(done)
			{
				that.renderTestableUIPromise().done(function(el)
				{
					theTestRunner.replaceTestableUI(el);
					done();
				});
			});

			describe("Tag box", function () {
				it('Tag suggestions', function(done){
					that.testTagSuggestions(done);
				})
			});

		});

	},

	renderTestableUIPromise: function()
	{
		var pageToTest = 'Tag.html';
		return $.ajax({
				url: pageToTest, dataType: 'html' }
		).then(function(page) {
				return $(page);	});
	},

	testTagSuggestions: function (done)
	{
		var that = this;

		var game = new Game({
			name: "Test Game"
		});

		new TagView({
			game: game,
			backend: new Backend()
		}
		).render().whenRendered.done(function(view) {
			$("#TagTile").after(view.$el);
			that.testTagSuggestions_whenRendered(view, done);
		});
	},

	testTagSuggestions_whenRendered: function(view, done) {
		Insisting.seen(
			function ()
			{
				var tagSuggestions = view.$("#TagSuggestionView");
				expect(tagSuggestions.length).to.equal(2);
			},
			function() { done(); },
			function(error) { done(error); throw error; }
		);
	}

});
