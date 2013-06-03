var CollectionNewTile = {
	tile: new Tile(
		{url: 'CollectionNew.html', selector: "#collection-new"}),

	ajaxTile: function (callback) {
		this.tile.ajaxTile(callback);
	}
};

var CollectionNewEmptyView = Backbone.View.extend({

	tileEmptyCommandHintA: null,
	tileEmptyCommandHintB: null,

	render: function() {
		var tileEmptyCommandHint = this.$('#empty-command-hint');
		tileEmptyCommandHint.remove();
		this.tileEmptyCommandHintA = tileEmptyCommandHint.clone();
		this.tileEmptyCommandHintB = tileEmptyCommandHint.clone();
		var selectorAfterwards = '#empty-command-hint-afterwards';
		this.tileEmptyCommandHintA.find(selectorAfterwards).text("add games");
		this.tileEmptyCommandHintB.find(selectorAfterwards).text("stay here");

		var that = this;
		CommandBoxTile.ajaxTile(function(tile) {
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
		viewCommandBox.appendCommandHint(this.tileEmptyCommandHintA);
		viewCommandBox.appendCommandHintAlternate(this.tileEmptyCommandHintB);

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
		var aUrl = "/collection/" + name + "/create";
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
		this.tileEmptyCommandHintA.find(selector).text(name);
		this.tileEmptyCommandHintB.find(selector).text(name);
	},

	on_empty_change_input: function (view) {  "use strict";
		var input = view.input_query_val();
		this.updateWithInputValue(input);
	}
});
