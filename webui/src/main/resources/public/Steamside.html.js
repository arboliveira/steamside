"use strict";

var SessionView = Backbone.View.extend({
    el : 'body',

    initialize: function () {
		this.model.bind('change', this.render, this);
    },

    render: function ()
	{
        var m = this.model;

        this.$('#userName').text(m.userName());
        this.$('#version').text(m.versionOfSteamside());
        this.$('#number-of-games').text(m.gamesOwned());

        return this;
    }
});

var SteamsideView = Backbone.View.extend({

	el: "body",

	sessionModel: null,
	spritesKids: null,

	initialize: function(options)
	{
		this.sessionModel = options.sessionModel;
		this.spritesKids = options.spritesKids;
	},

	render: function ()
	{
		var sessionModel = this.sessionModel;

		this.applyKidsMode();

		new SessionView({model: sessionModel}).render();

		return this;
	},

	applyKidsMode: function()
	{
		var kids = this.sessionModel.kidsMode();

		if (!kids) {
			this.$("#KidsModeIndicator").hide();
			return;
		}

		new KidsView({
			el: this.$el,
			username: this.sessionModel.userName(),
			spritesKids: this.spritesKids
		}).render();
	}
});

var Steamside_html =
{
    render_page: function ()
	{
		window.onerror = function errorHandler(msg, url, line, col, error)
		{
			ErrorHandler.explode(error);
			throw error;
		};

		var backend = new Backend();

		var sessionModel = new SessionModel();

		backend.fetch_promise(sessionModel).done(function()
		{
			backend.set_backoff(sessionModel.backoff());

			var spritesKids = new KidsSpriteSheet();

			var kidsMode = sessionModel.kidsMode();

			var spritesSteamside = new SteamsideSpriteSheet();

			var cardTemplatePromise;
			if (kidsMode)
			{
				cardTemplatePromise = spritesKids.card.sprite_promise();
			}
			else {
				cardTemplatePromise =  spritesSteamside.card.sprite_promise();
			}

			new SteamsideRouter({
				sessionModel: sessionModel,
				cardTemplatePromise: cardTemplatePromise,
				spriteMoreButton: spritesSteamside.moreButton,
				backend: backend
			});

			// Start Backbone history a necessary step for bookmarkable URL's
			Backbone.history.start();

			new SteamsideView({
				sessionModel: sessionModel,
				cardTemplatePromise: cardTemplatePromise,
				spritesKids: spritesKids
			}).render();
		}
		);
    }
};
