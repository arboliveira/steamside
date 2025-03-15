import {ErrorHandler} from "#steamside/Error.js";
import {MoreButtonView} from "#steamside/MoreButton.js";
import {heartbeat} from "#steamside/Heartbeat.js";

export const Game = Backbone.Model.extend({

	initialize: function(){
		this.tags = new Game_Tags();
		this.tags.reset( this.get( "tags" ) );
	},

	appid : function() {
		return this.get('appid');
    },
    name: function() {
		return this.get('name');
    },
    link: function() {
		return this.get('link');
    },
    image: function() {
        return this.get('image');
    },
    store: function() {
        return this.get('store');
    },
    unavailable: function() {
    	return this.get('unavailable') === 'Y';
    },

	tagsCollection: function() {
		return this.tags;
	}
});


export const Game_Player = Backbone.Model.extend(
{
	initialize: function(options) {
		this.backend = options.backend;
	},

	play: function(game) {
		const aUrl = game.link();
		const that = game;

		that.trigger('game:play:beforeSend');

		this.backend.ajax_ajax_promise_2(aUrl)
			.done(function()
			{
				that.trigger('game:play:complete');
			})
			.fail(function(jqXHR, textStatus, error)
			{
				that.trigger('game:play:complete');
				
				// TODO display error closer to line of sight
				ErrorHandler.calm(error);
			});
	}
});


const Game_Tag = Backbone.Model.extend({

	name: function() {
		return this.get('name');
	}

});


const Game_Tags = Backbone.Collection.extend({
	model: Game_Tag
});


export const ContinueGames = Backbone.Collection.extend({
    model: Game,
    url: 'api/continues/continues.json'
});

export const FavoritesCollection = Backbone.Collection.extend({
    model: Game,
    url: 'api/favorites/favorites.json'
});

const DeckRow = Backbone.View.extend({
	className: 'game-row',

	render: function()
	{
		return this;
	}
});

const Game_Tag_View = Backbone.View.extend({

	render: function()
	{
		this.$(".game-tag-name").text(this.model.name());
		this.$(".game-tag-link").attr('href', this.collection_url());
		return this;
	},

	collection_url: function()
	{
		const name = this.model.name();
		return "#/collections/" + name + "/edit";
	}

});

const Game_Tag_ListView = Backbone.View.extend({

	render: function() {
		const container = this.$el;

		const one_el = this.$(".game-tag");
		container.empty();

		this.collection.each( function(one) {
			/**
			 * @type Game_Tag_View
			 */
			const view = new Game_Tag_View({
				model: one,
				el: one_el.clone()
			});
			container.append(view.render().el);
		});

		return this;
	}
});



