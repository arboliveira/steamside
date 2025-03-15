export const SpriteSheet = Backbone.Model.extend(
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
			const that = this;
			const sprite_promise =
				this.promise.then(
					function(document) {
						const $xml = $(document);
						const element = $xml.find(selector);
						const outer = $('<div>').append(element.clone()).remove();
						const full = outer.html();
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

const Sprite = Backbone.Model.extend(
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

export const SpriteBuilder = Backbone.Model.extend(
	{
		/**
		 * @returns Sprite
		 */
		build: function() {
			const sheet = new SpriteSheet({url: this.attributes.url});
			try {
				return sheet.sprite(this.attributes.selector);
			}
			finally {
				sheet.dispose();
			}
		}
	}
);
