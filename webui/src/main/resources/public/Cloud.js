"use strict";

var CloudModel = Backbone.Model.extend(
{
	url: "api/cloud/cloud.json",

	cloudEnabled: function()
	{
		return this.get("cloud");
	},

	cloudSyncedLocation: function()
	{
		return this.get("cloudSyncedLocation");
	},

	cloudEnabled_set: function(v)
	{
		this.set("cloud", v);
	},

	cloudSyncedLocation_set: function(v)
	{
		return this.set("cloudSyncedLocation", v);
	}

});

var CloudView = Backbone.View.extend(
{
	events:
	{
		"click #SaveButton": "editSave"
	},

	initialize: function(options)
	{
		var that = this;

		that.backend = options.backend;
		that.model = options.model;

		that.cloud_CommandBox = new CommandBoxView({
			placeholder_text: '',
			on_change_input: function(input) { that.on_cloud_change_input(input); },
			on_command: function(view) { that.on_cloud_command(view) },
			on_command_alternate: function(view) { that.on_cloud_command_alternate(view) },
			on_command_confirm: function(view) { that.on_cloud_command_confirm(view) }
		});

		that.whenSprite =
			CloudView.sprite.sprite_promise().then(function(el) {
				that.$el.append(el);
				that.$("#CloudAddressCommandBoxView").empty().append(
					that.cloud_CommandBox.el);
				return that;
			});
		that.whenModel = that.backend.fetch_promise(that.model);
	},

	render: function () {
		var that = this;
		that.whenRendered = this.whenSprite.then(function(view) {
			view.render_el();
			return view;
		});
		return that;
	},

	render_el: function()
	{
		var that = this;

		that.cloud_CommandBox
			.render_commandBox_promise().done(function(view)
				{
					that.rendered_cloud_CommandBox(view);
				});

		that.whenModel.done(function() {
			that.editBegin();
		});

		this.tooltips_prepare();

		return this;
	},

	/**
	 * @param {CommandBoxView} commandBoxView
	 */
	rendered_cloud_CommandBox: function(commandBoxView) {
		commandBoxView.label_text('Mirror to directory');
		commandBoxView.input_query_el().attr("data-bind", "value: cloudSyncedLocation");
		// TODO Enter Visit url
		commandBoxView.hideCommandButtonStrip();
	},

	editBegin: function()
	{
		var that = this;

		that.$("#CloudEnabledCheckbox")
			.prop("checked", that.model.cloudEnabled());

		that.cloud_CommandBox.input_query_setval(that.model.cloudSyncedLocation());
	},

	editSave: function()
	{
		var that = this;

		that.tooltip_saved_hide();

		that.model.cloudEnabled_set(
			that.$("#CloudEnabledCheckbox")
				.prop("checked"));
		that.model.cloudSyncedLocation_set(that.cloud_CommandBox.input_query_val());

		that.model.save()
			.done(function()
				{
					that.save_done();
				})
			.fail(function(error)
				{
		            that.save_fail(error);
				});
	},

	save_done: function()
	{
		this.tooltip_saved_show();
	},

	save_fail: function(error)
	{
		this.cloud_CommandBox.trouble(error);
	},

	tooltip_saved_hide: function () {
		this.$('#SaveButton').tooltipster('hide');
	},

	tooltip_saved_show: function () {
		this.$('#SaveButton').tooltipster('show');
	},

	tooltips_prepare: function ()
	{
		this.$('#SaveButton').tooltipster({
			position: 'right',
			trigger: 'custom',
			interactive: 'true'
		});
	},

	on_cloud_change_input: function (input) {

	},

	on_cloud_command: function (input) {

	},

	on_cloud_command_alternate: function (input) {

	},

	on_cloud_command_confirm: function (input) {

	},


	/**
	 * @type CloudModel
	 */
	model: null,

	/**
	 * @type Backend
	 */
	backend: null,

	/**
	 * @type Deferred
	 */
	whenRendered: null,

	/**
	 * @type Deferred
	 */
	whenModel: null,

	/**
	 * @type Deferred
	 */
	whenSprite: null,

	/**
	 * @type CommandBoxView
	 */
	cloud_CommandBox: null

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder(
		{url: 'Cloud.html', selector: "#CloudView"}).build()

});




