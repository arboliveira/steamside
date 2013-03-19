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
	celln: null,
	hidden: null,

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

		var hiding = true;
		this.hidden = [];		

		
		var thisview = this;
		this.rown = 0;		
		this.celln = 0;
		this.collection.each( function(oneResult) {
						
			thisview.renderOneCell(oneResult);
			
		});

		var button = $('#continue-more-button');
		var morelink = button.find('.more-button-link');
		morelink.text(hiding ? 'more...' : 'less...');
		var thishidden = this.hidden;
		morelink.click(function(e) {
			var i;
			for (i = 0; i < thishidden.length; i += 1) {
				if (hiding) {
					thishidden[i].fadeIn();
				} else { 
					thishidden[i].fadeOut();
				}
			}
			hiding = !hiding;
			morelink.text(hiding ? 'more...' : 'less...');
			e.preventDefault();
		});
		this.tilesEl.append(button);
		button.fadeIn();
	},
	
	renderOneCell: function(
		oneResult
		) { "use strict";

		var xcells = 3;
		var largewidth = 40;
		var regularwidth = 30;
		var fillerwidth = 100 - xcells * regularwidth;

		var vsession = this.session;
		var vcell = this.gamecellEl;
		var vrow = this.gamerowEl;
		var vtilesEl = this.tilesEl;
		var vtile = this.gametileEl;

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

			this.celln += 1;
			if (this.celln === 1) {
				this.rown += 1;
				if (this.rown === 1) { 
					cellwidth = largewidth;
				} else {
					var rowsep = vrow.clone();
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
				if (this.celln === xcells) {
					this.celln = 0;
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
			var thisview = this;

			// TODO Must receive the 'continue' collection, this could be the 'favorites'
			var vcontinue = this.collection;
		
			gamelink.click(function(e) {
				thisview.gameClicked(
					link, 
					gametile_loading_overlay, 
					gametile_loading_underlay,
					vcontinue
				);
				e.preventDefault();
			});

			cell.width(cellwidth.toString() + "%");
			cell.append(clonedtile);
			vtilesEl.append(cell);
			cell.show();
			
			if (visible) {
				clonedtile.fadeIn();
			} else {
				this.hidden.push(clonedtile);
			}
		
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
