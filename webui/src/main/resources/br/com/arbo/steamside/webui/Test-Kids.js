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

	runTests: function ()
	{
		var that = this;
		var describe = global.describe;
		var it = global.it;
		var mocha = global.mocha;

		describe('Kids', function(){
			describe("Game Card", function () {
				it('Loading...', function(done){
					that.testLoading(done);
				})
			});
		});

		mocha.run();
	},

	testLoading: function (done)
	{
		var that = this;

		var kidsTileset = new Tileset({url: 'Kids.html'});

		var cardTemplatePromise =
			KidsTilePromise.buildCardTemplatePromise(kidsTileset);

		cardTemplatePromise.done(function(template_el) {
			that.testLoading_done_cardTemplateLoaded(template_el, done);
		});
	},

	testLoading_done_cardTemplateLoaded: function(template_el, done)
	{
		var cardHolder = $("#test-space");

		var backend = null;

		var enormity = {
			styleClass: 'game-tile-large',
			width: 100
		};

		var oneResult = new Game();
		oneResult.set("name", "Game of Kids");

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
		$(card_el).show();

		cardHolder.append(card_el);

		// ---

		if (true) return;

		var segment = $('#search-segment');
		var input = segment.find('.search-text-input');
		input.val('anything');
		var button = segment.find('.command-button').get(0);
		button.click();

		var games;

		var is_effect_seen = function ()
		{
			games = segment.find('.game-tile');
			return games.length == 2;
		};

		var ok_effect_seen = function()
		{
			assert.equal(games.length, 2, 'search results');
			done();
		};

		this.insist(is_effect_seen, ok_effect_seen, done);
	}

});
