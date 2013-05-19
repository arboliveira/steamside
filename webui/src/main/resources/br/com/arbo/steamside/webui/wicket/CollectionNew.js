var CollectionNewEmptyView = Backbone.View.extend({

    events: {
        "keyup #input-empty-name": "on_input_keyup",
        "change #input-empty-name": "on_input_keyup"
    },

    on_input_keyup: function (e) {  "use strict";
        e.preventDefault();
        var val = $(e.target).val();
        var full;
        if (val === '') {
            full = "Create collection";
        } else {
            full = "Create " + val + " collection and start adding games";
        }
        var el_hint = this.$("#empty-command-hint");
        el_hint.text(full);
        var form = this.$("#form-empty");
        form.attr("action", "#/collections/" + val + "/edit");
    }
});
