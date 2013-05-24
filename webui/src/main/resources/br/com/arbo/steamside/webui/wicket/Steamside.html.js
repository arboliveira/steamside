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

	render: function() {
		this.$el.empty();

		var that = this;

		var on_command = function(input) {
			that.doSearch(input);
		}

		SteamsideTileset.tileCommandBox(
			function(tile) {
				var view = new CommandBoxView({
					el: tile.clone(),
					on_command: on_command,
					placeholder_text: 'game or command'
				});
				that.$el.append(view.render().el);
			}
		);

		return this;
	},

    doSearch: function(input) {     "use strict";
        var c = this.collection;
        c.value = input;
        fetch_json(c);
    }
});

var SessionView = Backbone.View.extend({
    el : 'body',

    initialize: function () {
		this.model.bind('change', this.render, this);
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
        this.setElement(SteamsideTileset.collectionPick().clone());
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

function sideshow(element) {
	var segments = element.find('.segment');
	var left = true;
	segments.each(function(){
		var segment = $(this);
		var header = segment.find('.side-header');
		header.removeClass('side-header-at-left');
		header.removeClass('side-header-at-right');
		var content = segment.find('.content');
		content.removeClass('content-at-right');
		content.removeClass('content-at-left');
		left = !left;
		if (left) {
			header.addClass('side-header-at-left');
			content.addClass('content-at-right');
		} else {
			header.addClass('side-header-at-right');
			content.addClass('content-at-left');
		}
	});
}

var SteamsideView = Backbone.View.extend({

	el: "body",

	render: function () {  "use strict";
		var sessionModel = new SessionModel();
		var continues = new ContinueGames();
		var searchResults = new SearchResults();
		var favorites = new FavoritesCollection();

		sideshow(this.$el);

		new SessionView({model: sessionModel});

		new DeckView({
			el: $('#continues-deck'),
			collection: continues,
			continues: continues
		});

		new SearchTextView({
			el: $('#div-id-search-text'),
			collection: searchResults
		}).render();

		new DeckView({
			el: $('#search-results-deck'),
			collection: searchResults,
			continues: continues
		});

		new DeckView({
			el: $('#favorites-deck'),
			collection: favorites,
			continues: continues
		});

		fetch_json(sessionModel);
		fetch_json(continues);
		fetch_json(favorites);

		$("#input-id-text-search").focus();

		return this;
	}
});

var Steamside_html = {

    render_page: function (tileset) {     "use strict";

        new SteamsideRouter();
        // Start Backbone history a necessary step for bookmarkable URL's
        Backbone.history.start();

		new SteamsideView().render();
    }

};