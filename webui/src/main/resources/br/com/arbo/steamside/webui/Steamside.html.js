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

function sideshow(element)
{
	var segments = element.find('.segment');
	var left = true;
	segments.each(function(){
		var segment = $(this);
		var header = segment.find('.side-header');
		header.removeClass('side-header-at-left');
		header.removeClass('side-header-at-right');
		var content = segment.find('.content');
		content.removeClass('content-at-right');
		content.removeClass('content-at-left');
		left = !left;
		if (left) {
			header.addClass('side-header-at-left');
			content.addClass('content-at-right');
		} else {
			header.addClass('side-header-at-right');
			content.addClass('content-at-left');
		}
	});
}

var HomeView = Backbone.View.extend({
	el: "#primary-view",

	sessionModel: null,
	kidsTileset: null,
	cardTemplatePromise: null,

	initialize: function(options) {
		this.sessionModel = options.sessionModel;
		this.kidsTileset = options.kidsTileset;

		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;
		this.backend = options.backend;
	},

	render: function ()
	{
		var continues = new ContinueGames();
		var favorites = new FavoritesCollection();

		var that = this;

		var kidsMode = this.sessionModel.kidsmode();

		if (!kidsMode) {
			new DeckView({
				el: $('#continues-deck'),
				cardTemplatePromise: this.cardTemplatePromise,
				collection: continues,
				continues: continues,
				kidsMode: this.sessionModel.kidsmode(),
				on_tag: that.on_tag,
				backend: that.backend
			});
		}

		if (!kidsMode)
		{
			new SearchView({
				el: $('#search-segment'),
				cardTemplatePromise: this.cardTemplatePromise,
				continues: continues,
				on_tag: that.on_tag,
				backend: that.backend
			}).render();
		}

		if (true || !kidsMode)
		{
			new DeckView({
				el: $('#favorites-deck'),
				cardTemplatePromise: this.cardTemplatePromise,
				collection: favorites,
				continues: continues,
				kidsMode: this.sessionModel.kidsmode(),
				on_tag: that.on_tag,
				backend: that.backend
			});

			this.backend.fetch_fetch_json(favorites);
		}

		this.backend.fetch_fetch_json(continues);

		sideshow(this.$el);

		return this;
	},

	on_tag: function(game, segmentWithGameCard)
	{
		TagTile.on_tag(game, segmentWithGameCard, this.backend);
	}

});

var SteamsideView = Backbone.View.extend({

	el: "body",

	sessionModel: null,
	cardTemplatePromise: null,
	kidsTileset: null,

	initialize: function(options)
	{
		this.sessionModel = options.sessionModel;
		this.cardTemplatePromise = options.cardTemplatePromise;
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
		var kids = this.sessionModel.kidsmode();

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

var Steamside_html = {

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

			var kidsMode = sessionModel.kidsmode();

			var cardTemplatePromise = that.buildCardTemplatePromise(kidsMode, kidsTileset, steamsideTileset);

			new SteamsideRouter({
				sessionModel: sessionModel,
				cardTemplatePromise: cardTemplatePromise,
				kidsTileset: kidsTileset,
				backend: backend
			});

			/*
			 Load the entire tileset before you try to render anything.
			 This includes history start, which loads the main page.
			 */
			SteamsideTileset.loadTileset();

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