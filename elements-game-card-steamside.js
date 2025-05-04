import { Customary, CustomaryElement } from "#customary";
import { toastOrNot } from "#steamside/vfx-toaster.js";
import { PlayPleaseEvent } from "#steamside/requests/play/PlayPleaseEvent.js";
import { GameCardElement_ActionButtonClick_eventName } from "#steamside/elements/game-card/GameCardElement_ActionButtonClick_Event.js";
import { NowPlayingEvent } from "#steamside/requests/play/NowPlayingEvent.js";
import { GameOverEvent } from "#steamside/requests/play/GameOverEvent.js";
export class GameCardElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-game-card-steamside',
        config: {
            attributes: [
                'include_action_button_remove',
                'include_action_button_add',
                'kids_mode',
                'now_playing',
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
                'now_playing': (el, a) => el.#on_changed_now_playing(a),
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
                    type: NowPlayingEvent.eventName,
                    listener: (el, e) => el.#on_NowPlayingEvent(e),
                },
                {
                    type: GameOverEvent.eventName,
                    listener: (el, e) => el.#on_GameOverEvent(e),
                },
            ],
            lifecycle: {
                willUpdate: el => el.#on_willUpdate(),
            },
        },
    }; }
    #on_willUpdate() {
        this.instruments_panel_visible = this.kids_mode !== 'true';
    }
    #on_action_button_click(e, action_button) {
        e.preventDefault();
        const detail = {
            action_button,
            game: this.game,
            originator: e.target,
        };
        this.dispatchEvent(new CustomEvent(GameCardElement_ActionButtonClick_eventName, {
            detail,
            composed: true,
            bubbles: true,
        }));
    }
    #on_changed_game(a) {
        this.#on_changed_game_tags(a.tags);
        this.#on_changed_game_unavailable(a.unavailable);
    }
    #on_changed_game_tags(game_tags) {
        if (!game_tags)
            return;
        this.game_tag_views = game_tags.map(tag => ({
            tag_name: tag.name,
            tag_url: `./InventoryWorld.html?name=${tag.name}`,
        }));
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
        const url = this.game.link;
        this.dispatchEvent(new PlayPleaseEvent({
            appid: this.game.appid,
            url,
            originator: this,
        }));
    }
    #on_NowPlayingEvent(e) {
        this.setAttribute('now_playing', 'true');
        toastOrNot({
            content: e.detail.toast_content,
            target: this.renderRoot.lastElementChild,
        });
    }
    #on_GameOverEvent(_e) {
        this.setAttribute('now_playing', 'false');
    }
    #on_changed_now_playing(a) {
        this.game_tile_inner_loading_overlay_visible = isOn(a);
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
}
Customary.declare(GameCardElement);
function isOn(a) {
    if (a === 'false')
        return false;
    return !!a;
}
//# sourceMappingURL=elements-game-card-steamside.js.map