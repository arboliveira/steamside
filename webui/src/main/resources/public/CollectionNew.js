"use strict";

Steamside.CollectionsNewWorld =
{
	nameController: 'CollectionsNewController',

	htmlWorld: 'CollectionNew.html',

	controller: function($scope, theBackend)
	{
		new CollectionNewView(
			{
				backend: theBackend
			}
		).render();
	}
};


var CollectionNewView = Backbone.View.extend({

	el: "#collection-new",

	initialize: function(options)
	{
		this.backend = options.backend;
	},

	render: function () {
		/**
		 * @type CollectionNewEmptyView
		 */
		var collectionNewEmptyView = new CollectionNewEmptyView({
			el: this.$('#collection-new-empty-segment'),
			backend: this.backend
		});
		collectionNewEmptyView.render();

		/**
		 * @type CollectionCopyAllCategoriesView
		 */
		var collectionCopyAllCategoriesView = new CollectionCopyAllCategoriesView({
			el: this.$('#collection-copy-all-categories-segment'),
			backend: this.backend
		});
		collectionCopyAllCategoriesView.render();

		return this;
	}
}
);

var CollectionNewEmptyView = Backbone.View.extend({

	elCommandHintA: null,
	elCommandHintB: null,

	initialize: function(options)
	{
		this.backend = options.backend;
	},

	render: function() {
		var that = this;

		var $emptyCommandHint = this.$('#empty-command-hint');
		$emptyCommandHint.remove();
		this.elCommandHintA = $emptyCommandHint.clone();
		this.elCommandHintB = $emptyCommandHint.clone();
		var selectorAfterwards = '#empty-command-hint-afterwards';
		this.elCommandHintA.find(selectorAfterwards).text("add games");
		this.elCommandHintB.find(selectorAfterwards).text("stay here");

		new CommandBoxView(
			{
				placeholder_text: 'Name for empty collection',
				on_command: function(input) { that.on_empty_command(input) },
				on_command_alternate: function(input) { that.on_empty_command_alternate(input) },
				on_change_input: function(input) { that.on_empty_change_input(input); }
			}
		).render().whenRendered.done(function(view)
			{
				that.on_empty_CommandBox_rendered(view);
			});

		return this;
	},

	on_empty_CommandBox_rendered: function(viewCommandBox) {
		var targetEl = this.$('#div-empty-name-form');
		targetEl.empty();
		targetEl.append(viewCommandBox.el);

		viewCommandBox.emptyCommandHints();
		viewCommandBox.appendCommandHint(this.elCommandHintA);
		viewCommandBox.appendCommandHintAlternate(this.elCommandHintB);

		this.updateWithInputValue("");
		viewCommandBox.input_query_focus();
	},

	nameForCollection: function(input) {
		if (input == '') return "Favorites";
		return input;
	},

	on_empty_command: function(view) {
		var input = view.input_query_val();
		this.createEmpty({name: input, stay: false});
	},

	on_empty_command_alternate: function(view) {
		var input = view.input_query_val();
		this.createEmpty({name: input, stay: false});
	},

	createEmpty: function(args) {     "use strict";
		var name = this.nameForCollection(args.name);
		var aUrl = "api/collection/" + encodeURIComponent(name) + "/create";
		var that = this;

		// TODO display 'creating...'
		/*
		 beforeSend: function(){
		 },
		 */

		this.backend.ajax_ajax_promise(aUrl)
			.done(function()
				{
					if (args.stay) {
						var input_el = that.$('#input-text-command-box');
						input_el.val('');
						input_el.focus();
						that.on_empty_change_input('');
					} else {
						// TODO AngularJS $location service
						$location.path("/collections/" + name + "/edit");
					}
				});
	},

	updateWithInputValue: function (input) {
		var name = this.nameForCollection(input);
		var selector = '#empty-command-hint-subject';
		this.elCommandHintA.find(selector).text(name);
		this.elCommandHintB.find(selector).text(name);
	},

	on_empty_change_input: function (view) {  "use strict";
		var input = view.input_query_val();
		this.updateWithInputValue(input);
	}
});

var CollectionCopyAllCategoriesView = Backbone.View.extend({

	events: {
		"click .button-copy-em-all": "buttonCopyEmAll"
	},

	initialize: function(options)
	{
		this.backend = options.backend;
	},

	render: function() {
		return this;
	},

	buttonCopyEmAll: function (e) {
		e.preventDefault();
		var jLink = $(e.target);
		var aUrl = jLink.attr( "href" );

		this.backend.ajax_ajax_promise(aUrl)
			.done(function()
			{
				// TODO AngularJS $location service
				$location.path("/mygames");
			});
	}
});

