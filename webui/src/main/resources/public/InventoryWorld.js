import {SteamsideCollectionApps} from "#steamside/Inventory.js";
import {CollectionEditView} from "#steamside/CollectionEdit.js";
import {Tag} from "#steamside/Tag.js";
import {sideshow} from "#steamside/Sideshow.js";

export const Steamside_InventoryWorld =
{
	nameController: 'InventoryWorldController',

	htmlWorld: 'InventoryWorld.html',

	controller: function(
		$scope, $routeParams, theBackend, spritesSteamside)
	{
		const inventory_name = $routeParams.name;

		const spriteMoreButton = spritesSteamside.moreButton;
		const cardTemplatePromise = spritesSteamside.card.sprite_promise();

		/*
		 https://github.com/jashkenas/backbone/issues/2566#issuecomment-26065829
		 */
		const workaroundFirefox = decodeURIComponent(inventory_name);

		new InventoryWorldView(
			{
				inventory_name: workaroundFirefox,
				backend: theBackend,
				cardTemplatePromise: cardTemplatePromise,
				spriteMoreButton: spriteMoreButton
			}
		).render();
	}
};


const InventoryWorldView = Backbone.View.extend(
{
	el: "#CollectionEditView",

	initialize: function(options)
	{
		this._backend = options.backend;
		this._inventory_name = options.inventory_name;
		this._cardTemplatePromise = options.cardTemplatePromise;
		this._spriteMoreButton = options.spriteMoreButton;
	},

	render: function () {
		const that = this;

		const inventory = new SteamsideCollectionApps();
		inventory.collection_name = this._inventory_name;

		const tag = new Tag({name: this._inventory_name});

		this.$el.append(
			new CollectionEditView(
				{
					inventory: inventory,
					tag: tag,
					cardTemplatePromise: this._cardTemplatePromise,
					spriteMoreButton: this._spriteMoreButton,
					backend: this._backend
				}
			).render().el
		);

		that.sideshow();
	},

	sideshow: function()
	{
		sideshow(this.$el);
	}
}
);
