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

		this.$(".input-tag").focus();

		return this;
	},

	renderCommandHints: function () {
		var template = this.$('#empty-command-hint');
		template.remove();

		this.elCommandHintA =
			this.renderCommandHint(template, "Search collections for");
		this.elCommandHintB =
			this.renderCommandHint(template, "Tag as");
	},

	renderCommandHint: function (template, begin) {
		var el = template.clone();
		el.find('#empty-command-hint-begin').text(begin);
		return el;
	},

	on_CommandBox_TileLoaded: function(tile) {
		var that = this;
		var viewCommandBox = new CommandBoxView({
			el: tile.clone(),
			placeholder_text: 'Collection for ' + this.game.name(),
			on_command: function(input) { that.on_empty_command(input) },
			on_command_alternate: function(input) { that.on_empty_command_alternate(input) },
			on_change_input: function(input) { that.on_empty_change_input(input); }
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

	createEmpty: function(args) {
		var name = this.nameForCollection(args.name);
		var aUrl = "api/collection/" + name + "/create";
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
		}).fail(function(error){
			that.elCommandHintA.text(error.status + ' ' + error.statusText);
		});
	},

	updateWithInputValue: function (input) {
		var name = this.nameForCollection(input);
		var selector = '#empty-command-hint-subject';
		this.elCommandHintA.find(selector).text(name);
		this.elCommandHintB.find(selector).text(name);
	},

	on_empty_change_input: function (view) {
		var input = view.input_query_val();
		this.updateWithInputValue(input);
	}

});

