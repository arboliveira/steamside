import {OwnedAppsInventory, OwnedCount, TaglessAppsInventory, TaglessCount} from "#steamside/Inventory.js";
import {CollectionEditView} from "#steamside/CollectionEdit.js";
import {TagStickersView} from "#steamside/TagStickersView.js";
import {TagsCollection} from "#steamside/Tag.js";
import {SpriteSheet} from "#steamside/spritesheet.js";

const CollectionPickSpriteSheet = Backbone.Model.extend(
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
		const sheet = new SpriteSheet({url: 'CollectionPick.html'});
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

export const CollectionPickSpriteSheetSingleton = {
	sprites: new CollectionPickSpriteSheet()
};


export const CollectionPickView = Backbone.View.extend(
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
		const that = this;
		CollectionPickView.sprite.sprite_promise().done(function(el)
			{
				that.$el.append(el.clone());
				that.render_el();
			});
		return that;
	},

	render_el: function()
	{
		const that = this;
		that.renderPurpose();
		that.renderTagStickers();
		return that;
	},

	renderPurpose: function ()
	{
		const that = this;

		if (that._purposeless) {
			that.$('#PurposeView').hide();
		}

		if (that._purpose_text !== undefined) {
			that.$('#InnerPurpose').text(that._purpose_text);
		}

		if (that._purpose_el !== undefined) {
			const el_purpose = that.$('#InnerPurpose');
			el_purpose.empty();
			el_purpose.append(that._purpose_el);
		}
		return that;
	},

	renderTagStickers: function ()
	{
		const that = this;

		const tags = new TagsCollection();

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

		const that = this;

		const inventory = new TaglessAppsInventory();

		const tag = new TaglessCount();
		this._backend.fetch_promise(tag).done(function() {
			tag.readonly_set(true);
		});

		const inventoryView = new CollectionEditView({
				inventory: inventory,
				tag: tag,
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

		const that = this;

		const inventory = new OwnedAppsInventory();

		const tag = new OwnedCount();
		this._backend.fetch_promise(tag).done(function() {
			tag.readonly_set(true);
		});

		const inventoryView = new CollectionEditView({
			inventory: inventory,
			tag: tag,
			cardTemplatePromise: that._cardTemplatePromise,
			spriteMoreButton: that._spriteMoreButton,
			backend: that._backend,
			simplified: true
		}).render();

		that.$el.after(inventoryView.$el);
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
