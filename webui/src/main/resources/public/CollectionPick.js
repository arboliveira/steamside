"use strict";

var CollectionPickSpriteSheet = Backbone.Model.extend(
{
	/**
	 * @public
	 * @type Sprite
	 */
	room: null,

	/**
	 * @public
	 * @type Sprite
	 */
	stickers: null,

	/**
	 * @public
	 * @type Sprite
	 */
	sticker: null,

	initialize: function () {
		var sheet = new SpriteSheet({url: 'CollectionPick.html'});
		try {
			this.room = sheet.sprite("#collection-pick-collections-segment");
			this.stickers = sheet.sprite("#TagStickersView");
			this.sticker = sheet.sprite("#TagStickerView");
		}
		finally {
			sheet.dispose();
		}
	}
});

var CollectionPickSpriteSheetSingleton = {
	sprites: new CollectionPickSpriteSheet()
};


var CollectionPickView = Backbone.View.extend(
{
	events: {
		"click #LinkTagless": "clickLinkTagless",
		"click #LinkOwned": "clickLinkOwned"
	},

	initialize: function(options)
	{
		this._on_collection_pick = options.on_collection_pick;
		this._backend = options.backend;
		this._purposeless = options.purposeless;
		this._purpose_text = options.purpose_text;
		this._purpose_el = options.purpose_el;
		this._cardTemplatePromise = options.cardTemplatePromise;
		this._spriteMoreButton = options.spriteMoreButton;
	},

	render: function()
	{
		var that = this;
		CollectionPickView.sprite.sprite_promise().done(function(el)
			{
				that.$el.append(el.clone());
				that.render_el();
			});
		return that;
	},

	render_el: function()
	{
		var that = this;
		that.renderPurpose();
		that.renderTagStickers();
		return that;
	},

	renderPurpose: function ()
	{
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

	renderTagStickers: function ()
	{
		var that = this;

		var tags = new TagsCollection();

		that._backend.fetch_promise(tags);

		that.$("#TagStickersView").empty().append(
			new TagStickersView(
				{
					collection: tags,
					on_tag_clicked: that._on_collection_pick
				})
				.render().el
		);
	},

	clickLinkTagless: function(e)
	{
		e.preventDefault();

		var that = this;

		var inventory = new TaglessAppsInventory();

		var inventoryView = new CollectionEditView({
				inventory: inventory,
				collection_name: "Tagless",
				cardTemplatePromise: that._cardTemplatePromise,
				spriteMoreButton: that._spriteMoreButton,
				backend: that._backend,
				simplified: true
			}).render();

		that.$el.after(inventoryView.$el);
	},

	clickLinkOwned: function(e)
	{

		e.preventDefault();

	},

	_on_collection_pick: null,
	_backend: null

}, {

	/**
	 * @public
	 * @type Sprite
	 */
	sprite: CollectionPickSpriteSheetSingleton.sprites.room

});
