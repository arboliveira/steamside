import {TagStickerView} from "#steamside/TagStickerView.js";
import {TagAGameView} from "#steamside/TagAGameView.js";
import {SearchResults} from "#steamside/Search.js";
import {CommandBoxView} from "#steamside/CommandBox.js";
import {CollectionPickView} from "#steamside/CollectionPick.js";
import {ContinueGames, DeckView} from "#steamside/GameCardDeck.js";
import {SpriteSheet} from "#steamside/spritesheet.js";

const CollectionEditSpriteSheet = Backbone.Model.extend(
{
	/**
	 * @public
	 * @type Sprite
	 */
	edit: null,

	/**
	 * @public
	 * @type Sprite
	 */
	view: null,

	initialize: function () {
		const sheet = new SpriteSheet({url: 'CollectionEdit.html'});
		try {
			this.edit = sheet.sprite("#tile-collection-edit");
			this.view = sheet.sprite("#CollectionView");
		}
		finally {
			sheet.dispose();
		}
	}
});


const CollectionEditSpriteSheetSingleton = {
	sprites: new CollectionEditSpriteSheet()
};


export const CollectionEditView = Backbone.View.extend({

	events:
	{
		"click #side-link-combine": "combineClicked"
	},

	initialize: function(options)
	{
		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.simplified = options.simplified;
		this.cardTemplatePromise = options.cardTemplatePromise;
		this.spriteMoreButton = options.spriteMoreButton;
		this.backend = options.backend;
		this._inventory = options.inventory;
		this._tag = options.tag;
	},

	render: function () {
		const that = this;
		this.whenRendered =
			CollectionEditView.sprite.sprite_promise().then(function(el) {
				that.$el.append(el.clone());
				that.render_el();
				return that;
			});
		return that;
	},

	render_el: function()
	{
		const that = this;

		// TODO Reuse same continues collection as front page?
		const continues = new ContinueGames();

		this.combineView = new CombineView({
			backend: that.backend,
            el: that.$('#CombineView'),
            collection_editing: that._tag.name()
        });

		this.combine_purpose_el = this.$("#CombinePurposeView");
		this.combine_purpose_el.remove();

		const tag = that._tag;

		this.$("#TagSticker").empty().append(
			new TagStickerView({
				model: tag
			})
				.render().el
		);

		//this.$("#display-collection-name").text(that._tag.name());

		const inCollection = that._inventory;

		new DeckView({
            el: this.$('#games-in-collection-deck'),
			cardTemplatePromise: this.cardTemplatePromise,
			spriteMoreButton: that.spriteMoreButton,
            collection: inCollection,
			continues: continues,
			on_GameCard_render: function(viewGameCard) { that.on_GamesInCollection_GameCard_render(viewGameCard) },
			on_tag: function(game, segmentWithGameCard) { that.on_game_card_tag(game, segmentWithGameCard) },
			backend: this.backend
        });

		this.backend.fetch_fetch_json(inCollection);

		if (this.simplified)
		{
			this.$("#add-games-segment").remove();
		}
		else
		{
			this.renderSearch(continues);
		}
	},

	renderSearch: function (continues) {
		const that = this;

		const collectionEditSearchResults = new SearchResults();
		this.collectionEditSearchResults = collectionEditSearchResults;

		new CommandBoxView(
			{
				placeholder_text: 'search for games',
				on_change_input: function (input) {
					that.on_search_input_changed(input)
				},
				on_command: function (input) {
					that.on_search_command(input)
				},
				on_command_alternate: function (input) {
					that.on_search_command_alternate(input)
				}
			}
		).render()
			.whenRendered.done(function (view) {
				that.render_search_CommandBox(view);
			});

		new DeckView({
			el: this.$('#collection-edit-search-results-deck'),
			cardTemplatePromise: this.cardTemplatePromise,
			spriteMoreButton: that.spriteMoreButton,
			collection: collectionEditSearchResults,
			continues: continues,
			on_GameCard_render: function (viewGameCard) {
				that.on_SearchResults_GameCard_render(viewGameCard)
			},
			on_tag: function (game, segmentWithGameCard) {
				that.on_game_card_tag(game, segmentWithGameCard)
			},
			backend: this.backend
		});
	},

	on_game_card_tag: function(game, segmentWithGameCard) {
		const that = this;
		const view = new TagAGameView({
				game: game,
				cardTemplatePromise: that.cardTemplatePromise,
				backend: that.backend
			}
		).render();
		segmentWithGameCard.after(view.$el);
		$('html, body').scrollTop(view.$el.offset().top);
	},

	render_search_CommandBox: function(viewCommandBox) {
		const view_el = viewCommandBox.el;
		const searchEl = this.$('#collection-edit-search-command-box');
		searchEl.empty();
		searchEl.append(view_el);
		const recent = this.$('#input-recent');
		recent.remove();
		const form = this.$("#form-command-box");
		form.append(recent);
		viewCommandBox.input_query_focus();

        this.prepareSearchRecent();
        this.backend.fetch_fetch_json(this.collectionEditSearchResults);
	},

	on_search_input_changed: function(view) {
		const input = view.input_query_val();
		const find = view.$el.find('#command-hint');
		if (input === '') {
			find.text('recently played');
		} else {
            find.text('search ' + input);
		}
	},

	on_search_command: function(view)
	{
		const input = view.input_query_val();
		if (input === '') {
            this.prepareSearchRecent();
		} else {
			this.collectionEditSearchResults.setQuery(input);
		}
		this.backend.fetch_fetch_json(this.collectionEditSearchResults);
	},

    prepareSearchRecent: function ()
	{
        const recent = this.$('#input-recent');
        recent.attr('value', 'true');
        const form = this.$("#form-command-box");
        const q = form.serialize();
        this.collectionEditSearchResults.setQueryString(q);
    },

    on_search_command_alternate: function(view)
	{
	},

	find_play_button: function (viewGameCard)
	{
		const bar = viewGameCard.$el.find('.game-tile-command-bar');
		return bar.find('.game-tile-play');
	},

	on_SearchResults_GameCard_render: function (viewGameCard) {
		const play = this.find_play_button(viewGameCard);
		const add = this.build_add_button(play, viewGameCard);
		add.insertBefore(play);
	},

	on_GamesInCollection_GameCard_render: function (viewGameCard)
	{
		const play = this.find_play_button(viewGameCard);
		const button = this.build_remove_button(play, viewGameCard);
		button.insertAfter(play);
	},

	build_add_button: function (play, viewGameCard)
	{
		const add = play.clone();
		add.text('add');
		add.removeClass('game-tile-play');

		const that = this;
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
		const button = play.clone();
		button.text('remove');
		button.removeClass('game-tile-play');

		const that = this;
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
		const that = this;

		const name = that._tag.name();
        const aUrl = "api/collection/" + name + "/add/" + appid;

		// TODO display 'adding...'
		/*
		 beforeSend: function(){
		 },
		 */

		that.backend.ajax_ajax_promise(aUrl)
			.done(function()
			{
                that.backend.fetch_promise(that._inventory);
	        });
	},

	on_remove_click: function(appid)
	{
		const that = this;

		const name = that._tag.name();
		const aUrl = "api/collection/" + name + "/remove/" + appid;

		// TODO display 'removing...'
		/*
		 beforeSend: function(){
		 },
		 */

		that.backend.ajax_ajax_promise(aUrl)
			.done(function()
			{
				that.backend.fetch_promise(that._inventory);
			});
	},

	combineClicked: function(e)
	{
		e.preventDefault();

		const that = this;

		const viewCollectionPick = new CollectionPickView(
			{
				purpose_el: that.build_combine_purpose(),
				on_collection_pick: function (collection) {
					viewCollectionPick.remove();
					that.on_collection_combine(collection);
				},
				backend: that.backend
			})
			.render();

		that.$("#collection-segment").after(viewCollectionPick.el);
	},

	build_combine_purpose: function()
	{
		const el_combine_purpose = this.combine_purpose_el.clone();

		el_combine_purpose.find('#CombineCollectionName')
			.text(this._tag.name());

		return el_combine_purpose;
	},

	on_collection_combine: function(collection)
	{
        this.combineView.renderCommandBox(collection.name());
	},

	/**
	 * @type Sprite
	 */
	spriteMoreButton: null,

	cardTemplatePromise: null,
	combineView: null,
	combine_purpose_el: null,
	_inventory: null,
	_tag: null,
	backend: null,
	simplified: false,
	whenRendered: null

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: CollectionEditSpriteSheetSingleton.sprites.edit

});


