var SearchResults = Backbone.Collection.extend({
	model: Game,
	query: null,
	queryString: null,
	url: function() {
		var base = "search.json?";
		if (this.queryString != null) return base + this.queryString;
		return base + "query=" + this.query;
	}
});

