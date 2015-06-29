"use strict";

// ============================================================================

var KidsSettingsView = Backbone.View.extend(
{
	events:
	{
		"click #AddKid": "on_AddKid_click"
	},

	initialize: function (options)
	{
		var that = this;

		this._backend = options.backend;

		KidsSettingsView.sprite.sprite_promise().done(function(el) {
			that._elEditKid = el.find("#kid-edit-segment").remove();
		});
	},

	render: function ()
	{
		var that = this;
		KidsSettingsView.sprite.sprite_promise().done(function (el) {
			that.$el.append(el);
			that.render_el();
		});
		return this;
	},

	render_el: function ()
	{
		this.renderKidsCollection();
	},

	renderKidsCollection: function () {
		var that = this;
		this._viewKidsCollection = new KidsCollectionView({
			collection: this.collection,
			kidsSettingsView: this
		});
		this._viewKidsCollection._elOneKid = that.$el.find("#KidView").remove();
		this.$('#KidsCollectionView').append(this._viewKidsCollection.el);
	},

	addKid: function()
	{
		var kid = new Kid();
		this.listenToOnce(kid, 'sync', this.addKidToCollection);
		this.editKid(kid);
	},

	addKidToCollection: function(kid)
	{
		this.collection.add(kid);
	},

	editKid: function(kid)
	{
		var that = this;
		that.segmentKids()
			.after(
				new KidEditView({
					model: kid,
					el: that._elEditKid.clone(),
					backend: that._backend
				})
					.render().el
			);
	},

	segmentKids: function()
	{
		return this.$("#kids-segment");
	},

	on_AddKid_click: function(e)
	{
		e.preventDefault();
		this.addKid();
	}

}, {
	/**
	 * @public
	 * @type Sprite
	 */
	sprite: new SpriteBuilder(
		{url: 'KidsSettings.html', selector: "#KidsSettingsView"}).build()
}
);

// ============================================================================

var KidsCollectionView = Backbone.View.extend(
{
	initialize: function (options)
	{
		this._kidsSettingsView = options.kidsSettingsView;

		this.listenTo(this.collection, 'reset', this.render);
		this.listenTo(this.collection, 'add', this.render);
		this.listenTo(this.collection, 'remove', this.render);
	},

	render: function()
	{
		var that = this;
		that.$el.empty();
		that.collection.each(function (one) {
			that.renderOne(one);
		});
	},

	renderOne: function( one )
	{
		var that = this;
		var elOneKid = that._elOneKid;
		that.$el
			.append(
			new KidView({
				model: one,
				el: elOneKid.clone(),
				kidsSettingsView: that._kidsSettingsView
			})
				.render().el
		);
	},

	_elOneKid: null
}
);

// ============================================================================

var KidView = Backbone.View.extend(
{
	events:
	{
		"click": "onClick"
	},

	initialize: function (options)
	{
		this._kidsSettingsView = options.kidsSettingsView;

		this.listenTo(this.model, 'sync', this.render);
	},

	render: function ()
	{
		var that = this;
		that.$("#Name").text(that.model.name());
		that.$("#User").text(that.model.user());
		that.$("#Inventory").empty().append(
			new TagStickerView({
				model: new Tag({name: that.model.inventory()})
			})
				.render().el
		);
		return that;
	},

	onClick: function(e)
	{
		e.preventDefault();
		this._kidsSettingsView.editKid(this.model);
	}
}
);

// ============================================================================

var KidEditView = Backbone.View.extend(
{
	events:
	{
		"click #SaveButton": "on_Save_click",
		"click #side-link-inventory-switch": "on_inventory_switch_click",
		"click #DeleteKid": "on_DeleteKid_click"
	},

	initialize: function (options)
	{
		var that = this;
		this._backend = options.backend;
		this._modelTag = new Tag({name: that.model.inventory()});
		this._viewTagSticker =
			new TagStickerView({
				model: that._modelTag,
				on_clicked: function(){that.on_InventoryInput_click();}
			});
		that.$("#InventoryInput").empty().append(
			that._viewTagSticker.el);
	},

	render: function ()
	{
		var that = this;

		that.$("#NameInput").val(that.model.name());
		that.$("#UserInput").val(that.model.user());
		that._viewTagSticker.render();

		setTimeout(function(){
			that.$("#NameInput").focus();
		}, 0);

		this.tooltips_prepare();

		return this;
	},

	on_Save_click: function(e)
	{
		e.preventDefault();
		this.save();
	},

	on_inventory_switch_click: function(e)
	{
		e.preventDefault();
		this.switchInventory();
	},

	on_InventoryInput_click: function()
	{
		this.switchInventory();
	},

	on_DeleteKid_click: function(e)
	{
		e.preventDefault();
		this.delete();
	},

	save: function ()
	{
		var that = this;

		that.tooltip_save_fail_hide();

		that.model.name_set(that.$("#NameInput").val());
		that.model.user_set(that.$("#UserInput").val());
		that.model.inventory_set(that._modelTag.name());

		that.model.save()
			.done(function()
			{
				that.save_done();
			})
			.fail(function(error)
			{
				that.save_fail(error);
			});
	},

	save_done: function()
	{
		this.remove();
	},

	save_fail: function(error)
	{
		// TODO save_fail
		this.tooltip_save_fail_show(error);
		console.log(error);
	},

	tooltip_save_fail_hide: function () {
		var button = this.$('#SaveButton');
		button.tooltipster('hide');
	},

	tooltip_save_fail_show: function (error) {
		var button = this.$('#SaveButton');
		button.tooltipster('content', ErrorHandler.toString(error));
		button.tooltipster('show');
	},

	tooltips_prepare: function ()
	{
		this.$('#SaveButton').tooltipster({
			content: 'Saved successfully!',
			position: 'right',
			trigger: 'custom',
			interactive: 'true'
		});
	},

	delete: function()
	{
		var that = this;
		this.model.destroy({wait: true})
			.done(function()
			{
				that.delete_done();
			})
			.fail(function(error)
			{
				that.delete_fail(error);
			});
	},

	delete_done: function()
	{
		this.remove();
	},

	delete_fail: function(error)
	{
		// TODO delete_fail
		console.log(error);
	},

	switchInventory: function()
	{
		var that = this;

		var viewInventorySwitch = new CollectionPickView(
			{
				purpose_text:
					that.model.name() + ' is allowed to play...? (pick one)',
				backend: that._backend,
				on_collection_pick: function(modelInventory)
				{
					that._modelTag.name_set(modelInventory.name());
					viewInventorySwitch.remove();
				}
			}
		).render();

		this.$el.after(viewInventorySwitch.el);
		$('html, body').scrollTop(viewInventorySwitch.$el.offset().top);
	},

	/**
	 * @param modelInventory TagSticker
	 */
	on_switch_inventory_pick: function(modelInventory)
	{
		this.model.inventory_set(modelInventory.name());
	},

	/**
	 * @type Kid
	 */
	model: null,

	/**
	 * @type Backend
	 */
	_backend: null,

}
);

