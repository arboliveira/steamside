"use strict";

var CollectionPickView = Backbone.View.extend(
{
	initialize: function(options)
	{
		this.on_collection_pick = options.on_collection_pick;
		this.backend = options.backend;
		this._purposeless = options.purposeless;
		this._purpose_text = options.purpose_text;
		this._purpose_el = options.purpose_el;
	},

	render: function()
	{
		var that = this;
		this.whenRendered =
			CollectionPickView.sprite.sprite_promise().then(function(el)
				{
					that.$el.append(el.clone());
					that.render_el();
					return that;
				});
		return this;
	},

	render_el: function()
	{
		var that = this;

		that.renderPurpose();

		var collections = new SteamsideCollectionInfoCollection();

		this.backend.fetch_promise(collections).done(function()
		{
			var el_list = that.$("#SteamsideCollectionInfoListView");
			/**
			 * @type SteamsideCollectionInfoListView
			 */
			var steamsideCollectionInfoListView = new SteamsideCollectionInfoListView(
				{
					el: el_list,
					collection: collections,
					on_collection_pick: that.on_collection_pick
				}
			);
			steamsideCollectionInfoListView.render();

			if (collections.length > 0)
			{
				that.$("#no-collections-yet").hide();
			}
		});

		return this;
	},

	renderPurpose: function () {
		var that = this;

		if (that._purposeless) {
			that.$('#PurposeView').hide();
		}

		if (that._purpose_text != undefined) {
			that.$('#InnerPurpose').text(that._purpose_text);
		}

		if (that._purpose_el != undefined) {
			var el_purpose = that.$('#InnerPurpose');
			el_purpose.empty();
			el_purpose.append(that._purpose_el);
		}
		return that;
	},

	on_collection_pick: null,
	whenRendered: null,
	backend: null

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder({url: 'CollectionPick.html', selector: "#collection-pick-collections-segment"}).build()

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
			container.append(
				new SteamsideCollectionInfoView({
					model: one,
					el: one_el.clone(),
					on_collection_pick: that.on_collection_pick
				})
					.render().el
			);
        });
        return this;
    }
});

