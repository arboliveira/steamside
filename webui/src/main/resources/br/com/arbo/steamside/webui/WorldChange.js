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
				$wel.removeClass(
					'animated fadeInRight fadeInLeft fadeOutRight fadeOutLeft');
				$wel.addClass(
					'animated fadeOutRight steamside-animated');
				$wel.hide();
//			this.worldview.$el.remove();
			}

			this.worldbed_el.append(view.$el);

			if (this.worldview != null) {
				var $vel = view.$el;
				$vel.show();
				$vel.removeClass(
					'animated fadeInRight fadeInLeft fadeOutRight fadeOutLeft');
				$vel.addClass(
					'animated fadeInLeft steamside-animated');
			}

			this.worldview = view;
		}

	});

var World = Backbone.Model.extend(
	{
		view: null,

		worldActions: null,

		initialize: function(options) {
			this.worldActions = options.worldActions;
		},

		submitForView: function(whenViewReady)
		{
			var that = this;

			var tileLoadDone = function(tile)
			{
				if (that.view == null)
				{
					that.view = that.newView(tile);
				}

				whenViewReady(that.view);
			};

			that.tileLoad(tileLoadDone);
		},

		respawn: function()
		{
			this.view = null;
		},

		tileLoad: function (whenDone)
		{
			return this.worldActions.tileLoad(whenDone);
		},

		newView: function(tile)
		{
			return this.worldActions.newView(tile);
		},

		isFront: function() {
			return this.worldActions.isFront();
		}
	});

var WorldActions = Backbone.Model.extend(
	{
		newView: function(){},

		tileLoad: function(){},

		isFront: function()
		{
			return false;
		}
	}
);

