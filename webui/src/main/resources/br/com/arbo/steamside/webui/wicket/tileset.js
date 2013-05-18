var Tileset = {
    _gameCard: null,
    _moreButton: null,
    _collectionPick: null,
    _tileSteamClient: new Object,
    _tileCollectionNew: new Object,
	_tileCollectionEdit: new Object,

    gameCard: function () {
        "use strict";
        return this._gameCard;
    },
    moreButton: function () {
        "use strict";
        return this._moreButton;
    },
    collectionPick: function () {
        "use strict";
        return this._collectionPick;
    },

    tileSteamClient: function (callback) {        "use strict";
		this.ajaxTile(
			'SteamClient.html', "#steam-client",
			this._tileSteamClient, callback);
    },

    tileCollectionNew: function (callback) {        "use strict";
		this.ajaxTile(
			'CollectionNew.html', "#collection-new",
			this._tileCollectionNew, callback);
    },

	tileCollectionEdit: function (callback) {		"use strict";
		this.ajaxTile(
			'CollectionEdit.html', "#collection-edit",
			this._tileCollectionEdit, callback);
	},

	ajaxTile: function (url, selector, holder, callback) {
		var had = holder.tile;
		if (had != null) {
			callback(had);
			return;
		}
		var that = this;
		$.ajax({url: url, success: function (content) {
			that.storeTile(content, selector, holder, callback);
		}, dataType: 'xml'});
	},

	storeTile: function(content, selector, holder, callback) { "use strict";
		var xml = $(content);
		var put = this.xml2html(xml, selector);
		holder.tile = put;
		callback(put);
	},

	load: function (callback) {
        "use strict";
        var path = 'tileset.html';
        var that = this;
        $.ajax({url:path, success: function(template) {
            var t = $(template);
            that._gameCard   = that.xml2html(t, ".game-tile");
            that._moreButton = that.xml2html(t, ".more-button");
            that._collectionPick = that.xml2html(t, ".collection-pick");
			callback(that);
        }, dataType: 'xml'});
    },
    xml2html: function(xml, selector) {
        var element = xml.find(selector);
       	var outer = $('<div>').append(element.clone()).remove();
        var full = outer.html();
        return $(full);
    }
};
