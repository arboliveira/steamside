var SearchResults = Backbone.Collection.extend({
	model: Game,
	query: null,
	url: function() {
		return "search.json?query=" + this.query;
	}
});

var SearchView = Backbone.View.extend({

	render: function() {
		this.$el.empty();

		var that = this;

		var on_command = function(input) {
			that.doSearch(input);
		}

		CommandBoxTile.ajaxTile(
			function(tile) {
				var view = new CommandBoxView({
					el: tile.clone(),
					on_command: on_command,
					placeholder_text: 'game or command'
				});
				var view_el = view.render().el;
				that.$el.append(view_el);
			}
		);

		return this;
	},

	doSearch: function(input) {     "use strict";
		var c = this.collection;
		c.query = input;
		fetch_json(c);
	}
});

