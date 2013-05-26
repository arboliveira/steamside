var SteamsideRouter = Backbone.Router.extend({
    routes: {
        "": "home",
        "favorites/switch": "switch_favorites",
        "collections/new": "collections_new",
        "collections/:name/edit": "collections_edit",
        "steamclient": "steam_client"
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

        var categories = new SteamCategoryCollection();
		CollectionPickTile.ajaxTile(
			function(tile) {
				var view = new SwitchFavoritesView({
					el: tile.clone(),
					collection: categories,
					on_category_change: on_category_change
				});
				fetch_json(categories, function () {
					that.setSecondaryView(view);
				});
			}
		);
    },

    collections_new: function() {   "use strict";
        var that = this;
		CollectionNewTile.ajaxTile(function(tile) {
            var view = new CollectionNewEmptyView({
                el: tile.clone()
            });
            that.setSecondaryView(view);
        });
    },

    collections_edit: function(name) {   "use strict";
        var that = this;
		CollectionEditTile.ajaxTile(function(tile) {
			var view = new CollectionEditView({
                el: tile.clone()
            });
            that.setSecondaryView(view);
        });
    },

    steam_client:  function() {   "use strict";
        var that = this;

        var callback = function(tile) {
            var view = new SteamClientView({
                el: tile.clone()
            });
            that.setSecondaryView(view);
        };

		SteamClientTile.ajaxTile(callback);
    },

    setSecondaryView:  function(view) {   "use strict";
        var s_el = $('#secondary-view');
        s_el.empty();
        s_el.append(view.render().el);
        s_el.show();
        $('#primary-view').hide();
    }
});
