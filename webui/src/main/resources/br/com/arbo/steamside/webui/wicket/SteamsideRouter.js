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
        var view = new SwitchFavoritesView({
            collection: categories,
            on_category_change: on_category_change
        });

        fetch_json(categories, function () {
            that.setSecondaryView(view);
        });
    },

    collections_new: function() {   "use strict";
        var that = this;

        var callback = function(tile) {
            var view = new CollectionNewEmptyView({
                el: tile.clone()
            });
            that.setSecondaryView(view);
        };

        Tileset.tileCollectionNew(callback);
    },

    collections_edit: function(name) {   "use strict";
        var that = this;

        var apps = new SteamsideCollectionApps();

        var callback = function(tile) {
            var elTile = tile.clone();
            var view = new CollectionEditView({
                el: elTile,
                collection: apps
            });
            that.setSecondaryView(view);
            fetch_json(apps);
        };

        Tileset.tileCollectionEdit(callback);
    },

    steam_client:  function() {   "use strict";
        var that = this;

        var callback = function(tile) {
            var view = new SteamClientView({
                el: tile.clone()
            });
            that.setSecondaryView(view);
        };

        Tileset.tileSteamClient(callback);
    },

    setSecondaryView:  function(view) {   "use strict";
        var s_el = $('#secondary-view');
        s_el.empty();
        s_el.append(view.render().el);
        s_el.show();
        $('#primary-view').hide();
    }
});
