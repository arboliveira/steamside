"use strict";

var Test_Steamside_html = Backbone.Model.extend({

	pageToTest: function() {
		return 'Steamside.html';
	},

	runTests: function () {
		var that = this;
		asyncTest("Continues Segment", function(){that.testContinues()});
		asyncTest("Search Segment", function(){that.testSearch()});
		asyncTest("Favorites Segment - more button", function(){that.testFavorites_moreButton()});
		asyncTest("Favorites Segment - switch", function(){that.testFavorites_switch()});
	},

	testContinues: function() {
		var segment = $('#continues-segment');

		var that = this;

		var whenMoreButtonAppears = function () {
			var b = segment.find('.more-button-text');
			if (!b.is(':visible')) {
				setTimeout(whenMoreButtonAppears, 40);
				return;
			}

			equal(that.visibleGames(segment), 3, 'visible games before more');
			b.click();
			equal(that.visibleGames(segment), 5, 'visible games after more');

			start();
		};
		whenMoreButtonAppears();
	},

	testSearch: function () {
		var segment = $('#search-segment');
		var input = segment.find('.search-text-input');
		input.val('anything');
		var button = segment.find('.command-button').get(0);
		button.click();
		var whenGamesAppear = function() {
			var games = segment.find('.game-tile');
			if (games.length == 0) {
				setTimeout(whenGamesAppear, 40);
				return;
			}
			equal(games.length, 2, 'search results');

			start();
		};
		whenGamesAppear();
	},

	testFavorites_moreButton: function () {
		var segment = $('#favorites-segment');

		var that = this;

		var whenMoreButtonAppears = function () {
			var b = segment.find('.more-button-text');
			if (!b.is(':visible')) {
				setTimeout(whenMoreButtonAppears, 300);
				return;
			}

			equal(that.visibleGames(segment), 3, 'visible games before more');
			b.click();
			equal(that.visibleGames(segment), 5, 'visible games after more');

			start();
		};
		whenMoreButtonAppears();
	},

	testFavorites_switch: function ()
	{
		var segment = $('#favorites-segment');

		var l = segment.find("#switch-link").get(0);
		l.click();

		var whenDialogAppears = function() {
			var dialog = $("#collection-pick-steam-categories-list");
			if (dialog.length == 0) {
				setTimeout(whenDialogAppears, 300);
				return;
			}

			var buttons = dialog.find(".collection-pick-steam-category-button");
			equal(buttons.length, 3, 'category buttons');

			var bMaybeLater = $(".back-button");
			bMaybeLater.click();

			start();
		};
		whenDialogAppears();
	},

	visibleGames: function (segment) {
		return segment.find('.game-tile').filter(':visible').length;
	}

});

