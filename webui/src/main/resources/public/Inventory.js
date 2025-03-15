import {Tag} from "#steamside/Tag.js";
import {Game} from "#steamside/GameCardDeck.js";

export const SteamsideCollectionApps = Backbone.Collection.extend({
	model: Game,
	collection_name: null,
	url: function() {
		return `api/collection/${encodeURIComponent(this.collection_name)}/collection.json`;
	}
});

export const TaglessAppsInventory = Backbone.Collection.extend({
	model: Game,
	url: function() {
		return "api/inventory/tagless.json";
	}
});

export const TaglessCount = Tag.extend({
	url: "api/inventory/tagless-count.json"
});

export const OwnedAppsInventory = Backbone.Collection.extend({
	model: Game,
	url: function() {
		return "api/inventory/owned.json";
	}
});

export const OwnedCount = Tag.extend({
	url: "api/inventory/owned-count.json"
});

