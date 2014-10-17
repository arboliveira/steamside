"use strict";

var GameTilePromise =
{
	buildCardTemplatePromise: function(steamsideTileset)
	{
		var cardTile = new Tile(
			{
				tileset: steamsideTileset,
				selector: ".game-tile"
			}
		);

		return cardTile.el_promise;
	}
};

var Game = Backbone.Model.extend({

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
    	return this.get('unavailable') == 'Y';
    },

	tagsCollection: function() {
		return this.tags;
	}
});


var Game_Player = Backbone.Model.extend(
{
	initialize: function(options) {
		this.backend = options.backend;
	},

	play: function(game) {
		var aUrl = game.link();
		var that = game;

		that.trigger('game:play:beforeSend');

		this.backend.ajax_ajax_promise(aUrl)
			.done(function()
			{
				that.trigger('game:play:complete');
			});
	}
});


var Game_Tag = Backbone.Model.extend({

	name: function() {
		return this.get('name');
	}

});


var Game_Tags = Backbone.Collection.extend({
	model: Game_Tag
});


var ContinueGames = Backbone.Collection.extend({
    model: Game,
    url: 'api/continues/continues.json'
});

var FavoritesCollection = Backbone.Collection.extend({
    model: Game,
    url: 'api/favorites/favorites.json'
});

var DeckRow = Backbone.View.extend({
	className: 'game-row'
});

var Game_Tag_View = Backbone.View.extend({

	events:
	{
		//"click .game-tag-link": "tagClicked"
	},

	render: function()
	{
		this.$(".game-tag-name").text(this.model.name());
		this.$(".game-tag-link").attr('href', this.collection_url());
		return this;
	},

	tagClicked: function(e)
	{
		e.preventDefault();

		/*
		Backbone.history.navigate(
				,
			{trigger: true});
			*/
	},

	collection_url: function()
	{
		var name = this.model.name();
		return "#/collections/" + name + "/edit";
	}

});

var Game_Tag_ListView = Backbone.View.extend({

	render: function() {
		var container = this.$el;

		var one_el = this.$(".game-tag");
		container.empty();

		this.collection.each( function(one) {
			var view = new Game_Tag_View({
				model: one,
				el: one_el.clone()
			});
			container.append(view.render().el);
		});

		return this;
	}
});



var GameCardView = Backbone.View.extend({
	continues: null,
    enormity: null,
	width: 0,
	on_render: null,
	on_tag: null,
	player: null,

	events: {
		"mouseenter .game-link": "mouseenter_hot_zone",
        "mouseleave .game-link": "mouseleave_hot_zone",
        "click .game-link": "gameClicked",
        "click .game-tile-play": "playClicked",
        "click .game-tile-tag": "tagClicked"
	},

	initialize: function(options)
	{
		this.continues = options.continues;
		this.enormity = options.enormity;
		this.on_render = options.on_render;
		this.on_tag = options.on_tag;
		this.backend = options.backend;
		this.player = new Game_Player({ backend: options.backend });

		this.listenTo(this.model, 'game:play:beforeSend', this.game_play_beforeSend);
		this.listenTo(this.model, 'game:play:complete', this.game_play_complete);
	},

	render: function ()
	{
		var name = this.model.name();
		var link = this.model.link();
		var img = this.model.image();
        var store = this.model.store();

		this.$el.addClass(this.enormity.styleClass);
		this.$el.width(this.enormity.width.toString() + "%");
		this.$('.game-name').text(name);
		this.$('.game-img').attr('src', img);
		this.$('.game-link').attr('href', link);
        this.$('.game-tile-store').attr('href', store);

        if (this.model.unavailable())
		{
        	this.$el.addClass('game-unavailable');
        	this.$('.game-tile-play').hide();
        }
		else
		{
			this.$('.game-tile-play').attr('href', link);
		}

		var tags = this.model.tagsCollection();
		new Game_Tag_ListView({
			el: this.$("#game-tag-list"),
			collection: tags
		}).render();

		if (this.on_render != null) this.on_render(this);

		return this;		
	},

	littleCommandToLightUp: function () {
		var $game = this.$('.game-tile-command');
		if ($game.length == 0) return null;
		return $game.first();
	},

	mouseenter_hot_zone: function(e) {
        e.preventDefault();
		var littleCommandToLightUp = this.littleCommandToLightUp();
		if (littleCommandToLightUp == null) return;
		littleCommandToLightUp.addClass('what-will-happen');
    },

    mouseleave_hot_zone: function(e) {
        e.preventDefault();
		var littleCommandToLightUp = this.littleCommandToLightUp();
		if (littleCommandToLightUp == null) return;
		littleCommandToLightUp.removeClass('what-will-happen');
    },

    gameClicked: function(e)
	{
		e.preventDefault();
		var w = this.littleCommandToLightUp();
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
        var overlay = this.$('.game-tile-inner-loading-overlay');
        var underlay = this.$('.game-tile-inner');
        overlay.show();
        underlay.addClass('game-tile-inner-blurred');
		var img = overlay.find('img');
		heartbeat(img, false, 0, 15, 25);
    },

    hideOverlay: function() {
        var overlay = this.$('.game-tile-inner-loading-overlay');
        var underlay = this.$('.game-tile-inner');
        overlay.hide();
        underlay.removeClass('game-tile-inner-blurred');
    },

    redisplay_continues: function () {
        this.backend.fetch_fetch_json(this.continues);
    }
});

