"use strict";

var CollectionPickTile = {
	tile: new Tile(
		{
			url: 'CollectionPick.html',
			selector: "#collection-pick-collections-segment"
		}
	)
};

var CollectionPickView = Backbone.View.extend(
{
	combine_collection: null,

	on_collection_pick: null,

	initialize: function(options)
	{
		this.combine_collection = options.combine_collection;
		this.on_collection_pick = options.on_collection_pick;
		this.backend = options.backend;
	},

	render: function()
	{
		var that = this;
		this.whenRendered =
			CollectionPickTile.tile.el_promise.then(function(tile)
				{
					var el = tile.clone();
					that.render_el(el);
					that.$el.append(el);
					return that;
				});
		return this;
	},

	render_el: function(el)
	{
		var that = this;

		var m = this.combine_collection;
		if (m != null)
		{
			el.find('#CombineCollectionName').text(m);
			el.find('#CombineCollectionWithView').show();
		}

		var collections = new SteamsideCollectionInfoCollection();

		this.backend.fetch_promise(collections).done(function()
		{
			var el_list = el.find("#SteamsideCollectionInfoListView");
			new SteamsideCollectionInfoListView(
				{
					el: el_list,
					collection: collections,
					on_collection_pick: that.on_collection_pick
				}
			).render();

			if (collections.length > 0)
			{
				el.find("#no-collections-yet").hide();
			}
		}
		);

		return this;
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
    on_collection_pick: null,

    events: {
        "click": "collectionClicked"
    },

	initialize: function(options)
	{
		this.on_collection_pick = options.on_collection_pick;
	},

	render: function()
	{
        var name_el = this.$el.find("#collection-pick-one-name");
		var name_text = this.model.name();
        name_el.text(name_text);
		var fragment = '#/collections/' + name_text + '/edit';
		this.link_el().attr('href', fragment);
		var count_text = this.model.count();
		var count_el = this.$el.find("#count");
		count_el.text(count_text);
        return this;
    },

	link_el: function () {
		return this.$el.find("#collection-pick-one-link");
	},

	collectionClicked: function(e)
	{
        e.preventDefault();

		if (this.on_collection_pick != null)
		{
			this.on_collection_pick(this.model);
			return;
		}

		var fragment = this.link_el().attr('href');

		Backbone.history.navigate(fragment, {trigger: true});
    }
});

var SteamsideCollectionInfoListView = Backbone.View.extend(
{
	on_collection_pick: null,

	initialize: function(options)
	{
		this.on_collection_pick = options.on_collection_pick;
	},

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
                on_collection_pick: that.on_collection_pick
            });
            container.append(view.render().el);
        });
        return this;
    }
});

