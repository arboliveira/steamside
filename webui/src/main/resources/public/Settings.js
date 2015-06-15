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

		var cloudModel = new CloudModel();

		new CloudView({
			cloudModel: cloudModel,
			backend: that.backend
		}
		).render().whenRendered.done(function(view) {
				that.$("#CloudView-goes-here").append(view.el);
			});
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
		newView: function()
		{
			return new SettingsView(
				{
					backend: this.attributes.backend
				}
			);
		}
	}
);

