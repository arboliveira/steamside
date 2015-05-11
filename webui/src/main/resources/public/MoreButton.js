"use strict";

var MoreButtonTile =
{
	tile: new Tile(
		{tileset: SteamsideTileset.tileset, selector: ".more-button"})
};

var MoreButtonView = Backbone.View.extend(
{
	hiding: true,
	deck: null,

	events:
	{
		"click" : "moreClicked"
	},

	initialize: function(options)
	{
		this.deck = options.deck;
	},

	render: function()
	{
		var that = this;
		this.whenRendered =
			MoreButtonTile.tile.el_promise.then(function(tile) {
				that.render_el(tile.clone());
				return that;
			});
		return this;
	},

	render_el: function(el)
	{
		this.$el.append(el);
		this.textRefresh();
		this.$el.fadeIn();
	},

	moreClicked: function(e)
	{
		e.preventDefault();
		this.toggle();
	},

	toggle: function()
	{
		this.deck.toggleVisibility();
		this.hiding = !this.hiding;
		this.textRefresh();
	},

	textRefresh: function()
	{
		this.$('.more-button-text').text(this.hiding ? 'more...' : 'less...');
	}
});

