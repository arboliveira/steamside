"use strict";

var MyGamesWorld = WorldActions.extend(
{
	newView: function()
	{
		var that = this;

		return new MyGamesView(
			{
				backend: that.attributes.backend
			}
		);
	}
});

var MyGamesView = Backbone.View.extend(
{
	initialize: function(options)
	{
		this.backend = options.backend;
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

		new CollectionPickView(
			{
				backend: that.backend
			}
		).render().whenRendered.done(function(view)
			{
				view.$('#PurposeView').hide();
				that.$("#CollectionPickView").append(view.el);
			});
	},

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder({url: 'MyGames.html', selector: "#MyGamesView"}).build(),

}
);
