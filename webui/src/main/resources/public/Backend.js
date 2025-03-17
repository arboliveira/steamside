import {ErrorHandler} from "#steamside/Error.js";
import {fetchSessionData} from "#steamside/data-session.js";

export function newBackendMaybeDisabledThisSession() {
	const backend = new Backend();
	void fetchAndSetSessionBackendDisabled(backend);
	return backend;
}

async function fetchAndSetSessionBackendDisabled(backend){
	const json = await fetchSessionData();
	backend.setSessionBackendDisabled(json.backoff);
}

export class Backend
{
	#sessionBackendDisabled;

	setSessionBackendDisabled(sessionBackendDisabled) {
		this.#sessionBackendDisabled = !!sessionBackendDisabled;
	}

	/**
	 * @param {Model | Collection} fetchable
	 * @return {Deferred}
	 */
	fetch_promise(fetchable)
	{
		const promise = fetchable.fetch({
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
	}

	fetch_fetch_json(collection, success)
	{
		collection.fetch({
			reset: true,
			mimeType: 'application/json',
			cache: false,
			success: success,
			error: function() { console.log(arguments); }
		});
	}

	ajax_ajax_promise(aUrl)
	{
		const promise = this.ajax_ajax_promise_2(aUrl);
	
		promise.fail(function(jqXHR, textStatus, errorThrown)
		{
			ErrorHandler.explode(errorThrown);
		});
	
		return promise;
		 
	}

	ajax_ajax_promise_2(aUrl)
	{
		let promise;

		if (this.#sessionBackendDisabled)
		{
			alert("Back end is OFF. Ignoring: \n\n" + aUrl);

			//promise = $.ajax({});
			promise = $.Deferred();

			setTimeout(function()
				{
					promise.resolve();
				},
				6000);
		}
		else {
			promise = $.ajax(aUrl);
		}

		promise.fail(function() { console.log(arguments); });

		return promise;
	}

}
