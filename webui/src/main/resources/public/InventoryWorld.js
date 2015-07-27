Steamside.InventoryWorld =
{
	nameController: 'InventoryWorldController',

	htmlWorld: 'InventoryWorld.html',

	controller: function(
		$scope, $routeParams, theBackend, spritesSteamside)
	{
		var inventory_name = $routeParams.name;

		var spriteMoreButton = spritesSteamside.moreButton;
		var cardTemplatePromise = spritesSteamside.card.sprite_promise();

		/*
		 https://github.com/jashkenas/backbone/issues/2566#issuecomment-26065829
		 */
		var workaroundFirefox = decodeURIComponent(inventory_name);

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


var InventoryWorldView = Backbone.View.extend(
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
		var that = this;

		var inventory = new SteamsideCollectionApps();
		inventory.collection_name = this._inventory_name;

		this.$el.append(
			new CollectionEditView(
				{
					inventory: inventory,
					collection_name: this._inventory_name,
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