const Combine = Backbone.Model.extend({

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
		this.calc_same();
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
        const name = this.nameForCombinedCollection(input);
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

	is_same_as_editing: function()
	{
		const combinedName = this.get_combined_name();
		const collectionEditing = this.get_collection_editing();
		return combinedName === collectionEditing;
	},

	is_same_as_combine: function()
	{
		const combinedName = this.get_combined_name();
		const collectionCombine = this.get_collection_combine();
		return combinedName === collectionCombine;
	},

	calc_same: function()
    {
		const combinedName = this.get_combined_name();
		if (
			(this.get_collection_editing() === combinedName)
			||
			(this.get_collection_combine() === combinedName)
			)
		{
			this.set_same(combinedName);
		}
		else
		{
			this.set_same(null);
		}
    },

    nameForCombinedCollection: function(input) {
        if (input === '')
		{
			const collectionEditing = this.get_collection_editing();
			const collectionCombine = this.get_collection_combine();
			return collectionEditing + ' and ' + collectionCombine;
		}
        return input;
    }

});

const CombineCommandBoxHintAView = Backbone.View.extend({

	combine: null,

    initialize: function(options)
    {
        this.$('#CombineCommandHintDelete').remove();
		const combine = options.combine;
        this.combine = combine;
        this.listenTo(combine, 'change:combined_name', this.change_combined_name);
		this.listenTo(combine, 'change:same', this.change_same);
		this.listenTo(combine, 'change', this.change_all);
    },

    change_combined_name: function ()
    {
		const el_hintCollection = this.$('#CombineCommandHintCollection');
		const combinedName = this.combine.get_combined_name();
		el_hintCollection.text(combinedName);
    },

    change_same: function ()
    {
        const action = (this.combine.get_same() != null) ? 'Update' : 'Create';
        this.$('#CombineCommandHintAction').text(action);
    },

    change_all: function()
    {
        this.change_combined_name();
        this.change_same();
    }

});

