"use strict";

var TagSuggestion = Backbone.Model.extend({
	name: function() {
		return this.get('name');
	}
});



var TagSuggestionsCollection = Backbone.Collection.extend({
	model: TagSuggestion,
	url: 'api/collection/tag-suggestions.json'
});


var TagView = Backbone.View.extend({

	initialize: function(options) {
		this.game = options.game;
		if (options.cardTemplatePromise == undefined)
		{
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;
		this.backend = options.backend;
	},

	render: function() {
		var that = this;
		this.whenRendered =
			TagView.sprite.sprite_promise().then(function(el) {
				that.$el.append(el.clone());
				that.render_el();
				return that;
			});
		return this;
	},

	render_el: function() {
		var that = this;

		this.$el.hide();

		this.renderGameCard();
		this.renderCommandHints();

		this.viewCommandBox = new CommandBoxView(
			{
				placeholder_text: 'Collection for ' + this.game.name(),
				on_command: function(input) { that.on_tag_command(input) },
				on_command_alternate: function(input) { that.on_tag_command_alternate(input) },
				on_change_input: function(input) { that.on_tag_change_input(input); }
			}
		);
		that.$('#div-command-box').empty().append(this.viewCommandBox.el);
		this.viewCommandBox.render_commandBox_promise().done(function(view)
		{
			that.on_CommandBox_whenRendered(view);
		});

		var suggestions = new TagSuggestionsCollection();
		this.backend.fetch_promise(suggestions).done(function () {
			that.renderTagSuggestionsView(suggestions);
		});

		this.$el.slideDown();
	},

	renderGameCard: function()
	{
		var that = this;

		var enormityRegular = {
			styleClass: 'game-tile-regular',
			width: 30
		};

		this.viewGameCard = new GameCardView({
			el: this.$("#GameCardView").empty(),
			cardTemplatePromise: that.cardTemplatePromise,
			model: that.game,
			enormity: enormityRegular,
			backend: this.backend,
			kidsMode: this.kidsMode,
			continues: this.continues,
			on_render: this.on_GameCard_render,
			on_tag: function (game) {
				that.on_tag_deck(game);
			}
		});
		this.viewGameCard.render();
	},

	renderTagSuggestionsView: function (suggestions) {
		var that = this;
		/**
		 * @type TagSuggestionsView
		 */
		new TagSuggestionsView({
			el: that.$("#TagSuggestionsView"),
			collection: suggestions,
			on_tag_suggestion_select: function (model) {
				that.on_tag_suggestion_select(model)
			}
		}).render();
	},

	renderCommandHints: function () {
		var template = this.$('#tag-command-hint');
		template.remove();

		this.elCommandHintA =
			this.renderCommandHint(template, "Tag as");
		this.elCommandHintB =
			this.renderCommandHint(template, "Search collections for");
	},

	renderCommandHint: function (template, begin) {
		var el = template.clone();
		el.find('#tag-command-hint-begin').text(begin);
		return el;
	},

	on_CommandBox_whenRendered: function(viewCommandBox) {
		viewCommandBox.emptyCommandHints();
		viewCommandBox.appendCommandHint(this.elCommandHintA);
		viewCommandBox.appendCommandHintAlternate(this.elCommandHintB);

		// TODO Favorites or most recently used
		this.updateWithInputValue("");

		viewCommandBox.input_query_focus();
	},

	on_tag_command: function(view) {
		var input = view.input_query_val();

		var appid = this.game.appid();
		var collection = this.nameForCollection(input);
		var aUrl = "api/app/" + appid + "/tag/" + encodeURIComponent(collection);
		var that = this;

		// TODO display 'creating...'
		/*
		 beforeSend: function(){
		 },
		 */

		this.backend.ajax_ajax_promise(aUrl)
			.done(function()
				{
					that.on_tag_done();
				})
			.fail(function(error)
				{
					view.trouble(error);
				});
	},

	on_tag_command_alternate: function(view) {

	},

	on_tag_done: function() {
		this.viewCommandBox.input_query_setval('');

		// TODO update the Game model in this view, otherwise tags don't change
		this.viewGameCard.viewGame_Tag_List.render();
	},

	nameForCollection: function(input) {
		if (input == '') return "Favorites";
		return input;
	},

	on_tag_change_input: function (view) {
		var input = view.input_query_val();
		this.updateWithInputValue(input);
	},

	updateWithInputValue: function (input) {
		var name = this.nameForCollection(input);
		var selector = '#tag-command-hint-subject';
		this.elCommandHintA.find(selector).text(name);
		this.elCommandHintB.find(selector).text(name);
	},

	on_tag_suggestion_select: function(model) {
		this.viewCommandBox.input_query_setval(model.name());
	},

	/**
	 * @type Game
	 */
	game: null,

	/**
	 * @type CommandBoxView
	 */
	viewCommandBox: null,

	/**
	 * @type GameCardView
	 */
	viewGameCard: null,

	whenRendered: null

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder({url: 'Tag.html', selector: "#TagTile"}).build()

});


var TagSuggestionView = Backbone.View.extend({
	on_tag_suggestion_select: null,

	events: {
		"click": "tagClicked"
	},

	initialize: function(options) {
		this.on_tag_suggestion_select = options.on_tag_suggestion_select;
	},

	render: function() {
		var choose_el = this.$el.find("#tag-name");
		var name_text = this.model.name();
		choose_el.text(name_text);
		return this;
	},

	tagClicked: function(e) {
		e.preventDefault();

		this.on_tag_suggestion_select(this.model);
	}
});


var TagSuggestionsView = Backbone.View.extend({

	initialize: function(options) {
		this.on_tag_suggestion_select = options.on_tag_suggestion_select;
	},

	render: function() {
		var container = this.$el;

		var one_el = this.$("#TagSuggestionView");
		container.empty();

		var that = this;
		this.collection.each( function(one) {
			var view = that.renderTagSuggestionView(one, one_el.clone());
			container.append(view.el);
		});
		return this;
	},

	renderTagSuggestionView: function (oneTagSuggestion, el) {
		var that = this;
		/**
		 * @type TagSuggestionView
		 */
		var tagSuggestionView = new TagSuggestionView({
			model: oneTagSuggestion,
			el: el,
			on_tag_suggestion_select: that.on_tag_suggestion_select
		});
		return tagSuggestionView.render();
	}
});

