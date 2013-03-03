var AppCollectionView = Backbone.View.extend({
	gamerowEl: null,
	gamecellEl: null,
	gametileEl: null,
	tilesEl: null,

    initialize: function() {
    	this.gamerowEl = this.options.gamerowEl;
    	this.gamecellEl = this.options.gamecellEl;
    	this.gametileEl = this.options.gametileEl;
    	this.tilesEl = this.$('.game-tiles');
        this.collection.on('reset', this.render, this);
    },

    render: function() {
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
		var hidden = new Object();    	

		var rown = 0;    	
    	var celln = 0;
    	var rowsep = null;
    	
        this.collection.each( function(oneResult) {
	        var appid = oneResult.get('appid');
			var name = oneResult.get('name');
			var link = oneResult.get('link');
			var size = oneResult.get('size');
			var visible = oneResult.get('visible') == 'true';
	        var img = 
	        	'http://cdn.steampowered.com/v/gfx/apps/' 
	        	+ appid + '/header.jpg';

			var cell = vcell.clone();
			var cellwidth;

			celln++;
			if (celln == 1) {
				rown++;
				if (rown == 1) { 
					cellwidth = largewidth;
				} else {
					rowsep = vrow.clone();
					rowsep.show();
					vtilesEl.append(rowsep);
					var filler = vcell.clone();
					filler.html('&nbsp;');
					filler.width("" + fillerwidth + "%");
					filler.show();
					vtilesEl.append(filler);
					cellwidth = regularwidth;
				}
			} else {
				cellwidth = regularwidth;
				if (celln == xcells) celln = 0;
			}

	        var clonedtile = vtile.clone();
	        clonedtile.find('.game-name').text(name);
	        clonedtile.addClass('game-tile-' + size);
	        clonedtile.find('.game-img').attr('src', img);
	        var link = clonedtile.find('.game-link');
	        link.attr('href', link);
	        link.click(function(e) {
	        	var jLink = $( this );
	        	var aUrl = jLink.attr( "href" );
	        	alert(aUrl);
				$.ajax(
					{
						url: aUrl
					}
				);
				e.preventDefault();
    		});

	if (true) {
			cell.width("" + cellwidth + "%");
    		cell.append(clonedtile);
	        vtilesEl.append(cell);
			cell.show();
	    } else {
			clonedtile.width("" + cellwidth + "%");
	        vtilesEl.append(clonedtile);
	    }
	        
	        if (visible) 
	        	clonedtile.fadeIn();
	        else
	        	hidden[appid] = clonedtile;
        });

        var button = $('#continue-more-button');
        var morelink = button.find('.more-button-link');
        morelink.text(hiding ? 'more...' : 'less...');
        morelink.click(function(e) {
        	for (index in hidden) {
        		if (hiding) 
        			hidden[index].fadeIn();
        		else 
        			hidden[index].fadeOut();
        	}
        	hiding = !hiding;
	        morelink.text(hiding ? 'more...' : 'less...');
			e.preventDefault();
   		});
        vtilesEl.append(button);
        button.fadeIn();
    }
});
