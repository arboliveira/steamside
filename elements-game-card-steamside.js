import { Customary, CustomaryElement } from "#customary";
import { Backend } from "#steamside/data-backend.js";
import { pop_toast } from "#steamside/vfx-toaster.js";
export class GameCardElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.backend = new Backend();
        this.gameRunner = new GameRunner(this);
    }
    static { this.customary = {
        name: 'elements-game-card-steamside',
        config: {
            attributes: [
                'include_action_button_remove',
                'include_action_button_add',
                'kids_mode',
            ],
            state: [
                'game',
                'instruments_panel_visible',
                'game_tag_views',
                'game_tile_inner_loading_overlay_visible',
                'game_tile_play_visible',
                'action_button_remove_visible',
                'action_button_add_visible',
            ],
            define: {
                fontLocation: 'https://fonts.googleapis.com/css?family=Karla:regular,bold'
            },
        },
        values: {
            'game_tile_play_visible': true,
        },
        hooks: {
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            changes: {
                'game': (el, a) => el.#on_changed_game(a),
                'game_tile_inner_loading_overlay_visible': (el, a) => el.#on_changed_game_tile_inner_loading_overlay_visible(a),
                'include_action_button_remove': (el, a) => el.action_button_remove_visible = a === 'true',
                'include_action_button_add': (el, a) => el.action_button_add_visible = a === 'true',
            },
            events: [
                {
                    type: 'mouseenter',
                    selector: '.game-link',
                    listener: (el, e) => el.#mouseenter_hot_zone(e),
                },
                {
                    type: 'mouseleave',
                    selector: '.game-link',
                    listener: (el, e) => el.#mouseleave_hot_zone(e),
                },
                {
                    type: 'click',
                    selector: '.game-link',
                    listener: (el, e) => el.#gameClicked(e),
                },
                {
                    type: 'click',
                    selector: '.game-tile-play',
                    listener: (el, e) => el.#playClicked(e),
                },
                {
                    type: 'click',
                    selector: '.game-tile-tag',
                    listener: (el, e) => el.#on_action_button_click(e, 'tag'),
                },
                {
                    type: 'click',
                    selector: '.action-button-remove',
                    listener: (el, e) => el.#on_action_button_click(e, 'remove'),
                },
                {
                    type: 'click',
                    selector: '.action-button-add',
                    listener: (el, e) => el.#on_action_button_click(e, 'add'),
                },
                {
                    type: 'game:play:beforeSend',
                    listener: (el, e) => el.#game_play_beforeSend(),
                },
                {
                    type: 'game:play:complete',
                    listener: (el, e) => el.#game_play_complete(),
                },
            ],
            lifecycle: {
                connected: el => el.#on_connected(),
                willUpdate: el => el.#on_willUpdate(),
            },
        },
    }; }
    #on_willUpdate() {
        this.instruments_panel_visible = this.kids_mode !== 'true';
    }
    #on_action_button_click(e, action_button) {
        e.preventDefault();
        this.dispatchEvent(new CustomEvent('GameCardElement:ActionButtonClick', {
            detail: {
                action_button,
                game: this.game,
                targetInteractedWith: e.target,
            },
            composed: true,
            bubbles: true,
        }));
    }
    #on_changed_game(a) {
        this.#on_changed_game_tags(a.tags);
        this.#on_changed_game_unavailable(a.unavailable);
    }
    #on_changed_game_tags(a) {
        if (!a)
            return;
        const game_tags = a;
        const views = game_tags.map(tag => ({
            tag_name: tag.name,
            tag_url: `./InventoryWorld.html?name=${tag.name}`,
        }));
        this.game_tag_views = views;
    }
    #on_changed_game_unavailable(aBoolean) {
        const available = !aBoolean;
        this.game_tile_play_visible = available;
        if (!available)
            this.#get_game_tile().classList.add('game-unavailable');
    }
    #get_game_tile() {
        return this.renderRoot.querySelector('.game-tile');
    }
    #mouseenter_hot_zone(e) {
        e.preventDefault();
        const littleCommandToLightUp = this.#littleCommandToLightUp();
        if (littleCommandToLightUp == null)
            return;
        littleCommandToLightUp.classList.add('what-will-happen');
    }
    #mouseleave_hot_zone(e) {
        e.preventDefault();
        const littleCommandToLightUp = this.#littleCommandToLightUp();
        if (littleCommandToLightUp == null)
            return;
        littleCommandToLightUp.classList.remove('what-will-happen');
    }
    #littleCommandToLightUp() {
        // return the first of them all
        return this.renderRoot.querySelector('.game-tile-command');
    }
    async #gameClicked(e) {
        e.preventDefault();
        const w = this.#littleCommandToLightUp();
        if (w) {
            w.click();
            return;
        }
        await this.#playClicked(e);
    }
    async #playClicked(e) {
        e.preventDefault();
        await this.playGame();
    }
    async playGame() {
        await this.gameRunner.runGame(this.game.link, this);
    }
    #game_play_beforeSend() {
        this.#showOverlay();
    }
    #game_play_complete() {
        this.#hideOverlay();
        this.#redisplay_continues();
    }
    #showOverlay() {
        this.game_tile_inner_loading_overlay_visible = true;
    }
    #on_changed_game_tile_inner_loading_overlay_visible(a) {
        const underlay = this.renderRoot.querySelector('.game-tile-inner');
        if (a) {
            underlay.classList.add('game-tile-inner-blurred');
        }
        else {
            underlay.classList.remove('game-tile-inner-blurred');
        }
    }
    #hideOverlay() {
        this.game_tile_inner_loading_overlay_visible = false;
    }
    #redisplay_continues() {
        // FIXME happens after the game is played. may not be needed if continues view updates itself
        // this.backend.fetch_fetch_json(this.continues);
    }
    async #on_connected() {
        await this.backend.fetchSessionDataAndDisableBackendIfOffline();
    }
}
Customary.declare(GameCardElement);
class GameRunner {
    constructor(card) {
        this.card = card;
    }
    async runGame(aUrl, gameCardElement) {
        this.notify_before_send(gameCardElement);
        try {
            await this.card.backend.fetchBackend({ url: aUrl });
            this.notify_complete(gameCardElement);
        }
        catch (error) {
            pop_toast({
                error: error,
                completion_fn: (target) => this.notify_complete(target),
                offline_imagine_spot: `you're playing`,
                target: gameCardElement.renderRoot.lastElementChild
            });
        }
    }
    notify_before_send(target) {
        this.triggerEvent(target, 'game:play:beforeSend');
    }
    notify_complete(target) {
        this.triggerEvent(target, 'game:play:complete');
    }
    triggerEvent(target, type) {
        target.dispatchEvent(new CustomEvent(type, {
            composed: true,
        }));
    }
}
//# sourceMappingURL=elements-game-card-steamside.js.map