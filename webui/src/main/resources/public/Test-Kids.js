"use strict";

var Test_Kids_html = Backbone.Model.extend({

	pageToTest: function()
	{
		return 'Kids.html';
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

		describe('Kids', function(){

			before(function(done)
			{
				pageLoader.loadPage(that, done);
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

		cardTemplatePromise.done(function(template_el) {
			that.testLoading_done_cardTemplateLoaded(template_el, done);
		});
	},

	testLoading_done_cardTemplateLoaded: function(template_el, done)
	{
		var expect = global.expect;

		var oneResult = new Game();
		oneResult.set("name", "Game of Kids");
		oneResult.set("link", "would be URL");

		var cardHolder = $("#test-space");

		var backend_invoked = $.Deferred();

		var MockBackend = Backbone.Model.extend(
			{
				ajax_ajax_promise: function(aUrl)
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
			el: template_el.clone(),
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
