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

	return promise;
}

function dataTypeOf(aUrl) {
	if (aUrl.indexOf('.js') === aUrl.length - 3) return 'script';
	return 'json';
}
