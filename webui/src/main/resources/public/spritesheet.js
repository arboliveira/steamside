"use strict";

var SpriteSheet = Backbone.Model.extend(
	{
		initialize: function()
		{
			this.promise = $.ajax({
				url: this.attributes.url,
				dataType: 'xml'
			});

			this.promise.fail(
				function() {
					console.log(arguments);
				}
			);
		},

		/**
		 * @param selector String
		 * @returns Sprite
		 */
		sprite: function(selector) {
			var that = this;
			var sprite_promise =
				this.promise.then(
					function(document) {
						var $xml = $(document);
						var element = $xml.find(selector);
						var outer = $('<div>').append(element.clone()).remove();
						var full = outer.html();
						return $(full);
					}
				);
			return new Sprite({promise: sprite_promise});
		},

		dispose: function() {
			this.promise = null;
		},

		promise: null
	}
);

var Sprite = Backbone.Model.extend(
	{
		/**
		 * @private
		 */
		_promise: null,

		initialize: function(a)
		{
			this._promise = a.promise;
		},

		/**
		 * @public
		 * @return Deferred
		 */
		sprite_promise: function() {
			return this._promise;
		}
	}
);

var SpriteBuilder = Backbone.Model.extend(
	{
		/**
		 * @returns Sprite
		 */
		build: function() {
			var sheet = new SpriteSheet({url: this.attributes.url});
			try {
				return sheet.sprite(this.attributes.selector);
			}
			finally {
				sheet.dispose();
			}
		}
	}
);
