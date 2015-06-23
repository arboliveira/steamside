"use strict";

var SettingsView = Backbone.View.extend({

	initialize: function(options)
	{
		var that = this;
		this._backend = options.backend;
	},

	render_promise: function()
	{
		return this.render().whenRendered;
	},

	render: function () {
		var that = this;
		this.whenRendered =
			SettingsView.sprite.sprite_promise().then(function(el) {
				that.$el.append(el);
				that.render_el();
				return that;
			});
		return this;
	},

	render_el: function()
	{
		this.renderCloud();
		this.renderKids();
		return this;
	},

	renderCloud: function () {
		var that = this;

		var model = new CloudModel();

		that.$("#CloudView-goes-here")
			.append(
				new CloudView({
					model: model,
					backend: that._backend
				})
					.render().el
			);
	},

	renderKids: function () {
		var that = this;

		var kidsCollection = new KidsCollection();

		that._backend.fetch_promise(kidsCollection);

		that.$("#KidsSettingsView-goes-here")
			.append(
				new KidsSettingsView({
					collection: kidsCollection,
					backend: that._backend
				})
					.render().el
			);

		return that;
	},

	/**
	 * @type Backend
	 */
	_backend: null,

	/**
	 * @type Deferred
	 */
	whenRendered: null

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder(
		{url: 'Settings.html', selector: "#SettingsView"}).build()

});



var SettingsWorld = WorldActions.extend(
{
	initialize: function()
	{
		this._view_promise =
			new SettingsView(
				{
					backend: this.attributes.backend
				}
			).render_promise();
	},

	view_render_promise: function()
	{
		return this._view_promise;
	},

	_view_promise: null
});

