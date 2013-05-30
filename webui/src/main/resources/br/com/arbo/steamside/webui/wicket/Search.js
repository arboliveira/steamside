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
					placeholder_text: 'game or command',
					on_command: function(input) { that.options.on_command(input); },
					on_command_alternate: function(input) { that.options.on_command_alternate(input) },
					on_change_input: function(input) { that.options.on_change_input(input) }
				});
				var view_el = view.render().el;
				that.$el.append(view_el);
				that.options.on_CommandBox_rendered(view);
			}
		);

		return this;
	}

});

