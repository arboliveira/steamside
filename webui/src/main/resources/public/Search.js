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
	searchResults: null,
	continues: null,
	on_tag: null,
	player: null,

	events: {
		//'submit #form-tag': 'event_form_submit'
	},

	initialize: function(options)
	{
		this.cardTemplatePromise = options.cardTemplatePromise;
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
			el: $('#search-results-deck'),
			cardTemplatePromise: this.cardTemplatePromise,
			collection: searchResults,
			continues: this.continues,
			on_tag: this.on_tag,
			backend: this.backend
		});

		var tileSearchCommandHint = this.$('#search-command-hint');
		tileSearchCommandHint.remove();
		this.tileSearchHintContinueA = tileSearchCommandHint.clone();
		this.tileSearchHintContinueB = tileSearchCommandHint.clone();
		this.tileSearchHintSearchA = tileSearchCommandHint.clone();
		this.tileSearchHintSearchB = tileSearchCommandHint.clone();
		var selectorVerb = '#search-command-hint-verb';
		this.tileSearchHintSearchA.find(selectorVerb).text("search");
		this.tileSearchHintSearchB.find(selectorVerb).text("play first result for");

		new CommandBoxView(
			{
				placeholder_text: 'game or command',
				on_change_input: function(input) { that.on_search_input_changed(input) },
				on_command: function(input) { that.on_search_command(input) },
				on_command_alternate: function(input) { that.on_search_command_alternate(input) }
			}
		).render().whenRendered.done(function(view)
			{
				that.rendered_search_CommandBox(view);
			});

		return this;
	},

	rendered_search_CommandBox: function(viewCommandBox){
		var viewCommandBox_el = viewCommandBox.el;
		viewCommandBox.emptyCommandHints();
		viewCommandBox.appendCommandHint(this.tileSearchHintContinueA);
		viewCommandBox.appendCommandHint(this.tileSearchHintSearchA);
		viewCommandBox.appendCommandHintAlternate(this.tileSearchHintContinueB);
		viewCommandBox.appendCommandHintAlternate(this.tileSearchHintSearchB);

		var searchEl = $('#search-command-box');
		searchEl.empty();
		searchEl.append(viewCommandBox_el);
		viewCommandBox.input_query_focus();
	},

	on_search_input_changed: function(view) {
		var input = view.input_query_val();
		if (input == '') {
			this.tileSearchHintContinueA.show();
			this.tileSearchHintContinueB.show();
			this.tileSearchHintSearchA.hide();
			this.tileSearchHintSearchB.hide();
		} else {
			this.tileSearchHintContinueA.hide();
			this.tileSearchHintContinueB.hide();
			this.tileSearchHintSearchA.show();
			this.tileSearchHintSearchB.show();
			var selector = '#search-command-hint-subject';
			this.tileSearchHintSearchA.find(selector).text(input);
			this.tileSearchHintSearchB.find(selector).text(input);
		}
	},

	on_search_command: function(view) {
		var input = view.input_query_val();
		if (input == '') {
			var gameA = this.continues.at(0);
			this.player.play(gameA);
		} else {
			var searchResults = this.searchResults;
			searchResults.query = input;
			this.backend.fetch_fetch_json(searchResults);
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
			this.backend.fetch_fetch_json(searchResults, function() {
				var first = searchResults.at(0);
				that.player.play(first);
			});
		}
	},

	continues_reset: function() {
		var gameA = this.continues.at(0);
		var gameB = this.continues.at(1);
		var selector = '#search-command-hint-subject';
		this.tileSearchHintContinueA.find(selector).text(gameA.name());
		this.tileSearchHintContinueB.find(selector).text(gameB.name());
	}

});
