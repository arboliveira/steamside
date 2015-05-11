"use strict";

var KidsTilePromise =
{
	buildCardTemplatePromise: function(kidsTileset)
	{
		var cardTile = new Tile(
			{
				tileset: kidsTileset,
				selector: "#KidsGameCard"
			}
		);

		return cardTile.el_promise;
	}
}

var KidsView = Backbone.View.extend({

	tileset: null,

	initialize: function(options)
	{
		this.tileset = options.tileset;
	},

	render: function ()
	{
		this.$el
			.removeClass("steamside-body-background")
			.addClass("steamside-kids-body-background");

		this.$("#page-header-navigation-bar").hide();
		this.$("#search-segment").hide();
		this.$("#continues-segment").hide();
		//this.$("#favorites-segment").hide();
		this.$("#collections-segment").hide();

		this.$(".side-links").hide();

		var that = this;

		this.tileset.promise.done(function(xml)
		{
			that.on_tileset_done($(xml));
		});

		return this;
	},

	on_tileset_done: function($xml)
	{
		var greetings = new Tile({selector: "#KidsGreetingView"});
		var tile_el = greetings.chomp_el($xml).clone();
		this.$("#PageHeaderBannerSection").append(tile_el);
	}
});

