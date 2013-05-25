var Tileset = Backbone.Model.extend({
	url: null,

	initialize: function() {
		this.url = this.attributes.url;
	},

	ajaxTileset: function (callback) {
		var that = this;
		$.ajax({
			url: that.url,
			success: function(template) {
				var xml = $(template);
				callback(xml);
			},
			error: function() {
				console.log(arguments);
			},
			dataType: 'xml'});
	}
});

var Tile = Backbone.Model.extend({
	url: null,
	tileset: null,
	selector: null,
	tile: null,

	initialize: function() {
		this.url = this.attributes.url;
		if (this.url != null)
			this.tileset = new Tileset({url: this.url});
		this.selector = this.attributes.selector;
	},

	ajaxTile: function (callback) {
		var had = this.tile;
		if (had != null) {
			callback(had);
			return;
		}
		var that = this;

		this.tileset.ajaxTileset(function(xml) {
			var put = that.chomp(xml);
			callback(put);
		});
	},

	chomp: function(xml) {
		var element = xml.find(this.selector);
		this.tile = this.xml2html(element);
		return this.tile;
	},

	xml2html: function(element) {
		var outer = $('<div>').append(element.clone()).remove();
		var full = outer.html();
		return $(full);
	}
});

var SteamsideTileset = {
	loaded: false,

	_gameCard: new Tile({selector: ".game-tile"}),
	_moreButton: new Tile({selector: ".more-button"}),
	_collectionPick: new Tile({selector: ".collection-pick"}),

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
		if (this.loaded) { callback(); return; }
		var that = this;
		var tileset = new Tileset({url: 'tileset.html'});
		tileset.ajaxTileset(function(t) {
			that._gameCard.chomp(t);
			that._moreButton.chomp(t);
			that._collectionPick.chomp(t);
			that.loaded = true;
			callback();
		});
    }
};