export const GameCardView = Backbone.View.extend({

	events: {
		"mouseenter .game-link": "mouseenter_hot_zone",
        "mouseleave .game-link": "mouseleave_hot_zone",
        "click .game-link": "gameClicked",
        "click .game-tile-play": "playClicked",
        "click .game-tile-tag": "tagClicked"
	},

	initialize: function(options)
	{
		this.cardTemplatePromise = options.cardTemplatePromise;
		this.continues = options.continues;
		this.enormity = options.enormity;
		this.on_render = options.on_render;
		this.on_tag = options.on_tag;
		this.backend = options.backend;

		this.player = new Game_Player({ backend: options.backend });

		this.listenTo(this.model, 'game:play:beforeSend', this.game_play_beforeSend);
		this.listenTo(this.model, 'game:play:complete', this.game_play_complete);
	},

	render: function() {
		const that = this;
		this.cardTemplatePromise.done(function(template_el) {
			that.$el.append(template_el.clone());
			that.render_el();
		});
	},

	render_el: function ()
	{
		const name = this.model.name();
		const link = this.model.link();
		const img = this.model.image();
        const store = this.model.store();

		const outermost = this.$el;
		outermost.addClass(this.enormity.styleClass);
		outermost.width(this.enormity.width.toString() + "%");
		outermost.addClass("game-tile-in-deck");
		outermost.addClass("game-tile");

		const gameTile = this.$("#game-tile");
		gameTile.removeClass("game-tile");

		this.$('.game-name').text(name);
		this.$('.game-img').attr('src', img);
		this.$('.game-link').attr('href', link);
        this.$('.game-tile-store').attr('href', store);

        if (this.model.unavailable())
		{
        	gameTile.addClass('game-unavailable');
        	this.$('.game-tile-play').hide();
        }
		else
		{
			this.$('.game-tile-play').attr('href', link);
		}

		const tags = this.model.tagsCollection();

		this.viewGame_Tag_List = new Game_Tag_ListView({
			el: this.$("#game-tag-list"),
			collection: tags
		}).render();

		if (this.on_render != null) this.on_render(this);

		return this;		
	},

	littleCommandToLightUp: function () {
		const $game = this.$('.game-tile-command');
		if ($game.length === 0) return null;
		return $game.first();
	},

	mouseenter_hot_zone: function(e) {
        e.preventDefault();
		const littleCommandToLightUp = this.littleCommandToLightUp();
		if (littleCommandToLightUp == null) return;
		littleCommandToLightUp.addClass('what-will-happen');
    },

    mouseleave_hot_zone: function(e) {
        e.preventDefault();
		const littleCommandToLightUp = this.littleCommandToLightUp();
		if (littleCommandToLightUp == null) return;
		littleCommandToLightUp.removeClass('what-will-happen');
    },

    gameClicked: function(e)
	{
		e.preventDefault();
		const w = this.littleCommandToLightUp();
		if (w != null)
		{
			w.click();
			return;
		}
		this.playClicked(e);
	},

	playClicked: function(e)
	{
		e.preventDefault();
		this.player.play(this.model);
	},

	tagClicked: function(e)
	{
		e.preventDefault();
		if (this.on_tag != null) this.on_tag(this.model);
	},

	game_play_beforeSend: function () {
		this.showOverlay();
	},

	game_play_complete: function () {
		this.hideOverlay();
		this.redisplay_continues();
	},

    showOverlay: function() {
        const overlay = this.$('.game-tile-inner-loading-overlay');
        const underlay = this.$('.game-tile-inner');
        overlay.show();
        underlay.addClass('game-tile-inner-blurred');
		const img = overlay.find('img');
		heartbeat(img, false, 0, 15, 25);
    },

    hideOverlay: function() {
        const overlay = this.$('.game-tile-inner-loading-overlay');
        const underlay = this.$('.game-tile-inner');
        overlay.hide();
        underlay.removeClass('game-tile-inner-blurred');
    },

    redisplay_continues: function () {
        this.backend.fetch_fetch_json(this.continues);
    },

	continues: null,
	enormity: null,
	width: 0,
	on_render: null,
	on_tag: null,
	player: null,

	/**
	 * @type Game_Tag_ListView
	 */
	viewGame_Tag_List: null
});

const FillerCellView = Backbone.View.extend({
	width: 0,
	
	initialize: function(options)
	{
		this.width = options.width;
	},

	render: function() {
		this.$el.html('&nbsp;');
		this.$el.width(this.width.toString() + "%");
		this.$el.addClass('game-cell-filler');
		return this;
	}
});

