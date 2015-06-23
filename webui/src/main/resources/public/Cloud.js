"use strict";

var CloudModel = Backbone.Model.extend(
{
	url: "api/cloud/cloud.json",

	cloudEnabled: function()
	{
		return this.get("cloud");
	},

	dontpadUrl: function()
	{
		return this.get("dontpad");
	},

	cloudEnabled_set: function(v)
	{
		this.set("cloud", v);
	},

	dontpadUrl_set: function(v)
	{
		return this.set("dontpad", v);
	}

});

/**
 * @param {CloudView} cloudView
 */
function CloudEdit(cloudView)
{
	var self = this;

	self.cloudEnabled = ko.observable();
	self.dontpadUrl = ko.observable();

	self.apply = function() {
		cloudView.editSave();
	}
}

var CloudView = Backbone.View.extend(
{
	initialize: function(options)
	{
		var that = this;
		this.backend = options.backend;
		this.cloudModel = options.cloudModel;

		var saveAction;
		if (options.saveAction == undefined)
		{
			saveAction = function(model)
			{
				return model.save();
			}
		}
		else
		{
			saveAction = options.saveAction;
		}
		this.saveAction = saveAction;

		this.cloudEdit = new CloudEdit(this);

		this.whenSprite =
			CloudView.sprite.sprite_promise().then(function(el) {
				that.$el.append(el);
				return that;
			});
		this.whenModel = this.backend.fetch_promise(this.cloudModel);
	},

	render: function () {
		var that = this;
		this.whenRendered = this.whenSprite.then(function(view) {
			view.render_el();
			return view;
		});
		return this;
	},

	render_el: function()
	{
		var that = this;

		this.cloud_CommandBox = new CommandBoxView({
			placeholder_text: 'http://dontpad.com/(address to sync your Steamside data)',
			on_change_input: function(input) { that.on_cloud_change_input(input); },
			on_command: function(view) { that.on_cloud_command(view) },
			on_command_alternate: function(view) { that.on_cloud_command_alternate(view) },
			on_command_confirm: function(view) { that.on_cloud_command_confirm(view) }
		});

		that.$("#CloudAddressCommandBoxView").append(this.cloud_CommandBox.el);

		this.cloud_CommandBox
			.render_commandBox_promise().done(function(view)
				{
					that.rendered_cloud_CommandBox(view);
				});

		that.whenModel.done(function(model) {
			that.editBegin();
		});

		this.tooltips_prepare();

		return this;
	},

	editBegin: function()
	{
		var that = this;

		ko.applyBindings(that.cloudEdit, that.el);

		that.cloudEdit.cloudEnabled(that.cloudModel.cloudEnabled());
		that.cloudEdit.dontpadUrl(that.cloudModel.dontpadUrl());
	},

	editSave: function()
	{
		var that = this;

		that.tooltip_saved_hide();

		that.cloudModel.cloudEnabled_set(that.cloudEdit.cloudEnabled());
		that.cloudModel.dontpadUrl_set(that.cloudEdit.dontpadUrl());

		that.saveAction(that.cloudModel)
			.done(function()
				{
					that.tooltip_saved_show();
				})
			.fail(function(error)
				{
		            that.cloud_CommandBox.trouble(error);
				});
	},

	/**
	 * @param {CommandBoxView} commandBoxView
	 */
	rendered_cloud_CommandBox: function(commandBoxView) {
		commandBoxView.label_text('Cloud address');
		commandBoxView.input_query_el().attr("data-bind", "value: dontpadUrl");
		// TODO Enter Visit url
		commandBoxView.hideCommandButtonStrip();
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
	cloudModel: null,

	/**
	 * @type CloudEdit
	 */
	cloudEdit: null,

	/**
	 * @type Backend
	 */
	backend: null,

	saveAction: null,

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
	whenSprite: null

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder(
		{url: 'Cloud.html', selector: "#CloudView"}).build()

});




