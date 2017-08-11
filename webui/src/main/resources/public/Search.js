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

	continueACommandModel: null,
    continueBCommandModel: null,

	initialize: function(options)
	{
	    var that = this;

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
		this.$location = options.$location;

		this.player = new Game_Player({ backend: options.backend });

		this.continueACommandModel = new CommandHintWithVerbAndSubjectModel({
            verb: "continue",
            on_fire: function(commandBoxView) { that.on_continue_command(commandBoxView) }
		});
        this.continueBCommandModel = new CommandHintWithVerbAndSubjectModel({
            verb: "continue",
            on_fire: function(commandBoxView) { that.on_continue_command_alternate(commandBoxView) }
        });
        this.exploreACommandModel = new CommandHintWithVerbAndSubjectModel({
            verb: "explore",
            subject: "My games",
            on_fire: function(commandBoxView) { that.on_explore_command(commandBoxView) }
        });
        this.exploreBCommandModel = new CommandHintWithVerbAndSubjectModel({
            verb: "explore",
            subject: "Steam Client",
            on_fire: function(commandBoxView) { that.on_explore_command_alternate(commandBoxView) }
        });
        this.searchACommandModel = new CommandHintWithVerbAndSubjectModel({
            verb: "search",
            on_fire: function(commandBoxView) { that.on_search_command(commandBoxView) }
        });
        this.searchBCommandModel = new CommandHintWithVerbAndSubjectModel({
            verb: "play first result for",
            on_fire: function(commandBoxView) { that.on_search_command_alternate(commandBoxView) }
        });

        this.listenTo(this.continues, 'reset', this.on_continues_reset);
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

        this.viewHintA = new CommandHintWithVerbAndSubjectView({
            model: that.continueACommandModel,
            el: elSearchCommandHint.clone()
        });
        this.viewHintB = new CommandHintWithVerbAndSubjectView({
            model: that.continueBCommandModel,
            el: elSearchCommandHint.clone()
        });

		this.viewCommandBox = new CommandBoxView(
			{
				placeholder_text: 'game or command',
				on_change_input: function(input) { that.on_search_input_changed(input) },
				on_command: function(commandBoxView) {
				    that.viewHintA.model.on_fire(commandBoxView)
                },
				on_command_alternate: function(commandBoxView) {
                    that.viewHintB.model.on_fire(commandBoxView)
				}
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

        viewCommandBox.appendCommandHint(this.viewHintA.el);
        viewCommandBox.appendCommandHintAlternate(this.viewHintB.el);

		this.refresh_command_hints();

		return this;
	},

	command_box_input_query_focus: function() {
		this.viewCommandBox.input_query_focus();
	},

    on_continues_reset: function() {
        this.refresh_command_hints();
    },

    on_search_input_changed: function(view) {
        this.refresh_command_hints();
	},

	refresh_command_hints: function() {
		var that = this;

        var input = that.viewCommandBox.input_query_val();

        if (input == '') {
            if (that.continues.size() == 0) {
                that.viewHintA.replaceModel(that.exploreACommandModel);
                that.viewHintB.replaceModel(that.exploreBCommandModel);
            }
            else {
                var gameA = this.continues.at(0);
                var gameB = this.continues.at(1);

                that.continueACommandModel.subject_set(gameA.name());

                that.viewHintA.replaceModel(that.continueACommandModel);

                if (gameB == undefined) {
                    that.viewHintB.replaceModel(that.continueACommandModel);
                }
                else {
                    that.continueBCommandModel.subject_set(gameB.name());

                    that.viewHintB.replaceModel(that.continueBCommandModel);
                }
            }
        }
        else {
            this.searchACommandModel.subject_set(input);
            this.searchBCommandModel.subject_set(input);
            this.viewHintA.replaceModel(that.searchACommandModel);
            this.viewHintB.replaceModel(that.searchBCommandModel);
        }

        this.viewHintA.render();
        this.viewHintB.render();
    },

    on_continue_command: function(commandBoxView) {
        var gameA = this.continues.at(0);

        this.player.play(gameA);
    },

    on_continue_command_alternate: function(commandBoxView) {
        var gameB = this.continues.at(1);

        this.player.play(gameB);
    },

    on_search_command: function(commandBoxView) {
		var input = commandBoxView.input_query_val();

		var searchResults = this.searchResults;
		searchResults.query = input;
		this.backend.fetch_promise(searchResults);
	},

	on_search_command_alternate: function(commandBoxView) {
		var that = this;

		var input = commandBoxView.input_query_val();

		var searchResults = this.searchResults;
		searchResults.query = input;
		this.backend.fetch_promise(searchResults).done(function() {
			var first = searchResults.at(0);
			that.player.play(first);
		});
	},

	on_explore_command: function() {
		//this.$location.url('/mygames');
		window.location.assign('/#/mygames');
	},

	on_explore_command_alternate: function() {
        //this.$location.url('/steamclient');
        window.location.assign('/#/steamclient');
	},

	whenRendered: null,

	/**
	 * @type CommandBoxView
	 */
	viewCommandBox: null

});

