import {KidsSettingsView} from "#steamside/KidsSettings.js";
import {CloudModel, CloudView} from "#steamside/Cloud.js";
import {KidsCollection} from "#steamside/Kids.js";
import {sideshow} from "#steamside/Sideshow.js";

export const Steamside_SettingsWorld =
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



const SettingsView = Backbone.View.extend({

	el: "#SettingsView",

	initialize: function(options)
	{
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
		const that = this;

		const model = new CloudModel();

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
		const that = this;

		const kidsCollection = new KidsCollection();

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
