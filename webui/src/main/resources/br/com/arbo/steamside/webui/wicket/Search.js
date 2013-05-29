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

		CommandBoxTile.ajaxTile(
			function(tile) {
				var view = new CommandBoxView({
					el: tile.clone(),
					on_command: function(input) { that.doSearch(input); },
					on_change_input: function(input) { that.doInputChanged(input); },
					placeholder_text: 'game or command'
				});
				var view_el = view.render().el;
				that.$el.append(view_el);
				that.doCommandBoxRendered(view);
			}
		);

		return this;
	},

	doInputChanged: function (input) {
		this.options.on_input_changed(input);
	},

	doSearch: function(input) {     "use strict";
		var c = this.collection;
		c.query = input;
		fetch_json(c);
	},

	doCommandBoxRendered: function(viewCommandBox) {
		this.options.on_CommandBox_rendered(viewCommandBox);
	}
});

