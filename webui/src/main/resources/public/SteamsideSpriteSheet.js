"use strict";

var SteamsideSpriteSheet = Backbone.Model.extend(
	{
		/**
		 * @public
		 * @type Sprite
		 */
		card: null,

		/**
		 * @public
		 * @type Sprite
		 */
		moreButton: null,

		initialize: function () {
			var sheet = new SpriteSheet({url: 'tileset.html'});
			this.card = sheet.sprite("#game-tile");
			this.moreButton = sheet.sprite("#MoreButton");
		}
	}
);

