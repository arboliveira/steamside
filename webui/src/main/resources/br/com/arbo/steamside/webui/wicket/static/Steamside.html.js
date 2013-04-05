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
    initialize: function() {		"use strict";
        this.continues = this.options.continues;
        this.width = this.options.width;
        this.setElement(Tileset.collectionPick().clone());
    },

    render: function () {
        return this;
    }
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

        var r = new SwitchFavoritesView().render().$el;
        var rh = $('<div></div>');
        rh.html(r.html());
        rh.dialog({title:'Switch Favorites to...', modal:true});
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
