var CollectionEditTile = {
	tile: new Tile(
		{url: 'CollectionEdit.html', selector: "#tile-collection-edit"}),

	ajaxTile: function (callback) {
		this.tile.ajaxTile(callback);
	}
};

var SteamsideCollectionApps = Backbone.Collection.extend({
    model: Game,
	collection_name: null,
	url: function() {
		return "api/collection/collection.json?name=" + this.collection_name;
	}
});

var CollectionEditView = Backbone.View.extend({

	render: function() {		"use strict";

		var that = this;

		var collectionEditSearchResults = new SearchResults();
		this.collectionEditSearchResults = collectionEditSearchResults;

		CommandBoxTile.ajaxTile(function(tile) {
			that.render_search_CommandBox(tile);
		});

		// TODO Reuse same continues collection as front page?
		var continues = new ContinueGames();
		new DeckView({
			el: this.$('#collection-edit-search-results-deck'),
			collection: collectionEditSearchResults,
			continues: continues,
			on_GameCard_render: function(viewGameCard) { that.on_SearchResults_GameCard_render(viewGameCard) }
		});

		var inCollection = new SteamsideCollectionApps();
        inCollection.collection_name = that.options.collection_name;

		new DeckView({
            el: this.$('#games-in-collection-deck'),
            collection: inCollection
        });

		collectionEditSearchResults.queryString = "recent=true";
		fetch_json(collectionEditSearchResults);
		fetch_json(inCollection);

        return this;
    },

	render_search_CommandBox: function(tile) {
		var that = this;
		var viewCommandBox = new CommandBoxView({
			el: tile.clone(),
			placeholder_text: 'search for games',
			on_change_input: function(input) { that.on_search_input_changed(input) },
			on_command: function(input) { that.on_search_command(input) },
			on_command_alternate: function(input) { that.on_search_command_alternate(input) }
		});
		var view_el = viewCommandBox.render().el;
		var searchEl = this.$('#collection-edit-search-command-box');
		searchEl.empty();
		searchEl.append(view_el);
		var recent = this.$('#input-recent');
		recent.remove();
		var form = this.$("#form-command-box");
		form.append(recent);
		/*
		viewCommandBox.emptyCommandHints();
		viewCommandBox.appendCommandHint(this.tileSearchHintContinueA);
		viewCommandBox.appendCommandHint(this.tileSearchHintSearchA);
		viewCommandBox.appendCommandHintAlternate(this.tileSearchHintContinueB);
		viewCommandBox.appendCommandHintAlternate(this.tileSearchHintSearchB);
		*/
		viewCommandBox.input_query_focus();
	},

	on_search_input_changed: function(view) {
		/*
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
		*/
	},

	on_search_command: function(view) {
		var input = view.input_query_val();
		if (input == '') {
			var recent = this.$('#input-recent');
			recent.attr('value', 'true');
			var form = this.$("#form-command-box");
			var q = form.serialize();
			this.collectionEditSearchResults.queryString = q;
		} else {
			this.collectionEditSearchResults.query = input;
		}
		fetch_json(this.collectionEditSearchResults);
	},

	on_search_command_alternate: function(view) {
		/*
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
		*/
	},

	on_SearchResults_GameCard_render: function (viewGameCard) {
		var bar = viewGameCard.$el.find('.game-tile-command-bar');
		var play = bar.find('.game-tile-play');
		var add = play.clone();
		add.text('add');
		add.removeClass('game-tile-play');
		add.insertBefore(play);
		var that = this;
		add.click(function(e) {e.preventDefault(); that.on_add_click(); viewGameCard.$el.slideUp(); });
	},

	on_add_click: function() {

	}
});
