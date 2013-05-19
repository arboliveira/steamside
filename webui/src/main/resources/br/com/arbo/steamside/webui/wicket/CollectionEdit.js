var SteamsideCollectionApps = Backbone.Collection.extend({
    model: Game,
    url: 'apps.json'
});

var CollectionEditView = Backbone.View.extend({

    initialize: function() {		"use strict";

    },

    render: function() {		"use strict";
        new DeckView({
            el: this.$('#games-in-collection-deck'),
            collection: this.collection
        });

        return this;
    }
});
