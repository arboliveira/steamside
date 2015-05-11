"use strict";

var HomeWorld = WorldActions.extend(
	{
		sessionModel: null,
		cardTemplatePromise: null,

		initialize: function(options)
		{
			this.sessionModel = options.sessionModel;

			if (options.cardTemplatePromise == null)
			{
				throw new Error("cardTemplatePromise is required");
			}
			this.cardTemplatePromise = options.cardTemplatePromise;
			this.backend = options.backend;
		},

		tileLoad: function(whenDone)
		{
			// HomeTile.whenLoaded(whenDone);
			whenDone(null);
		},

		newView: function(/*tile*/)
		{
			return new HomeView({
				// el: tile.clone(),
				sessionModel: this.sessionModel,
				cardTemplatePromise: this.cardTemplatePromise,
				backend: this.backend
			}).render();
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

	initialize: function(options)
	{
		this.sessionModel = options.sessionModel;

		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;

		this.backend = options.backend;
	},

	render: function ()
	{
		var continues = new ContinueGames();

		var that = this;

		var kidsMode = this.sessionModel.kidsMode();

		if (!kidsMode)
		{
			new DeckView({
				el: $('#continues-deck'),
				cardTemplatePromise: that.cardTemplatePromise,
				collection: continues,
				continues: continues,
				kidsMode: kidsMode,
				on_tag: that.on_tag,
				backend: that.backend
			});

			new SearchView(
				{
					el: $('#search-segment'),
					cardTemplatePromise: this.cardTemplatePromise,
					continues: continues,
					on_tag: that.on_tag,
					backend: that.backend
				}
			).render();
		}

		this.backend.fetch_promise(continues);

		new FavoritesView(
			{
				cardTemplatePromise: this.cardTemplatePromise,
				backend: this.backend,
				on_tag: this.on_tag,
				kidsMode: kidsMode,
				continues: continues
			}
		).render();

		return this;
	},

	on_tag: function(game, segmentWithGameCard)
	{
		TagTile.on_tag(game, segmentWithGameCard, this.backend);
	}
});



var FavoritesView = Backbone.View.extend(
{
	el: "#favorites-segment",

	favorites: null,

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

		this.backend = options.backend;
		this.on_tag = options.on_tag;
		this.kidsMode = options.kidsMode;
		this.continues = options.continues;
	},

	render: function()
	{
		this.switch_purpose_el = this.$("#SwitchPurposeView");
		this.switch_purpose_el.remove();

		this.favorites = new FavoritesCollection();

		new DeckView({
			el: $('#favorites-deck'),
			cardTemplatePromise: this.cardTemplatePromise,
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
