"use strict";

var ErrorHandler =
{
	calm: function (message)
	{
		$("#ErrorMessageView").text(message);
		
		var $box = $("#ErrorBoxView");
		
		$box.show();
		
		$('html, body').scrollTop($box.offset().top);
	},	

	explode: function (e)
	{
		console.log(e);

		var trace = printStackTrace({e: e});
		
		$("#ErrorMessageView").text(e + " #### " +trace);
		
		var $box = $("#ErrorBoxView");
		
		$box.show();
		
		$('html, body').scrollTop($box.offset().top);
	},

	toString: function (error) {
		var message;
		if (error.responseJSON != undefined)
		{
			return error.responseJSON.message;
		}
		return error.status + ' ' + error.statusText + '\n' + error.responseText;
	},


};

