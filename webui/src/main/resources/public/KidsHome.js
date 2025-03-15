import {SpriteSheet} from "#steamside/spritesheet.js";

export const KidsSpriteSheet = Backbone.Model.extend(
	{
		/**
		 * @type Sprite
		 */
		card: null,

		/**
		 * @type Sprite
		 */
		greeting: null,

		initialize: function () {
			const sheet = new SpriteSheet({url: 'KidsHome.html'});
			this.card = sheet.sprite("#KidsGameCard");
			this.greeting = sheet.sprite("#KidsGreetingView");
		}
	}
);

export const KidsView = Backbone.View.extend({

	spritesKids: null,

	initialize: function(options)
	{
		this.spritesKids = options.spritesKids;
	},

	render: function ()
	{
		const that = this;

		this.$el
			.removeClass("steamside-body-background")
			.addClass("steamside-kids-body-background");

		this.$("#WelcomeToSteamside").hide();
		this.$("#search-segment").hide();
		this.$("#continues-segment").hide();
		this.$("#collections-segment").hide();
		this.$(".side-links").hide();

		this.$("#PageHeaderBannerMenu").hide();
		this.spritesKids.greeting.sprite_promise().done(function(el)
		{
			that.$("#PageHeaderBannerSection").append(el.clone());
		});

		return this;
	}

});

