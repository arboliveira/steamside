"use strict";

var CloudModel = Backbone.Model.extend(
{

});

var CloudView = Backbone.View.extend(
{
	cloudModel: null,

	initialize: function(options)
	{
		this.cloudModel = options.cloudModel;
	},

	render: function()
	{
		var that = this;

		this.whenRendered = new CommandBoxView(
			{
				placeholder_text: 'http://dontpad.com/(address to sync your Steamside data)',
				on_change_input: function(input) { that.on_cloud_change_input(input); },
				on_command: function(view) { that.on_cloud_command(view) },
				on_command_alternate: function(view) { that.on_cloud_command_alternate(view) },
				on_command_confirm: function(view) { that.on_cloud_command_confirm(view) }
			}
		).render().whenRendered.then(function(view)
			{
				that.rendered_cloud_CommandBox(view);
			});

		return this;
	},

	rendered_cloud_CommandBox: function(commandBoxView) {
		var randomSuggestion = this.cloudModel.randomSuggestion();
		commandBoxView.input_query_setval(randomSuggestion);
		this.$el.append(commandBoxView.el);
	},

	on_cloud_change_input: function (input) {

	},

	on_cloud_command: function (input) {

	},

	on_cloud_command_alternate: function (input) {

	},

	on_cloud_command_confirm: function (input) {

	},

	whenRendered: null
});

var Cloud_html =
{
	render_page: function ()
	{
		var MockCloudModel = Backbone.View.extend(
			{
				randomSuggestion: function(){
				return "RANDOM_SUGGESTION";
			}
		});

		//if (false)
		new CloudView({
			el: $('#CloudView'),
			cloudModel: new MockCloudModel()
		}).render();

	}
};
