"use strict";

var HomeWorld = WorldActions.extend(
	{
		sessionModel: null,
		kidsTileset: null,
		cardTemplatePromise: null,

		initialize: function(options)
		{
			this.sessionModel = options.sessionModel;
			this.kidsTileset = options.kidsTileset;

			if (options.cardTemplatePromise == null)
			{
				throw new Error("cardTemplatePromise is required");
			}
			this.cardTemplatePromise = options.cardTemplatePromise;
			this.backend = options.backend;
		},

		tileLoad: function(whenDone)
		{
			// HomeTile.whenLoaded(whenDone);
			whenDone(null);
		},

		newView: function(/*tile*/)
		{
			return new HomeView({
				// el: tile.clone(),
				sessionModel: this.sessionModel,
				cardTemplatePromise: this.cardTemplatePromise,
				kidsTileset: this.kidsTileset,
				backend: this.backend
			}).render();
		},

		isFront: function()
		{
			return true;
		}
	}
);

var HomeView = Backbone.View.extend(
{
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

		if (!kidsMode)
		{
			new DeckView({
				el: $('#continues-deck'),
				cardTemplatePromise: that.cardTemplatePromise,
				collection: continues,
				continues: continues,
				kidsMode: kidsMode,
				on_tag: that.on_tag,
				backend: that.backend
			});

			new SearchView(
				{
					el: $('#search-segment'),
					cardTemplatePromise: this.cardTemplatePromise,
					continues: continues,
					on_tag: that.on_tag,
					backend: that.backend
				}
			).render();
		}

		new DeckView({
			el: $('#favorites-deck'),
			cardTemplatePromise: that.cardTemplatePromise,
			collection: favorites,
			continues: continues,
			kidsMode: kidsMode,
			on_tag: that.on_tag,
			backend: that.backend
		});

		this.backend.fetch_promise(favorites);

		this.backend.fetch_promise(continues);

		return this;
	},

	on_tag: function(game, segmentWithGameCard)
	{
		TagTile.on_tag(game, segmentWithGameCard, this.backend);
	}

});

