"use strict";

var Tileset = Backbone.Model.extend(
{
	promise: null,

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
	}

});

var Tile = Backbone.Model.extend(
{
    selector: null,
	tileset: null,
	el_promise: null,

	initialize: function()
	{
        this.selector = this.attributes.selector;

        if (this.attributes.tileset != null)
		{
            this.tileset = this.attributes.tileset;
        }
		else if (this.attributes.url != null)
		{
            this.tileset = new Tileset({url: this.attributes.url});
        }

		if (this.tileset != null)
		{
			var that = this;

			this.el_promise = this.tileset.promise.then(
				function($xml) {
					return that.chomp_el($($xml));
				}
			);
		}
	},

	ajaxTile: function (callback_el) {
		this.el_promise.done(callback_el);
	},

	chomp_el: function($xml) {
		var element = $xml.find(this.selector);
		return this.xml2html(element);
	},

	xml2html: function(element) {
		var outer = $('<div>').append(element.clone()).remove();
		var full = outer.html();
		return $(full);
	}
});

var SteamsideTileset = {

    tileset: new Tileset({url: 'tileset.html'})

};
