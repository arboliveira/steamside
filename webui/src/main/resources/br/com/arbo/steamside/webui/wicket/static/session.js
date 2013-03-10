var SessionModel = Backbone.Model.extend({
	url: "session.json",
    username : function() {		"use strict";
		return this.get('username');
    },
    kidsmode: function() {		"use strict";
		return this.get('kidsmode') === true;
    }
});

