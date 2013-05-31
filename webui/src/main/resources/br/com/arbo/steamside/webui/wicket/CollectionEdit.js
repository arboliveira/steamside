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
		return "apps.json?collection_name=" + this.collection_name;
	}
});

var CollectionEditView = Backbone.View.extend({

    render: function() {		"use strict";

		var that = this;

		var collectionEditSearchResults = new SearchResults();

		new SearchView({
			el: this.$('#collection-edit-search-command-box'),
			collection: collectionEditSearchResults,
			on_CommandBox_rendered: function(viewCommandBox) { that.on_search_CommandBox_rendered(viewCommandBox) },
			on_change_input: function(input) { that.on_search_input_changed(input) },
			on_command: function(input) { that.on_search_command(input) },
			on_command_alternate: function(input) { that.on_search_command_alternate(input) }
		}).render();

		var inCollection = new SteamsideCollectionApps();

		new DeckView({
            el: this.$('#games-in-collection-deck'),
            collection: inCollection
        });

		fetch_json(inCollection);

        return this;
    },

	on_search_CommandBox_rendered: function(viewCommandBox) {
		/*
		viewCommandBox.emptyCommandHints();
		viewCommandBox.appendCommandHint(this.tileSearchHintContinueA);
		viewCommandBox.appendCommandHint(this.tileSearchHintSearchA);
		viewCommandBox.appendCommandHintAlternate(this.tileSearchHintContinueB);
		viewCommandBox.appendCommandHintAlternate(this.tileSearchHintSearchB);
		viewCommandBox.focusInput();
		*/
	},

	on_search_input_changed: function(input) {
		/*
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

	on_search_command: function(input) {
		/*
		if (input == '') {
			var gameA = this.continues.at(0);
			gameA.play();
		} else {
			this.searchResults.query = input;
			fetch_json(this.searchResults);
		}
		*/
	},

	on_search_command_alternate: function(input) {
		/*
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
	}
});
