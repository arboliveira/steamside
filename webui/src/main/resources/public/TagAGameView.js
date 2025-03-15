import {CommandBoxView} from "#steamside/CommandBox.js";
import {TagStickersView} from "#steamside/TagStickersView.js";
import {TagSuggestionsCollection} from "#steamside/Tag.js";
import {GameCardView} from "#steamside/GameCardDeck.js";
import {SpriteBuilder} from "#steamside/spritesheet.js";

export const TagAGameView = Backbone.View.extend({

    initialize: function (options) {
        const that = this;
        this.game = options.game;
        if (options.cardTemplatePromise === undefined) {
            throw new Error("cardTemplatePromise is required");
        }
        this.cardTemplatePromise = options.cardTemplatePromise;
        this.backend = options.backend;
        this.viewCommandBox = new CommandBoxView(
            {
                placeholder_text: 'Collection for ' + this.game.name(),
                on_command: function (input) {
                    that.on_tag_command(input)
                },
                on_command_alternate: function (input) {
                    that.on_tag_command_alternate(input)
                },
                on_change_input: function (input) {
                    that.on_tag_change_input(input);
                }
            }
        );
        this.suggestions = new TagSuggestionsCollection();
    },

    render: function () {
        const that = this;
        this.whenRendered =
            TagAGameView.sprite.sprite_promise().then(function (el) {
                that.$el.append(el.clone());
                that.render_el();
                return that;
            });
        return this;
    },

    render_el: function () {
        this.renderGameCard();
        this.renderCommandHints();
        this.renderCommandBox();
        this.renderTagSuggestions();
    },

    renderGameCard: function () {
        const that = this;

        const enormityRegular = {
            styleClass: 'game-tile-regular',
            width: 30
        };

        this.viewGameCard = new GameCardView({
            el: this.$("#GameCardView").empty(),
            cardTemplatePromise: that.cardTemplatePromise,
            model: that.game,
            enormity: enormityRegular,
            backend: this.backend,
            kidsMode: this.kidsMode,
            continues: this.continues,
            on_render: this.on_GameCard_render,
            on_tag: function (game) {
                that.on_tag_deck(game);
            }
        });
        this.viewGameCard.render();
    },

    renderCommandHints: function () {
        const template = this.$('#tag-command-hint');
        template.remove();

        this.elCommandHintA =
            this.renderCommandHint(template, "Tag as");
        this.elCommandHintB =
            this.renderCommandHint(template, "Search collections for");
    },

    renderCommandHint: function (template, begin) {
        const el = template.clone();
        el.find('#tag-command-hint-begin').text(begin);
        return el;
    },

    renderCommandBox: function () {
        const that = this;
        that.$('#div-command-box').empty().append(this.viewCommandBox.el);
        this.viewCommandBox.render_commandBox_promise().done(function (view) {
            that.on_CommandBox_whenRendered(view);
        });
    },

    on_CommandBox_whenRendered: function (viewCommandBox) {
        viewCommandBox.emptyCommandHints();
        viewCommandBox.appendCommandHint(this.elCommandHintA);
        viewCommandBox.appendCommandHintAlternate(this.elCommandHintB);

        // TODO Favorites or most recently used
        this.updateWithInputValue("");

        viewCommandBox.input_query_focus();
    },

    renderTagSuggestions: function () {
        const that = this;
        this.backend.fetch_promise(that.suggestions);
        that.$("#TagSuggestionsView").empty().append(
            new TagStickersView({
                collection: that.suggestions,
                on_tag_clicked: function (model) {
                    that.on_tag_suggestion_select(model)
                }
            }).render().el
        );
    },

    on_tag_command: function (view) {
        const input = view.input_query_val();

        const appid = this.game.appid();
        const collection = this.nameForCollection(input);
        const aUrl = "api/app/" + appid + "/tag/" + encodeURIComponent(collection);
        const that = this;

        // TODO display 'creating...'
        /*
         beforeSend: function(){
         },
         */

        this.backend.ajax_ajax_promise(aUrl)
            .done(function () {
                that.on_tag_done();
            })
            .fail(function (error) {
                view.trouble(error);
            });
    },

    on_tag_command_alternate: function (view) {

    },

    on_tag_done: function () {
        this.viewCommandBox.input_query_setval('');

        // TODO update the Game model in this view, otherwise tags don't change
        this.viewGameCard.viewGame_Tag_List.render();
    },

    nameForCollection: function (input) {
        if (input === '') return "Favorites";
        return input;
    },

    on_tag_change_input: function (view) {
        const input = view.input_query_val();
        this.updateWithInputValue(input);
    },

    updateWithInputValue: function (input) {
        const name = this.nameForCollection(input);
        const selector = '#tag-command-hint-subject';
        this.elCommandHintA.find(selector).text(name);
        this.elCommandHintB.find(selector).text(name);
    },

    on_tag_suggestion_select: function (model) {
        this.viewCommandBox.input_query_setval(model.name());
    },

    /**
     * @type Game
     */
    game: null,

    /**
     * @type CommandBoxView
     */
    viewCommandBox: null,

    /**
     * @type GameCardView
     */
    viewGameCard: null,

    whenRendered: null

}, {

    /**
     * @public
     * @type Sprite
     */
    sprite: new SpriteBuilder({url: 'Tag.html', selector: "#TagTile"}).build()

});