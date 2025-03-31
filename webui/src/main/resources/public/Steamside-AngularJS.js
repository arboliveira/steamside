export class Steamside_AngularJS
{
	constructor() {
		this.moduleSteamside = angular.module(
			"Steamside-ng-app", ["ngRoute"]);
	}

	moduleSteamside;

	configureApplicationModule()
	{
		this.config_routeProvider();
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
			.when('/', {templateUrl: 'Home.html',})
			.when('/mygames', {templateUrl: 'MyGames.html'})
			.when('/steamclient', {templateUrl: 'SteamClient.html'})
			.when('/settings', {templateUrl: 'Settings.html'})
			.when('/exit', {templateUrl: 'Exit.html'})
			.when('/collections/:name/edit', {templateUrl: 'InventoryWorld.html'})
			.when('/collections/new', {templateUrl: 'CollectionNew.html'})
			.otherwise({
				redirectTo: '/'
			});
	}
};
