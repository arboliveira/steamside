import {newBackendMaybeDisabledThisSession} from "#steamside/Backend.js";
import {Steamside_SettingsWorld} from "#steamside/Settings.js";
import {Steamside_InventoryWorld} from "#steamside/InventoryWorld.js";
import {Steamside_CollectionsNewWorld} from "#steamside/CollectionNew.js";
import {Steamside_MyGamesWorld} from "#steamside/MyGames.js";
import {Steamside_HomeWorld} from "#steamside/Home.js";
import {SteamsideSpriteSheet} from "#steamside/SteamsideSpriteSheet.js";
import {KidsSpriteSheet} from "#steamside/KidsHome.js";
import {SessionModel} from "#steamside/session.js";

const nameBackend = 'Backend';
const nameSessionModel = 'SessionModel';
const nameKidsMode = 'KidsMode';
const nameSpritesKids = 'SpritesKids';
const nameSpritesSteamside = 'SpritesSteamside';

export class Steamside_AngularJS
{
	constructor() {
		this.moduleSteamside = angular.module(
			"Steamside-ng-app", ["ngRoute"]);
	}

	moduleSteamside;

	configureApplicationModule()
	{
		this.constant_Backend();
		this.constant_SpritesKids();
		this.constant_SpritesSteamside();

		this.factory_SessionModel();
		this.factory_KidsMode();

		this.controller_HomeWorld();
		this.controller_MyGamesWorld();
		this.controller_SettingsWorld();
		this.controller_InventoryWorld();
		this.controller_CollectionsNewWorld();

		this.config_routeProvider();
	}

	constant_Backend()
	{
		const backend = newBackendMaybeDisabledThisSession();
		this.moduleSteamside.constant(nameBackend, backend);
	}

	constant_SpritesKids() {
		const that = this;
		that.moduleSteamside.constant(
			nameSpritesKids, new KidsSpriteSheet());
	}

	constant_SpritesSteamside() {
		const that = this;
		that.moduleSteamside.constant(
			nameSpritesSteamside, new SteamsideSpriteSheet());
	}

	factory_SessionModel()
	{
		const that = this;
		that.moduleSteamside.factory(
			nameSessionModel,
			[nameBackend, function(_theBackend) {
				return new SessionModel();
			}]);
	}

	factory_KidsMode()
	{
		const that = this;
		that.moduleSteamside.factory(
			nameKidsMode,
			[nameSessionModel, function(theSessionModel) {
				return theSessionModel;
			}]);
	}

	controller_HomeWorld()
	{
		const that = this;
		that.moduleSteamside.controller(
			Steamside_HomeWorld.nameController,
			['$scope', '$location',
				nameBackend,
				nameSessionModel,
				nameKidsMode,
				nameSpritesKids,
				nameSpritesSteamside,
			function ($scope, $location, theBackend, theSessionModel, theKidsMode,
				  		theSpritesKids, theSpritesSteamside){
				Steamside_HomeWorld.controller(
					$scope, $location, theBackend, theSessionModel, theKidsMode,
					theSpritesKids, theSpritesSteamside);
			}]);
	}

	controller_MyGamesWorld()
	{
		const that = this;
		that.moduleSteamside.controller(
			Steamside_MyGamesWorld.nameController,
				['$scope', nameBackend, nameSpritesSteamside,
					function ($scope, theBackend, spritesSteamside){
				Steamside_MyGamesWorld.controller($scope, theBackend, spritesSteamside);
			}]);
	}

	controller_SettingsWorld()
	{
		const that = this;
		that.moduleSteamside.controller(
			Steamside_SettingsWorld.nameController,
			['$scope', nameBackend, function ($scope, theBackend){
				Steamside_SettingsWorld.controller($scope, theBackend);
			}]);
	}

	controller_InventoryWorld()
	{
		const that = this;
		that.moduleSteamside.controller(
			Steamside_InventoryWorld.nameController,
			['$scope', '$routeParams',
			nameBackend,
			nameSpritesSteamside,
				function ($scope, $routeParams,
						  theBackend, spritesSteamside){
					Steamside_InventoryWorld.controller(
						$scope, $routeParams, theBackend,
						spritesSteamside);
				}]);
	}

	controller_CollectionsNewWorld()
	{
		const that = this;
		that.moduleSteamside.controller(
			Steamside_CollectionsNewWorld.nameController,
			['$scope', nameBackend, function ($scope, theBackend){
				Steamside_CollectionsNewWorld.controller($scope, theBackend);
			}]);
	}

	config_routeProvider()
	{
		const that = this;
		that.moduleSteamside.config(
			['$routeProvider', function($routeProvider) {
				SteamsideRouter.config_routeProvider($routeProvider);
			}]);
	}
}

const SteamsideRouter =
{
	config_routeProvider: function($routeProvider) {
		$routeProvider
			.when('/', {
				templateUrl: Steamside_HomeWorld.htmlWorld,
				controller: Steamside_HomeWorld.nameController
			})
			.when('/mygames', {
				templateUrl: Steamside_MyGamesWorld.htmlWorld,
				controller: Steamside_MyGamesWorld.nameController
			})
			.when('/steamclient', {templateUrl: 'SteamClient.html'})
			.when('/settings', {
				templateUrl: Steamside_SettingsWorld.htmlWorld,
				controller: Steamside_SettingsWorld.nameController
			})
			.when('/exit', {templateUrl: 'Exit.html'})
			.when('/collections/:name/edit', {
				templateUrl: Steamside_InventoryWorld.htmlWorld,
				controller: Steamside_InventoryWorld.nameController
			})
			.when('/collections/new', {
				templateUrl: Steamside_CollectionsNewWorld.htmlWorld,
				controller: Steamside_CollectionsNewWorld.nameController
			})
			.otherwise({
				redirectTo: '/'
			});
	}
};
