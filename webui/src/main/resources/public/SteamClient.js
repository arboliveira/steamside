import {sideshow} from "#steamside/Sideshow.js";

export const Steamside_SteamClientWorld =
{
	nameController: 'SteamClientController',

	htmlWorld: 'SteamClient.html',

	controller: function($scope, theBackend)
	{
		const model = new SteamClientStatusModel();

		new SteamClientView(
			{
				model: model,
				backend: theBackend
			}
		);

		theBackend.fetch_promise(model);
	}
};


const SteamClientStatusModel = Backbone.Model.extend(
{
	url: "api/steamclient/status.json",

	running: function() {
		const v = this.get("running");
		return (v === true) || (v === "true");
	},
	here: function() {
		const v = this.get("here");
		return (v === true) || (v === "true");
	}
});


const SteamClientView = Backbone.View.extend({

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
		const that = this;

		const modelSteamClientStatus = this.model;

		const running = modelSteamClientStatus.running();

		const status = this.$('#SteamClientStatusMessage');
		const anotherUser = this.$('#SteamClientAnotherUserMessage');
		const button = this.$('#ButtonOpenSteamClient');

		let statusVisible = false;
		let anotherVisible = false;
		let buttonText = 'Open Steam Client';

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
        const jLink = $(e.target);
        const aUrl = jLink.attr( "href" );
		this.backend.ajax_ajax_promise(aUrl);
    },

	sideshow: function()
	{
		sideshow(this.$el);
	}
}
);
