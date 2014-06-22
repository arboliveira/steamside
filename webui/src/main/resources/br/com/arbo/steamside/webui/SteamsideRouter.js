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

	sessionModel: null,

	initialize: function(options) {
		this.sessionModel = options.sessionModel;
	},

	homeView: { view: null },

	home: function()
	{
		var sessionModel = this.sessionModel;

		this.goWorld({
			viewBox: this.homeView,

			tileLoad: function(whenDone)
			{
				// HomeTile.whenLoaded(whenDone);
				whenDone(null);
			},

			newView: function(tile)
			{
				return new HomeView({
					// el: tile.clone(),
					sessionModel: sessionModel
				}).render();
			}
		});
    },

	switch_favoritesView: { view: null },

	switch_favorites: function() {
        var that = this;

        var on_category_change = function()
		{
            that.navigate("", {trigger: true});
            // TODO Refresh favorites
        };

		this.goWorld({
			viewBox: this.switch_favoritesView,

			tileLoad: function(whenDone)
			{
				CollectionPickTile.whenLoaded(whenDone);
			},

			newView: function(tile)
			{
				return new SwitchFavoritesView({
					el: tile.clone(),
					on_category_change: on_category_change
				}).render();
			}
		});
    },

	collections_newView: { view: null },

    collections_new: function() {
		this.goWorld({
			viewBox: this.collections_newView,

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
		});
    },

	collections_editView: { view: null },

	collections_edit: function(name)
	{
		/*
		 https://github.com/jashkenas/backbone/issues/2566#issuecomment-26065829
		 */
		var workaroundFirefox = decodeURIComponent(name);

		this.goWorld({
			viewBox: this.collections_editView,

			tileLoad: function(whenDone)
			{
				CollectionEditTile.whenLoaded(whenDone);
			},

			newView: function(tile)
			{
				return new CollectionEditView({
					el: tile.clone(),
					collection_name: workaroundFirefox
				}).render();
			}
		});
    },

	steamclientView: { view: null },

    steam_client:  function()
	{
		this.goWorld({
			viewBox: this.steamclientView,

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
		});
    },

	exitView: { view: null },

    exit:  function()
	{
		this.goWorld({
			viewBox: this.exitView,

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
		});
    },

	goWorld: function(options)
	{
		var that = this;

		var whenDone = function(tile)
		{
			if (options.viewBox.view == null)
			{
				options.viewBox.view = options.newView(tile);
			}

			that.setWorldview(options.viewBox.view);
		};

		options.tileLoad(whenDone);
	},

	worldview: null,

	setWorldview:  function(view) {
		if (this.worldview != null) {
			this.worldview.$el.slideUp();
//			this.worldview.$el.remove();
		}

		$('#world').append(view.$el);

		if (this.worldview != null) {
			view.$el.slideDown();
		}

		this.worldview = view;
	}
});
