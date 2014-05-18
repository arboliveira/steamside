var Tileset = Backbone.Model.extend({
	promise: null,

    initialize: function() {
		this.promise = $.ajax({
			url: this.attributes.url,
			dataType: 'xml'
		});
		this.promise.fail(
			function() {
				console.log(arguments);
			}
		);
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
		if (this.tileset != null) {
			var that = this;
			this.tileset.promise.done(
				function($xml) {
					that.chomp($($xml));
				}
			);
		}
	},

	ajaxTile: function (callback) {
		var that = this;
		this.tileset.promise.done(
			function() {
				callback(that.tile);
			}
		);
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

    tileset: new Tileset({url: 'tileset.html'}),

	loadTileset: function() {
		var that = this;
		this.tileset.promise.done(
			function($xml) {
				that.on_load($($xml));
			}
		);
		return this.tileset.promise;
	},

	on_load: function ($xml) {
		this._gameCard = new Tile({selector: ".game-tile"});
		this._moreButton = new Tile({selector: ".more-button"});
		this._collectionPick = new Tile({selector: ".collection-pick"});
		this._gameCard.chomp($xml);
		this._moreButton.chomp($xml);
		this._collectionPick.chomp($xml);
	},

    ajaxGameCard: function (callback) {
		var that = this;
		this.tileset.promise.done(
			function() {
				callback(that._gameCard.tile);
			}
		);
    },

    ajaxMoreButton: function (callback) {
		var that = this;
		this.tileset.promise.done(
			function() {
				callback(that._moreButton.tile);
			}
		);
    },

    ajaxCollectionPick: function (callback) {
		var that = this;
		this.tileset.promise.done(
			function() {
				callback(that._collectionPick.tile);
			}
		);
    }

};