import {TagSuggestionsCollection} from "#steamside/Tag.js";
import {SteamsideCollectionApps} from "#steamside/Inventory.js";
import {CollectionEditView} from "#steamside/CollectionEdit.js";
import {TagAGameView} from "#steamside/TagAGameView.js";
import {SearchView} from "#steamside/Search.js";
import {CollectionPickView} from "#steamside/CollectionPick.js";
import {KidsView} from "#steamside/KidsHome.js";
import {ErrorHandler} from "#steamside/Error.js";
import {ContinueGames, DeckView, FavoritesCollection} from "#steamside/GameCardDeck.js";
import {sideshow} from "#steamside/Sideshow.js";

export const Steamside_HomeWorld =
{
	nameController: 'HomeController',

	htmlWorld: 'Home.html',

	controller: function(
		$scope, $location, theBackend, theSessionModel, theKidsMode,
		theSpritesKids, theSpritesSteamside)
	{
		window.onerror = function errorHandler(msg, url, line, col, error)
		{
			ErrorHandler.explode(error);
			throw error;
		};

		new SteamsideView({
			sessionModel: theSessionModel,
			kidsMode: theKidsMode,
			spritesKids: theSpritesKids
		}).render();

		new HomeView({
			sessionModel: theSessionModel,
			kidsMode: theKidsMode,
			spritesKids: theSpritesKids,
			spritesSteamside: theSpritesSteamside,
			backend: theBackend,
			$location: $location
		});

		theBackend.fetch_promise(theSessionModel);
	}
};


const HomeView = Backbone.View.extend(
{
	el: "#HomeView",

	sessionModel: null,
	_kidsMode: null,

	cardTemplatePromise: null,

	/**
	 * @type Sprite
	 */
	spriteMoreButton: null,

	backend: null,
	$location: null,

	/**
	 * @type Deferred
	 */
	whenRendered: null,

	initialize: function(options)
	{
		this.sessionModel = options.sessionModel;
		this._kidsMode = options.kidsMode;

		this.spritesKids = options.spritesKids;
		this.spritesSteamside = options.spritesSteamside;
		this.spriteMoreButton = options.spritesSteamside.moreButton;

		this.backend = options.backend;
		this.$location = options.$location;

		this.listenTo(this._kidsMode, 'sync', this.render);
	},

	render: function ()
	{
		const that = this;

		that.$el.hide();

		that.cardTemplatePromise = this.decide_cardTemplatePromise();

		const continues = new ContinueGames();

		const kidsMode = this._kidsMode.kidsMode();

		const viewFavorites = this.renderFavoritesView(kidsMode, continues);

		let promiseRenderRecentTagged = null;

		let searchView = null;

		if (!kidsMode)
		{
			this.createContinuesDeck(continues);
			searchView = this.renderSearchSegment(continues);
			promiseRenderRecentTagged =
				this.renderRecentTagged(viewFavorites.$el, continues);
		}

		this.backend.fetch_promise(continues);

		let sideshow_ready;

		if (promiseRenderRecentTagged != null)
		{
			sideshow_ready = promiseRenderRecentTagged;
		}
		else
		{
			sideshow_ready = $.when(this);
		}

		sideshow_ready.done(function(){
			that.sideshow();
			if (searchView != null)
			{
				setTimeout(function () {
					searchView.command_box_input_query_focus();
				}, 0);
			}
		});

		this.whenRendered = sideshow_ready;

		return this;
	},

	decide_cardTemplatePromise: function()
	{
		const kidsMode = this._kidsMode.kidsMode();
		if (kidsMode)
		{
			return this.spritesKids.card.sprite_promise();
		}
		return this.spritesSteamside.card.sprite_promise();
	},

	renderFavoritesView: function (kidsMode, continues) {
		const that = this;

		/**
		 * @type FavoritesView
		 */
		const favoritesView = new FavoritesView(
			{
				cardTemplatePromise: that.cardTemplatePromise,
				spriteMoreButton: that.spriteMoreButton,
				backend: that.backend,
				on_tag: that.on_game_card_tag,
				kidsMode: kidsMode,
				continues: continues
			}
		);
		return favoritesView.render();
	},

	createContinuesDeck: function (continues) {
		const that = this;

		new DeckView({
			el: $('#continues-deck'),
			cardTemplatePromise: that.cardTemplatePromise,
			spriteMoreButton: that.spriteMoreButton,
			collection: continues,
			continues: continues,
			on_tag: that.on_game_card_tag,
			backend: that.backend
		});
	},

	renderSearchSegment: function (continues) {
		const that = this;

		const searchView = new SearchView(
			{
				el: $('#search-segment'),
				cardTemplatePromise: that.cardTemplatePromise,
				spriteMoreButton: that.spriteMoreButton,
				continues: continues,
				on_tag: that.on_game_card_tag,
				backend: that.backend,
				$location: that.$location
			}
		);

		searchView.render();

		return searchView;
	},

	renderRecentTagged: function (segmentBeforeRecentTagged) {
		const that = this;

		const recentTagged = new TagSuggestionsCollection();

		segmentBeforeRecentTagged.after(
			new RecentTaggedView({
				collection: recentTagged,
				backend: that.backend,
				spriteMoreButton: that.spriteMoreButton,
				cardTemplatePromise: that.cardTemplatePromise
			})
				.el);

		return this.backend.fetch_promise(recentTagged);
	},

	on_game_card_tag: function(game, segmentWithGameCard)
	{
		const that = this;
		const tagView = new TagAGameView({
			game: game,
			cardTemplatePromise: that.cardTemplatePromise,
			backend: that.backend
		}).render();
		segmentWithGameCard.after(tagView.$el);
		$('html, body').scrollTop(tagView.$el.offset().top);
	},

	sideshow: function()
	{
		this.$el.show();

		sideshow(this.$el);
	}
});



