var SessionModel = Backbone.Model.extend({
	url: "api/session/session.json",
    versionOfSteamside: function() {
        return this.get("versionOfSteamside");
    },
    userName : function() {		"use strict";
		return this.get('username');
    },
    kidsmode: function() {		"use strict";
		return this.get('kidsmode') === true;
    }
});

