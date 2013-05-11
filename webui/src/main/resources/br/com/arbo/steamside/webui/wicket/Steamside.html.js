var ContinueGames = Backbone.Collection.extend({
    model: Game,
    url: 'continue.json'
});

var SearchResults = Backbone.Collection.extend({
    model: Game,
    value: null,
    url: function() {
        return "search.json?query=" + this.value;
    }
});

var FavoritesCollection = Backbone.Collection.extend({
    model: Game,
    url: 'favorites.json'
});

var SearchTextView = Backbone.View.extend({
    events: {
        'submit form': 'submitSearch'
    },

    initialize: function() {                           "use strict";

    },

    submitSearch: function(e) {     "use strict";
        e.preventDefault();

        var c = this.collection;
        c.value = this.$('#input-id-text-search').val();
        fetch_json(c);
    }
});

var SessionView = Backbone.View.extend({
    el : 'body',

    initialize: function () {
        var m = this.model;
        m.bind('change',this.render, this);
        fetch_json(m);
    },

    render: function () {                "use strict";
        var m = this.model;
        var username = m.username();
        var kidsMode = m.kidsmode();

        if (kidsMode) {
            $("#page-header-navigation-bar").hide();
            $("#search-segment").hide();
            $("#favorites-segment").hide();
            $("#collections-segment").hide();
        }
        return this;
    }
});

var SwitchFavoritesView = Backbone.View.extend({
    on_category_change: null,

    events: {
        "click .back-button"         : "backButtonClicked"
    },

    initialize: function() {		"use strict";
        this.setElement(Tileset.collectionPick().clone());
        this.on_category_change = this.options.on_category_change;
    },

    render: function () {  "use strict";
        new SteamCategoriesView({
            el: this.$("#collection-pick-steam-categories-list"),
            collection: this.collection,
            on_category_change: this.on_category_change
        }).render();
        return this;
    },

    backButtonClicked: function (e) {  "use strict";
        e.preventDefault();
        history.back();
    }
});

var SteamCategoryCollection = Backbone.Collection.extend({
    model: SteamCategory,
    url: 'steam-categories.json'
});


var Steamside_html = {

    render_page: function (tileset) {     "use strict";

        new SteamsideRouter();
        // Start Backbone history a necessary step for bookmarkable URL's
        Backbone.history.start();

        var sessionModel = new SessionModel();
        var continues = new ContinueGames();
        var searchResults = new SearchResults();
        var favorites = new FavoritesCollection();

        new SessionView({model: sessionModel});

        new DeckView({
            el: $('#continues-deck'),
            collection: continues,
            session: sessionModel,
            continues: continues
        });

        new SearchTextView({
            el: $('#div-id-search-text'),
            collection: searchResults
        });

        new DeckView({
            el: $('#search-results-deck'),
            collection: searchResults,
            session: sessionModel,
            continues: continues
        });

        new DeckView({
            el: $('#favorites-deck'),
            collection: favorites,
            session: sessionModel,
            continues: continues
        });

        fetch_json(continues);
        fetch_json(favorites);

        $("#input-id-text-search").focus();
    }

};