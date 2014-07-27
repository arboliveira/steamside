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

	cardTemplatePromise: null,

	initialize: function(options)
	{
		var worldbed_el = $('#world');

		if (options.cardTemplatePromise == null)
		{
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;

		this.worldchanger = new Worldchanger({worldbed_el: worldbed_el});

		this.homeView = new World({
			worldActions:this.newHomeView(
				options.sessionModel, options.kidsTileset)
		});

		this.switch_favoritesView = new World({worldActions:this.newSwitch_favoritesView()});

		this.collections_newView = new World({worldActions:new CollectionsNewWorld()});

		this.worldCollectionsEdit = new CollectionsEditWorld({cardTemplatePromise: this.cardTemplatePromise});
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
			ErrorHandler.explode(e);
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
			cardTemplatePromise: this.cardTemplatePromise,
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
