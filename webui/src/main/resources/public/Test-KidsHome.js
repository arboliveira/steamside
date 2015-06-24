"use strict";

var Test_KidsHome = Backbone.Model.extend({

	initialize: function()
	{

	},

	addTests: function (theTestRunner)
	{
		var that = this;
		var before = global.before;
		var describe = global.describe;
		var it = global.it;

		describe('Kids', function(){

			before(function(done)
			{
				done();
			});

			describe("Game Card", function () {
				it('Loading overlay', function(done){
					that.testLoading(done, theTestRunner);
				})
			});
		});
	},

	testLoading: function (done, theTestRunner)
	{
		var kidsSpriteSheet = new KidsSpriteSheet();

		var cardTemplatePromise = kidsSpriteSheet.card.sprite_promise();

		var expect = global.expect;

		var oneResult = new Game();
		oneResult.set("name", "Game of Kids");
		oneResult.set("link", "would be URL");

		var backend_invoked = $.Deferred();

		var MockBackend = Backbone.Model.extend(
			{
				ajax_ajax_promise_2: function(aUrl)
				{
					expect(aUrl).to.equal('would be URL');
					return backend_invoked;
				},

				fetch_fetch_json: function(collection, success)
				{

				}
			});

		var backend = new MockBackend();

		var enormity = {
			styleClass: 'game-tile-large',
			width: 100
		};

		var card_view = new GameCardView({
			cardTemplatePromise: cardTemplatePromise,
			model: oneResult,
			enormity: enormity,
			kidsMode: true,
			continues: null,
			on_render: null,
			on_tag: null,
			backend: backend
		});
		card_view.render();

		theTestRunner.replaceTestableUI(card_view.$el);

		Insisting.assertRetry({
			done: done,
			condition: function()
			{
				card_view.$el.show();
				expect($("#KidsGameCard")).to.have.length(1);
			},
			success: function()
			{
				var game_link = card_view.$('.game-link');

				// No asserts, just don't crash on mouseenter and mouseleave
				game_link.trigger('mouseenter');
				game_link.trigger('mouseleave');

				game_link.trigger('click');

				Insisting.assertRetry({
					done: done,
					condition: function () {
						var loadingOverlay = card_view.$('.game-tile-inner-loading-overlay');
						expect(loadingOverlay.is(':visible')).to.equal(true);
					},
					success: function() {
						backend_invoked.done(function()
						{
							var loadingOverlay = card_view.$('.game-tile-inner-loading-overlay');
							expect(loadingOverlay.is(':visible')).to.equal(false);
							done();
						});

						backend_invoked.resolve();
					}
				});
			}
		});


	}

});
