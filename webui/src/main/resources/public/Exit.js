"use strict";

var ExitView = Backbone.View.extend(
{
	events: {
		"click #TryAgain": "clickTryAgain"
	},

	initialize: function(options)
	{
		this.backend = options.backend;
		this.sessionModel = options.sessionModel;
	},

	render: function () {
		var that = this;
		this.whenRendered =
			ExitView.sprite.sprite_promise().then(function(el) {
				that.render_el(el.clone());
				return that;
			});
		return this;
    },

	render_el: function(el) {
		this.$el.append(el);
		var input = this.$("#CommandLine");
		input.val(this.sessionModel.executable());
		this.backend.ajax_ajax_promise('api/exit');
	},

	clickTryAgain: function(e) {
		e.preventDefault();
		var input = this.$("#CommandLine");
		input.show();
		input.focus();
		input.select();
		this.$("#TryAgain").text("Copy to your command line:");
	},

	whenRendered: null

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder({url: 'Exit.html', selector: "#exit"}).build()
}
);

var ExitWorld = WorldActions.extend(
	{
		/**
		 * @override
		 */
		newView: function()
		{
			return new ExitView({
				sessionModel: this.attributes.sessionModel,
				backend: this.attributes.backend
			});
		}
	}
);
