var CollectionEditTile = {
	tile: new Tile(
		{url: 'CollectionEdit.html', selector: "#tile-collection-edit"}),

	whenLoaded: function (callback) {
		this.tile.ajaxTile(callback);
	}
};

var CollectionsEditWorld = WorldActions.extend(
	{
		collection_name: null,

		cardTemplatePromise: null,

		initialize: function(options)
		{
			if (options.cardTemplatePromise == null)
			{
				throw new Error("cardTemplatePromise is required");
			}
			this.cardTemplatePromise = options.cardTemplatePromise;
		},

		respawnWithCollection: function(collection_name) {
			this.collection_name = collection_name;
		},

		tileLoad: function(whenDone)
		{
			CollectionEditTile.whenLoaded(whenDone);
		},

		newView: function(tile)
		{
			return new CollectionEditView({
				el: tile.clone(),
				cardTemplatePromise: this.cardTemplatePromise,
				collection_name: this.collection_name
			}).render();
		}
	}
);

var SteamsideCollectionApps = Backbone.Collection.extend({
    model: Game,
	collection_name: null,
	url: function() {
		return "api/collection/collection.json?name=" + encodeURIComponent(this.collection_name);
	}
});

var CollectionEditView = Backbone.View.extend({

	events: {
		"click #side-link-merge": "mergeClicked"
	},

	cardTemplatePromise: null,

	initialize: function(options)
	{
		if (this.options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = this.options.cardTemplatePromise;

		this.collection_name = options.collection_name;
	},

	render: function()
	{
		var that = this;

		var collectionEditSearchResults = new SearchResults();
		this.collectionEditSearchResults = collectionEditSearchResults;

		CommandBoxTile.whenLoaded(function(tile) {
			that.render_search_CommandBox(tile);
		});

		// TODO Reuse same continues collection as front page?
		var continues = new ContinueGames();
		new DeckView({
			el: this.$('#collection-edit-search-results-deck'),
			cardTemplatePromise: this.cardTemplatePromise,
			collection: collectionEditSearchResults,
			continues: continues,
			on_GameCard_render: function(viewGameCard) { that.on_SearchResults_GameCard_render(viewGameCard) },
			on_tag: function(game, segmentWithGameCard) { TagTile.on_tag(game, segmentWithGameCard) }
		});

        var name = that.collection_name;
        this.$("#display-collection-name").text(name);

		var inCollection = new SteamsideCollectionApps();
        this.inCollection = inCollection;
        inCollection.collection_name = name;




		new DeckView({
            el: this.$('#games-in-collection-deck'),
			cardTemplatePromise: this.cardTemplatePromise,
            collection: inCollection,
			on_GameCard_render: function(viewGameCard) { that.on_GamesInCollection_GameCard_render(viewGameCard) },
			on_tag: function(game, segmentWithGameCard) { TagTile.on_tag(game, segmentWithGameCard) }
        });

		fetch_json(inCollection);

        return this;
    },

	render_search_CommandBox: function(tile) {
		var that = this;
		var viewCommandBox = new CommandBoxView({
			el: tile.clone(),
			placeholder_text: 'search for games',
			on_change_input: function(input) { that.on_search_input_changed(input) },
			on_command: function(input) { that.on_search_command(input) },
			on_command_alternate: function(input) { that.on_search_command_alternate(input) }
		});
		var view_el = viewCommandBox.render().el;
		var searchEl = this.$('#collection-edit-search-command-box');
		searchEl.empty();
		searchEl.append(view_el);
		var recent = this.$('#input-recent');
		recent.remove();
		var form = this.$("#form-command-box");
		form.append(recent);
		viewCommandBox.emptyCommandHints();
		viewCommandBox.input_query_focus();

        this.prepareSearchRecent();
        fetch_json(this.collectionEditSearchResults);
	},

	on_search_input_changed: function(view) {
		var input = view.input_query_val();
		if (input == '') {
            this.$('.command-hint').text('recently played');
		} else {
            this.$('.command-hint').text('search ' + input);
		}
	},

	on_search_command: function(view)
	{
		var input = view.input_query_val();
		if (input == '') {
            this.prepareSearchRecent();
		} else {
			this.collectionEditSearchResults.setQuery(input);
		}
		fetch_json(this.collectionEditSearchResults);
	},

    prepareSearchRecent: function ()
	{
        var recent = this.$('#input-recent');
        recent.attr('value', 'true');
        var form = this.$("#form-command-box");
        var q = form.serialize();
        this.collectionEditSearchResults.setQueryString(q);
    },

    on_search_command_alternate: function(view)
	{
	},

	find_play_button: function (viewGameCard)
	{
		var bar = viewGameCard.$el.find('.game-tile-command-bar');
		var play = bar.find('.game-tile-play');
		return play;
	},

	on_SearchResults_GameCard_render: function (viewGameCard) {
		var play = this.find_play_button(viewGameCard);
		var add = this.build_add_button(play, viewGameCard);
		add.insertBefore(play);
	},

	on_GamesInCollection_GameCard_render: function (viewGameCard)
	{
		var play = this.find_play_button(viewGameCard);
		var button = this.build_remove_button(play, viewGameCard);
		button.insertBefore(play);
	},

	build_add_button: function (play, viewGameCard)
	{
		var add = play.clone();
		add.text('add');
		add.removeClass('game-tile-play');

		var that = this;
		add.click(function (e) {
			e.preventDefault();
			that.on_add_click(viewGameCard.model.appid());
			// TODO UI Hide the card by event reaction instead of brute force
			viewGameCard.$el.slideUp();
		});
		return add;
	},

	build_remove_button: function (play, viewGameCard)
	{
		var button = play.clone();
		button.text('remove');
		button.removeClass('game-tile-play');

		var that = this;
		button.click(function (e) {
			e.preventDefault();
			that.on_remove_click(viewGameCard.model.appid());
			// TODO UI Hide the card by event reaction instead of brute force
			viewGameCard.$el.slideUp();
		});
		return button;
	},

	on_add_click: function(appid) {
        var name = this.collection_name;
        var aUrl = "api/collection/" + name + "/add/" + appid;

        var that = this;
        $.ajax({
            url: aUrl,
            dataType: dataTypeOf(aUrl),
            beforeSend: function(){
                // TODO display 'creating...'
            },
            complete: function(){
                fetch_json(that.inCollection);
            }
        });
	},

	on_remove_click: function(appid)
	{
		var name = this.collection_name;
		var aUrl = "api/collection/" + name + "/remove/" + appid;

		var that = this;
		$.ajax({
			url: aUrl,
			dataType: dataTypeOf(aUrl),
			beforeSend: function(){
				// TODO display 'removing...'
			},
			complete: function(){
				fetch_json(that.inCollection);
			}
		});
	},

	mergeClicked: function()
	{

	}
});
