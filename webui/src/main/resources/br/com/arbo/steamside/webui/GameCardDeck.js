var Game = Backbone.Model.extend({

	initialize: function( opts ){
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
	},

	play: function() {
		var aUrl = this.link();
		var that = this;

		$.ajax({
			url: aUrl,
			dataType: dataTypeOf(aUrl),
			beforeSend: function(){
				that.trigger('game:play:beforeSend');
			},
			complete: function(){
				that.trigger('game:play:complete');
			}
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

var DeckCell = Backbone.View.extend({
	view: null,
	alwaysVisible: false,
	visible: false,
	
	initialize: function() {		"use strict";
		this.view = this.options.view;
		this.alwaysVisible = this.options.alwaysVisible;
		this.visible = this.alwaysVisible;
	},
			
	toggleVisibility: function() {		"use strict";
		if (this.alwaysVisible) {
			this.visible = true;
		} else {
			this.visible = !this.visible;
		}
		this.display();		
	},
	
	display: function() {		"use strict";
		var el = this.view.$el;
		if (this.visible) {
			el.fadeIn();
		} else { 
			el.fadeOut();
		}
	}
});

var DeckRow = Backbone.View.extend({
	className: 'game-row'
});

var Deck = Backbone.Model.extend({
	deck: null,

	initialize: function() {		"use strict";
		this.deck = [];
	},

	push: function(view, alwaysVisible) {		"use strict";
		this.deck.push(
			new DeckCell({
				view: view,
				alwaysVisible: alwaysVisible
			})
		);
	},
	
	display: function() {		"use strict";
		var i;
		for (i = 0; i < this.deck.length; i += 1) {
			this.deck[i].display();
		}
	},
	
	toggleVisibility: function() {		"use strict";
		var i;
		for (i = 0; i < this.deck.length; i += 1) {
			this.deck[i].toggleVisibility();
		}
	}
});


var Game_Tag_View = Backbone.View.extend({

	events: {
		"click .game-tag-name": "tagClicked"
	},

	render: function() {
		var name_el = this.$el.find(".game-tag-name");
		name_el.text(this.model.name());
		return this;
	},

	tagClicked: function(e) {
		e.preventDefault();
		var name = this.model.name();

		Backbone.history.navigate(
				"#/collections/" + name + "/edit",
			{trigger: true});
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

	events: {
		"mouseenter .game-link": "mouseenter_hot_zone",
        "mouseleave .game-link": "mouseleave_hot_zone",
        "click .game-link": "gameClicked",
        "click .game-tile-play": "playClicked",
        "click .game-tile-tag": "tagClicked"
	},

	initialize: function() {		"use strict";
		this.continues = this.options.continues;
		this.enormity = this.options.enormity;
		this.on_render = this.options.on_render;
		this.on_tag = this.options.on_tag;
		this.model.on('game:play:beforeSend', this.game_play_beforeSend, this);
		this.model.on('game:play:complete', this.game_play_complete, this);
	},

	render: function () {		"use strict";
		var name = this.model.name();
		var link = this.model.link();
		var img = this.model.image();
        var store = this.model.store();

		this.$el.hide();
		this.$el.addClass(this.enormity.styleClass);
		this.$el.width(this.enormity.width.toString() + "%");
		this.$('.game-name').text(name);
		this.$('.game-img').attr('src', img);
		this.$('.game-link').attr('href', link);
        this.$('.game-tile-store').attr('href', store);

        if (this.model.unavailable()) {
        	this.$el.addClass('game-unavailable');
        	this.$('.game-tile-play').hide();
        }

		var tags = this.model.tagsCollection();
		new Game_Tag_ListView({
			el: this.$("#game-tag-list"),
			collection: tags
		}).render();

		if (this.on_render != null) this.on_render(this);

		return this;		
	},

	whatWillHappen: function () {
		var $game = this.$('.game-tile-command');
		if ($game.length == 0) return null;
		return $game.first();
	},

	mouseenter_hot_zone: function(e) {
        e.preventDefault();
		var whatWillHappen = this.whatWillHappen();
		whatWillHappen.addClass('what-will-happen');
    },

    mouseleave_hot_zone: function(e) {
        e.preventDefault();
		var whatWillHappen = this.whatWillHappen();
		whatWillHappen.removeClass('what-will-happen');
    },

    gameClicked: function(e) {				"use strict";
		e.preventDefault();
		var w = this.whatWillHappen();
		if (w != null)
		{
			w.click();
			return;
		}
		this.playClicked(e);
	},

	playClicked: function(e) {				"use strict";
		e.preventDefault();
		this.model.play();
	},

	tagClicked: function(e) {				"use strict";
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
    },

    hideOverlay: function() {
        var loading_overlay = this.$('.game-tile-inner-loading-overlay');
        var loading_underlay = this.$('.game-tile-inner');
        loading_overlay.hide();
        loading_underlay.removeClass('game-tile-inner-blurred');
    },

    redisplay_continues: function () {		"use strict";
        fetch_json(this.continues);
    }
});

var FillerCellView = Backbone.View.extend({
	width: 0,
	
	initialize: function() {		"use strict";
		this.width = this.options.width;
	},

	render: function() {		"use strict";
		this.$el.html('&nbsp;');
		this.$el.width(this.width.toString() + "%");
		this.$el.addClass('game-cell-filler');
		return this;
	}
});

var MoreButtonView = Backbone.View.extend({
	hiding: true,
	deck: null,
	
	events: {
		"click" : "moreClicked"
	},
	
	initialize: function() {		"use strict";
		this.deck = this.options.deck;
	},

	render: function() {		"use strict";
		this.textRefresh();
		this.$el.fadeIn();
		return this;
	},
	
	moreClicked: function(e) {		"use strict";
		e.preventDefault();
		this.toggle();
	},

	toggle: function() {		"use strict";
		this.deck.toggleVisibility();
		this.hiding = !this.hiding;
		this.textRefresh();
	},
	
	textRefresh: function() {		"use strict";
		this.$('.more-button-text').text(this.hiding ? 'more...' : 'less...');
	}
});

var DeckView = Backbone.View.extend({
    alwaysVisible: false,
	xCell: 0,
	yRow: 0,
	deck: null,
	first_row: null,
	current_row: null,
	cardTemplatePromise: null,
    continues: null,
	kidsMode: false,
	on_GameCard_render: null,
	on_tag: null,

	initialize: function() {
        /*
         visible will not be part of the result anymore,
         because we want logic like "first row is visible"
         and this depends on calculations inside the browser
         */

		if (this.options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}

		this.cardTemplatePromise = this.options.cardTemplatePromise;

		this.kidsMode = this.options.kidsMode === true;
		this.alwaysVisible = this.kidsMode;
		this.continues = this.options.continues;
		this.on_GameCard_render = this.options.on_GameCard_render;
		this.on_tag = this.options.on_tag;
		this.collection.on('reset', this.render, this);
	},

	renderMoreButton: function () {
		if (this.yRow == 1) return;
		if (this.alwaysVisible) return;
        if (this.first_row == null) /* zero games */ return;

		var that = this;
		SteamsideTileset.ajaxMoreButton(function (tile) {
			var moreButton = new MoreButtonView({
				el: tile.clone(),
				deck: that.deck
			});
			that.first_row.append(moreButton.render().el);
		});
	},

	render: function() {
		try {
			this.$el.empty();
			this.$el.addClass('game-deck');

			this.xCell = 0;
			this.yRow = 0;
			this.deck = new Deck();
			this.first_row = null;
			this.current_row = null;

			var that = this;
			this.collection.each(function (oneResult) {
				that.renderOneCell(oneResult);
			});
			this.deck.display();
			this.renderMoreButton();
		}
		catch (e)
		{
			var trace = printStackTrace({e: e});
			$("#ErrorMessageView").text(e + " #### " +trace);
			$("#ErrorBoxView").show();
			throw e;
		}
		return this;
	},
	
	renderOneCell: function(oneResult) { "use strict";

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
		
		if (this.xCell === 1) {
			this.startNewRow();
		}

		if (this.xCell === 1 && this.yRow > 1) {
			var filler_view =
				new FillerCellView({
					width: widthFiller
				});
			this.deck.push(filler_view, this.alwaysVisible);
			var filler_el = filler_view.render().el;
			this.current_row.append(filler_el);
		}

		var enormity;
        var topLeftIsLarger = this.xCell === 1 && this.yRow === 1;
        if (topLeftIsLarger) {
			enormity = enormityLarge;
		} else {
			enormity = enormityRegular;
		}

		var that = this;

		this.cardTemplatePromise.done(function(template_el) {
			that.renderGameCard(template_el, oneResult, enormity);
		});
	},

	renderGameCard: function(template_el, oneResult, enormity) {
		var that = this;
		var on_tag_deck = function(game) {
			that.on_tag_deck(game);
		};
		var card_view = new GameCardView({
			el: template_el.clone(),
			model: oneResult,
			enormity: enormity,
			kidsMode: this.kidsMode,
			continues: this.continues,
			on_render: this.on_GameCard_render,
			on_tag: on_tag_deck
		});
		this.deck.push(card_view, this.alwaysVisible || this.yRow === 1);
		var card_el = card_view.render().el;
		this.current_row.append(card_el);
	},
	
	startNewRow: function() {        "use strict";
		var row_view = new DeckRow().render();
		var row_el = row_view.el;
		var row = row_view.$el;
		row.show();
		this.$el.append(row_el);
		this.current_row = row;
		if (this.first_row === null) {
			this.first_row = row;
		}
	},

	on_tag_deck: function(game) {
		var segment = this.$el.parent().parent();
		if (this.on_tag != null) this.on_tag(game, segment);
	}
});

function fetch_json(collection, success) {
    collection.fetch({
        mimeType: 'application/json',
        cache: false,
        success: success,
        error: function() { console.log(arguments); }
    });
}

function dataTypeOf(aUrl) {
    if (aUrl.indexOf('.js') === aUrl.length - 3) return 'script';
    return 'json';
}

