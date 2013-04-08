var Tileset = {
    _gameCard: null,
    _moreButton: null,
    _collectionPick: null,

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
        if (false) return element;
        var full = element.wrap('<div></div>').parent().html();
        var html = $(full);
        return html;
    }
};
