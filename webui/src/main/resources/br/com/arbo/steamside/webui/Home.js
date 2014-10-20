"use strict";

var HomeWorld = WorldActions.extend(
	{
		sessionModel: null,
		cardTemplatePromise: null,

		initialize: function(options)
		{
			this.sessionModel = options.sessionModel;

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
	cardTemplatePromise: null,

	initialize: function(options)
	{
		this.sessionModel = options.sessionModel;

		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;

		this.backend = options.backend;
	},

	render: function ()
	{
		var continues = new ContinueGames();

		var that = this;

		var kidsMode = this.sessionModel.kidsMode();

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

		this.backend.fetch_promise(continues);

		new FavoritesView(
			{
				cardTemplatePromise: this.cardTemplatePromise,
				backend: this.backend,
				on_tag: this.on_tag,
				kidsMode: kidsMode,
				continues: continues
			}
		).render();

		return this;
	},

	on_tag: function(game, segmentWithGameCard)
	{
		TagTile.on_tag(game, segmentWithGameCard, this.backend);
	}
});



var FavoritesView = Backbone.View.extend(
{
	el: "#favorites-segment",

	events:
	{
		"click #side-link-combine": "combineClicked"
	},

	initialize: function(options)
	{
		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;

		this.backend = options.backend;
		this.on_tag = options.on_tag;
		this.kidsMode = options.kidsMode;
		this.continues = options.continues;
	},

	render: function()
	{
		var favorites = new FavoritesCollection();

		new DeckView({
			el: $('#favorites-deck'),
			cardTemplatePromise: this.cardTemplatePromise,
			collection: favorites,
			continues: this.continues,
			kidsMode: this.kidsMode,
			on_tag: this.on_tag,
			backend: this.backend
		});

		this.backend.fetch_promise(favorites);
		
		return this;
	}
});