const CombineCommandBoxHintBView = Backbone.View.extend({

	combine: null,

    initialize: function(options)
    {
		const combine = options.combine;
        this.combine = combine;
		this.listenTo(combine, 'change:combined_name', this.change_combined_name);
		this.listenTo(combine, 'change:collection_editing', this.change_collection_editing);
		this.listenTo(combine, 'change:collection_combine', this.change_collection_combine);
		this.listenTo(combine, 'change', this.change_all);
    },

    change_combined_name: function ()
    {
		const el_hintCollection = this.$('#CombineCommandHintCollection');
		const combinedName = this.combine.get_combined_name();
		el_hintCollection.text(combinedName);
    },

    change_collection_editing: function ()
    {
		const el_deleteName_editing = this.$('#CombineCommandHintDelete1Name');
		el_deleteName_editing.text(this.combine.get_collection_editing());
    },

    change_collection_combine: function ()
    {
		const el_deleteName_combine = this.$('#CombineCommandHintDelete2Name');
		el_deleteName_combine.text(this.combine.get_collection_combine());
    },

    change_same: function ()
    {
		const same_as_combine = this.combine.is_same_as_combine();
		const same_as_editing = this.combine.is_same_as_editing();

        const same = same_as_combine || same_as_editing;

		const action = same ? 'Update' : 'Create';
        this.$('#CombineCommandHintAction').text(action);

		const el_delete_editing = this.$('#CombineCommandHintDelete1');
		if (!same_as_editing)
			el_delete_editing.show();
		else
			el_delete_editing.hide();

		const el_delete_combine = this.$('#CombineCommandHintDelete2');
		if (!same_as_combine)
			el_delete_combine.show();
		else
			el_delete_combine.hide();
	},

    change_all: function()
    {
        this.change_collection_combine();
        this.change_collection_editing();
        this.change_combined_name();
        this.change_same();
    }

});

const CombineView = Backbone.View.extend({

    collection_editing: null,

    combineCommandHintTemplate: null,

    initialize: function(options)
    {
		this.backend = options.backend;
        this.collection_editing = options.collection_editing;

        this.combineCommandHintTemplate = this.$('#CombineCommandHint');
        this.combineCommandHintTemplate.remove();
    },

    renderCommandBox: function(collection_combine)
    {
        const that = this;

		this.combine = new Combine({
			collection_editing: that.collection_editing,
			collection_combine: collection_combine
		});

		/**
		 * @type CombineCommandBoxView
		 */
		const combineCommandBoxView = new CombineCommandBoxView(
			{
				backend: that.backend,
				combine: that.combine,
				combineCommandHintTemplate: that.combineCommandHintTemplate
			}
		).render();
		that.$el.append(combineCommandBoxView.el);
		combineCommandBoxView.whenRendered.done(function(view)
        {
            that.render_combine_CommandBox(view);
        });
    },

    render_combine_CommandBox: function(viewCommandBox)
    {
        viewCommandBox.input_query_focus();
    }

});

