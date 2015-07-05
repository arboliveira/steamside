"use strict";

Steamside.MyGamesWorld =
{
	nameController: 'MyGamesController',

	htmlWorld: 'MyGames.html',

	controller: function($scope, theBackend)
	{
		new MyGamesView(
			{
				backend: theBackend
			}
		).render();
	}
};


var MyGamesView = Backbone.View.extend(
{
	el: "#MyGamesView",

	initialize: function(options)
	{
		this.backend = options.backend;
	},

	render: function () {
		var that = this;

		that.$("#CollectionPickView").append(
			new CollectionPickView(
				{
					backend: that.backend,
					purposeless: true
				}
			).render().el
		);

		that.sideshow();

		return this;
	},

	sideshow: function()
	{
		sideshow(this.$el);
	}
}
);
