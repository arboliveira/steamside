"use strict";

var Worldchanger = Backbone.Model.extend(
	{
		worldbed_el: null,

		initialize: function(options) {
			this.worldbed_el = options.worldbed_el;
		},

		worldview: null,

		goWorld: function(world, afterwards) {
			$("body").show();

			var that = this;

			if (this.worldview == null)
			{
				if (!world.isFront()) {
					that.worldbed_el.children().hide();
				}
			}

			world.submitForView(function(view)
			{
				that.setWorldview(view, afterwards);
			});
		},

		setWorldview: function(view, afterwards) {
			if (this.worldview != null)
			{
				var $wel = this.worldview.$el;
				$wel.hide();
			}

			this.worldbed_el.append(view.$el);

			if (this.worldview != null) {
				var $vel = view.$el;
				$vel.show();
			}

			view.whenRendered.then(function()
			{
				sideshow(view.$el);
			}
			).then(afterwards);

			this.worldview = view;
		}

	});

var World = Backbone.Model.extend(
	{
		view: null,

		/**
		 * @type WorldActions
		 */
		worldActions: null,

		initialize: function(options) {
			this.worldActions = options.worldActions;
		},

		submitForView: function(whenViewReady)
		{
			this.worldActions.view_render_promise().done(whenViewReady);
		},

		respawn: function()
		{
			this.view = null;
		},

		isFront: function() {
			return this.worldActions.isFront();
		}
	});

var WorldActions = Backbone.Model.extend(
	{
		isFront: function()
		{
			return false;
		}
	}
);

