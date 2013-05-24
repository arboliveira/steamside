var CollectionNewEmptyView = Backbone.View.extend({

	render: function() {
		var targetEl = this.$('#div-empty-name-form');
		targetEl.empty();

		var that = this;

		var on_command =

		SteamsideTileset.tileCommandBox(
			function(tile) {
				var view = new CommandBoxView({
					el: tile.clone(),
					placeholder_text: 'Name for empty collection',
					on_command: function(input) { that.doCommand(input); },
					on_change_input: function(input) { that.doChangeInput(input); }
				});
				targetEl.append(view.render().el);
				that.doChangeInput("");
			}
		);

		return this;
	},

	doCommand: function(input) {     "use strict";
		var form = this.$("#form-empty");
		form.attr("action", "#/collections/" + input + "/edit");
		/*
		var c = this.collection;
		c.value = input;
		fetch_json(c);
		*/
	},

    doChangeInput: function (input) {  "use strict";
		var c_hint = this.$("#command-hint");
		var c_hint_alternate = this.$("#command-hint-alternate");
		if (input === '') {
			var same = '(waiting for input...)'
			c_hint.text(same);
			c_hint_alternate.text(same);
		} else {
			var full = "Create " + input + " collection";
			c_hint.text(full + " and start adding games");
			c_hint_alternate.text(full + " and stay here");
		}
    }
});
