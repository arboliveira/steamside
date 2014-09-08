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
	var promise = $.ajax(
		{
			url: aUrl,
			dataType: dataTypeOf(aUrl)
		}
	);

	promise.fail(function() { console.log(arguments); });

	promise.fail(function(jqXHR, textStatus, errorThrown)
	{
		ErrorHandler.explode(errorThrown);
	});

	if (BACKOFF)
	{
		promise.done(function()
			{
				alert("This would be the part where something is posted to the back end");
			}
		);
	}

	return promise;
}

function dataTypeOf(aUrl)
{
	if (BACKOFF) return 'text';

	if (aUrl.indexOf('.js') === aUrl.length - 3) return 'script';
	return 'json';
}
