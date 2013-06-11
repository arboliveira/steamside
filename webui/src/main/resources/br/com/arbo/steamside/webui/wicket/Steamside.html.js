var ContinueGames = Backbone.Collection.extend({
    model: Game,
    url: 'api/continues/continues.json'
});

var FavoritesCollection = Backbone.Collection.extend({
    model: Game,
    url: 'favorites.json'
});

var SessionView = Backbone.View.extend({
    el : 'body',

    initialize: function () {
		this.model.bind('change', this.render, this);
    },

    render: function () {                "use strict";
        var m = this.model;

        this.$('#userName').text(m.userName());
        this.$('#version').text(m.versionOfSteamside());

        // TODO var username = m.username();
        var kidsMode = m.kidsmode();

        if (kidsMode) {
            $("#page-header-navigation-bar").hide();
            $("#search-segment").hide();
            $("#favorites-segment").hide();
            $("#collections-segment").hide();
        }
        return this;
    }
});

var SwitchFavoritesView = Backbone.View.extend({
    on_category_change: null,

    events: {
        "click .back-button"         : "backButtonClicked"
    },

    initialize: function() {		"use strict";
        this.on_category_change = this.options.on_category_change;
    },

    render: function () {  "use strict";
        new SteamCategoriesView({
            el: this.$("#collection-pick-steam-categories-list"),
            collection: this.collection,
            on_category_change: this.on_category_change
        }).render();
        return this;
    },

    backButtonClicked: function (e) {  "use strict";
        e.preventDefault();
        history.back();
    }
});

function sideshow(element) {
	var segments = element.find('.segment');
	var left = true;
	segments.each(function(){
		var segment = $(this);
		var header = segment.find('.side-header');
		header.removeClass('side-header-at-left');
		header.removeClass('side-header-at-right');
		var content = segment.find('.content');
		content.removeClass('content-at-right');
		content.removeClass('content-at-left');
		left = !left;
		if (left) {
			header.addClass('side-header-at-left');
			content.addClass('content-at-right');
		} else {
			header.addClass('side-header-at-right');
			content.addClass('content-at-left');
		}
	});
}

var SteamsideView = Backbone.View.extend({

	el: "body",

	continues: null,
	searchResults: null,

	render: function () {  "use strict";
		var sessionModel = new SessionModel();

		var continues = new ContinueGames();
		var searchResults = new SearchResults();
		var favorites = new FavoritesCollection();

		var tileSearchCommandHint = this.$('#search-command-hint');
		tileSearchCommandHint.remove();
		this.tileSearchHintContinueA = tileSearchCommandHint.clone();
		this.tileSearchHintContinueB = tileSearchCommandHint.clone();
		this.tileSearchHintSearchA = tileSearchCommandHint.clone();
		this.tileSearchHintSearchB = tileSearchCommandHint.clone();
		var selectorVerb = '#search-command-hint-verb';
		this.tileSearchHintSearchA.find(selectorVerb).text("search");
		this.tileSearchHintSearchB.find(selectorVerb).text("play first result for");

		this.continues = continues;
		this.continues.on('reset', this.continues_reset, this);

		this.searchResults = searchResults;

		var that = this;

		sideshow(this.$el);

		new SessionView({model: sessionModel});

		CommandBoxTile.ajaxTile(function(tile) {
			that.render_search_CommandBox(tile);
		});

		new DeckView({
			el: $('#search-results-deck'),
			collection: searchResults,
			continues: continues
		});

		new DeckView({
			el: $('#favorites-deck'),
			collection: favorites,
			continues: continues
		});

		fetch_json(sessionModel, function() {
			new DeckView({
				el: $('#continues-deck'),
				collection: continues,
				continues: continues,
				kidsMode: sessionModel.kidsmode()
			});
			fetch_json(continues);
			fetch_json(favorites);
		});

		return this;
	},

	continues_reset: function() {
		var gameA = this.continues.at(0);
		var gameB = this.continues.at(1);
		var selector = '#search-command-hint-subject';
		this.tileSearchHintContinueA.find(selector).text(gameA.name());
		this.tileSearchHintContinueB.find(selector).text(gameB.name());
	},

	render_search_CommandBox: function(tile) {
		var that = this;
		var viewCommandBox = new CommandBoxView({
			el: tile.clone(),
			placeholder_text: 'game or command',
			on_change_input: function(input) { that.on_search_input_changed(input) },
			on_command: function(input) { that.on_search_command(input) },
			on_command_alternate: function(input) { that.on_search_command_alternate(input) }
		});
		var viewCommandBox_el = viewCommandBox.render().el;
		viewCommandBox.emptyCommandHints();
		viewCommandBox.appendCommandHint(this.tileSearchHintContinueA);
		viewCommandBox.appendCommandHint(this.tileSearchHintSearchA);
		viewCommandBox.appendCommandHintAlternate(this.tileSearchHintContinueB);
		viewCommandBox.appendCommandHintAlternate(this.tileSearchHintSearchB);

		var searchEl = $('#search-command-box');
		searchEl.empty();
		searchEl.append(viewCommandBox_el);
		viewCommandBox.input_query_focus();
	},

	on_search_input_changed: function(view) {
		var input = view.input_query_val();
		if (input == '') {
			this.tileSearchHintContinueA.show();
			this.tileSearchHintContinueB.show();
			this.tileSearchHintSearchA.hide();
			this.tileSearchHintSearchB.hide();
		} else {
			this.tileSearchHintContinueA.hide();
			this.tileSearchHintContinueB.hide();
			this.tileSearchHintSearchA.show();
			this.tileSearchHintSearchB.show();
			var selector = '#search-command-hint-subject';
			this.tileSearchHintSearchA.find(selector).text(input);
			this.tileSearchHintSearchB.find(selector).text(input);
		}
	},

	on_search_command: function(view) {
		var input = view.input_query_val();
		if (input == '') {
			var gameA = this.continues.at(0);
			gameA.play();
		} else {
			var searchResults = this.searchResults;
			searchResults.query = input;
			fetch_json(searchResults);
		}
	},

	on_search_command_alternate: function(view) {
		var input = view.input_query_val();
		if (input == '') {
			var gameB = this.continues.at(1);
			gameB.play();
		} else {
			var searchResults = this.searchResults;
			searchResults.query = input;
			fetch_json(searchResults, function() {
				var first = searchResults.at(0);
				first.play();
			});
		}
	}
});

var Steamside_html = {

    render_page: function () {     "use strict";

        new SteamsideRouter();
        // Start Backbone history a necessary step for bookmarkable URL's
        Backbone.history.start();

		/*
		Load the entire tileset before you try to render anything.
		If you don't, something funny happens:
		Multiple ajax requests pile up simultaneously
		because the "loaded" flag of SteamsideTileset
		has not been set yet.
		That makes tiles be rendered randomly.
		 */
		SteamsideTileset.ajaxTileset(function(){
			new SteamsideView().render();
		});
    }

};