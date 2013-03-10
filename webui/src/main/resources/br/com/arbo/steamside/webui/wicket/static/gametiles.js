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

var AppCollectionView = Backbone.View.extend({
	session: null,
	gamerowEl: null,
	gamecellEl: null,
	gametileEl: null,
	tilesEl: null,

	initialize: function() {		"use strict";
		this.session = this.options.session;
		this.gamerowEl = this.options.gamerowEl;
		this.gamecellEl = this.options.gamecellEl;
		this.gametileEl = this.options.gametileEl;
		this.tilesEl = this.$('.game-tiles');
		this.collection.on('reset', this.render, this);
	},

	render: function() {		"use strict";
		var xcells = 3;
		var largewidth = 40;
		var regularwidth = 30;
		var fillerwidth = 100 - xcells * regularwidth;
	
		var vrow = this.gamerowEl;
		var vcell = this.gamecellEl;
		var vtile = this.gametileEl;
		var vtilesEl = this.tilesEl;
		vtilesEl.empty();

		var hiding = true;
		var hidden = [];		

		var rown = 0;		
		var celln = 0;
		var rowsep = null;
		
		var vsession = this.session;
		
		this.collection.each( function(oneResult) {
			var appid = oneResult.appid();
			var name = oneResult.name();
			var link = oneResult.link();
			var size = oneResult.size();
			var visible = vsession.kidsmode() || oneResult.visible();
			var img = 
				'http://cdn.steampowered.com/v/gfx/apps/' 
				+ appid + '/header.jpg';

			var cell = vcell.clone();
			var cellwidth;

			celln += 1;
			if (celln === 1) {
				rown += 1;
				if (rown === 1) { 
					cellwidth = largewidth;
				} else {
					rowsep = vrow.clone();
					rowsep.show();
					vtilesEl.append(rowsep);
					var filler = vcell.clone();
					filler.html('&nbsp;');
					filler.width(fillerwidth.toString() + "%");
					filler.show();
					vtilesEl.append(filler);
					cellwidth = regularwidth;
				}
			} else {
				cellwidth = regularwidth;
				if (celln === xcells) {
					celln = 0;
				}
			}

			var clonedtile = vtile.clone();
			clonedtile.find('.game-name').text(name);
			clonedtile.addClass('game-tile-' + size);
			clonedtile.find('.game-img').attr('src', img);
			var gametile_loading_underlay = clonedtile.find('.game-tile-inner');
			var gametile_loading_overlay = clonedtile.find('.game-tile-inner-overlay');
			var gamelink = clonedtile.find('.game-link');
			gamelink.attr('href', link);
			gamelink.click(function(e) {
				var jLink = $( this );
				var aUrl = jLink.attr( "href" );
				$.ajax({
						url: aUrl,
						beforeSend: function(){
							gametile_loading_overlay.show();
							gametile_loading_underlay.addClass('game-tile-inner-blurred');
						},
						complete: function(){
							gametile_loading_overlay.hide();
							gametile_loading_underlay.removeClass('game-tile-inner-blurred');
						}						
				});
				e.preventDefault();
			});

			cell.width(cellwidth.toString() + "%");
			cell.append(clonedtile);
			vtilesEl.append(cell);
			cell.show();
			
			if (visible) {
				clonedtile.fadeIn();
			} else {
				hidden.push(clonedtile);
			}
		});

		var button = $('#continue-more-button');
		var morelink = button.find('.more-button-link');
		morelink.text(hiding ? 'more...' : 'less...');
		morelink.click(function(e) {
			var i;
			for (i = 0; i < hidden.length; i += 1) {
				if (hiding) {
					hidden[i].fadeIn();
				} else { 
					hidden[i].fadeOut();
				}
			}
			hiding = !hiding;
			morelink.text(hiding ? 'more...' : 'less...');
			e.preventDefault();
		});
		vtilesEl.append(button);
		button.fadeIn();
	}
});
