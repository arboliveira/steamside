var SteamsideCollectionApps = Backbone.Collection.extend({
    model: Game,
    url: 'apps.json'
});

var CollectionEditView = Backbone.View.extend({

    render: function() {		"use strict";
        new DeckView({
            el: this.$('#games-in-collection-deck'),
            collection: this.collection
        });

        return this;
    }
});