export const DeckView = Backbone.View.extend(
{
    alwaysVisible: false,
	xCell: 0,
	yRow: 0,
	first_row: null,
	current_row: null,
	cardTemplatePromise: null,
    continues: null,
	tailVisibility: false,
	on_GameCard_render: null,
	on_tag: null,

	/**
	 * @type Sprite
	 */
	spriteMoreButton: null,

	initialize: function(options)
	{
        /*
         visible will not be part of the result anymore,
         because we want logic like "first row is visible"
         and this depends on calculations inside the browser
         */

		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;

		if (options.spriteMoreButton == null) {
			throw new Error("spriteMoreButton is required");
		}
		this.spriteMoreButton = options.spriteMoreButton;

		this.alwaysVisible = options.alwaysVisible === true;
		this.continues = options.continues;
		this.on_GameCard_render = options.on_GameCard_render;
		this.on_tag = options.on_tag;
		this.backend = options.backend;

		this.listenTo(this.collection, 'reset', this.render);
	},

	render: function()
	{
		const that = this;

		try {
			this.$el.empty();
			this.$el.addClass('game-deck');

			this.xCell = 0;
			this.yRow = 0;
			this.first_row = null;
			this.current_row = null;
			this.tailVisibility = false;

			this.collection.each(function (oneResult) {
				that.renderOneCell(oneResult);
			});

			this.renderMoreButton();
		}
		catch (e)
		{
			ErrorHandler.explode(e);
			throw e;
		}
		return this;
	},
	
	renderOneCell: function(oneResult)
	{
		const cells_in_a_row = 3;

        const enormityRegular = {
            styleClass: 'game-tile-regular',
            width: 30
        };
        const enormityLarge = {
            styleClass: 'game-tile-large',
            width: 100 - 5 - enormityRegular.width * (cells_in_a_row - 1)
        };

		const widthFiller = 100 - 5 - enormityRegular.width * cells_in_a_row;

		this.xCell += 1;
		if (this.xCell === 1) {
			this.yRow += 1;
		} else {
			if (this.xCell === cells_in_a_row) {
				this.xCell = 0;
			}
		}

		let rowForCell;

		if (this.xCell === 1)
		{
			rowForCell = this.startNewRow();
		}
		else
		{
			rowForCell = this.current_row;
		}

		if (this.xCell === 1 && this.yRow > 1)
		{
			/**
			 * @type FillerCellView
			 */
			const filler_view =
				new FillerCellView(
					{
						width: widthFiller
					});
			const filler_el = filler_view.render().el;
			rowForCell.append(filler_el);
		}

		let enormity;
        const topLeftIsLarger = this.xCell === 1 && this.yRow === 1;
        if (topLeftIsLarger)
		{
			enormity = enormityLarge;
		}
		else
		{
			enormity = enormityRegular;
		}

		const that = this;

		that.renderGameCard(oneResult, enormity, rowForCell);
	},

	renderGameCard: function(oneResult, enormity, rowForCell)
	{
		const that = this;

		/**
		 * @type GameCardView
		 */
		const card_view = new GameCardView({
			cardTemplatePromise: that.cardTemplatePromise,
			model: oneResult,
			enormity: enormity,
			backend: this.backend,
			continues: this.continues,
			on_render: this.on_GameCard_render,
			on_tag: function(game)
			{
				that.on_tag_deck(game);
			}
		});
		rowForCell.append(card_view.el);
		card_view.render();
	},

	renderMoreButton: function ()
	{
		if (this.yRow === 1) return;
		if (this.alwaysVisible) return;
		if (this.first_row == null) /* zero games */ return;

		const that = this;

		const moreButtonView = new MoreButtonView(
			{
				deck: that,
				spriteMoreButton: that.spriteMoreButton
			}
		);
		that.first_row.append(moreButtonView.el);
		moreButtonView.render();
	},

	startNewRow: function()
	{
		/**
		 * @type DeckRow
		 */
		const deckRow = new DeckRow();
		const row_view = deckRow.render();
		const row_el = row_view.el;
		const row = row_view.$el;

		this.$el.append(row_el);

		if (this.first_row === null)
		{
			this.first_row = row;
		}
		else
		{
			row.addClass('game-row-tail');
			if (!this.alwaysVisible) row.hide();
		}

		this.current_row = row;

		return row;
	},

	toggleVisibility: function()
	{
		this.tailVisibility = !this.tailVisibility;

		const tails = this.$('.game-row-tail');

		if (this.tailVisibility)
		{
			tails.slideDown();
		}
		else
		{
			tails.slideUp();
		}
	},

	on_tag_deck: function(game)
	{
		const segment = this.$el.parent().parent();
		if (this.on_tag != null) this.on_tag(game, segment);
	}
});
