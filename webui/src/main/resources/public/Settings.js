"use strict";


Steamside.SettingsWorld =
{
	nameController: 'SettingsController',

	htmlWorld: 'Settings.html',

	controller: function($scope, theBackend)
	{
		new SettingsView(
			{
				backend: theBackend
			}
		).render();
	}
};



var SettingsView = Backbone.View.extend({

	el: "#SettingsView",

	initialize: function(options)
	{
		var that = this;
		this._backend = options.backend;
	},

	render: function ()
	{
		this.renderCloud();
		this.renderKids();
		this.sideshow();
		return this;
	},

	renderCloud: function () {
		var that = this;

		var model = new CloudModel();

		that.$("#CloudView-goes-here")
			.append(
				new CloudView({
					model: model,
					backend: that._backend
				})
					.render().el
			);
	},

	renderKids: function () {
		var that = this;

		var kidsCollection = new KidsCollection();

		that._backend.fetch_promise(kidsCollection);

		that.$("#KidsSettingsView-goes-here")
			.append(
				new KidsSettingsView({
					collection: kidsCollection,
					backend: that._backend
				})
					.render().el
			);

		return that;
	},

	sideshow: function()
	{
		sideshow(this.$el);
	},

	/**
	 * @type Backend
	 */
	_backend: null
}
);
