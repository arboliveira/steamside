var SteamClientView = Backbone.View.extend({

    events: {
        "click .button-steam-browser-protocol": "buttonSteamBrowserProtocolClicked"
    },

    render: function () {  "use strict";
        return this;
    },

    buttonSteamBrowserProtocolClicked: function (e) {  "use strict";
        e.preventDefault();
        var jLink = $(e.target);
        var aUrl = jLink.attr( "href" );
        $.ajax(
            {
                url: aUrl
            }
        );
    }
});
