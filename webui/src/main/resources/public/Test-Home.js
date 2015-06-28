"use strict";

var Test_Home = Backbone.Model.extend({

	renderTestableUIPromise: function()
	{
		var pageToTest = 'Steamside.html';
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

		describe('Home', function(){

			before(function(done)
			{
				that.renderTestableUIPromise().done(function(el)
				{
					theTestRunner.replaceTestableUI(el);
					done();
				});
			});

			describe("Continues Segment", function () {
				it('more button', function(done){
					that.testContinues(done);
				})
			});
			describe("Search Segment", function () {
				it('should happy day', function(done){
					that.testSearch(done);
				})
			});
			describe("Favorites Segment", function () {
				it('more button', function(done){
					that.testFavorites_moreButton(done);
				});
				it('switch', function(done){
					that.testFavorites_switch(done);
				})
			})
		});
	},

	testContinues: function(done) {
		var segment = $('#continues-segment');

		var that = this;

		var b;

		that.insist(function ()
			{
				b = segment.find('.more-button-text');
				assert.equal(b.is(':visible'), true, 'visible more button');
			},
			function()
			{
				var visibleGamesBeforeMoreClicked = that.visibleGames(segment);
				expect(visibleGamesBeforeMoreClicked).to.equal(3);

				b.click();

				that.insist(function()
					{
						var visibleGamesAfterMoreClicked = that.visibleGames(segment);
						expect(visibleGamesAfterMoreClicked).to.equal(13);
					},
					function(){
						done();
					},
					done);
			},
			done);
	},

	testSearch: function (done) {
		var segment = $('#search-segment');
		var input = segment.find('.command-text-input');
		input.val('anything');
		var button = segment.find('.command-button').get(0);
		button.click();

		this.insist(function ()
			{
				var searchResults = segment.find('.game-tile');
				expect(searchResults.length).to.equal(4);
			},
			function()
			{
				done();
			},
			done);
	},

	testFavorites_moreButton: function (done) {
		var segment = $('#favorites-segment');

		var that = this;

		var b;

		that.insist(function ()
			{
				b = segment.find('.more-button-text');
				assert.equal(b.is(':visible'), true, 'visible more button');
			},
			function()
			{
				var visibleGamesBeforeMoreClicked = that.visibleGames(segment);
				expect(visibleGamesBeforeMoreClicked).to.equal(3);

				b.click();

				that.insist(function()
					{
						var visibleGamesAfterMoreClicked = that.visibleGames(segment);
						expect(visibleGamesAfterMoreClicked).to.equal(4);
					},
					function(){
						done();
					},
					done);
			},
			done);
	},

	testFavorites_switch: function (done)
	{
		var segment = $('#favorites-segment');

		var l = segment.find("#side-link-favorite-switch").get(0);
		l.click();

		this.insist(function ()
			{
				var listView = $("#TagStickersView");
				expect(listView.length).to.equal(1);

				var info = listView.find("#TagStickerView");
				assert.equal(info.length > 0, true, 'tag stickers');
			},
			function()
			{
				done();
			},
			done);
	},

	insist: function(is_effect_seen, ok_effect_seen, done) {
		var no_effect_seen = function (error) {
			if (!error) error = 'no effect seen';
			if (!(error instanceof Error)) error = new Error(error);
			done(error);
			throw error;
		};
		Insisting.seen(is_effect_seen, ok_effect_seen, no_effect_seen, 1500);
	},

	visibleGames: function (segment) {
		return segment.find('.game-tile').filter(':visible').length;
	}

});
