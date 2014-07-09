var SessionModel = Backbone.Model.extend({
	url: "api/session/session.json",
    versionOfSteamside: function() {
        return this.get("versionOfSteamside");
    },
    gamesOwned: function() {
        return this.get("gamesOwned");
    },
    userName : function() {		"use strict";
		return this.get('username');
    },
    kidsmode: function() {		"use strict";
		var v = this.get('kidsmode');
		if (v === true) return true;
		return v === "true";
    }
});
