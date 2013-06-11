var CollectionPickTile = {

	ajaxTile: function (callback) {
		SteamsideTileset.ajaxCollectionPick(callback);
	}
};

var SteamCategory = Backbone.Model.extend({
	name: function() {		"use strict";
		return this.get('name');
	},
	link: function() {		"use strict";
		return this.get('link');
	}
});

var SteamCategoryCollection = Backbone.Collection.extend({
    model: SteamCategory,
    url: 'api/steam-categories/steam-categories.json'
});

var SteamCategoryView = Backbone.View.extend({
	on_category_change: null,

	initialize: function() {		"use strict";
		this.on_category_change = this.options.on_category_change;
	},

	render: function() { "use strict";
		var that = this;
		var button_el = this.$el.find(".collection-pick-steam-category-button");
		button_el.text(this.model.name());
		var b = button_el.button();
		b.click(function(event){
			that.categoryClicked(event);
		});
		var icon_el = button_el.next();
		icon_el.button({
			text: false,
			icons: {
				primary: "ui-icon-help"
			}
		}).click(function(event){
				that.categoryClicked(event);
			});
		button_el.parent().buttonset();
		return this;
	},

	categoryClicked: function(e) {		"use strict";
		e.preventDefault();
		var aUrl = this.model.link();
		var that = this;
		$.ajax({
			url: aUrl,
			dataType: dataTypeOf(aUrl),
			beforeSend: function(){
				// loading...
			},
			complete: function(){
				that.category_changed();
			}
		});
	},

	category_changed: function() {
		this.on_category_change();
	}
});

var SteamCategoriesView = Backbone.View.extend({
	on_category_change: null,

	initialize: function() {		"use strict";
		this.on_category_change = this.options.on_category_change;
	},

	render: function() { "use strict";
		var container = this.$el;

		var one_el = this.$(".collection-pick-steam-category");
		container.empty();

        var that = this;
		this.collection.each( function(one) {
			var view = new SteamCategoryView({
				model: one,
				el: one_el.clone(),
				on_category_change: that.on_category_change
			});
			container.append(view.render().el);
		});
		return this;
	}
});
