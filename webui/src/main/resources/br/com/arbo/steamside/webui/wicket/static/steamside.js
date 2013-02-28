var AppCollectionView = Backbone.View.extend({
	gametileEl: null,
	tilesEl: null,

    initialize: function() {
    	this.gametileEl = this.options.gametileEl;
    	this.tilesEl = this.$('.game-tiles');
        this.collection.on('reset', this.render, this);
    },

    render: function() {
    	var vtile = this.gametileEl;
    	var vtilesEl = this.tilesEl;
    	vtilesEl.empty();

		var hiding = true;
		var hidden = new Object();    	
    	
        this.collection.each( function(oneResult) {
	        var appid = oneResult.get('appid');
			var name = oneResult.get('name');
			var link = oneResult.get('link');
			var size = oneResult.get('size');
			var visible = oneResult.get('visible') == 'true';
	        var img = 
	        	'http://cdn.steampowered.com/v/gfx/apps/' 
	        	+ appid + '/header.jpg';

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
    		
	        vtilesEl.append(clonedtile);
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
