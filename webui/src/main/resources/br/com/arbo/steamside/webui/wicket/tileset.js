var Tileset = Backbone.Model.extend({
	url: null,
    loaded: false,

    initialize: function() {
		this.url = this.attributes.url;
	},

	ajaxTileset: function (options) {
        if (this.loaded) { options.on_always(); return; }
		var that = this;
		$.ajax({
			url: that.url,
			success: function(template) {
                that.loaded = true;
				var $xml = $(template);
				options.on_load($xml);
                options.on_always();
			},
			error: function() {
				console.log(arguments);
			},
			dataType: 'xml'});
	}
});

var Tile = Backbone.Model.extend({
    selector: null,
	tileset: null,
	tile: null,

	initialize: function() {
        this.selector = this.attributes.selector;
        if (this.attributes.tileset != null) {
            this.tileset = this.attributes.tileset;
        } else if (this.attributes.url != null) {
            this.tileset = new Tileset({url: this.attributes.url});
        }
	},

	ajaxTile: function (callback) {
		var that = this;
		this.tileset.ajaxTileset({
            on_load: function($xml) {
		    	that.chomp($xml);
		    },
            on_always: function() {
                callback(that.tile);
            }
        });
	},

	chomp: function($xml) {
		var element = $xml.find(this.selector);
		this.tile = this.xml2html(element);
	},

	xml2html: function(element) {
		var outer = $('<div>').append(element.clone()).remove();
		var full = outer.html();
		return $(full);
	}
});

var SteamsideTileset = {
	loaded: false,

    tileset: new Tileset({url: 'tileset.html'}),

    ajaxGameCard: function (callback) {
		var that = this;
		this.ajaxTileset(
			function() {
				callback(that._gameCard.tile);
			}
		);
    },
    ajaxMoreButton: function (callback) {
		var that = this;
		this.ajaxTileset(
			function() {
				callback(that._moreButton.tile);
			}
		);
    },
    ajaxCollectionPick: function (callback) {
		var that = this;
        this.ajaxTileset(
			function() {
				callback(that._collectionPick.tile);
			}
		);
    },

    ajaxTileset: function (callback) {
        var that = this;
        this.tileset.ajaxTileset({
            on_load: function($xml) {
                that.on_load($xml);
            },
            on_always: function() {
                callback();
            }
        });
    },

    on_load: function ($xml) {
        this._gameCard = new Tile({selector: ".game-tile"});
        this._moreButton = new Tile({selector: ".more-button"});
        this._collectionPick = new Tile({selector: ".collection-pick"});
        this._gameCard.chomp($xml);
        this._moreButton.chomp($xml);
        this._collectionPick.chomp($xml);
    }
};
