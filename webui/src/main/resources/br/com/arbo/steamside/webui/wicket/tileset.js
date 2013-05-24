var Tile = Backbone.Model.extend({
	tile: null,
	selector: null,

	initialize: function() {
		this.selector = this.attributes.selector;
	},

	ajaxTile: function (url, callback) {
		var had = this.tile;
		if (had != null) {
			callback(had);
			return;
		}
		var that = this;
		$.ajax({
			url: url,
			success: function (content) {
				var xml = $(content);
				var put = that.chomp(xml);
				callback(put);
			},
			error: function() {
				console.log(arguments);
			},
			dataType: 'xml'});
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
	_gameCard: new Tile({selector: ".game-tile"}),
	_moreButton: new Tile({selector: ".more-button"}),
	_collectionPick: new Tile({selector: ".collection-pick"}),
	_tileSteamClient: new Tile({selector: "#steam-client"}),
	_tileCollectionNew: new Tile({selector: "#collection-new"}),
	_tileCollectionEdit: new Tile({selector: "#collection-edit"}),
	_tileCommandBox: new Tile({selector: "#command-box"}),

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
		this._tileSteamClient.ajaxTile(
			'SteamClient.html',
			 callback);
    },

    tileCollectionNew: function (callback) {        "use strict";
		this._tileCollectionNew.ajaxTile(
			'CollectionNew.html',
			callback);
    },

	tileCollectionEdit: function (callback) {		"use strict";
		this._tileCollectionEdit.ajaxTile(
			'CollectionEdit.html',
			callback);
	},

	tileCommandBox: function (callback) {		"use strict";
		this._tileCommandBox.ajaxTile(
			'CommandBox.html',
			callback);
	},

	load: function (callback) {
        "use strict";
        var url = 'tileset.html';
        var that = this;
        $.ajax({
			url: url,
			success: function(template) {
				var t = $(template);
				that._gameCard.chomp(t);
				that._moreButton.chomp(t);
				that._collectionPick.chomp(t);
				callback(that);
        	},
			error: function() {
				console.log(arguments);
			},
			dataType: 'xml'});
    }
};
