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
	}
};

