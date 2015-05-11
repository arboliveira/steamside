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
	kidsTileset: null,

	initialize: function(options)
	{
		this.sessionModel = options.sessionModel;
		this.kidsTileset = options.kidsTileset;
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
			tileset: this.kidsTileset
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

		var that = this;

		backend.fetch_promise(sessionModel).done(function()
		{
			backend.set_backoff(sessionModel.backoff());

			var kidsTileset = new Tileset({url: 'Kids.html'});

			var steamsideTileset = SteamsideTileset.tileset;

			var kidsMode = sessionModel.kidsMode();

			var cardTemplatePromise = that.buildCardTemplatePromise(kidsMode, kidsTileset, steamsideTileset);

			new SteamsideRouter({
				sessionModel: sessionModel,
				cardTemplatePromise: cardTemplatePromise,
				kidsTileset: kidsTileset,
				backend: backend
			});

			// Start Backbone history a necessary step for bookmarkable URL's
			Backbone.history.start();

			new SteamsideView({
				sessionModel: sessionModel,
				cardTemplatePromise: cardTemplatePromise,
				kidsTileset: kidsTileset
			}).render();
		}
		);
    },

	buildCardTemplatePromise: function(kidsMode, kidsTileset, steamsideTileset)
	{
		if (kidsMode)
		{
			return KidsTilePromise.buildCardTemplatePromise(kidsTileset);
		}

		return GameTilePromise.buildCardTemplatePromise(steamsideTileset);
	}
};
