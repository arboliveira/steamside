"use strict";

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
		"click #command-button-alternate": "doCommandAlternate",
		"click #command-button-confirm": "doCommandConfirm"
	},

	render: function() {
		this.input_query_el().attr('placeholder', this.options.placeholder_text);
		this.$('#command-confirm').hide();
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
		this.emptyCommandHintConfirm();
	},

	appendCommandHint: function (elHint) {
		this.$("#command-hint").append(elHint);
	},

	appendCommandHintAlternate: function (elHint) {
		this.$("#command-hint-alternate").append(elHint);
	},

	showCommandHintConfirm: function (elHint) {
		var el = this.emptyCommandHintConfirm();
		el.append(elHint);
		this.$('#command-confirm').show();
	},

	hideCommandHintConfirm: function ()
	{
		this.emptyCommandHintConfirm();
		this.$('#command-confirm').hide();
	},

	emptyCommandHintConfirm: function ()
	{
		var el = this.$("#command-confirm-what");
		el.empty();
		return el;
	},

	input_query_val: function () {
		return this.input_query_el().val();
	},

	input_query_setval: function (val) {
		var input_el = this.input_query_el();
		input_el.val(val);
		input_el.focus();
		this.change_input();
	},

	doCommand: function () {
		this.options.on_command(this);
	},

	doCommandAlternate: function () {
		this.options.on_command_alternate(this);
	},

	doCommandConfirm: function ()
	{
		this.options.on_command_confirm(this);
	},

	event_keydown_input: function(e) {
		if (!(e.keyCode === 13)) return;

		e.preventDefault();

		if (e.shiftKey)
		{
			this.doCommandConfirm();
			return;
		}

		if (e.ctrlKey)
		{
			this.doCommandAlternate();
			return;
		}

		this.doCommand();
	},

	change_input: function () {
		this.options.on_change_input(this);
	},

	event_change_input: function(e) {
		e.preventDefault();
		this.change_input();
	},

	trouble: function (error) {
		var span = this.$("#command-trouble");
		span.text(error.status + ' ' + error.statusText);
		span.show();
	}

});

