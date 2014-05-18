var SearchResults = Backbone.Collection.extend({
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

var SearchView = Backbone.View.extend({

	searchResults: null,
	continues: null,
	on_tag: null,

	events: {
		//'submit #form-tag': 'event_form_submit'
	},

	initialize: function() {
		this.continues = this.options.continues;
		this.on_tag = this.options.on_tag;

		this.continues.on('reset', this.continues_reset, this);
	},

	render: function() {
		var searchResults = new SearchResults();

		this.searchResults = searchResults;

		new DeckView({
			el: $('#search-results-deck'),
			collection: searchResults,
			continues: this.continues,
			on_tag: this.on_tag
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

		var that = this;
		CommandBoxTile.whenLoaded(function(tile) {
			that.render_search_CommandBox(tile);
		});

		return this;
	},

	render_search_CommandBox: function(tile) {
		var that = this;
		var viewCommandBox = new CommandBoxView({
			el: tile.clone(),
			placeholder_text: 'game or command',
			on_change_input: function(input) { that.on_search_input_changed(input) },
			on_command: function(input) { that.on_search_command(input) },
			on_command_alternate: function(input) { that.on_search_command_alternate(input) }
		});
		var viewCommandBox_el = viewCommandBox.render().el;
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
			gameA.play();
		} else {
			var searchResults = this.searchResults;
			searchResults.query = input;
			fetch_json(searchResults);
		}
	},

	on_search_command_alternate: function(view) {
		var input = view.input_query_val();
		if (input == '') {
			var gameB = this.continues.at(1);
			gameB.play();
		} else {
			var searchResults = this.searchResults;
			searchResults.query = input;
			fetch_json(searchResults, function() {
				var first = searchResults.at(0);
				first.play();
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

