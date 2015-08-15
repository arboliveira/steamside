"use strict";

Steamside.MyGamesWorld =
{
	nameController: 'MyGamesController',

	htmlWorld: 'MyGames.html',

	controller: function($scope, theBackend, spritesSteamside)
	{
		var spriteMoreButton = spritesSteamside.moreButton;
		var cardTemplatePromise = spritesSteamside.card.sprite_promise();

		new MyGamesView(
			{
				backend: theBackend,
				cardTemplatePromise: cardTemplatePromise,
				spriteMoreButton: spriteMoreButton
			}
		).render();
	}
};


var MyGamesView = Backbone.View.extend(
{
	el: "#MyGamesView",

	initialize: function(options)
	{
		this.backend = options.backend;
		this._cardTemplatePromise = options.cardTemplatePromise;
		this._spriteMoreButton = options.spriteMoreButton;
	},

	render: function () {
		var that = this;

		that.$("#CollectionPickView").append(
			new CollectionPickView(
				{
					cardTemplatePromise: that._cardTemplatePromise,
					spriteMoreButton: that._spriteMoreButton,
					backend: that.backend,
					purposeless: true
				}
			).render().el
		);

		that.sideshow();

		return this;
	},

	sideshow: function()
	{
		sideshow(this.$el);
	}
}
);
