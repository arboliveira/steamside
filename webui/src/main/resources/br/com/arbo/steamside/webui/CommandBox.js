var CommandBoxTile = {
	tile: new Tile(
		{url: 'CommandBox.html', selector: "#tile-command-box"}),

	whenLoaded: function (callback) {
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
		this.input_query_el().attr('placeholder', this.options.placeholder_text);
		this.change_input();
		return this;
	},

	input_query_el: function() {
		return this.$('#input-text-command-box');
	},

	input_query_focus: function() {
		this.input_query_el().focus();
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

	input_query_val: function () {
		return this.input_query_el().val();
	},

	doCommand: function () {
		this.options.on_command(this);
	},

	doCommandAlternate: function () {
		this.options.on_command_alternate(this);
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
		this.options.on_change_input(this);
	},

	event_change_input: function(e) {     "use strict";
		e.preventDefault();
		this.change_input();
	},

	trouble: function (error) {
		var span = this.$("#command-trouble");
		span.text(error.status + ' ' + error.statusText);
		span.show();
	}

});

