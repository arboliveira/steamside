var TagView = Backbone.View.extend({

	game: null,

	events: {
		'submit #form-tag': 'event_form_submit'
	},

	initialize: function() {
		this.game = this.options.game;
	},

	render: function() {
		this.$(".game-name").text(this.game.name());
		this.$el.hide();
		this.$el.slideDown();

		this.$(".input-tag").focus();

		return this;
	},

	event_form_submit: function(e) {

		e.preventDefault();

		alert(this.game.name() + ' ==> ' + this.$('.input-tag').val());

	}

});

