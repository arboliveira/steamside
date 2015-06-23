"use strict";

var SearchResults = Backbone.Collection.extend(
{
	model: Game,
	query: null,
	queryString: null,
	url: function() {
		var base = "api/search/search.json?";
		if (this.queryString != null) return base + this.queryString;
		return base + "query=" + this.query;
	},

    setQueryString: function(value) {
        this.queryString = value;
        this.query = null;
    },

    setQuery: function(value) {
        this.queryString = null;
        this.query = value;
    }
});

var SearchView = Backbone.View.extend(
{
	cardTemplatePromise: null,
	spriteMoreButton: null,
	searchResults: null,
	continues: null,
	on_tag: null,
	player: null,

	initialize: function(options)
	{
		if (options.cardTemplatePromise == null) {
			throw new Error("cardTemplatePromise is required");
		}
		this.cardTemplatePromise = options.cardTemplatePromise;

		if (options.spriteMoreButton == null) {
			throw new Error("spriteMoreButton is required");
		}
		this.spriteMoreButton = options.spriteMoreButton;

		this.continues = options.continues;
		this.on_tag = options.on_tag;
		this.backend = options.backend;
		this.player = new Game_Player({ backend: options.backend });

		this.listenTo(this.continues, 'reset', this.continues_reset);
	},

	render: function() {
		var that = this;

		var searchResults = new SearchResults();

		this.searchResults = searchResults;

		new DeckView({
			el: that.$('#search-results-deck'),
			cardTemplatePromise: that.cardTemplatePromise,
			spriteMoreButton: that.spriteMoreButton,
			collection: searchResults,
			continues: this.continues,
			on_tag: this.on_tag,
			backend: this.backend
		});

		var elSearchCommandHint = this.$('#search-command-hint');
		elSearchCommandHint.remove();

		this.viewHintContinueA = new CommandHintWithVerbAndSubjectView({
			el: elSearchCommandHint.clone(),
			verb: "continue"
		}).render();
		this.viewHintContinueB = new CommandHintWithVerbAndSubjectView({
			el: elSearchCommandHint.clone(),
			verb: "continue"
		}).render();
		this.viewHintSearchA = new CommandHintWithVerbAndSubjectView({
			el: elSearchCommandHint.clone(),
			verb: "search"
		}).render();
		this.viewHintSearchB = new CommandHintWithVerbAndSubjectView({
			el: elSearchCommandHint.clone(),
			verb: "play first result for"
		}).render();

		this.viewCommandBox = new CommandBoxView(
			{
				placeholder_text: 'game or command',
				on_change_input: function(input) { that.on_search_input_changed(input) },
				on_command: function(input) { that.on_search_command(input) },
				on_command_alternate: function(input) { that.on_search_command_alternate(input) }
			}
		);

		var searchEl = that.$('#search-command-box');
		searchEl.empty();
		searchEl.append(this.viewCommandBox.el);

		this.whenRendered = this.viewCommandBox
			.render_commandBox_promise().then(function(view)
				{
					that.rendered_search_CommandBox(view);
					return that;
				});

		return this;
	},

	rendered_search_CommandBox: function(viewCommandBox){
		viewCommandBox.emptyCommandHints();
		viewCommandBox.appendCommandHint(this.viewHintContinueA.el);
		viewCommandBox.appendCommandHint(this.viewHintSearchA.el);
		viewCommandBox.appendCommandHintAlternate(this.viewHintContinueB.el);
		viewCommandBox.appendCommandHintAlternate(this.viewHintSearchB.el);
		return this;
	},

	command_box_input_query_focus: function() {
		this.viewCommandBox.input_query_focus();
	},

	on_search_input_changed: function(view) {
		var input = view.input_query_val();

		if (input == '') {
			this.viewHintContinueA.show();
			this.viewHintContinueB.show();
			this.viewHintSearchA.hide();
			this.viewHintSearchB.hide();
		}
		else {
			this.viewHintContinueA.hide();
			this.viewHintContinueB.hide();
			this.viewHintSearchA.show();
			this.viewHintSearchB.show();
			this.viewHintSearchA.subject_set(input);
			this.viewHintSearchB.subject_set(input);
		}

		this.viewHintContinueA.render();
		this.viewHintContinueB.render();
		this.viewHintSearchA.render();
		this.viewHintSearchB.render();
	},

	on_search_command: function(view) {
		var input = view.input_query_val();
		if (input == '') {
			var gameA = this.continues.at(0);
			this.player.play(gameA);
		} else {
			var searchResults = this.searchResults;
			searchResults.query = input;
			this.backend.fetch_promise(searchResults);
		}
	},

	on_search_command_alternate: function(view) {
		var that = this;
		var input = view.input_query_val();
		if (input == '') {
			var gameB = this.continues.at(1);
			this.player.play(gameB);
		} else {
			var searchResults = this.searchResults;
			searchResults.query = input;
			this.backend.fetch_promise(searchResults).done(function() {
				var first = searchResults.at(0);
				that.player.play(first);
			});
		}
	},

	continues_reset: function() {
		var gameA = this.continues.at(0);
		var gameB = this.continues.at(1);

		this.viewHintContinueA.subject_set(gameA.name());
		this.viewHintContinueA.render();
		this.viewHintContinueB.subject_set(gameB.name());
		this.viewHintContinueB.render();
	},

	whenRendered: null,

	/**
	 * @type CommandBoxView
	 */
	viewCommandBox: null

});

