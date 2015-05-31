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
	events: {
		"click #TryAgain": "clickTryAgain",
	},

	initialize: function(options)
	{
		this.backend = options.backend;
		this.sessionModel = options.sessionModel;
	},

	render: function () {
		var input = this.$("#CommandLine");
		input.val(this.sessionModel.executable());
		this.backend.ajax_ajax_promise('api/exit');
        return this;
    },

	clickTryAgain: function(e) {
		e.preventDefault();
		var input = this.$("#CommandLine");
		input.show();
		input.focus();
		input.select();
		this.$("#TryAgain").text("Copy to your command line:");
	}

});

