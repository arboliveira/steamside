var ErrorHandler = {
	explode: function (e)
	{
		var trace = printStackTrace({e: e});
		$("#ErrorMessageView").text(e + " #### " +trace);
		$("#ErrorBoxView").show();
	}
};

