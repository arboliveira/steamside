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

    initialize: function() {

    },

    submitSearch: function(e) {
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

    render: function () {
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
    container: null,
    one_el: null,

    initialize: function() {		"use strict";
        this.setElement(Tileset.collectionPick().clone());
    },

    render: function () {
        this.one_el = this.$(".collection-pick-steam-category");
        this.container = this.$("#collection-pick-steam-categories-list");
        this.container.empty();

        var that = this;
        this.collection.each( function(one) {
            that.renderOneSteamCategory(one);
        });
        return this;
    },

    renderOneSteamCategory: function(one) { "use strict";
        var clone_el = this.one_el.clone();
        var button_el = clone_el.find(".collection-pick-steam-category-button");
        button_el.text(one.name());
        button_el.button();
        var icon_el = button_el.next();
        icon_el.button({
                text: false,
                icons: {
                    primary: "ui-icon-help"
                }
        });
        button_el.parent().buttonset();
        this.container.append(clone_el);
    }
});

var SteamCategoryCollection = Backbone.Collection.extend({
    model: SteamCategory,
    url: 'steam-categories.json'
});

function render_page(tileset) {

    $('#nav-steam-client').click(function(e) {
        e.preventDefault();

        var jLink = $( this );
        var aUrl = jLink.attr( "href" );
        $.ajax(
            {
                url: aUrl
            }
        );
    });

    $('#switch-link').click(function(e) {
        e.preventDefault();

        var collection = new SteamCategoryCollection();
        fetch_json(collection, function() {
            var view = new SwitchFavoritesView({collection: collection});
            view.render();
            var xml2html = $('<div></div>');
            xml2html.html(view.$el.html());
            xml2html.dialog({title:'Switch Favorites to...', modal:true});
        });
    });

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
