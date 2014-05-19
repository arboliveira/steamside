var TagTile = {
	tile: new Tile(
		{url: 'Tag.html', selector: "#TagTile"}),

	whenLoaded: function (callback)
	{
		this.tile.ajaxTile(callback);
	},

	on_tag: function(game, segmentWithGameCard)
	{
		TagTile.whenLoaded(function(tile)
		{
			var clone = tile.clone();

			var view = new TagView({
				el: clone,
				game: game
			});

			view.render();

			segmentWithGameCard.after(clone);
		});
	}
};


var TagView = Backbone.View.extend({

	game: null,

	initialize: function() {
		this.game = this.options.game;
	},

	render: function() {
		this.$(".game-name").text(this.game.name());

		this.renderCommandHints();

		var that = this;
		CommandBoxTile.whenLoaded(function(tile) {
			that.on_CommandBox_TileLoaded(tile);
		});

		this.$el.hide();
		this.$el.slideDown();

		// this.on_tag_done();

		return this;
	},

	renderCommandHints: function () {
		var template = this.$('#tag-command-hint');
		template.remove();

		this.elCommandHintA =
			this.renderCommandHint(template, "Tag as");
		this.elCommandHintB =
			this.renderCommandHint(template, "Search collections for");
	},

	renderCommandHint: function (template, begin) {
		var el = template.clone();
		el.find('#tag-command-hint-begin').text(begin);
		return el;
	},

	on_CommandBox_TileLoaded: function(tile) {
		var that = this;
		var viewCommandBox = new CommandBoxView({
			el: tile.clone(),
			placeholder_text: 'Collection for ' + this.game.name(),
			on_command: function(input) { that.on_tag_command(input) },
			on_command_alternate: function(input) { that.on_tag_command_alternate(input) },
			on_change_input: function(input) { that.on_tag_change_input(input); }
		});

		var targetEl = this.$('#div-command-box');
		targetEl.empty();
		targetEl.append(viewCommandBox.render().el);

		viewCommandBox.emptyCommandHints();
		viewCommandBox.appendCommandHint(this.elCommandHintA);
		viewCommandBox.appendCommandHintAlternate(this.elCommandHintB);

		// TODO Favorites or most recently used
		this.updateWithInputValue("");

		viewCommandBox.input_query_focus();
	},

	on_tag_command: function(view) {
		var input = view.input_query_val();

		var appid = this.game.appid();
		var collection = this.nameForCollection(input);
		var aUrl = "api/app/" + appid + "/tag/" + encodeURIComponent(collection);
		var that = this;

		// TODO display 'creating...'
		/*
		 beforeSend: function(){
		 },
		 */

		$.ajax({
			url: aUrl,
			dataType: dataTypeOf(aUrl)
		}).done(function(){
			that.on_tag_done();
		}).fail(function(error){
			view.trouble(error);
		});
	},

	on_tag_command_alternate: function(view) {

	},

	on_tag_done: function() {
		var input_el = this.$('#input-text-command-box');
		input_el.val('');
		input_el.focus();
		this.on_tag_change_input(this);
	},

	nameForCollection: function(input) {
		if (input == '') return "Favorites";
		return input;
	},

	on_tag_change_input: function (view) {
		var input = view.input_query_val();
		this.updateWithInputValue(input);
	},

	updateWithInputValue: function (input) {
		var name = this.nameForCollection(input);
		var selector = '#tag-command-hint-subject';
		this.elCommandHintA.find(selector).text(name);
		this.elCommandHintB.find(selector).text(name);
	}

});

