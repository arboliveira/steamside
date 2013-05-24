var Tileset = Backbone.Model.extend({
	url: null,

	initialize: function() {
		this.url = this.attributes.url;
	},

	load: function (callback) {
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
	selector: null,
	tile: null,

	initialize: function() {
		this.url = this.attributes.url;
		this.selector = this.attributes.selector;
	},

	ajaxTile: function (callback) {
		var had = this.tile;
		if (had != null) {
			callback(had);
			return;
		}
		var that = this;

		var tileset = new Tileset({url: this.url});
		tileset.load(
			function(xml) {
				var put = that.chomp(xml);
				callback(put);
			}
		);
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
	tileset: new Tileset({url: 'tileset.html'}),

	_gameCard: new Tile({selector: ".game-tile"}),
	_moreButton: new Tile({selector: ".more-button"}),
	_collectionPick: new Tile({selector: ".collection-pick"}),
	_tileSteamClient: new Tile(
		{url: 'SteamClient.html', selector: "#steam-client"}),
	_tileCollectionNew: new Tile(
		{url: 'CollectionNew.html', selector: "#collection-new"}),
	_tileCollectionEdit: new Tile(
		{url: 'CollectionEdit.html', selector: "#collection-edit"}),

    gameCard: function () {
        "use strict";
        return this._gameCard.tile;
    },
    moreButton: function () {
        "use strict";
        return this._moreButton.tile;
    },
    collectionPick: function () {
        "use strict";
        return this._collectionPick.tile;
    },

    tileSteamClient: function (callback) {        "use strict";
		this._tileSteamClient.ajaxTile(callback);
    },

    tileCollectionNew: function (callback) {        "use strict";
		this._tileCollectionNew.ajaxTile(callback);
    },

	tileCollectionEdit: function (callback) {		"use strict";
		this._tileCollectionEdit.ajaxTile(callback);
	},

	load: function (callback) {
		var that = this;
		this.tileset.load(
			function(t) {
				that._gameCard.chomp(t);
				that._moreButton.chomp(t);
				that._collectionPick.chomp(t);
				callback();
			}
		);
    }
};
