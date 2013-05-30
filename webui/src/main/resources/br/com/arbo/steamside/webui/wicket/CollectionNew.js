var CollectionNewTile = {
	tile: new Tile(
		{url: 'CollectionNew.html', selector: "#collection-new"}),

	ajaxTile: function (callback) {
		this.tile.ajaxTile(callback);
	}
}

var CollectionNewEmptyView = Backbone.View.extend({

	render: function() {
		var targetEl = this.$('#div-empty-name-form');
		targetEl.empty();

		var that = this;

		var on_command =

		CommandBoxTile.ajaxTile(
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
		var aUrl = "/collection/" + input + "/create";
		var after = "#/collections/" + input + "/edit";

		$.ajax({
			url: aUrl,
			dataType: dataTypeOf(aUrl),
			beforeSend: function(){
				//that.showOverlay('Now playing');
			},
			complete: function(){
				Backbone.history.navigate(after, {trigger: true});
			}
		});


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
			var same = '(waiting for input...)';
			c_hint.text(same);
			c_hint_alternate.text(same);
		} else {
			var full = "Create " + input + " collection";
			c_hint.text(full + " and start adding games");
			c_hint_alternate.text(full + " and stay here");
		}
    }
});
