var SearchResults = Backbone.Collection.extend({
	model: Game,
	query: null,
	queryString: null,
	url: function() {
		var base = "api/search/search.json?";
		if (this.queryString != null) return base + this.queryString;
		return base + "query=" + this.query;
	},

    setQueryString: function(value) {
        this.queryString = value;
        this.query = null;
    },

    setQuery: function(value) {
        this.queryString = null;
        this.query = value;
    }
});

