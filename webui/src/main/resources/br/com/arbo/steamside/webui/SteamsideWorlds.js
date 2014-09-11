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

var CollectionsNewWorld = WorldActions.extend(
	{
		initialize: function(options)
		{
			this.backend = options.backend;
		},

		tileLoad: function(whenDone)
		{
			CollectionNewTile.whenLoaded(whenDone);
		},

		newView: function(tile)
		{
			return new CollectionNewView({
				el: tile.clone(),
				backend: this.backend
			}).render();
		}
	}
);

var SteamClientWorld = WorldActions.extend(
	{
		initialize: function(options)
		{
			this.backend = options.backend;
		},

		tileLoad: function(whenDone)
		{
			SteamClientTile.ajaxTile(whenDone);
		},

		newView: function(tile)
		{
			return new SteamClientView(
				{
					el: tile.clone(),
					backend: this.backend
				}
			).render();
		}
	}
);

var ExitWorld = WorldActions.extend(
	{
		initialize: function(options)
		{
			this.backend = options.backend;
		},

		tileLoad: function(whenDone)
		{
			ExitTile.ajaxTile(whenDone);
		},

		newView: function(tile)
		{
			return new ExitView({
				el: tile.clone(),
				backend: this.backend
			}).render();
		}
	}
);
