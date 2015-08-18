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

var TaglessCount = Tag.extend({
	url: "api/inventory/tagless-count.json"
});

var OwnedAppsInventory = Backbone.Collection.extend({
	model: Game,
	url: function() {
		return "api/inventory/owned.json";
	}
});

var OwnedCount = Tag.extend({
	url: "api/inventory/owned-count.json"
});

