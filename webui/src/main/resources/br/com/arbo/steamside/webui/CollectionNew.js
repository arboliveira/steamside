var CollectionNewTile = {
	tile: new Tile(
		{url: 'CollectionNew.html', selector: "#collection-new"}),

	whenLoaded: function (callback) {
		this.tile.ajaxTile(callback);
	}
};

var CollectionNewView = Backbone.View.extend({

	render: function ()
	{
		new CollectionNewEmptyView({
			el: this.$('#collection-new-empty-segment')
		}).render();

		new CollectionCopyAllCategoriesView({
			el: this.$('#collection-copy-all-categories-segment')
		}).render();

		return this;
	}
});

var CollectionNewEmptyView = Backbone.View.extend({

	elCommandHintA: null,
	elCommandHintB: null,

	render: function() {
		var tileEmptyCommandHint = this.$('#empty-command-hint');
		tileEmptyCommandHint.remove();
		this.elCommandHintA = tileEmptyCommandHint.clone();
		this.elCommandHintB = tileEmptyCommandHint.clone();
		var selectorAfterwards = '#empty-command-hint-afterwards';
		this.elCommandHintA.find(selectorAfterwards).text("add games");
		this.elCommandHintB.find(selectorAfterwards).text("stay here");

		var that = this;
		CommandBoxTile.whenLoaded(function(tile) {
			that.on_empty_CommandBox_rendered(tile);
		});

		return this;
	},

	on_empty_CommandBox_rendered: function(tile) {
		var that = this;
		var viewCommandBox = new CommandBoxView({
			el: tile.clone(),
			placeholder_text: 'Name for empty collection',
			on_command: function(input) { that.on_empty_command(input) },
			on_command_alternate: function(input) { that.on_empty_command_alternate(input) },
			on_change_input: function(input) { that.on_empty_change_input(input); }
		});

		var targetEl = this.$('#div-empty-name-form');
		targetEl.empty();
		targetEl.append(viewCommandBox.render().el);

		viewCommandBox.emptyCommandHints();
		viewCommandBox.appendCommandHint(this.elCommandHintA);
		viewCommandBox.appendCommandHintAlternate(this.elCommandHintB);

		this.updateWithInputValue("");
		viewCommandBox.input_query_focus();
	},

	nameForCollection: function(input) {
		if (input == '') return "Favorites";
		return input;
	},

	on_empty_command: function(view) {
		var input = view.input_query_val();
		this.createEmpty({name: input, stay: false});
	},

	on_empty_command_alternate: function(view) {
		var input = view.input_query_val();
		this.createEmpty({name: input, stay: false});
	},

	createEmpty: function(args) {     "use strict";
		var name = this.nameForCollection(args.name);
		var aUrl = "api/collection/" + encodeURIComponent(name) + "/create";
		var that = this;

		$.ajax({
			url: aUrl,
			dataType: dataTypeOf(aUrl),
			beforeSend: function(){
				// TODO display 'creating...'
			},
			complete: function(){
				if (args.stay) {
					var input_el = that.$('#input-text-command-box');
					input_el.val('');
					input_el.focus();
					that.on_empty_change_input('');
				} else {
					Backbone.history.navigate(
						"#/collections/" + name + "/edit",
						{trigger: true});
				}
			}
		});
	},

	updateWithInputValue: function (input) {
		var name = this.nameForCollection(input);
		var selector = '#empty-command-hint-subject';
		this.elCommandHintA.find(selector).text(name);
		this.elCommandHintB.find(selector).text(name);
	},

	on_empty_change_input: function (view) {  "use strict";
		var input = view.input_query_val();
		this.updateWithInputValue(input);
	}
});

var CollectionCopyAllCategoriesView = Backbone.View.extend({

	events: {
		"click .button-copy-em-all": "buttonCopyEmAll"
	},

	render: function() {
		return this;
	},

	buttonCopyEmAll: function (e) {
		e.preventDefault();
		var jLink = $(e.target);
		var aUrl = jLink.attr( "href" );

		$.ajax(
			{
				url: aUrl
			}
		).done(function(){
			Backbone.history.navigate(
					"#/favorites/switch",
				{trigger: true});
		});
	}
});