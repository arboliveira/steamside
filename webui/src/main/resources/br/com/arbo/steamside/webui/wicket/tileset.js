var Tileset = {
    _gameCard: null,
    _moreButton: null,
    _collectionPick: null,
    _tileSteamClient: null,
    _tileCollectionNew: null,

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

    tileSteamClient: function (callback) {
        "use strict";
		if (this._tileSteamClient != null) callback(this._tileSteamClient);
		var that = this;
		$.ajax({url:'SteamClient.html', success: function(html) {
			var tile = $(html);
			that._tileSteamClient = that.xml2html(tile, ".steam-client");
			callback(that._tileSteamClient);
		}, dataType: 'xml'});
    },

    tileCollectionNew: function (callback) {
        "use strict";
        if (this._tileCollectionNew != null) callback(this._tileCollectionNew);
        var that = this;
        $.ajax({url:'CollectionNew.html', success: function(html) {
            var tile = $(html);
            that._tileCollectionNew = that.xml2html(tile, ".collection-new");
            callback(that._tileCollectionNew);
        }, dataType: 'xml'});
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
