"use strict";

var Steamside_AngularJS =
{
	moduleSteamside: null,

	configureApplicationModule: function ()
	{
		this.moduleSteamside = angular.module(
			"Steamside-ng-app", ["ngRoute"]);

		this.constant_Backend();
		this.constant_SpritesKids();
		this.constant_SpritesSteamside();

		this.factory_SessionModel();
		this.factory_KidsMode();

		this.controller_HomeWorld();
		this.controller_MyGamesWorld();
		this.controller_SteamClientWorld();
		this.controller_SettingsWorld();
		this.controller_ExitWorld();
		this.controller_InventoryWorld();
		this.controller_CollectionsNewWorld();

		this.config_routeProvider();
	},

	nameBackend: 'Backend',
	nameSessionModel: 'SessionModel',
	nameKidsMode: 'KidsMode',
	nameSpritesKids: 'SpritesKids',
	nameSpritesSteamside: 'SpritesSteamside',
	nameCardTemplatePromise: 'CardTemplatePromise',

	constant_Backend: function()
	{
		var that = this;
		var backoffModel = new BackoffModel();
		var backend = new Backend();
		backend.fetch_promise(backoffModel).done(function() {
			backend.set_backoff(backoffModel.backoff());
		});
		that.moduleSteamside.constant(
			Steamside_AngularJS.nameBackend, backend);
	},

	constant_SpritesKids: function() {
		var that = this;
		that.moduleSteamside.constant(
			Steamside_AngularJS.nameSpritesKids, new KidsSpriteSheet());
	},

	constant_SpritesSteamside: function() {
		var that = this;
		that.moduleSteamside.constant(
			Steamside_AngularJS.nameSpritesSteamside, new SteamsideSpriteSheet());
	},

	factory_SessionModel: function()
	{
		var that = this;
		that.moduleSteamside.factory(
			Steamside_AngularJS.nameSessionModel,
			[Steamside_AngularJS.nameBackend, function(theBackend) {
				return new SessionModel();
			}]);
	},

	factory_KidsMode: function()
	{
		var that = this;
		that.moduleSteamside.factory(
			Steamside_AngularJS.nameKidsMode,
			[Steamside_AngularJS.nameSessionModel, function(theSessionModel) {
				return theSessionModel;
			}]);
	},

	controller_HomeWorld: function()
	{
		var that = this;
		that.moduleSteamside.controller(
			Steamside.HomeWorld.nameController,
			['$scope', '$location',
				Steamside_AngularJS.nameBackend,
				Steamside_AngularJS.nameSessionModel,
				Steamside_AngularJS.nameKidsMode,
				Steamside_AngularJS.nameSpritesKids,
				Steamside_AngularJS.nameSpritesSteamside,
			function ($scope, $location, theBackend, theSessionModel, theKidsMode,
				  		theSpritesKids, theSpritesSteamside){
				Steamside.HomeWorld.controller(
					$scope, $location, theBackend, theSessionModel, theKidsMode,
					theSpritesKids, theSpritesSteamside);
			}]);
	},

	controller_MyGamesWorld: function()
	{
		var that = this;
		that.moduleSteamside.controller(
			Steamside.MyGamesWorld.nameController,
				['$scope', Steamside_AngularJS.nameBackend, Steamside_AngularJS.nameSpritesSteamside,
					function ($scope, theBackend, spritesSteamside){
				Steamside.MyGamesWorld.controller($scope, theBackend, spritesSteamside);
			}]);
	},

	controller_SteamClientWorld: function()
	{
		var that = this;
		that.moduleSteamside.controller(
			Steamside.SteamClientWorld.nameController,
			['$scope', Steamside_AngularJS.nameBackend, function ($scope, theBackend){
				Steamside.SteamClientWorld.controller($scope, theBackend);
			}]);
	},

	controller_SettingsWorld: function()
	{
		var that = this;
		that.moduleSteamside.controller(
			Steamside.SettingsWorld.nameController,
			['$scope', Steamside_AngularJS.nameBackend, function ($scope, theBackend){
				Steamside.SettingsWorld.controller($scope, theBackend);
			}]);
	},

	controller_ExitWorld: function()
	{
		var that = this;
		that.moduleSteamside.controller(
			Steamside.ExitWorld.nameController,
			['$scope', Steamside_AngularJS.nameBackend, function ($scope, theBackend){
				Steamside.ExitWorld.controller($scope, theBackend);
			}]);
	},

	controller_InventoryWorld: function()
	{
		var that = this;
		that.moduleSteamside.controller(
			Steamside.InventoryWorld.nameController,
			['$scope', '$routeParams',
			Steamside_AngularJS.nameBackend,
			Steamside_AngularJS.nameSpritesSteamside,
				function ($scope, $routeParams,
						  theBackend, spritesSteamside){
					Steamside.InventoryWorld.controller(
						$scope, $routeParams, theBackend,
						spritesSteamside);
				}]);
	},

	controller_CollectionsNewWorld: function()
	{
		var that = this;
		that.moduleSteamside.controller(
			Steamside.CollectionsNewWorld.nameController,
			['$scope', Steamside_AngularJS.nameBackend, function ($scope, theBackend){
				Steamside.CollectionsNewWorld.controller($scope, theBackend);
			}]);
	},

	config_routeProvider: function()
	{
		var that = this;
		that.moduleSteamside.config(
			['$routeProvider', function($routeProvider) {
				Steamside.Router.config_routeProvider($routeProvider);
			}]);
	}
};
