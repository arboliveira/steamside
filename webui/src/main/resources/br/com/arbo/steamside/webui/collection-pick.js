"use strict";

var SwitchFavoritesCollectionsTile = {

	whenLoaded: function (callback) {
		SteamsideTileset.ajaxCollectionPick(callback);
	}
};

var SwitchFavoritesWorld = WorldActions.extend(
	{
		on_category_change: null,

		initialize: function(options)
		{
			this.on_category_change = options.on_category_change;
		},

		tileLoad: function(whenDone)
		{
			SwitchFavoritesCollectionsTile.whenLoaded(whenDone);
		},

		newView: function(tile)
		{
			var that = this;

			return new SwitchFavoritesView({
				el: tile.clone(),
				on_category_change: function() {that.on_category_change();}
			}).render();
		}
	}
);

var CollectionPickTile = {
	tile: new Tile(
		{
			url: 'CollectionPick.html',
			selector: "#collection-pick-collections-segment"
		}
	),

	ajaxTile: function (callback) {
		this.tile.ajaxTile(callback);
	}
};

var CollectionPickView = Backbone.View.extend(
{
	render: function()
	{
		var that = this;

		var collections = new SteamsideCollectionInfoCollection();

		json_promise(collections).done(function()
		{
			new SteamsideCollectionInfoListView(
				{
					el: that.$("#SteamsideCollectionInfoListView"),
					collection: collections
				}
			).render();

			if (collections.length > 0)
			{
				that.$("#no-collections-yet").hide();
			}
		}
		);

		return this;
	}
});

var SwitchFavoritesView = Backbone.View.extend(
{
	on_category_change: null,

	events: {
		"click .back-button"         : "backButtonClicked"
	},

	initialize: function()
	{
		this.on_category_change = this.options.on_category_change;
	},

	render: function()
	{
		var that = this;

		var categories = new SteamCategoryCollection();
		json_promise(categories).done(function ()
		{
			new SteamCategoriesView(
				{
					el: that.$("#collection-pick-steam-categories-list"),
					collection: categories,
					on_category_change: that.on_category_change
				}
			).render();
		}
		);

		CollectionPickTile.ajaxTile(function(tile_el)
		{
			var pick = new CollectionPickView(
				{
					el: tile_el.clone()
				}
			);
			that.$("#CollectionPickView").append(pick.render().el);
		}
		);

		return this;
	},

	backButtonClicked: function (e) {
		e.preventDefault();
		history.back();
	}
});

var SteamsideCollectionInfo = Backbone.Model.extend(
{
    name: function() {
        return this.get('name');
    },
	count: function() {
		return this.get('count');
	}
});

var SteamsideCollectionInfoCollection = Backbone.Collection.extend(
{
    model: SteamsideCollectionInfo,
    url: 'api/collection/collections.json'
});

var SteamsideCollectionInfoView = Backbone.View.extend(
{
    on_collection_change: null,

    events: {
        "click": "collectionClicked"
    },

    render: function()
	{
        var that = this;
        var name_el = this.$el.find("#collection-pick-one-name");
		var name_text = this.model.name();
        name_el.text(name_text);
		var link_el = this.$el.find("#collection-pick-one-link");
		var fragment = '#/collections/' + name_text + '/edit';
		link_el.attr('href', fragment);
		var count_text = this.model.count();
		var count_el = this.$el.find("#count");
		count_el.text(count_text);
        return this;
    },

    collectionClicked: function(e)
	{
        e.preventDefault();

        var name = this.model.name();
		var fragment = "#/collections/" + name + "/edit";

		Backbone.history.navigate(fragment, {trigger: true});
    },

    category_changed: function()
	{
        this.on_category_change();
    }
});

var SteamsideCollectionInfoListView = Backbone.View.extend(
{
    render: function()
	{
        var container = this.$el;

        var one_el = this.$("#SteamsideCollectionInfoView");
        container.empty();

        var that = this;
        this.collection.each( function(one)
		{
            var view = new SteamsideCollectionInfoView({
                model: one,
                el: one_el.clone(),
                on_collection_change: that.options.on_collection_change
            });
            container.append(view.render().el);
        });
        return this;
    }
});

var SteamCategory = Backbone.Model.extend({
	name: function() {
		return this.get('name');
	},
	link: function() {
		return this.get('link');
	}
});

var SteamCategoryCollection = Backbone.Collection.extend({
    model: SteamCategory,
    url: 'api/steam-categories/steam-categories.json'
});

var SteamCategoryView = Backbone.View.extend({
	on_category_change: null,

	initialize: function() {
		this.on_category_change = this.options.on_category_change;
	},

	render: function() {
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

	categoryClicked: function(e) {
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

	initialize: function() {
		this.on_category_change = this.options.on_category_change;
	},

	render: function() {
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
