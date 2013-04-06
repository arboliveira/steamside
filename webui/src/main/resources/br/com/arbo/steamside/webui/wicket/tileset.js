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
            that._gameCard   = t.find(".game-tile");
            that._moreButton = t.find(".more-button");
            that._collectionPick = t.find(".collection-pick");
            callback(that);
        }, dataType: 'xml'});
    }
};
