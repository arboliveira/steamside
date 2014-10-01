"use strict";

var Test_Cloud_html = Backbone.Model.extend({

	pageToTest: function()
	{
		return 'Cloud.html';
	},

	isPageLoaded: function()
	{
		return true;
	},

	addTests: function (pageLoader)
	{
		var that = this;
		var before = global.before;
		var describe = global.describe;
		var it = global.it;

		describe('Cloud', function(){

			before(function(done)
			{
				pageLoader.loadPage(that, done);
			});

			describe("Sync to the cloud", function () {
				it('Yes please', function(done){
					// TO DO
					done();
				})
			});

		});
	}

});
