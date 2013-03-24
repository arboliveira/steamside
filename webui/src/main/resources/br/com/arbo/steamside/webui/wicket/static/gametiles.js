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

var GamecardView = Backbone.View.extend({
	continues: null,
	tile_el: null,
	cellwidth: 0,
	visible: false,
	
	initialize: function() {		"use strict";
		this.continues = this.options.continues;
		this.tile_el = this.options.tile_el;
		this.cellwidth = this.options.cellwidth;
		this.visible = this.options.visible;
	},

	render: function () {		"use strict";

		var oneResult = this.model;
		var appid = oneResult.appid();
		var name = oneResult.name();
		var link = oneResult.link();
		var size = oneResult.size();

		var img = 
			'http://cdn.steampowered.com/v/gfx/apps/' 
			+ appid + '/header.jpg';

		var clonedtile = this.tile_el;
		clonedtile.find('.game-name').text(name);
		clonedtile.addClass('game-tile-' + size);
		clonedtile.find('.game-img').attr('src', img);
		var gametile_loading_underlay = clonedtile.find('.game-tile-inner');
		var gametile_loading_overlay = clonedtile.find('.game-tile-inner-overlay');
		var gamelink = clonedtile.find('.game-link');
		gamelink.attr('href', link);
		var thisview = this;
		var vcontinue = this.continues;
		gamelink.click(function(e) {
			thisview.gameClicked(
				link, 
				gametile_loading_overlay, 
				gametile_loading_underlay,
				vcontinue
			);
			e.preventDefault();
		});

		var cell = $(this.el);
		cell.width(this.cellwidth.toString() + "%");
		cell.append(clonedtile);

		return this;		
	},
	
	gameClicked: function(
		aUrl, 
		gametile_loading_overlay, 
		gametile_loading_underlay,
		vcontinue
		) { "use strict";
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
		var filler = $(this.el);
		filler.html('&nbsp;');
		filler.width(this.fillerwidth.toString() + "%");
		filler.addClass('game-cell-filler');
		return this;
	}
});

var MoreButtonView = Backbone.View.extend({
	hiding: true,
	hidden: null,
	
	initialize: function() {		"use strict";
		this.hidden = this.options.hidden;
	},

	render: function() {		"use strict";
		this.textRefresh();
		var thisview = this;
		this.moreLink().click(function(e) {
			e.preventDefault();
			thisview.toggle();
		});
		this.button().fadeIn();
	},

	toggle: function() {		"use strict";
		var thishidden = this.hidden;
		var i;
		for (i = 0; i < thishidden.length; i += 1) {
			if (this.hiding) {
				thishidden[i].fadeIn();
			} else { 
				thishidden[i].fadeOut();
			}
		}
		this.hiding = !this.hiding;
		this.textRefresh();
	},
	
	textRefresh: function() {		"use strict";
		this.moreLink().text(this.hiding ? 'more...' : 'less...');
	},

	button: function() {		"use strict";
		return $(this.el);
	},
	
	moreLink: function() {		"use strict";
		return this.button().find('.more-button-link');
	}
});

var AppCollectionView = Backbone.View.extend({
	session: null,
	gamerowEl: null,
	gamecellEl: null,
	gametileEl: null,
	tilesEl: null,
	celln: null,
	hidden: null,
	first_row: null,
	current_row: null,

	initialize: function() {		"use strict";
		this.session = this.options.session;
		this.gamerowEl = this.options.gamerowEl;
		this.gamecellEl = this.options.gamecellEl;
		this.gametileEl = this.options.gametileEl;
		this.tilesEl = this.$('.game-tiles');
		this.collection.on('reset', this.render, this);
	},

	render: function() {		"use strict";
		this.tilesEl.empty();
		this.rown = 0;		
		this.celln = 0;
		this.hidden = [];		
		var thisview = this;
		this.collection.each( function(oneResult) {
			thisview.renderOneCell(oneResult);
		});

		var more_button_template = $('#continue-more-button'); 
		var button = more_button_template.clone();
		this.first_row.append(button);
		var morebutton = new MoreButtonView({
			el: button,
			hidden: this.hidden
		});
		morebutton.render();
	},
	
	renderOneCell: function(
		oneResult
		) { "use strict";

		var xcells = 3;
		var largewidth = 40;
		var regularwidth = 30;

		var vsession = this.session;
		var vcell = this.gamecellEl;
		var vrow = this.gamerowEl;
		var vtilesEl = this.tilesEl;

		/*
			visible will not be part of the result anymore,
			because we want logic like "first row is visible"
			and this depends on calculations inside the browser
		*/
		var alwaysvisible = vsession.kidsmode();
		
		var cellwidth;
		var visible = alwaysvisible;

		this.celln += 1;
		if (this.celln === 1) {
			var rowsep = vrow.clone();
			rowsep.show();
			vtilesEl.append(rowsep);
			this.current_row = rowsep;
			  
			this.rown += 1;
			if (this.rown === 1) {
				cellwidth = largewidth;
				this.first_row = this.current_row;
			} else {
				var fillerel = new FillerCellView({
					fillerwidth: 100 - xcells * regularwidth	
				}).render().el;
				this.current_row.append(fillerel);
				var filler = $(fillerel);
				if (visible) {
					filler.fadeIn();
				} else {
					this.hidden.push(filler);
				}
				
				cellwidth = regularwidth;
			}
		} else {
			cellwidth = regularwidth;
			if (this.celln === xcells) {
				this.celln = 0;
			}
		}

		if (this.rown === 1) { 
			visible = true;
		}

		// TODO Must receive the 'continue' collection, this could be the 'favorites'
		var vcontinue = this.collection;
		var vtile = this.gametileEl;

		var clonedtile = vtile.clone();
		var clonedcell = this.gamecellEl.clone();
		
		var gamecard_el = new GamecardView({
			model: oneResult,
			el: clonedcell,
			continues: vcontinue,
			tile_el: clonedtile,
			cellwidth: cellwidth,
			current_row: this.current_row,
			visible: visible,
			hidden: this.hidden
		}).render().el;
		this.current_row.append(gamecard_el);
		var gamecard = $(gamecard_el); 
		if (visible) {
			gamecard.fadeIn();
		} else {
			this.hidden.push(gamecard);
		}
	}
	
});
