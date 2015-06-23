"use strict";

var Test_Kids = Backbone.Model.extend({

	renderTestableUIPromise: function()
	{
		var pageToTest = 'Kids.html';
		return $.ajax({
				url: pageToTest, dataType: 'html' }
		).then(function(page) {
				return $(page);	});
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
				that.renderTestableUIPromise().done(function(el)
				{
					theTestRunner.replaceTestableUI(el);
					done();
				});
			});

			describe("Game Card", function () {
				it('Loading overlay', function(done){
					that.testLoading(done);
				})
			});
		});
	},

	testLoading: function (done)
	{
		var that = this;

		var kidsSpriteSheet = new KidsSpriteSheet();

		var cardTemplatePromise = kidsSpriteSheet.card.sprite_promise();

		var expect = global.expect;

		var oneResult = new Game();
		oneResult.set("name", "Game of Kids");
		oneResult.set("link", "would be URL");

		var cardHolder = $("#test-space");

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

		var card_el = card_view.render().el;
		var $card_el = $(card_el);
		$card_el.show();

		cardHolder.append(card_el);

		var game_link = $card_el.find('.game-link');

		// No asserts, just don't crash on mouseenter and mouseleave
		game_link.trigger('mouseenter');
		game_link.trigger('mouseleave');

		game_link.trigger('click');

		var loadingOverlay = $card_el.find('.game-tile-inner-loading-overlay');
		expect(loadingOverlay.is(':visible')).to.equal(true);

		backend_invoked.done(function()
		{
			expect(loadingOverlay.is(':visible')).to.equal(false);
			done();
		});

		backend_invoked.resolve();
	}

});
