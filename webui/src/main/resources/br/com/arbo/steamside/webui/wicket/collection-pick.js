var SteamCategory = Backbone.Model.extend({
	name: function() {		"use strict";
		return this.get('name');
	},
    link: function() {		"use strict";
        return this.get('link');
    }
});

var SteamCategoryView = Backbone.View.extend({
    render: function() { "use strict";
        var that = this;
        var button_el = this.$el.find(".collection-pick-steam-category-button");
        button_el.text(this.model.name());
        var b = button_el.button();
        b.click(function(event){
            that.categoryClicked(event);
        });
        var icon_el = button_el.next();
        icon_el.button({
            text: false,
            icons: {
                primary: "ui-icon-help"
            }
        }).click(function(event){
                that.categoryClicked(event);
            });
        button_el.parent().buttonset();
        return this;
    },

    categoryClicked: function(e) {		"use strict";
        e.preventDefault();
        var aUrl = this.model.link();
        var type = dataTypeOf(aUrl);
        var that = this;
        $.ajax({
            url: aUrl,
            dataType: type,
            beforeSend: function(){
                /*
                loading_overlay.show();
                loading_underlay.addClass('game-tile-inner-blurred');
                */
            },
            complete: function(){
                /*
                loading_overlay.hide();
                loading_underlay.removeClass('game-tile-inner-blurred');
                 */
                that.category_changed();
            }
        });
    },

    category_changed: function() {

    }
});

var SteamCategoriesView = Backbone.View.extend({
    render: function() { "use strict";
        var container = this.$el;

        var one_el = this.$(".collection-pick-steam-category");
        container.empty();

        this.collection.each( function(one) {
            var view = new SteamCategoryView({
                model: one,
                el: one_el.clone()
            });
            container.append(view.render().el);
        });
        return this;
    }
});
