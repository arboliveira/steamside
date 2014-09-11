"use strict";

var Backend = Backbone.Model.extend(
{
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
		return ajax_promise(aUrl);
	}
});

function ajax_promise(aUrl)
{
	var promise;

	var BACKOFF = true;

	if (BACKOFF)
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
}
