"use strict";

function fetch_json(collection, success) {
	collection.fetch({
		mimeType: 'application/json',
		cache: false,
		success: success,
		error: function() { console.log(arguments); }
	});
}

function json_promise(fetchable)
{
	var promise = fetchable.fetch({
		mimeType: 'application/json',
		cache: false
	});

	promise.fail(function() { console.log(arguments); });

	promise.fail(function(jqXHR, textStatus, errorThrown)
	{
		ErrorHandler.explode(errorThrown);
	});

	return promise;
}

var BACKOFF = false;

function ajax_promise(aUrl)
{
	var promise;

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
