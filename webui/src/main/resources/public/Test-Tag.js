"use strict";

var Test_Tag = Backbone.Model.extend({

	viewBeingTested: null,

	initialize: function() {
		var game = new Game({
			name: "Test Game"
		});

		var spritesSteamside = new SteamsideSpriteSheet();
		var cardTemplatePromise =  spritesSteamside.card.sprite_promise();

		this.viewBeingTested = new TagAGameView({
			game: game,
			cardTemplatePromise: cardTemplatePromise,
			backend: new Backend()
		}).render();
	},

	addTests: function (theTestRunner) {
		var that = this;
		var before = global.before;
		var describe = global.describe;
		var it = global.it;

		describe('Tag', function(){

			before(function(done)
			{
				theTestRunner.replaceTestableUI(that.viewBeingTested.$el);

				Insisting.assertRetry({
					done: done,
					condition: function()
					{
						var tagView = $("#TagAGameView");
						expect(tagView).to.have.length(1);
						expect(tagView.is(':visible')).to.equal(true);
					}
				});
			});

			describe("Tag box", function () {
				it('Tag suggestions', function(done){
					that.testTagSuggestions(done);
				})
			});

		});

	},

	testTagSuggestions: function (done)
	{
		var that = this;

		Insisting.assertRetry({
			done: done,
			condition: function ()
			{
				var tags = that.viewBeingTested.$(".display-collection-name");
				expect(tags).to.have.length(2);
				expect(tags[0].text).to.equal("Unplayed");
				expect(tags[1].text).to.equal("Favorites");
			}
		});
	}

});
