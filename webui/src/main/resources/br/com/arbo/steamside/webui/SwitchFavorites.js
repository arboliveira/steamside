"use strict";

var SwitchFavoritesCollectionsTile =
{
	tile: new Tile(
		{
			tileset: SteamsideTileset.tileset,
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
	on_category_change: null,

	events: {
		"click .back-button"         : "backButtonClicked"
	},

	initialize: function(options)
	{
		this.on_category_change = options.on_category_change;
		this.backend = options.backend;
	},

	render: function()
	{
		var that = this;

		var categories = new SteamCategoryCollection();
		this.backend.fetch_promise(categories).done(function ()
			{
				new SteamCategoriesView(
					{
						el: that.$("#switch-favorites-steam-categories-list"),
						collection: categories,
						on_category_change: that.on_category_change,
						backend: that.backend
					}
				).render();
			}
		);

		CollectionPickTile.ajaxTile(function(tile_el)
			{
				var pick = new CollectionPickView(
					{
						el: tile_el.clone(),
						backend: that.backend
					}
				);
				that.$("#CollectionPickView").append(pick.render().el);
			}
		);

		return this;
	},

	backButtonClicked: function (e) {
		e.preventDefault();
		history.back();
	}
});

