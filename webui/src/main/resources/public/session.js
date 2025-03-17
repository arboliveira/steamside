export async function fetchSessionData()
{
	const location = "./api/session/session.json";
	const response = await fetch(import.meta.resolve(location));
	const json = await response.json();
	return {
		backoff: strictlyBoolean(json.backoff),
		versionOfSteamside: json.versionOfSteamside,
		gamesOwned: json.gamesOwned,
		userName: json.userName,
		kidsMode: strictlyBoolean(json.kidsMode),
		executable: json.executable
	}
}

function strictlyBoolean(value)
{
	if (value === true) return true;
	if (value === false) return false;
	throw new Error('Value must be boolean true or false');
}

export const SessionModel = Backbone.Model.extend(
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
		return this.get('userName');
    },

    kidsMode: function()
	{
		return this.booleanValue(this.get('kidsMode'));
    },

	executable: function()
	{
		return this.get('executable');
	},

	booleanValue: function(v)
	{
		if (v === true) return true;
		return v === "true";
	}
});
