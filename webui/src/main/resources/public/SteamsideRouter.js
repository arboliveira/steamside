"use strict";

Steamside.Router =
{
	config_routeProvider: function($routeProvider) {
		$routeProvider
			.when('/', {
				templateUrl: Steamside.HomeWorld.htmlWorld,
				controller: Steamside.HomeWorld.nameController
			})
			.when('/mygames', {
				templateUrl: Steamside.MyGamesWorld.htmlWorld,
				controller: Steamside.MyGamesWorld.nameController
			})
			.when('/steamclient', {
				templateUrl: Steamside.SteamClientWorld.htmlWorld,
				controller: Steamside.SteamClientWorld.nameController
			})
			.when('/settings', {
				templateUrl: Steamside.SettingsWorld.htmlWorld,
				controller: Steamside.SettingsWorld.nameController
			})
			.when('/exit', {
				templateUrl: Steamside.ExitWorld.htmlWorld,
				controller: Steamside.ExitWorld.nameController
			})
			.when('/collections/:name/edit', {
				templateUrl: Steamside.InventoryWorld.htmlWorld,
				controller: Steamside.InventoryWorld.nameController
			})
			.when('/collections/new', {
				templateUrl: Steamside.CollectionsNewWorld.htmlWorld,
				controller: Steamside.CollectionsNewWorld.nameController
			})
			.otherwise({
				redirectTo: '/'
			});
	}
};
