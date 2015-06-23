"use strict";

var Kid = Backbone.Model.extend({
	name: function() {
		return this.get('name');
	},
	user: function() {
		return this.get('user');
	},
	inventory: function() {
		return this.get('inventory');
	},
	name_set: function(v) {
		return this.set('name', v);
	},
	user_set: function(v) {
		return this.set('user', v);
	},
	inventory_set: function(v) {
		return this.set('inventory', v);
	},
	url: function() {
		var base = 'api/kids/kid';
		if (this.isNew()) return base;
		return base + '/' + encodeURIComponent(this.id) + '.json';
	}
});

var KidsCollection = Backbone.Collection.extend({
	model: Kid,
	url: 'api/kids/kids.json'
});

