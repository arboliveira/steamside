var SteamsideRouter = Backbone.Router.extend({
    routes: {
        "": "home",
        "favorites/switch": "switch_favorites",
        "collections/new": "collections_new",
        "collections/:name/edit": "collections_edit",
        "steamclient": "steam_client",
        "exit": "exit"
    },

    home: function() {      "use strict";
        $('#secondary-view').hide();
        $('#primary-view').show();
    },

    switch_favorites: function() {   "use strict";
        var that = this;
        var on_category_change = function() {
            that.navigate("", {trigger: true});
            // TODO Refresh favorites
        };

		CollectionPickTile.ajaxTile(
			function(tile) {
				var view = new SwitchFavoritesView({
					el: tile.clone(),
					on_category_change: on_category_change
				});
				that.setSecondaryView(view);
			}
		);
    },

    collections_new: function() {   "use strict";
        var that = this;
		CollectionNewTile.whenLoaded(function(tile) {
            var view = new CollectionNewView({
                el: tile.clone()
            });
            that.setSecondaryView(view);
        });
    },

    collections_edit: function(name) {
        var that = this;
		CollectionEditTile.whenLoaded(function(tile) {
			var view = new CollectionEditView({
                el: tile.clone(),
                collection_name: name
            });
            that.setSecondaryView(view);
        });
    },

    steam_client:  function() {
        var that = this;
        SteamClientTile.ajaxTile(function(tile) {
            that.setSecondaryView(new SteamClientView({
                el: tile.clone()
            }));
        });
    },

    exit:  function() {
        var that = this;
        ExitTile.ajaxTile(function(tile) {
            that.setSecondaryView(new ExitView({
                el: tile.clone()
            }));
        });
    },

    setSecondaryView:  function(view) {   "use strict";
        var s_el = $('#secondary-view');
        s_el.empty();
        s_el.append(view.render().el);
        s_el.show();
        $('#primary-view').hide();
    }
});