const CombineCommandBoxView = Backbone.View.extend({

	backend: null,

    commandBox: null,

    combine: null,

    combineCommandHintAView: null,

    combineCommandHintBView: null,

	combineCommandHintCView: null,

	combineCommandHintTemplate: null,

	initialize: function(options)
    {
		this.backend = options.backend;
        this.combine = options.combine;

        const combineCommandHintTemplate = options.combineCommandHintTemplate;

        this.combineCommandHintAView = new CombineCommandBoxHintAView({
            el: combineCommandHintTemplate.clone(),
            combine: this.combine
        });

        this.combineCommandHintBView = new CombineCommandBoxHintBView({
            el: combineCommandHintTemplate.clone(),
            combine: this.combine
        });

		this.combineCommandHintTemplate = combineCommandHintTemplate;
	},

    render: function()
    {
        const collection_editing = this.combine.get_collection_editing();
        const collection_combine = this.combine.get_collection_combine();

        const that = this;

		this.whenRendered = new CommandBoxView(
			{
				placeholder_text:
					('Combine ' + collection_editing + ' with ' + collection_combine),
				on_change_input: function(input) { that.on_combine_change_input(input); },
				on_command: function(view) { that.on_combine_command(view) },
				on_command_alternate: function(view) { that.on_combine_command_alternate(view) },
				on_command_confirm: function(view) { that.on_combine_command_confirm(view) }
			}
		).render().whenRendered.then(function(view)
			{
				that.rendered_combineCommandBox(view);
				return view;
			});

        return this;
    },

	rendered_combineCommandBox: function(combineCommandBox)
	{
		this.combine.trigger('change');

		combineCommandBox.emptyCommandHints();
		combineCommandBox.appendCommandHint(this.combineCommandHintAView.el);
		combineCommandBox.appendCommandHintAlternate(this.combineCommandHintBView.el);

		this.commandBox = combineCommandBox;
	},

    input_query_focus: function()
    {
        this.commandBox.input_query_focus();
    },

    on_combine_change_input: function (view)
	{
        const input = view.input_query_val();
        this.combine.set_user_input_combined_name(input);
    },

	on_combine_command: function(commandBoxView)
	{
		const combine = this.combine;
		const editing_name = combine.get_collection_editing();
		const combine_name = combine.get_collection_combine();
		const spliced_name = combine.get_combined_name();
		const aUrl =
			"api/collection/" +
			encodeURIComponent(editing_name) +
			"/combine/" +
			encodeURIComponent(combine_name) +
			"/into/" +
			encodeURIComponent(spliced_name) +
			"/copy";

		// TODO display 'combining...'
		/*
		 beforeSend: function(){
		 },
		 */

		const that = this;

		this.backend.ajax_ajax_promise(aUrl)
			.done(function()
			{
				that.on_combine_command_done(commandBoxView);
			})
			.fail(function(error)
			{
				commandBoxView.trouble(error);
			});
	},

	on_combine_command_alternate: function(commandBoxView)
	{
		const that = this;
		const confirmCombine = new Combine(
			{
				collection_combine: that.combine.get_collection_combine(),
				collection_editing: that.combine.get_collection_editing()
			}
		);

		const combineCommandHintCView = new CombineCommandBoxHintBView(
		{
			el: that.combineCommandHintTemplate.clone(),
			combine: confirmCombine
		});

		confirmCombine.set_combined_name(that.combine.get_combined_name());

		commandBoxView.showCommandHintConfirm(combineCommandHintCView.el);

		this.combineCommandHintCView = combineCommandHintCView;
	},

	on_combine_command_confirm: function(commandBoxView)
	{
		const combine = this.combineCommandHintCView.combine;
		const editing_name = combine.get_collection_editing();
		const combine_name = combine.get_collection_combine();
		const spliced_name = combine.get_combined_name();
		const aUrl =
			"api/collection/" +
			encodeURIComponent(editing_name) +
			"/combine/" +
			encodeURIComponent(combine_name) +
			"/into/" +
			encodeURIComponent(spliced_name) +
			"/move";

		// TODO display 'combining...'
		/*
		 beforeSend: function(){
		 },
		 */

		commandBoxView.hideCommandHintConfirm();

		this.combineCommandHintCView = null;

		const that = this;

		this.backend.ajax_ajax_promise(aUrl)
			.done(function()
			{
				that.on_combine_command_done(commandBoxView);
			})
			.fail(function(error)
			{
				commandBoxView.trouble(error);
			});
	},

	on_combine_command_done: function(commandBoxView) {
		commandBoxView.input_query_setval('');
	}

});
