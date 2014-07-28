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
		},

		tileLoad: function(whenDone)
		{
			// HomeTile.whenLoaded(whenDone);
			whenDone(null);
		},

		newView: function(tile)
		{
			return new HomeView({
				// el: tile.clone(),
				sessionModel: this.sessionModel,
				cardTemplatePromise: this.cardTemplatePromise,
				kidsTileset: this.kidsTileset
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
		tileLoad: function(whenDone)
		{
			CollectionNewTile.whenLoaded(whenDone);
		},

		newView: function(tile)
		{
			return new CollectionNewView({
				el: tile.clone()
			}).render();
		}
	}
);

var SteamClientWorld = WorldActions.extend(
	{
		tileLoad: function(whenDone)
		{
			SteamClientTile.ajaxTile(whenDone);
		},

		newView: function(tile)
		{
			return new SteamClientView({
				el: tile.clone()
			}).render();
		}
	}
);

var ExitWorld = WorldActions.extend(
	{
		tileLoad: function(whenDone)
		{
			ExitTile.ajaxTile(whenDone);
		},

		newView: function(tile)
		{
			return new ExitView({
				el: tile.clone()
			}).render();
		}
	}
);
