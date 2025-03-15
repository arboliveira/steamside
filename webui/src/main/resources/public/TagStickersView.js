import {CollectionPickSpriteSheetSingleton} from "#steamside/CollectionPick.js";

import {TagStickerView} from "#steamside/TagStickerView.js";

export const TagStickersView = Backbone.View.extend(
    {
        initialize: function (options) {
            this._on_tag_clicked = options.on_tag_clicked;

            this.listenTo(this.collection, 'reset', this.render);
        },

        render: function () {
            const that = this;
            CollectionPickSpriteSheetSingleton.sprites.stickers
                .sprite_promise().done(function (el) {
                that.$el.append(el.clone());
                that.render_el();
            });
            return that;
        },

        render_el: function () {
            const that = this;

            if (that.collection.length == 0) {
                that.$("#TagStickerView").remove();
                that.$("#no-collections-yet").show();
                return that;
            }

            that.$el.empty();

            that.collection.each(function (one) {
                that.$el.append(
                    new TagStickerView({
                        model: one,
                        on_clicked: that._on_tag_clicked
                    })
                        .render().el
                );
            });

            return that;
        },

        _on_tag_clicked: null
    }
);