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
    	var position = 0;
        this.collection.each( function(oneResult) {
	        var clonedtile = vtile.clone();
        	position++;
        	
        	var sizeclass = position == 1 ? 'large' : 'regular';
	        clonedtile.addClass('game-tile-' + sizeclass);
	        
	        clonedtile.find('.game-name').text(oneResult.get('name'));
	        
	        var appid = oneResult.get('appid');
	        var img = 
	        	'http://cdn.steampowered.com/v/gfx/apps/' 
	        	+ appid + '/header.jpg';
	        clonedtile.find('.game-img').attr('src', img);
	        
	        var link = clonedtile.find('.game-link');
	        link.attr('href', oneResult.get('link'));
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
	        clonedtile.fadeIn();
        });
    }
});
