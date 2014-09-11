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
	initialize: function(options)
	{
		this.backend = options.backend;
	},

	render: function () {
		this.backend.ajax_ajax_promise('api/exit');
        return this;
    }

});

