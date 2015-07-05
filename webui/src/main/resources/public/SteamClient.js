"use strict";

Steamside.SteamClientWorld =
{
	nameController: 'SteamClientController',

	htmlWorld: 'SteamClient.html',

	controller: function($scope, theBackend)
	{
		var model = new SteamClientStatusModel();

		new SteamClientView(
			{
				model: model,
				backend: theBackend
			}
		);

		theBackend.fetch_promise(model);
	}
};


var SteamClientStatusModel = Backbone.Model.extend(
{
	url: "api/steamclient/status.json",

	running: function() {
		var v = this.get("running");
		return (v === true) || (v === "true");
	},
	here: function() {
		var v = this.get("here");
		return (v === true) || (v === "true");
	}
});


var SteamClientView = Backbone.View.extend({

	el: "#steam-client",

    events: {
		// any Steam Protocol button
        "click .button-steam-browser-protocol": "buttonSteamBrowserProtocolClicked"
    },

	initialize: function(options)
	{
		this.backend = options.backend;

		this.listenTo(this.model, 'sync', this.render);
	},

	render: function () {
		var that = this;

		var modelSteamClientStatus = this.model;

		var running = modelSteamClientStatus.running();

		var status = this.$('#SteamClientStatusMessage');
		var anotherUser = this.$('#SteamClientAnotherUserMessage');
		var button = this.$('#ButtonOpenSteamClient');

		var statusVisible = false;
		var anotherVisible = false;
		var buttonText = 'Open Steam Client';

		if (running)
		{
			if (modelSteamClientStatus.here())
			{
				statusVisible = true;
				status.text('Steam is running.');
			}
			else
			{
				anotherVisible = true;
				buttonText = 'Reopen Steam Client here';
			}
		}
		else
		{
			statusVisible = true;
			status.text('Steam is not running.');
		}

		if (statusVisible) status.show(); else status.hide();
		if (anotherVisible) anotherUser.show(); else anotherUser.hide();

		button.show();

		button.text(buttonText);

		that.sideshow();

		return that;
	},

    buttonSteamBrowserProtocolClicked: function (e) {
        e.preventDefault();
        var jLink = $(e.target);
        var aUrl = jLink.attr( "href" );
		this.backend.ajax_ajax_promise(aUrl);
    },

	sideshow: function()
	{
		sideshow(this.$el);
	}
}
);
