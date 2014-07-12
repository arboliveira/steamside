var KidsView = Backbone.View.extend({

	tileset: null,

	initialize: function()
	{
		this.tileset = this.options.tileset;
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

		this.$("#switch-link").hide();

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
		greetings.chomp($xml);
		var tile_el = greetings.tile.clone();
		this.$("#PageHeaderBannerSection").append(tile_el);
	}
});

