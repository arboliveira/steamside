"use strict";

var CloudModel = Backbone.Model.extend(
{

});

var CloudView = Backbone.View.extend(
{
	initialize: function(options) {
		this.cloudModel = options.cloudModel;
	},

	render: function()
	{
		var that = this;

		CommandBoxTile.whenLoaded(function(el_CommandBox) {
			that.render_cloud_CommandBox(el_CommandBox);
		});

		return this;
	},

	render_cloud_CommandBox: function(el_CommandBox)
	{
		var that = this;

		var commandBoxView = new CommandBoxView({
			el: el_CommandBox.clone(),
			placeholder_text: 'http://dontpad.com/(address to sync your Steamside data)',
			on_change_input: function(input) { that.on_cloud_change_input(input); },
			on_command: function(view) { that.on_cloud_command(view) },
			on_command_alternate: function(view) { that.on_cloud_command_alternate(view) },
			on_command_confirm: function(view) { that.on_cloud_command_confirm(view) }
		});

		var targetEl = this.$el;
		targetEl.append(commandBoxView.render().el);
	},

	on_cloud_change_input: function (input) {

	},

	on_cloud_command: function (input) {

	},

	on_cloud_command_alternate: function (input) {

	},

	on_cloud_command_confirm: function (input) {

	}
});

var Cloud_html =
{
	render_page: function ()
	{
		var cloudModel = new CloudModel();

		new CloudView({
			el: $('#CloudView'),
			cloudModel: cloudModel
		}).render();

	}
};
