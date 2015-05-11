"use strict";

var SwitchFavoritesCollectionsTile =
{
	tile: new Tile(
		{
			url: 'SwitchFavorites.html',
			selector: "#switch-favorites-tile"
		})
};

var SwitchFavoritesWorld = WorldActions.extend(
{
	on_category_change: null,

	initialize: function(options)
	{
		this.on_category_change = options.on_category_change;
		this.backend = options.backend;
	},

	tileLoad: function(whenDone)
	{
		SwitchFavoritesCollectionsTile.tile.el_promise.done(whenDone);
	},

	newView: function(tile)
	{
		var that = this;

		return new SwitchFavoritesView(
			{
				el: tile.clone(),
				on_category_change: function() {that.on_category_change();},
				backend: that.backend
			}
		).render();
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

	render: function()
	{
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

		return this;
	},

	backButtonClicked: function (e) {
		e.preventDefault();
		history.back();
	}
});
