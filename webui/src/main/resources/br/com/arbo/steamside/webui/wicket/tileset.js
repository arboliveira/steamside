var Tileset = {
    _gameCard: null,
    _moreButton: null,
    _collectionPick: null,
    _tileSteamClient: null,

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
    tileSteamClient: function () {
        "use strict";
        return this._tileSteamClient;
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
            $.ajax({url:'SteamClient.html', success: function(tSteamClient) {
                var jSteamClient = $(tSteamClient);
                that._tileSteamClient = that.xml2html(jSteamClient, ".steam-client");
                callback(that);
            }, dataType: 'xml'});
        }, dataType: 'xml'});
    },
    xml2html: function(xml, selector) {
        var element = xml.find(selector);
       	var outer = $('<div>').append(element.clone()).remove();
        var full = outer.html();
        return $(full);
    }
};
