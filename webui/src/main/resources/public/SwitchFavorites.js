"use strict";

var SwitchFavoritesWorld = WorldActions.extend(
{
	newView: function()
	{
		var that = this;

		return new SwitchFavoritesView(
			{
				on_category_change: function() {that.attributes.on_category_change();},
				backend: that.attributes.backend
			}
		);
	}
});

var SwitchFavoritesView = Backbone.View.extend(
{
	events: {
		"click .back-button"         : "backButtonClicked"
	},

	initialize: function(options)
	{
		this.backend = options.backend;
	},

	render: function () {
		var that = this;
		this.whenRendered =
			SwitchFavoritesView.sprite.sprite_promise().then(function(el) {
				that.render_el(el.clone());
				return that;
			});
		return this;
	},

	render_el: function(el) {
		this.$el.append(el);

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

	backButtonClicked: function (e) {
		e.preventDefault();
		history.back();
	}

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder({url: 'SwitchFavorites.html', selector: "#switch-favorites-tile"}).build(),

}
);
