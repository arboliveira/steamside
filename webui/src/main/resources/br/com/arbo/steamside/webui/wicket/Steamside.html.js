var SessionView = Backbone.View.extend({
    el : 'body',

    initialize: function () {
		this.model.bind('change', this.render, this);
    },

    render: function () {                "use strict";
        var m = this.model;

        this.$('#userName').text(m.userName());
        this.$('#version').text(m.versionOfSteamside());
        this.$('#number-of-games').text(m.gamesOwned());

        // TODO var username = m.username();
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

	continues: null,

	render: function () {  "use strict";
		var sessionModel = new SessionModel();

		var continues = new ContinueGames();
		var favorites = new FavoritesCollection();

		this.continues = continues;

		var that = this;

		sideshow(this.$el);

		new SessionView({model: sessionModel});

		new DeckView({
			el: $('#favorites-deck'),
			collection: favorites,
			continues: continues,
			on_tag: that.on_tag
		});

		new SearchView({
			el: $('#search-segment'),
			continues: continues,
			on_tag: that.on_tag
		}).render();

		fetch_json(sessionModel, function() {
			new DeckView({
				el: $('#continues-deck'),
				collection: continues,
				continues: continues,
				kidsMode: sessionModel.kidsmode(),
				on_tag: that.on_tag
			});
			fetch_json(continues);
			fetch_json(favorites);
		});

		return this;
	},

	on_tag: function(game, segmentWithGameCard) {
		var segment = $("#tag-segment");
		var clone = segment.clone();
		segmentWithGameCard.after(clone);

		var viewTag = new TagView({
			el: clone,
			game: game
		});

		viewTag.render();
	}
});

var Steamside_html = {

    render_page: function () {     "use strict";

        new SteamsideRouter();
        // Start Backbone history a necessary step for bookmarkable URL's
        Backbone.history.start();

		/*
		Load the entire tileset before you try to render anything.
		 */
		SteamsideTileset.loadTileset().done(function(){
			new SteamsideView().render();
		});
    }

};