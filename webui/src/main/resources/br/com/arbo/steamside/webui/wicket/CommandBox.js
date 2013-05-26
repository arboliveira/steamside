var CommandBoxTile = {
	tile: new Tile(
		{url: 'CommandBox.html', selector: "#tile-command-box"}),

	ajaxTile: function (callback) {
		this.tile.ajaxTile(callback);
	}
};

var CommandBoxView = Backbone.View.extend({

	events: {
		'submit form': 'key_enter',
		"keyup input": "change_input",
		"change input": "change_input"
	},

	render: function() {		"use strict";
		this.inputEl().attr('placeholder', this.options.placeholder_text);
		return this;
	},

	inputEl: function() {
		return this.$('input');
	},

	key_enter: function(e) {     "use strict";
		e.preventDefault();
		this.options.on_command(this.inputEl().val());
	},

	change_input: function(e) {     "use strict";
		e.preventDefault();
		this.options.on_change_input(this.inputEl().val());
	}

});

