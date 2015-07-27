"use strict";

var SteamsideCollectionApps = Backbone.Collection.extend({
	model: Game,
	collection_name: null,
	url: function() {
		return "api/collection/collection.json?name=" + encodeURIComponent(this.collection_name);
	}
});

var TaglessAppsInventory = Backbone.Collection.extend({
	model: Game,
	url: function() {
		return "api/inventory/tagless.json";
	}
});

var OwnedAppsInventory = Backbone.Collection.extend({
	model: Game,
	url: function() {
		return "api/inventory/owned.json";
	}
});
