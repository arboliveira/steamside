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

		var collectionEditSearchResults = new SearchResults();

		new SearchView({
			el: this.$('#collection-edit-search-command-box'),
			collection: collectionEditSearchResults
		}).render();

		var inCollection = new SteamsideCollectionApps();

		new DeckView({
            el: this.$('#games-in-collection-deck'),
            collection: inCollection
        });

		fetch_json(inCollection);

        return this;
    }
});
