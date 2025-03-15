import {CollectionPickView} from "#steamside/CollectionPick.js";
import {sideshow} from "#steamside/Sideshow.js";

export const Steamside_MyGamesWorld =
{
	nameController: 'MyGamesController',

	htmlWorld: 'MyGames.html',

	controller: function($scope, theBackend, spritesSteamside)
	{
		const spriteMoreButton = spritesSteamside.moreButton;
		const cardTemplatePromise = spritesSteamside.card.sprite_promise();

		new MyGamesView(
			{
				backend: theBackend,
				cardTemplatePromise: cardTemplatePromise,
				spriteMoreButton: spriteMoreButton
			}
		).render();
	}
};


const MyGamesView = Backbone.View.extend(
{
	el: "#MyGamesView",

	initialize: function(options)
	{
		this.backend = options.backend;
		this._cardTemplatePromise = options.cardTemplatePromise;
		this._spriteMoreButton = options.spriteMoreButton;
	},

	render: function () {
		const that = this;

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
