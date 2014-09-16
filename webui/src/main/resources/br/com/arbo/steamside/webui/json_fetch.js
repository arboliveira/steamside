"use strict";

var Backend = Backbone.Model.extend(
{
	backoff: false,

	fetch_promise: function(fetchable)
	{
		var promise = fetchable.fetch({
			reset: true,
			mimeType: 'application/json',
			cache: false
		});

		promise.fail(function() { console.log(arguments); });

		promise.fail(function(jqXHR, textStatus, errorThrown)
		{
			ErrorHandler.explode(errorThrown);
		});

		return promise;
	},

	fetch_fetch_json: function(collection, success)
	{
		collection.fetch({
			reset: true,
			mimeType: 'application/json',
			cache: false,
			success: success,
			error: function() { console.log(arguments); }
		});
	},

	ajax_ajax_promise: function(aUrl)
	{
		var promise;

		if (this.is_backoff())
		{
			alert("Back end is OFF. Ignoring: \n\n" + aUrl);

			promise = $.ajax({});
		}
		else {
			promise = $.ajax(aUrl);
		}

		promise.fail(function() { console.log(arguments); });

		promise.fail(function(jqXHR, textStatus, errorThrown)
		{
			ErrorHandler.explode(errorThrown);
		});

		return promise;
	},

	set_backoff: function(value)
	{
		this.backoff = value;
	},

	is_backoff: function()
	{
		return this.backoff;
	}
});
