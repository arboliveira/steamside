function fetch_json(collection) {
    collection.fetch({
        mimeType: 'application/json',
        cache: false,
        success: function() { /* console.log(collection); */ },
        error: function() { console.log(arguments); }
    });
}

var Game = Backbone.Model.extend({
    appid : function() {		"use strict";
		return this.get('appid');
    },
    name: function() {		"use strict";
		return this.get('name');
    },
    link: function() {		"use strict";
		return this.get('link');
    },
    size: function() {		"use strict";
		return this.get('size');
    },
    visible: function() {		"use strict";
		return this.get('visible') === 'true';
    }
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

var GameCardView = Backbone.View.extend({
	continues: null,
	width: 0,
	events: {
		"click .game-link"         : "gameClicked"
	},

	initialize: function() {		"use strict";
		this.continues = this.options.continues;
		this.width = this.options.width;
		this.setElement(Tileset.gameCard().clone());
	},
	
	render: function () {		"use strict";
		var appid = this.model.appid();
		var name = this.model.name();
		var link = this.model.link();
		var size = this.model.size();
		var img = 
			'http://cdn.steampowered.com/v/gfx/apps/' 
			+ appid + '/header.jpg';

		this.$el.hide();
		this.$el.addClass('game-tile-' + size);
		this.$el.width(this.width.toString() + "%");
		this.$('.game-name').text(name);
		this.$('.game-img').attr('src', img);
		this.$('.game-link').attr('href', link);

		return this;		
	},
	
	gameClicked: function(e) {				"use strict";
		e.preventDefault();

		var aUrl = this.model.link();
		var loading_underlay = this.$el.find('.game-tile-inner');
		var loading_overlay = this.$el.find('.game-tile-inner-overlay');
		var that = this;

		var type;
		if (aUrl.indexOf('.js') === aUrl.length - 3) {
			type = 'script';
		} else {
			type = 'json';
		}
		$.ajax({
				url: aUrl,
				dataType: type,
				beforeSend: function(){
					loading_overlay.show();
					loading_underlay.addClass('game-tile-inner-blurred');
				},
				complete: function(){
					loading_overlay.hide();
					loading_underlay.removeClass('game-tile-inner-blurred');
					fetch_json(that.continues);
				}						
		});
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
		this.setElement(Tileset.moreButton().clone());
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
	session: null,
	xCell: 0,
	yRow: 0,
	deck: null,
	first_row: null,
	current_row: null,
    continues: null,

	initialize: function() {		"use strict";
		this.session = this.options.session;
        this.continues = this.options.continues;
		this.collection.on('reset', this.render, this);
	},

	render: function() {		"use strict";
		this.$el.empty();
		this.xCell = 0;
		this.yRow = 0;
		this.deck = new Deck();
		this.first_row = null;
		this.current_row = null;
		
		var that = this;
		this.collection.each( function(oneResult) {
			that.renderOneCell(oneResult);
		});
		this.deck.display();

		var moreButton = new MoreButtonView({
			deck: this.deck
		});
		this.first_row.append(moreButton.render().el);
		return this;
	},
	
	renderOneCell: function(
		oneResult
		) { "use strict";

		var cells_in_a_row = 3;
		var widthRegular = 30;
		var widthLarge = 100 - 5 - widthRegular * (cells_in_a_row - 1);
		var widthFiller = 100 - 5 - widthRegular * cells_in_a_row;

		/*
			visible will not be part of the result anymore,
			because we want logic like "first row is visible"
			and this depends on calculations inside the browser
		*/
		var alwaysVisible = this.session.kidsmode();

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
			this.deck.push(filler_view, alwaysVisible);
			var filler_el = filler_view.render().el;
			this.current_row.append(filler_el);
		}

		var width;
        var topLeftIsLarger = this.xCell === 1 && this.yRow === 1;
        if (topLeftIsLarger) {
			width = widthLarge;
		} else {
			width = widthRegular;
		}

		var that_continue = this.continues;

		var card_view = new GameCardView({
			model: oneResult,
			continues: that_continue,
			width: width
		});
		this.deck.push(card_view, alwaysVisible || this.yRow === 1);
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
	}
});
