"use strict";

var Tag = Backbone.Model.extend({
	name: function() {
		return this.get('name');
	},
	count: function() {
		return this.get('count');
	}
});

var TagsCollection = Backbone.Collection.extend(
{
	model: Tag,
	url: 'api/collection/collections.json'
});

var TagSuggestionsCollection = Backbone.Collection.extend(
{
	model: Tag,
	url: 'api/collection/tag-suggestions.json'
});


var TagAGameView = Backbone.View.extend({

	initialize: function(options) {
		var that = this;
		this.game = options.game;
		if (options.cardTemplatePromise == undefined)
		{
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;
		this.backend = options.backend;
		this.viewCommandBox = new CommandBoxView(
			{
				placeholder_text: 'Collection for ' + this.game.name(),
				on_command: function(input) { that.on_tag_command(input) },
				on_command_alternate: function(input) { that.on_tag_command_alternate(input) },
				on_change_input: function(input) { that.on_tag_change_input(input); }
			}
		);
		this.suggestions = new TagSuggestionsCollection();
	},

	render: function() {
		var that = this;
		this.whenRendered =
			TagAGameView.sprite.sprite_promise().then(function(el) {
				that.$el.append(el.clone());
				that.render_el();
				return that;
			});
		return this;
	},

	render_el: function() {
		this.renderGameCard();
		this.renderCommandHints();
		this.renderCommandBox();
		this.renderTagSuggestions();
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

	renderCommandBox: function () {
		var that = this;
		that.$('#div-command-box').empty().append(this.viewCommandBox.el);
		this.viewCommandBox.render_commandBox_promise().done(function (view) {
			that.on_CommandBox_whenRendered(view);
		});
	},

	on_CommandBox_whenRendered: function(viewCommandBox) {
		viewCommandBox.emptyCommandHints();
		viewCommandBox.appendCommandHint(this.elCommandHintA);
		viewCommandBox.appendCommandHintAlternate(this.elCommandHintB);

		// TODO Favorites or most recently used
		this.updateWithInputValue("");

		viewCommandBox.input_query_focus();
	},

	renderTagSuggestions: function () {
		var that = this;
		this.backend.fetch_promise(that.suggestions);
		that.$("#TagSuggestionsView").empty().append(
			new TagStickersView({
				collection: that.suggestions,
				on_tag_clicked: function (model) {
					that.on_tag_suggestion_select(model)
				}
			}).render().el
		);
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


var TagStickerView = Backbone.View.extend(
	{
		events: {
			"click": "on_click"
		},

		initialize: function(options)
		{
			this._on_clicked = options.on_clicked;
		},

		render: function()
		{
			var that = this;
			CollectionPickSpriteSheetSingleton.sprites.sticker
				.sprite_promise().done(function(el){
					that.$el.append(el.clone());
					that.render_el();
				});
			return that;
		},

		render_el: function()
		{
			var name_el = this.$el.find("#collection-pick-one-name");
			var name_text = this.model.name();
			name_el.text(name_text);

			var fragment = '#/collections/' + name_text + '/edit';
			this.link_el().attr('href', fragment);

			var $count = this.$("#count");
			var count = this.model.count();
			if (count != undefined)
			{
				$count.text(count);
			}
			else
			{
				$count.remove();
			}

			return this;
		},

		link_el: function () {
			return this.$el.find("#collection-pick-one-link");
		},

		on_click: function(e)
		{
			e.preventDefault();

			if (this._on_clicked != null)
			{
				this._on_clicked(this.model);
				return;
			}

			var fragment = this.link_el().attr('href');

			Backbone.history.navigate(fragment, {trigger: true});
		},

		_on_clicked: null

	});

var TagStickersView = Backbone.View.extend(
	{
		initialize: function(options)
		{
			this._on_tag_clicked = options.on_tag_clicked;

			this.listenTo(this.collection, 'reset', this.render);
		},

		render: function()
		{
			var that = this;
			CollectionPickSpriteSheetSingleton.sprites.stickers
				.sprite_promise().done(function(el){
					that.$el.append(el.clone());
					that.render_el();
				});
			return that;
		},

		render_el: function()
		{
			var that = this;

			if (that.collection.length == 0)
			{
				that.$("#TagStickerView").remove();
				that.$("#no-collections-yet").show();
				return that;
			}

			that.$el.empty();

			that.collection.each( function(one)
			{
				that.$el.append(
					new TagStickerView({
						model: one,
						on_clicked: that._on_tag_clicked
					})
						.render().el
				);
			});

			return that;
		},

		_on_tag_clicked: null
	}
);