var FillerCellView = Backbone.View.extend({
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

var DeckView = Backbone.View.extend(
{
    alwaysVisible: false,
	xCell: 0,
	yRow: 0,
	first_row: null,
	current_row: null,
	cardTemplatePromise: null,
    continues: null,
	kidsMode: false,
	tailVisibility: false,
	on_GameCard_render: null,
	on_tag: null,

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

		this.kidsMode = options.kidsMode === true;
		this.alwaysVisible = this.kidsMode;
		this.continues = options.continues;
		this.on_GameCard_render = options.on_GameCard_render;
		this.on_tag = options.on_tag;
		this.backend = options.backend;

		this.listenTo(this.collection, 'reset', this.render);
	},

	render: function()
	{
		var that = this;

		try {
			this.$el.empty();
			this.$el.addClass('game-deck');

			this.xCell = 0;
			this.yRow = 0;
			this.first_row = null;
			this.current_row = null;

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
		var cells_in_a_row = 3;

        var enormityRegular = {
            styleClass: 'game-tile-regular',
            width: 30
        };
        var enormityLarge = {
            styleClass: 'game-tile-large',
            width: 100 - 5 - enormityRegular.width * (cells_in_a_row - 1)
        };

		var widthFiller = 100 - 5 - enormityRegular.width * cells_in_a_row;

		this.xCell += 1;
		if (this.xCell === 1) {
			this.yRow += 1;
		} else {
			if (this.xCell === cells_in_a_row) {
				this.xCell = 0;
			}
		}

		var rowForCell;

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
			var filler_view =
				new FillerCellView(
					{
						width: widthFiller
					});
			var filler_el = filler_view.render().el;
			rowForCell.append(filler_el);
		}

		var enormity;
        var topLeftIsLarger = this.xCell === 1 && this.yRow === 1;
        if (topLeftIsLarger)
		{
			enormity = enormityLarge;
		}
		else
		{
			enormity = enormityRegular;
		}

		var that = this;

		this.cardTemplatePromise.done(function(template_el) {
			that.renderGameCard(template_el, oneResult, enormity, rowForCell);
		});
	},

	renderGameCard: function(template_el, oneResult, enormity, rowForCell)
	{
		var that = this;
		var on_tag_deck = function(game)
		{
			that.on_tag_deck(game);
		};
		var card_view = new GameCardView(
			{
				el: template_el.clone(),
				model: oneResult,
				enormity: enormity,
				kidsMode: this.kidsMode,
				continues: this.continues,
				on_render: this.on_GameCard_render,
				on_tag: on_tag_deck,
				backend: this.backend
			});
		var card_el = card_view.render().el;
		rowForCell.append(card_el);
	},

	renderMoreButton: function ()
	{
		if (this.yRow == 1) return;
		if (this.alwaysVisible) return;
		if (this.first_row == null) /* zero games */ return;

		var that = this;

		new MoreButtonView(
			{
				deck: that
			}
		).render().whenRendered.done(function(view)
			{
				that.first_row.append(view.el);
			});
	},

	startNewRow: function()
	{
		var row_view = new DeckRow().render();
		var row_el = row_view.el;
		var row = row_view.$el;

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

		var tails = this.$('.game-row-tail');

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
		var segment = this.$el.parent().parent();
		if (this.on_tag != null) this.on_tag(game, segment);
	}
});
