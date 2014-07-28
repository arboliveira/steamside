"use strict";

var ExitTile =
{
    tile: new Tile(
        {url: 'Exit.html', selector: "#exit"}),

    ajaxTile: function (callback)
	{
        this.tile.ajaxTile(callback);
    }
};

var ExitView = Backbone.View.extend(
{
    render: function () {
        $.ajax(
            {
                url: 'api/exit'
            }
        );
        return this;
    }

});

