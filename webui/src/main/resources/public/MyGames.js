"use strict";

var MyGamesWorld = WorldActions.extend(
{
	initialize: function()
	{
		this._view_promise =
			new MyGamesView(
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

var MyGamesView = Backbone.View.extend(
{
	initialize: function(options)
	{
		this.backend = options.backend;
	},

	render_promise: function()
	{
		return this.render().whenRendered;
	},

	render: function () {
		var that = this;
		this.whenRendered =
			MyGamesView.sprite.sprite_promise().then(function(el) {
				that.$el.append(el.clone());
				that.render_el();
				return that;
			});
		return this;
	},

	render_el: function() {
		var that = this;

		that.$("#CollectionPickView").append(
			new CollectionPickView(
				{
					backend: that.backend,
					purposeless: true
				}
			).render().el
		);
	}

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder(
		{url: 'MyGames.html', selector: "#MyGamesView"}).build()

}
);
