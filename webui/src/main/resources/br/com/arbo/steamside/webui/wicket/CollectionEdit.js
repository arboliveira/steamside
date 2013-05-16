var App = Backbone.Model.extend();

var AppCollection = Backbone.Collection.extend({
    model: App,
    name: null,
    url: function() {
        //return 'collection/' + this.name + '/apps.json';

        // will already be under collection/favorites
        return 'apps.json';
    }
});

$(document).ready(function() {
    var gametile = $('#game-tile');

    var appcollection = new AppCollection();

    new DeckView({
        el: $('#app-collection'),
        collection: appcollection,
        gametileEl: gametile
    });

    appcollection.name = 'favorites';

    appcollection.fetch({
        success: function() { if (false) console.log(c.models); },
        error: function() { console.log(arguments); }
    });

});
