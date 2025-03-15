export const ErrorHandler =
{
	calm: function (message)
	{
		$("#ErrorMessageView").text(message);
		
		const $box = $("#ErrorBoxView");
		
		$box.show();
		
		$('html, body').scrollTop($box.offset().top);
	},	

	explode: function (e)
	{
		console.log(e);

		const trace = printStackTrace({e: e});
		
		$("#ErrorMessageView").text(e + " #### " +trace);
		
		const $box = $("#ErrorBoxView");
		
		$box.show();
		
		$('html, body').scrollTop($box.offset().top);
	},

	toErrorString: function (error) {
		if (error.responseJSON !== undefined)
		{
			return error.responseJSON.message;
		}
		return error.status + ' ' + error.statusText + '\n' + error.responseText;
	},


};

