"use strict";

var HomeWorld = WorldActions.extend(
	{
		sessionModel: null,
		cardTemplatePromise: null,

		/**
		 * @type CollectionEditSpriteSheet
		 */
		spritesCollectionEdit: null,

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
			this.spritesCollectionEdit = options.spritesCollectionEdit;
			this.spriteMoreButton = options.spriteMoreButton;

			this.backend = options.backend;
		},

		newView: function(/*_el*/)
		{
			return new HomeView({
				// el: _el.clone(),
				sessionModel: this.sessionModel,
				cardTemplatePromise: this.cardTemplatePromise,
				spritesCollectionEdit: this.spritesCollectionEdit,
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
	 * @type CollectionEditSpriteSheet
	 */
	spritesCollectionEdit: null,

	/**
	 * @type Sprite
	 */
	spriteMoreButton: null,

	backend: null,

	whenRendered: null,

	initialize: function(options)
	{
		this.sessionModel = options.sessionModel;

		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;
		this.spritesCollectionEdit = options.spritesCollectionEdit;
		this.spriteMoreButton = options.spriteMoreButton;

		this.backend = options.backend;
	},

	render: function ()
	{
		var continues = new ContinueGames();
		var kidsMode = this.sessionModel.kidsMode();

		var viewFavorites = this.renderFavoritesView(kidsMode, continues);

		if (!kidsMode)
		{
			this.createContinuesDeck(continues);
			this.renderSearchSegment(continues);
			if (false)
				this.renderRecentTagged(viewFavorites.$el, continues);
		}

		this.backend.fetch_promise(continues);

		var already = $.Deferred();
		already.resolve(this);
		this.whenRendered = already;

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

		new SearchView(
			{
				el: $('#search-segment'),
				cardTemplatePromise: that.cardTemplatePromise,
				continues: continues,
				on_tag: that.on_game_card_tag,
				backend: that.backend
			}
		).render();
	},

	renderRecentTagged: function (segmentBeforeRecentTagged, continues) {
		var that = this;

		that.spritesCollectionEdit.view.sprite_promise().done(function(_el)
		{
			var clone = _el.clone();

			var view = new CollectionEditView({
				el: clone,
				cardTemplatePromise: that.cardTemplatePromise,
				spriteMoreButton: that.spriteMoreButton,
				collection_name: "RECENT TAGGED",
				backend: that.backend
			});

			view.render();

			view.$el.hide();
			view.$el.slideDown();

			segmentBeforeRecentTagged.after(clone);
		});
	},

	on_game_card_tag: function(game, segmentWithGameCard)
	{
		var that = this;
		new TagView({
			game: game,
			segmentWithGameCard: segmentWithGameCard,
			backend: that.backend
		}).render();
	}
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
