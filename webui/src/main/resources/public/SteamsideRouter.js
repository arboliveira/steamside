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

	homeWorld: null,

	my_gamesView: null,

	collections_newView: null,

	worldActions_CollectionsEdit: null,

	collections_editWorld: null,

	steamclientView: null,
	settingsWorld: null,
	exitWorld: null,

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

		this.worldActions_Home = this.newHomeWorldActions(options.sessionModel);
		this.homeWorld = new World({worldActions: this.worldActions_Home});

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

		this.worldActions_CollectionsEdit = new CollectionsEditWorld(
			{
				cardTemplatePromise: this.cardTemplatePromise,
				spriteMoreButton: this.spriteMoreButton,
				backend: this.backend
			});
		this.collections_editWorld = new World({worldActions:this.worldActions_CollectionsEdit});

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

		this.worldActions_Exit = new ExitWorld(
			{
				backend: this.backend,
				sessionModel: options.sessionModel
			}
		);
		this.exitWorld = new World({worldActions: this.worldActions_Exit});
	},

	home: function()
	{
		var that = this;

		this.worldchanger.goWorld(this.homeWorld,
			function(afterwardsArgument)
			{
				that.worldActions_Home.view_render_promise().done(function(view_Home)
					{
						view_Home.searchView_whenRendered(function(searchView)
						{
							searchView.command_box_input_query_focus();
						});
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

			this.worldActions_CollectionsEdit.resetWithCollection(workaroundFirefox);

			this.collections_editWorld.respawn();
			this.worldchanger.goWorld(this.collections_editWorld);

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
		this.worldActions_Exit.exit();

		this.worldchanger.goWorld(this.exitWorld);
	},

	newHomeWorldActions: function(sessionModel)
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
