var SteamClientTile = {
	tile: new Tile(
		{url: 'SteamClient.html', selector: "#steam-client"}),

	ajaxTile: function (callback) {
		this.tile.ajaxTile(callback);
	}
};

var SteamClientView = Backbone.View.extend({

    events: {
        "click .button-steam-browser-protocol": "buttonSteamBrowserProtocolClicked"
    },

    render: function () {
        return this;
    },

    buttonSteamBrowserProtocolClicked: function (e) {
        e.preventDefault();
        var jLink = $(e.target);
        var aUrl = jLink.attr( "href" );
        $.ajax(
            {
                url: aUrl
            }
        );
    }
});

