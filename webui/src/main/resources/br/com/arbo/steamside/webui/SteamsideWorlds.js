"use strict";

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
