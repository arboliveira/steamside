"use strict";

var HomeWorld = WorldActions.extend(
	{
		sessionModel: null,
		cardTemplatePromise: null,

		/**
		 * @type Sprite
		 */
		spriteMoreButton: null,

		initialize: function(options)
		{
			this.sessionModel = options.sessionModel;

			if (options.cardTemplatePromise == null)
			{
				throw new Error("cardTemplatePromise is required");
			}
			this.cardTemplatePromise = options.cardTemplatePromise;
			this.spriteMoreButton = options.spriteMoreButton;

			this.backend = options.backend;
		},

		/**
		 * @override
		 */
		newView: function()
		{
			return new HomeView({
				sessionModel: this.sessionModel,
				cardTemplatePromise: this.cardTemplatePromise,
				spriteMoreButton: this.spriteMoreButton,
				backend: this.backend
			});
		},

		isFront: function()
		{
			return true;
		}
	}
);

var HomeView = Backbone.View.extend(
{
	el: "#primary-view",

	sessionModel: null,

	cardTemplatePromise: null,

	/**
	 * @type Sprite
	 */
	spriteMoreButton: null,

	backend: null,

	/**
	 * @type Deferred
	 */
	whenRendered: null,

	initialize: function(options)
	{
		this.sessionModel = options.sessionModel;

		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;
		this.spriteMoreButton = options.spriteMoreButton;

		this.backend = options.backend;
	},

	render: function ()
	{
		var that = this;

		var continues = new ContinueGames();
		var kidsMode = this.sessionModel.kidsMode();

		var viewFavorites = this.renderFavoritesView(kidsMode, continues);

		var promiseRenderRecentTagged = null;

		if (!kidsMode)
		{
			this.createContinuesDeck(continues);
			this.searchView = this.renderSearchSegment(continues);
			promiseRenderRecentTagged =
				this.renderRecentTagged(viewFavorites.$el, continues);
		}

		this.backend.fetch_promise(continues);

		if (promiseRenderRecentTagged != null)
		{
			this.whenRendered = promiseRenderRecentTagged.then(
				function() { return that; }
			);
		}
		else
		{
			this.whenRendered = $.when(this);
		}

		return this;
	},

	renderFavoritesView: function (kidsMode, continues) {
		var that = this;

		return new FavoritesView(
			{
				cardTemplatePromise: that.cardTemplatePromise,
				spriteMoreButton: that.spriteMoreButton,
				backend: that.backend,
				on_tag: that.on_game_card_tag,
				kidsMode: kidsMode,
				continues: continues
			}
		).render();
	},

	createContinuesDeck: function (continues) {
		var that = this;

		new DeckView({
			el: $('#continues-deck'),
			cardTemplatePromise: that.cardTemplatePromise,
			spriteMoreButton: that.spriteMoreButton,
			collection: continues,
			continues: continues,
			kidsMode: false,
			on_tag: that.on_game_card_tag,
			backend: that.backend
		});
	},

	renderSearchSegment: function (continues) {
		var that = this;

		return new SearchView(
			{
				el: $('#search-segment'),
				cardTemplatePromise: that.cardTemplatePromise,
				spriteMoreButton: that.spriteMoreButton,
				continues: continues,
				on_tag: that.on_game_card_tag,
				backend: that.backend
			}
		).render();
	},

	renderRecentTagged: function (segmentBeforeRecentTagged, continues) {
		var that = this;

		var suggestions = new TagSuggestionsCollection();

		return this.backend.fetch_promise(suggestions).then(function () {
			suggestions.each( function(oneTagSuggestion) {
				var view = that.renderRecentTaggedOne(
					oneTagSuggestion, segmentBeforeRecentTagged);
			});
		});
	},

	/**
	 * @param oneTagSuggestion TagSuggestion
	 */
	renderRecentTaggedOne: function(
		oneTagSuggestion, segmentBeforeRecentTagged
	)
	{
		var that = this;
		new CollectionEditView({
			collection_name: oneTagSuggestion.name(),
			cardTemplatePromise: that.cardTemplatePromise,
			spriteMoreButton: that.spriteMoreButton,
			backend: that.backend,
			simplified: true
		}).render().whenRendered.done(function(view)
			{
				view.$el.hide();
				segmentBeforeRecentTagged.after(view.$el);
				view.$el.slideDown();
			}
		);
	},

	on_game_card_tag: function(game, segmentWithGameCard)
	{
		var that = this;
		new TagView({
			game: game,
			segmentWithGameCard: segmentWithGameCard,
			backend: that.backend
		}).render();
	},

	/**
	 * @type SearchView
	 */
	searchView: null
});



var FavoritesView = Backbone.View.extend(
{
	el: "#favorites-segment",

	favorites: null,

	/**
	 * @type Sprite
	 */
	spriteMoreButton: null,

	events:
	{
		"click #side-link-favorite-switch": "switchClicked"
	},

	initialize: function(options)
	{
		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;
		this.spriteMoreButton = options.spriteMoreButton;
		this.backend = options.backend;
		this.on_tag = options.on_tag;
		this.kidsMode = options.kidsMode;
		this.continues = options.continues;
	},

	render: function()
	{
		var that = this;

		this.switch_purpose_el = this.$("#SwitchPurposeView");
		this.switch_purpose_el.remove();

		this.favorites = new FavoritesCollection();

		new DeckView({
			el: $('#favorites-deck'),
			cardTemplatePromise: this.cardTemplatePromise,
			spriteMoreButton: that.spriteMoreButton,
			collection: this.favorites,
			continues: this.continues,
			kidsMode: this.kidsMode,
			on_tag: this.on_tag,
			backend: this.backend
		});

		this.fetch_favorites_collection();

		return this;
	},

	fetch_favorites_collection: function()
	{
		this.backend.fetch_promise(this.favorites);
	},

	switchClicked: function(e) {
		e.preventDefault();

		var that = this;

		var viewCollectionPick = new CollectionPickView(
			{
				on_collection_pick: function(modelCollection)
				{
					viewCollectionPick.remove();
					that.on_switch_favorites_collection_pick(modelCollection);
				},
				backend: that.backend
			});

		viewCollectionPick.render().whenRendered.done(function(view)
		{
			that.rendered_SwitchFavoritesCollectionPickView(view);
		});
	},

	rendered_SwitchFavoritesCollectionPickView: function(view)
	{
		var el_switch_purpose = this.switch_purpose_el.clone();

		var el_purpose = view.$('#PurposeView');
		el_purpose.empty();
		el_purpose.append(el_switch_purpose);

		this.$el.after(view.el);

		$('html, body').scrollTop(view.$el.offset().top);
	},

	on_switch_favorites_collection_pick: function(modelCollection)
	{
		var aUrl =
			"api/favorites/set/" +
			encodeURIComponent(modelCollection.name());


		// TODO display 'setting favorites...'
		/*
		 beforeSend: function(){
		 },
		 */

		var that = this;

		this.backend.ajax_ajax_promise(aUrl)
			.done(function()
			{
				that.on_switch_favorites_done();
			})
			.fail(function(error)
			{
				// TODO display error closer to line of sight
				ErrorHandler.explode(error);
			});

		this.fetch_favorites_collection();
	},

	on_switch_favorites_done: function()
	{
		this.fetch_favorites_collection();
	}

});
