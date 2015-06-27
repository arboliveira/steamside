"use strict";

var SettingsView = Backbone.View.extend({

	initialize: function(options)
	{
		var that = this;
		this.backend = options.backend;
		this.whenReadyToRender =
			SettingsView.sprite.sprite_promise().then(function(el) {
				that.$el.append(el);
				return that;
			});
	},

	render_promise: function()
	{
		return this.render().whenRendered;
	},

	render: function () {
		var that = this;
		this.whenRendered = this.whenReadyToRender.then(function(view) {
			view.render_el();
			return view;
		});
		return this;
	},

	render_el: function() {
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

	/**
	 * @type Backend
	 */
	backend: null,

	/**
	 * @type Deferred
	 */
	whenRendered: null,

	whenReadyToRender: null

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

