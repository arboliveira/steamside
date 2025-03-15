import {SessionModel} from "#steamside/session.js";

export const Steamside_ExitWorld =
{
	nameController: 'ExitController',

	htmlWorld: 'Exit.html',

	controller: function($scope, theBackend)
	{
		const sessionModel = new SessionModel();

		const view = new ExitView(
			{
				sessionModel: sessionModel,
				backend: theBackend
			}
		);

		theBackend.fetch_promise(sessionModel);

		theBackend.ajax_ajax_promise('api/exit');
	}
};


const ExitView = Backbone.View.extend(
{
	el: "#exit",

	events: {
		"click #TryAgain": "clickTryAgain"
	},

	initialize: function(options)
	{
		this.backend = options.backend;
		this.sessionModel = options.sessionModel;
	},

	render: function () {
		const input = this.$("#CommandLine");
		input.val(this.sessionModel.executable());
	},

	clickTryAgain: function(e) {
		e.preventDefault();
		const input = this.$("#CommandLine");
		input.show();
		input.focus();
		input.select();
		this.$("#TryAgain").text("Copy to your command line:");
	}

}
);

