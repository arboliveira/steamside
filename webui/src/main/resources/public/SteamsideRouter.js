"use strict";

var SteamsideRouter = Backbone.Router.extend(
{
    routes: {
        "": "home",
        "mygames": "my_games",
        "collections/new": "collections_new",
        "collections/:name/edit": "collections_edit",
        "steamclient": "steam_client",
		"settings": "settings",
        "exit": "exit"
    },

	worldchanger: null,

	homeView: null,

	my_gamesView: null,

	collections_newView: null,

	worldCollectionsEdit: null,

	collections_editView: null,

	steamclientView: null,
	settingsWorld: null,
	exitView: null,

	cardTemplatePromise: null,

	/**
	 * @type Sprite
	 */
	spriteMoreButton: null,

	backend: null,

	initialize: function(options)
	{
		var worldbed_el = $('#world');

		if (options.cardTemplatePromise == null)
		{
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;
		this.spriteMoreButton = options.spriteMoreButton;
		this.backend = options.backend;

		this.worldchanger = new Worldchanger({worldbed_el: worldbed_el});

		this.homeView = new World({
			worldActions: this.newHomeView(options.sessionModel)
		});

		this.my_gamesView = new World({worldActions:this.newMy_gamesView()});

		this.collections_newView = new World(
			{
				worldActions: new CollectionsNewWorld(
					{
						backend: this.backend
					}
				)
			}
		);

		this.worldCollectionsEdit = new CollectionsEditWorld(
			{
				cardTemplatePromise: this.cardTemplatePromise,
				spriteMoreButton: this.spriteMoreButton,
				backend: this.backend
			});
		this.collections_editView = new World({worldActions:this.worldCollectionsEdit});

		this.steamclientView = new World(
			{
				worldActions: new SteamClientWorld(
					{
						backend: this.backend
					}
				)
			}
		);

		this.settingsWorld = new World(
			{
				worldActions: new SettingsWorld(
					{
						backend: this.backend
					}
				)
			}
		);

		this.exitView = new World({
			worldActions:new ExitWorld(
				{
					backend: this.backend,
					sessionModel: options.sessionModel
				}
			)
		});
	},

	home: function()
	{
		var that = this;

		this.worldchanger.goWorld(this.homeView,
			function()
			{
				var searchView = that.homeView.view.searchView;
				if (searchView == null) return;

				searchView.whenRendered.done(function(view)
				{
					searchView.command_box_input_query_focus();
				});
			}
		);
    },

	my_games: function()
	{
		this.worldchanger.goWorld(this.my_gamesView);
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

	settings:  function()
	{
		this.worldchanger.goWorld(this.settingsWorld);
	},

	exit:  function()
	{
		this.worldchanger.goWorld(this.exitView);
	},

	newHomeView: function(sessionModel)
	{
		return new HomeWorld(
		{
			sessionModel: sessionModel,
			cardTemplatePromise: this.cardTemplatePromise,
			spriteMoreButton: this.spriteMoreButton,
			backend: this.backend
		});
	},

	newMy_gamesView: function()
	{
		var router = this;

		return new MyGamesWorld(
		{
			backend: router.backend
		});
	}
});
