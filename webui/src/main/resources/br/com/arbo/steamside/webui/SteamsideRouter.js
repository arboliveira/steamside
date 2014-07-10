var Worldchanger = Backbone.Model.extend(
{
	worldbed_el: null,

	initialize: function(options) {
		this.worldbed_el = options.worldbed_el;
	},

	worldview: null,

	goWorld: function(world) {
		var that = this;

		if (this.worldview == null)
		{
			if (!world.isFront()) {
				that.worldbed_el.children().hide();
			}
		}

		world.submitForView(function(view)
		{
			that.setWorldview(view);
		});
	},

	setWorldview:  function(view) {
		if (this.worldview != null)
		{
			this.worldview.$el.slideUp();
//			this.worldview.$el.remove();
		}

		this.worldbed_el.append(view.$el);

		if (this.worldview != null) {
			view.$el.slideDown();
		}

		this.worldview = view;
	}

});

var World = Backbone.Model.extend(
{
	view: null,

	worldActions: null,

	initialize: function(options) {
		this.worldActions = options.worldActions;
	},

	submitForView: function(whenViewReady)
	{
		var that = this;

		var tileLoadDone = function(tile)
		{
			if (that.view == null)
			{
				that.view = that.newView(tile);
			}

			whenViewReady(that.view);
		};

		that.tileLoad(tileLoadDone);
	},

	respawn: function()
	{
		this.view = null;
	},

	tileLoad: function (whenDone)
	{
		return this.worldActions.tileLoad(whenDone);
	},

	newView: function(tile)
	{
		return this.worldActions.newView(tile);
	},

	isFront: function() {
		return this.worldActions.isFront();
	}
});

var WorldActions = Backbone.Model.extend(
{
	newView: function(){},

	tileLoad: function(){},

	isFront: function()
	{
		return false;
	}
}
);

var HomeWorld = WorldActions.extend(
{
	sessionModel: null,
	kidsTileset: null,

	initialize: function(options)
	{
		this.sessionModel = options.sessionModel;
		this.kidsTileset = options.kidsTileset;
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
			kidsTileset: this.kidsTileset
		}).render();
	},

	isFront: function()
	{
		return true;
	}
}
);

var CollectionsEditWorld = WorldActions.extend(
{
	collection_name: null,

	respawnWithCollection: function(collection_name) {
		this.collection_name = collection_name;
	},

	tileLoad: function(whenDone)
	{
		CollectionEditTile.whenLoaded(whenDone);
	},

	newView: function(tile)
	{
		return new CollectionEditView({
			el: tile.clone(),
			collection_name: this.collection_name
		}).render();
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

var SwitchFavoritesWorld = WorldActions.extend(
{
	on_category_change: null,

	initialize: function(options)
	{
		this.on_category_change = options.on_category_change;
	},

	tileLoad: function(whenDone)
	{
		CollectionPickTile.whenLoaded(whenDone);
	},

	newView: function(tile)
	{
		var that = this;

		return new SwitchFavoritesView({
			el: tile.clone(),
			on_category_change: function() {that.on_category_change();}
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

var SteamsideRouter = Backbone.Router.extend(
{
    routes: {
        "": "home",
        "favorites/switch": "switch_favorites",
        "collections/new": "collections_new",
        "collections/:name/edit": "collections_edit",
        "steamclient": "steam_client",
        "exit": "exit"
    },

	worldchanger: null,

	homeView: null,

	switch_favoritesView: null,

	collections_newView: null,

	worldCollectionsEdit: null,

	collections_editView: null,

	steamclientView: null,

	exitView: null,

	initialize: function(options)
	{
		var worldbed_el = $('#world');

		this.worldchanger = new Worldchanger({worldbed_el: worldbed_el});

		this.homeView = new World({
			worldActions:this.newHomeView(
				options.sessionModel, options.kidsTileset)
		});

		this.switch_favoritesView = new World({worldActions:this.newSwitch_favoritesView()});

		this.collections_newView = new World({worldActions:new CollectionsNewWorld()});

		this.worldCollectionsEdit = new CollectionsEditWorld();
		this.collections_editView = new World({worldActions:this.worldCollectionsEdit});

		this.steamclientView = new World({worldActions:new SteamClientWorld()});

		this.exitView = new World({worldActions:new ExitWorld()});
	},

	home: function()
	{
		this.worldchanger.goWorld(this.homeView);
    },

	switch_favorites: function()
	{
		this.worldchanger.goWorld(this.switch_favoritesView);
	},

	collections_new: function()
	{
		this.worldchanger.goWorld(this.collections_newView);
	},

	collections_edit: function(name)
	{
		try
		{

			/*
			 https://github.com/jashkenas/backbone/issues/2566#issuecomment-26065829
			 */
			var workaroundFirefox = decodeURIComponent(name);

			this.worldCollectionsEdit.respawnWithCollection(workaroundFirefox);

			this.collections_editView.respawn();
			this.worldchanger.goWorld(this.collections_editView);

		}
		catch (e)
		{
			var trace = printStackTrace({e: e});
			$("#ErrorMessageView").text(e + " #### " +trace);
			$("#ErrorBoxView").show();
			throw e;
		}

	},

	steam_client:  function()
	{
		this.worldchanger.goWorld(this.steamclientView);
	},

	exit:  function()
	{
		this.worldchanger.goWorld(this.exitView);
	},

	newHomeView: function(sessionModel, kidsTileset)
	{
		return new HomeWorld(
		{
			sessionModel: sessionModel,
			kidsTileset: kidsTileset
		});
	},

	newSwitch_favoritesView: function()
	{
		var router = this;

		return new SwitchFavoritesWorld(
		{
			on_category_change: function()
			{
				router.navigate("", {trigger: true});
				// TODO Refresh favorites
			}
		});
	}
});
