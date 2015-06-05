"use strict";

var MoreButtonView = Backbone.View.extend(
{
	hiding: true,
	deck: null,

	/**
	 * @type Sprite
	 */
	spriteMoreButton: null,

	whenRendered: null,

	events:
	{
		"click" : "moreClicked"
	},

	initialize: function(a)
	{
		this.deck = a.deck;
		this.spriteMoreButton = a.spriteMoreButton;
	},

	render: function()
	{
		var that = this;
		this.whenRendered =
			this.spriteMoreButton.sprite_promise().then(function(el) {
				that.render_el(el.clone());
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

