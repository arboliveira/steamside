"use strict";

var SessionModel = Backbone.Model.extend(
{
	url: "api/session/session.json",

	backoff: function()
	{
		return this.booleanValue(this.get("backoff"));
	},

	versionOfSteamside: function()
	{
        return this.get("versionOfSteamside");
    },

    gamesOwned: function()
	{
        return this.get("gamesOwned");
    },

    userName : function()
	{
		return this.get('username');
    },

    kidsmode: function()
	{
		return this.booleanValue(this.get('kidsmode'));
    },

	booleanValue: function(v)
	{
		if (v === true) return true;
		return v === "true";
	}
});
