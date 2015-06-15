"use strict";

var CommandBoxView = Backbone.View.extend({

	events: {
		'keydown #input-text-command-box': 'event_keydown_input',
		"keyup #input-text-command-box": "event_change_input",
		"change #input-text-command-box": "event_change_input",
		"click #command-button": "doCommand",
		"click #command-button-alternate": "doCommandAlternate",
		"click #command-button-confirm": "doCommandConfirm"
	},

	initialize: function(options)
	{
		this.placeholder_text = options.placeholder_text;
		this.on_change_input = options.on_change_input;
		this.on_command = options.on_command;
		this.on_command_alternate = options.on_command_alternate;
		this.on_command_confirm = options.on_command_confirm;
		this.on_change_input = options.on_change_input;
	},

	render: function() {
		var that = this;
		this.whenRendered =
			CommandBoxView.sprite.sprite_promise().then(function(el) {
				that.render_el(el.clone());
				return that;
			});
		return this;
	},

	render_el: function(el) {
		this.$el.append(el);
		this.input_query_el().attr('placeholder', this.placeholder_text);
		this.$('#command-confirm').hide();
		this.change_input();
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

	hideCommandButtonStrip: function ()
	{
		this.$('#command-button-strip').hide();
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

	label_el: function()
	{
		return this.$('#CommandLabel');
	},

	label_text: function( v )
	{
		var label_el = this.label_el();
		label_el.text(v);
		label_el.show();
	},

	doCommand: function () {
		this.on_command(this);
	},

	doCommandAlternate: function () {
		this.on_command_alternate(this);
	},

	doCommandConfirm: function ()
	{
		this.on_command_confirm(this);
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
		this.on_change_input(this);
	},

	event_change_input: function(e) {
		e.preventDefault();
		this.change_input();
	},

	trouble: function (error) {
		var message;
		if (error.responseJSON != undefined)
		{
			message = error.responseJSON.message;
		}
		if (message == null)
		{
			message = error.status + ' ' + error.statusText;
		}

		var span = this.$("#command-trouble");
		span.text(message);
		span.show();
	},

	whenRendered: null

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder({
		url: 'CommandBox.html', selector: "#tile-command-box"}).build()

});
