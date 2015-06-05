"use strict";

var Worldchanger = Backbone.Model.extend(
	{
		worldbed_el: null,

		initialize: function(options) {
			this.worldbed_el = options.worldbed_el;
		},

		worldview: null,

		goWorld: function(world) {
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
				that.setWorldview(view);
			});
		},

		setWorldview:  function(view) {
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

			sideshow(view.$el);

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
			if (this.view == null)
			{
				this.view = this.newView().render();
			}
			this.view.whenRendered.done(whenViewReady);
		},

		respawn: function()
		{
			this.view = null;
		},

		newView: function(el)
		{
			return this.worldActions.newView(el);
		},

		isFront: function() {
			return this.worldActions.isFront();
		}
	});

var WorldActions = Backbone.Model.extend(
	{
		newView: function(){},

		isFront: function()
		{
			return false;
		}
	}
);

