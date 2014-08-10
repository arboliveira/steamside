"use strict";

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

    combineView: null,

    collection_name: null,

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

        this.combineView = new CombineView({
            el: that.$('#CombineView'),
            collection_editing: that.collection_name
        });

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
		return bar.find('.game-tile-play');
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
		button.insertAfter(play);
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

	on_add_click: function(appid)
	{
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

	mergeClicked: function(e)
	{
		e.preventDefault();

		var that = this;

		CollectionPickTile.ajaxTile(function(tile_el)
			{
				var pick_el;
				var pick = new CollectionPickView(
					{
						el: tile_el.clone(),
						merge_collection: that.collection_name,
						on_collection_pick: function(collection)
							{
								$(pick_el).hide();
								that.on_collection_merge(collection);
							}
					}
				);
				pick_el = pick.render().el;
				that.$("#collection-segment").after(pick_el);
			}
		);
	},

	on_collection_merge: function(collection)
	{
        this.combineView.renderCommandBox(collection.name());
	}

});

var Combine = Backbone.Model.extend({

    initialize: function(options)
    {
        this.set_collection_editing(options.collection_editing);
        this.set_collection_combine(options.collection_combine);
    },

    set_collection_editing: function (value)
    {
        this.set('collection_editing', value);
        this.calc_same();
    },

    set_collection_combine: function (value)
    {
        this.set('collection_combine', value);
    },

    set_combined_name: function (value)
    {
        this.set('combined_name', value);
        this.calc_same();
    },

    set_same: function( value )
    {
        this.set('same', value);
    },

    set_user_input_combined_name: function (input) {
        var name = this.nameForCombinedCollection(input);
        this.set_combined_name(name);
    },

    get_collection_editing: function ()
    {
        return this.get('collection_editing');
    },

    get_collection_combine: function ()
    {
        return this.get('collection_combine');
    },

    get_combined_name: function ()
    {
        return this.get('combined_name');
    },

    get_same: function ()
    {
        return this.get('same');
    },

    calc_same: function()
    {
        this.set_same(this.get_collection_editing() == this.get_combined_name());
    },

    nameForCombinedCollection: function(input) {
        if (input == '')
            return this.get_collection_editing() + ' and ' + this.get_collection_combine();
        return input;
    }

});

var CombineCommandBoxHintAView = Backbone.View.extend({

    initialize: function(options)
    {
        this.$('#CombineCommandHintDelete').remove();
        this.combine = options.combine;
        this.combine.on('change:combined_name', this.change_combined_name, this);
        this.combine.on('change:same', this.change_same, this);
        this.combine.on('change', this.change_all, this);
    },

    change_combined_name: function ()
    {
        this.$('#CombineCommandHintCollection').text(this.combine.get_combined_name());
    },

    change_same: function ()
    {
        var action = this.combine.get_same() ? 'Update' : 'Create';
        this.$('#CombineCommandHintAction').text(action);
    },

    change_all: function()
    {
        this.change_combined_name();
        this.change_same();
    }

});

var CombineCommandBoxHintBView = Backbone.View.extend({

    initialize: function(options)
    {
        this.combine = options.combine;
        this.combine.on('change:combined_name', this.change_combined_name, this);
        this.combine.on('change:collection_editing', this.change_collection_editing, this);
        this.combine.on('change:collection_combine', this.change_collection_combine, this);
        this.combine.on('change', this.change_all, this);
    },

    change_combined_name: function ()
    {
        this.$('#CombineCommandHintCollection').text(this.combine.get_combined_name());
    },

    change_collection_editing: function ()
    {
        this.$('#CombineCommandHintDelete1Name').text(this.combine.get_collection_editing());
    },

    change_collection_combine: function ()
    {
        this.$('#CombineCommandHintDelete2Name').text(this.combine.get_collection_combine());
    },

    change_same: function ()
    {
        var same = this.combine.get_same();
        var action = same ? 'Update' : 'Create';
        this.$('#CombineCommandHintAction').text(action);
        var el_delete = this.$('#CombineCommandHintDelete1');
        if (same) el_delete.hide(); else el_delete.show();
    },

    change_all: function()
    {
        this.change_collection_combine();
        this.change_collection_editing();
        this.change_combined_name();
        this.change_same();
    }

});

var CombineView = Backbone.View.extend({

    collection_editing: null,

    combineCommandHintTemplate: null,

    initialize: function(options)
    {
        this.collection_editing = options.collection_editing;

        this.combineCommandHintTemplate = this.$('#CombineCommandHint');
        this.combineCommandHintTemplate.remove();
    },

    renderCommandBox: function(collection_combine)
    {
        var that = this;

        CommandBoxTile.whenLoaded(function(el_CommandBox)
        {
            that.render_merge_CommandBox(el_CommandBox, collection_combine);
        });
    },

    render_merge_CommandBox: function(el_CommandBox, collection_combine)
    {
        var that = this;

        this.combine = new Combine({
            collection_editing: that.collection_editing,
            collection_combine: collection_combine
        });

        var commandBoxView = new CombineCommandBoxView({
            combine: that.combine,
            combineCommandHintTemplate: that.combineCommandHintTemplate,
            el: el_CommandBox.clone()
        });

        var targetEl = this.$el;
        targetEl.append(commandBoxView.render().el);

        commandBoxView.input_query_focus();
    }

});

var CombineCommandBoxView = Backbone.View.extend({

    commandBox: null,

    combine: null,

    mergeCommandHintAView: null,

    mergeCommandHintBView: null,

    initialize: function(options)
    {
        this.combine = options.combine;

        var combineCommandHintTemplate = options.combineCommandHintTemplate;

        this.mergeCommandHintAView = new CombineCommandBoxHintAView({
            el: combineCommandHintTemplate.clone(),
            combine: this.combine
        });

        this.mergeCommandHintBView = new CombineCommandBoxHintBView({
            el: combineCommandHintTemplate.clone(),
            combine: this.combine
        });
    },

    render: function()
    {
        var collection_editing = this.combine.get_collection_editing();
        var collection_combine = this.combine.get_collection_combine();

        var that = this;

        var combineCommandBox = new CommandBoxView({
            el: that.el,
            placeholder_text:
                ('Combine ' + collection_editing + ' with ' + collection_combine),
            on_change_input: function(input) { that.on_combine_change_input(input); },
            on_command: function(input) { that.on_merge_command(input) },
            on_command_alternate: function(input) { that.on_merge_command_alternate(input) }
        });

        combineCommandBox.render();

        this.combine.trigger('change');

        combineCommandBox.emptyCommandHints();
        combineCommandBox.appendCommandHint(this.mergeCommandHintAView.el);
        combineCommandBox.appendCommandHintAlternate(this.mergeCommandHintBView.el);

        this.commandBox = combineCommandBox;

        return this;
    },

    input_query_focus: function()
    {
        this.commandBox.input_query_focus();
    },

    on_combine_change_input: function (view, collection) {
        var input = view.input_query_val();
        this.combine.set_user_input_combined_name(input);
    }

});
