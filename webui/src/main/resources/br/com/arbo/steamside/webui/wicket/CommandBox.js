var CommandBoxTile = {
	tile: new Tile(
		{url: 'CommandBox.html', selector: "#tile-command-box"}),

	ajaxTile: function (callback) {
		this.tile.ajaxTile(callback);
	}
};

var CommandBoxView = Backbone.View.extend({

	events: {
		'keydown #input-text-command-box': 'event_keydown_input',
		"keyup #input-text-command-box": "event_change_input",
		"change #input-text-command-box": "event_change_input",
		"click #command-button": "doCommand",
		"click #command-button-alternate": "doCommandAlternate"
	},

	render: function() {		"use strict";
		this.inputEl().attr('placeholder', this.options.placeholder_text);
		this.change_input();
		return this;
	},

	inputEl: function() {
		return this.$('#input-text-command-box');
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

	doCommand: function () {
		this.options.on_command(this.inputEl().val());
	},

	doCommandAlternate: function () {
		this.options.on_command_alternate(this.inputEl().val());
	},

	event_keydown_input: function(e) {
		if (e.keyCode === 13) {
			e.preventDefault();
			if (!e.ctrlKey) {
				this.doCommand();
			} else {
				this.doCommandAlternate();
			}
		}
	},

	change_input: function () {
		this.options.on_change_input(this.inputEl().val());
	},

	event_change_input: function(e) {     "use strict";
		e.preventDefault();
		this.change_input();
	}


});

