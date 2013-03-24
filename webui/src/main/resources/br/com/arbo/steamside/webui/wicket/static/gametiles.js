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

var GamecardView = Backbone.View.extend({
	continues: null,
	cellwidth: 0,
	events: {
		"click .game-link"         : "gameClicked"
	},

	initialize: function() {		"use strict";
		this.continues = this.options.continues;
		this.cellwidth = this.options.cellwidth;
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
		this.$el.width(this.cellwidth.toString() + "%");
		this.$('.game-name').text(name);
		this.$('.game-img').attr('src', img);
		this.$('.game-link').attr('href', link);

		return this;		
	},
	
	gameClicked: function(e) {				"use strict";
		e.preventDefault();

		var aUrl = this.model.link();
		var gametile_loading_underlay = this.$el.find('.game-tile-inner');
		var gametile_loading_overlay = this.$el.find('.game-tile-inner-overlay');
		var vcontinue = this.continues;

		var vdatatype;
		if (aUrl.indexOf('.js') === aUrl.length - 3) {
			vdatatype = 'script'; 
		} else {
			vdatatype = 'json';
		}
		$.ajax({
				url: aUrl,
				dataType: vdatatype,
				beforeSend: function(){
					gametile_loading_overlay.show();
					gametile_loading_underlay.addClass('game-tile-inner-blurred');
				},
				complete: function(){
					gametile_loading_overlay.hide();
					gametile_loading_underlay.removeClass('game-tile-inner-blurred');
					vcontinue.fetch();
				}						
		});
	}
});

var FillerCellView = Backbone.View.extend({
	fillerwidth: 0,
	
	initialize: function() {		"use strict";
		this.fillerwidth = this.options.fillerwidth;
	},

	render: function() {		"use strict";
		this.$el.html('&nbsp;');
		this.$el.width(this.fillerwidth.toString() + "%");
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
		this.$('.more-button-link').text(this.hiding ? 'more...' : 'less...');
	}
});

var AppCollectionView = Backbone.View.extend({
	session: null,
	gametileEl: null,
	morebutton_template: null,
	tilesEl: null,
	celln: 0,
	rown: 0,
	deck: null,
	first_row: null,
	current_row: null,

	initialize: function() {		"use strict";
		this.session = this.options.session;
		this.gametileEl = this.options.gametileEl;
		this.morebutton_template = this.options.morebutton_template;
		this.tilesEl = this.$('.game-tiles');
		this.collection.on('reset', this.render, this);
	},

	render: function() {		"use strict";
		this.tilesEl.empty();
		this.celln = 0;
		this.rown = 0;		
		this.deck = new Deck();
		this.first_row = null;
		this.current_row = null;
		
		var thisview = this;
		this.collection.each( function(oneResult) {
			thisview.renderOneCell(oneResult);
		});
		this.deck.display();

		var morebutton = new MoreButtonView({
			el: this.morebutton_template.clone(),
			deck: this.deck
		});
		this.first_row.append(morebutton.render().el);
	},
	
	renderOneCell: function(
		oneResult
		) { "use strict";

		var xcells = 3;
		var regularwidth = 30;
		var largewidth = 100 - 5 - regularwidth * (xcells - 1);
		var fillerwidth = 100 - 5 - regularwidth * xcells;

		/*
			visible will not be part of the result anymore,
			because we want logic like "first row is visible"
			and this depends on calculations inside the browser
		*/
		var alwaysvisible = this.session.kidsmode();

		this.celln += 1;
		if (this.celln === 1) {
			this.rown += 1;
		} else {
			if (this.celln === xcells) {
				this.celln = 0;
			}
		}
		
		if (this.celln === 1) {
			this.startNewRow();
		}

		if (this.celln === 1 && this.rown > 1) {
			var fillerview = 
				new FillerCellView({
					fillerwidth: fillerwidth	
				});
			this.deck.push(fillerview, alwaysvisible);
			var fillerel = fillerview.render().el;
			this.current_row.append(fillerel);
		}

		var cellwidth;
		if (this.celln === 1 && this.rown === 1) {
			cellwidth = largewidth;
		} else {
			cellwidth = regularwidth;
		}

		// TODO Must receive the 'continue' collection, this could be 'favorites' or 'search results'
		var vcontinue = this.collection;

		var gamecardview = new GamecardView({
			model: oneResult,
			el: this.gametileEl.clone(),
			continues: vcontinue,
			cellwidth: cellwidth
		});
		this.deck.push(gamecardview, alwaysvisible || this.rown === 1);
		var gamecard_el = gamecardview.render().el;
		this.current_row.append(gamecard_el);
	},
	
	startNewRow: function() {        "use strict";
		var row_view = new DeckRow().render();
		var row_el = row_view.el;
		var row = row_view.$el;
		row.show();
		this.tilesEl.append(row_el);
		this.current_row = row;
		if (this.first_row === null) {
			this.first_row = row;
		}
	}
});
