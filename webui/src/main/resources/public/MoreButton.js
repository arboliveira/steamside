"use strict";

var MoreButtonView = Backbone.View.extend(
{
	deck: null,

	events:
	{
		"click" : "moreClicked"
	},

	initialize: function(a)
	{
		var that = this;

		this.deck = a.deck;

		if (a.spriteMoreButton == null) {
			throw new Error("spriteMoreButton is required");
		}

		a.spriteMoreButton.sprite_promise().then(function(el) {
			that.$el.append(el.clone());
			that.render();
		});
	},

	render: function()
	{
		var showing = this.deck.tailVisibility;
		this.$('.more-button-text').text(!showing ? 'more...' : 'less...');
		this.$el.fadeIn();
		return this;
	},

	moreClicked: function(e)
	{
		e.preventDefault();
		this.deck.toggleVisibility();
		this.render();
	}
});