const FavoritesView = Backbone.View.extend(
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
		const that = this;

		this.switch_purpose_el = this.$("#SwitchPurposeView");
		this.switch_purpose_el.remove();

		this.favorites = new FavoritesCollection();

		new DeckView({
			el: $('#favorites-deck'),
			cardTemplatePromise: this.cardTemplatePromise,
			spriteMoreButton: that.spriteMoreButton,
			collection: this.favorites,
			continues: this.continues,
			alwaysVisible: this.kidsMode,
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

		const that = this;

		const el_switch_purpose = that.switch_purpose_el.clone();

		const viewCollectionPick = new CollectionPickView(
			{
				purpose_el: el_switch_purpose,
				on_collection_pick: function(modelCollection)
				{
					viewCollectionPick.remove();
					that.on_switch_favorites_collection_pick(modelCollection);
				},
				backend: that.backend
			}
		).render();

		this.$el.after(viewCollectionPick.el);
		$('html, body').scrollTop(viewCollectionPick.$el.offset().top);
	},

	on_switch_favorites_collection_pick: function(modelCollection)
	{
		const aUrl =
			"api/favorites/set/" +
			encodeURIComponent(modelCollection.name());


		// TODO display 'setting favorites...'
		/*
		 beforeSend: function(){
		 },
		 */

		const that = this;

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




const SteamsideView = Backbone.View.extend({

	el: "body",

	sessionModel: null,
	spritesKids: null,
	_kidsMode: null,

	initialize: function(options)
	{
		this.sessionModel = options.sessionModel;
		this.spritesKids = options.spritesKids;
		this._kidsMode = options.kidsMode;

		this.listenTo(this._kidsMode, 'sync', this.applyKidsMode);
	},

	render: function ()
	{
		const sessionModel = this.sessionModel;

		/**
		 * @type SessionView
		 */
		const sessionView = new SessionView({model: sessionModel});
		sessionView.render();

		return this;
	},

	applyKidsMode: function()
	{
		const kids = this._kidsMode.kidsMode();

		if (!kids) {
			this.$("#KidsModeIndicator").hide();
			return;
		}

		new KidsView({
			el: this.$el,
			username: this.sessionModel.userName(),
			spritesKids: this.spritesKids
		}).render();
	}
});



const SessionView = Backbone.View.extend({
	el : 'body',

	initialize: function () {
		this.listenTo(this.model, "change", this.render);
	},

	render: function ()
	{
		const m = this.model;

		this.$('#userName').text(m.userName());
		this.$('#version').text(m.versionOfSteamside());
		this.$('#number-of-games').text(m.gamesOwned());

		return this;
	}
});


const RecentTaggedView = Backbone.View.extend(
{
	initialize: function(options)
	{
		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;
		this.spriteMoreButton = options.spriteMoreButton;
		this.backend = options.backend;

		this.listenTo(this.collection, 'reset', this.render);
	},

	render: function () {
		const that = this;
		that.collection.each( function(oneTagSuggestion) {
			that.$el.after(
				that.renderRecentTaggedOne(oneTagSuggestion)
					.el);
		});
		return that;
	},

	/**
	 * @param oneTagSuggestion Tag
	 */
	renderRecentTaggedOne: function(oneTagSuggestion)
	{
		const that = this;

		const name = oneTagSuggestion.name();

		const inventory = new SteamsideCollectionApps();
		inventory.collection_name = name;

		return new CollectionEditView({
			inventory: inventory,
			tag: oneTagSuggestion,
			cardTemplatePromise: that.cardTemplatePromise,
			spriteMoreButton: that.spriteMoreButton,
			backend: that.backend,
			simplified: true
		}).render();
	}
}
);