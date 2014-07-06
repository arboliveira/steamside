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

var HomeView = Backbone.View.extend({
	el: "#primary-view",

	sessionModel: null,
	kidsTileset: null,

	initialize: function(options) {
		this.sessionModel = options.sessionModel;
		this.kidsTileset = options.kidsTileset;
	},

	render: function ()
	{
		var continues = new ContinueGames();
		var favorites = new FavoritesCollection();

		var that = this;

		var kidsMode = this.sessionModel.kidsmode();

		var cardTemplatePromise;

		if (kidsMode) {
			cardTemplatePromise = this.kidsTileset.promise.then(function(xml)
			{
				var cardTile = new Tile({selector: "#KidsGameCard"});
				cardTile.chomp($(xml));
				return cardTile.tile;
			});
		}
		else
		{
			cardTemplatePromise = SteamsideTileset.tileset.promise.then(function(xml)
			{
				var cardTile = new Tile({selector: ".game-tile"});
				cardTile.chomp($(xml));
				return cardTile.tile;
			});
		}

		new DeckView({
			el: $('#continues-deck'),
			cardTemplatePromise: cardTemplatePromise,
			collection: continues,
			continues: continues,
			kidsMode: this.sessionModel.kidsmode(),
			on_tag: that.on_tag
		});

		fetch_json(continues);

		if (!kidsMode) {
			new DeckView({
				el: $('#favorites-deck'),
				cardTemplatePromise: cardTemplatePromise,
				collection: favorites,
				continues: continues,
				on_tag: that.on_tag
			});

			new SearchView({
				el: $('#search-segment'),
				cardTemplatePromise: cardTemplatePromise,
				continues: continues,
				on_tag: that.on_tag
			}).render();

			fetch_json(favorites);
		}

		sideshow(this.$el);

		return this;
	},

	on_tag: function(game, segmentWithGameCard)
	{
		TagTile.on_tag(game, segmentWithGameCard);
	}

});

var SteamsideView = Backbone.View.extend({

	el: "body",

	sessionModel: null,
	kidsTileset: null,

	initialize: function()
	{
		this.sessionModel = this.options.sessionModel;
		this.kidsTileset = this.options.kidsTileset;
	},

	render: function ()
	{
		var sessionModel = this.sessionModel;

		this.applyKidsMode();

		new SessionView({model: sessionModel}).render();

		this.$el.show();

		return this;
	},

	applyKidsMode: function(kidsMode)
	{
		var kids = this.sessionModel.kidsmode();

		if (!kids) {
			this.$("#KidsModeIndicator").hide();
			return;
		}

		new KidsView({
			el: this.$el, username: this.sessionModel.userName(),
			tileset: this.kidsTileset
		}).render();
	}
});

var Steamside_html = {

    render_page: function ()
	{
		var sessionModel = new SessionModel();

		fetch_json(sessionModel, function()
		{
			var kidsTileset = new Tileset({url: 'Kids.html'});

			new SteamsideRouter({
				sessionModel: sessionModel,
				kidsTileset: kidsTileset
			});

			/*
			   Load the entire tileset before you try to render anything.
			   This includes history start, which loads the main page.
			 */
			SteamsideTileset.loadTileset().done(function()
			{
				// Start Backbone history a necessary step for bookmarkable URL's
				Backbone.history.start();

				new SteamsideView({
					sessionModel: sessionModel,
					kidsTileset: kidsTileset
				}).render();
			});

		});

    }

};