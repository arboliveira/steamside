var CommandBoxTile = {
	tile: new Tile(
		{url: 'CommandBox.html', selector: "#tile-command-box"}),

	ajaxTile: function (callback) {
		this.tile.ajaxTile(callback);
	}
};

var CommandBoxView = Backbone.View.extend({

	events: {
		'submit form': 'event_key_enter',
		"keyup input": "event_change_input",
		"change input": "event_change_input"
	},

	render: function() {		"use strict";
		this.inputEl().attr('placeholder', this.options.placeholder_text);
		this.change_input();
		return this;
	},

	inputEl: function() {
		return this.$('#input-id-text-search');
	},

	focusInput: function() {
		this.inputEl().focus();
	},

	emptyCommandHints: function() {
		this.$("#command-hint").empty();
		this.$("#command-hint-alternate").empty();
	},

	appendCommandHint: function (elHint) {
		this.$("#command-hint").append(elHint);
	},

	appendCommandHintAlternate: function (elHint) {
		this.$("#command-hint-alternate").append(elHint);
	},

	event_key_enter: function(e) {     "use strict";
		e.preventDefault();
		this.options.on_command(this.inputEl().val());
	},

	change_input: function () {
		this.options.on_change_input(this.inputEl().val());
	},

	event_change_input: function(e) {     "use strict";
		e.preventDefault();
		this.change_input();
	}


});

