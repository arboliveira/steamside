import {CollectionPickSpriteSheetSingleton} from "#steamside/CollectionPick.js";

export const TagStickerView = Backbone.View.extend(
    {
        events: {
            "click": "on_click"
        },

        initialize: function (options) {
            const that = this;

            this._on_clicked = options.on_clicked;

            this._el_ready = CollectionPickSpriteSheetSingleton.sprites.sticker
                .sprite_promise().then(function (el) {
                    that.$el.append(el.clone());
                });

            this.listenTo(this.model, "change", this.render);
        },

        render: function () {
            const that = this;
            that._el_ready.done(function () {
                that.render_el();
            });
            return that;
        },

        render_href: function () {
            if (this.model.readonly()) {
                return;
            }
            const name_text = this.model.name();
            const encoded = encodeURIComponent(name_text);
            const fragment = '#/collections/' + encoded + '/edit';
            this.link_el().attr('href', fragment);
        },

        render_name: function () {
            const name_el = this.$el.find("#collection-pick-one-name");
            const name_text = this.model.name();
            name_el.text(name_text);
        },

        render_count: function () {
            const $count = this.$("#count");
            const count = this.model.count();
            if (count !== undefined) {
                $count.text("" + count);
                $count.show();
            } else {
                $count.hide();
            }
        },

        render_el: function () {
            this.render_name();
            this.render_href();
            this.render_count();
            return this;
        },

        link_el: function () {
            return this.$el.find("#collection-pick-one-link");
        },

        on_click: function (e) {
            if (this.model.readonly()) {
                e.preventDefault();
                return;
            }

            if (this._on_clicked != null) {
                e.preventDefault();
                this._on_clicked(this.model);
            }
        },

        _on_clicked: null

    });