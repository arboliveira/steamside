"use strict";

var Test_Steamside_html = Backbone.Model.extend({

	pageToTest: function()
	{
		return 'Steamside.html';
	},

	isPageLoaded: function()
	{
		var segment = $('#continues-segment');
		var b = segment.find('.more-button-text');
		return b.is(':visible');
	},

	runTests: function ()
	{
		var that = this;
		var describe = global.describe;
		var it = global.it;
		var mocha = global.mocha;

		describe('Steamside', function(){
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

		mocha.run();
	},

	testContinues: function(done) {
		var segment = $('#continues-segment');

		var that = this;

		var b;

		var is_effect_seen = function ()
		{
			b = segment.find('.more-button-text');
			return b.is(':visible');
		};

		var ok_effect_seen = function()
		{
			expect(that.visibleGames(segment)).to.equal(3); //'visible games before more');
			b.click();
			// TODO THIS! IS! ASYNC!
			expect(that.visibleGames(segment)).to.equal(5); //'visible games after more');
			done();
		};

		this.insist(is_effect_seen, ok_effect_seen, done);
	},

	testSearch: function (done) {
		var segment = $('#search-segment');
		var input = segment.find('.search-text-input');
		input.val('anything');
		var button = segment.find('.command-button').get(0);
		button.click();

		var games;

		var is_effect_seen = function ()
		{
			games = segment.find('.game-tile');
			return games.length == 2;
		};

		var ok_effect_seen = function()
		{
			assert.equal(games.length, 2, 'search results');
			done();
		};

		this.insist(is_effect_seen, ok_effect_seen, done);
	},

	testFavorites_moreButton: function (done) {
		var segment = $('#favorites-segment');

		var that = this;

		var b;

		var is_effect_seen = function ()
		{
			b = segment.find('.more-button-text');
			return b.is(':visible');
		};

		var ok_effect_seen = function()
		{
			assert.equal(that.visibleGames(segment), 3, 'visible games before more');
			b.click();
			// TODO THIS! IS! ASYNC!
			assert.equal(that.visibleGames(segment), 5, 'visible games after more');
			done();
		};

		this.insist(is_effect_seen, ok_effect_seen, done);
	},

	testFavorites_switch: function (done)
	{
		var segment = $('#favorites-segment');

		var l = segment.find("#switch-link").get(0);
		l.click();

		var buttons;

		var is_effect_seen = function ()
		{
			var dialog = $("#collection-pick-steam-categories-list");
			if (dialog.length == 0) return false;
			buttons = dialog.find(".collection-pick-steam-category-button");
			return buttons.length == 3;
		};

		var ok_effect_seen = function()
		{
			assert.equal(buttons.length, 3, 'category buttons');

			var bMaybeLater = $(".back-button");
			bMaybeLater.click();

			done();
		};

		this.insist(is_effect_seen, ok_effect_seen, done);
	},

	insist: function(is_effect_seen, ok_effect_seen, done) {
		var no_effect_seen = function () {
			done(new Error('no effect seen'));
		};
		Insisting.seen(is_effect_seen, ok_effect_seen, no_effect_seen, 2000);
	},

	visibleGames: function (segment) {
		return segment.find('.game-tile').filter(':visible').length;
	}

});
