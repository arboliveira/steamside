"use strict";

var KidsSpriteSheet = Backbone.Model.extend(
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
			var sheet = new SpriteSheet({url: 'Kids.html'});
			this.card = sheet.sprite("#KidsGameCard");
			this.greeting = sheet.sprite("#KidsGreetingView");
		}
	}
);

var KidsView = Backbone.View.extend({

	spritesKids: null,

	initialize: function(options)
	{
		this.spritesKids = options.spritesKids;
	},

	render: function ()
	{
		var that = this;

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

