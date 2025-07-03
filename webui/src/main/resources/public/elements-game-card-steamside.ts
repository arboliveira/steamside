import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {toastError, toastOrNot} from "#steamside/vfx-toaster.js";

import {Game} from "#steamside/data-game.js";
import {Tag} from "#steamside/data-tag.js";

import {PlayPlease} from "#steamside/application/play/PlayPleaseEvent.js";
import {GameActionButtonClick} from "#steamside/elements/game-card/GameActionButtonClick.js";
import {NowPlaying} from "#steamside/application/play/NowPlayingEvent.js";
import {GameOver} from "#steamside/application/play/GameOverEvent.js";
import {CardDefaultActionPlease} from "#steamside/elements/game-card/CardDefaultActionPlease.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {GameCardTagPlease} from "#steamside/elements/game-card/GameCardTagPlease.js";
import {imagineDryRun} from "#steamside/data-offline-mode.js";
import {Fun} from "#steamside/application/Fun.js";
import {EventBusSubscribeOnConnected, EventBusUnsubscribeOnDisconnected} from "#steamside/event-bus/EventBusSubscribe.js";
import {SomethingWentWrong} from "#steamside/application/SomethingWentWrong.js";

export class GameCardElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<GameCardElement> =
		{
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
					'game': (el, a) =>
						el.#on_changed_game(a),
					'now_playing': (el, a) =>
						el.#on_changed_now_playing(a),
					'game_tile_inner_loading_overlay_visible': (el, a) =>
						el.#on_changed_game_tile_inner_loading_overlay_visible(a),
					'include_action_button_remove': (el, a) =>
						el.action_button_remove_visible = a === 'true',
					'include_action_button_add': (el, a) =>
						el.action_button_add_visible = a === 'true',
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
						listener: (el, e) => {
							e.preventDefault();
							el.#performDefaultAction();
						},
					},
					{
						type: 'click',
						selector: '.action-button-play',
						listener: (el, e) => {
							e.preventDefault();
							el.#playPlease();
						},
					},
					{
						type: 'click',
						selector: '.action-button-tag',
						listener: (el, e) =>
							Skyward.stage<GameCardTagPlease.EventDetail>(e, el,
								{type: GameCardTagPlease.eventType, detail: {game: el.game}}),
					},
					{
						type: 'click',
						selector: '.action-button-remove',
						listener: (el, e) =>
							Skyward.stage<GameActionButtonClick.EventDetail>(e, el,
								{type: GameActionButtonClick.eventType, detail: el.intention('remove')}),
					},
					{
						type: 'click',
						selector: '.action-button-add',
						listener: (el, e) =>
							Skyward.stage<GameActionButtonClick.EventDetail>(e, el,
								{type: GameActionButtonClick.eventType, detail: el.intention('add')}),
					},
					{
						type: CardDefaultActionPlease.eventType,
						listener: el => el.#performDefaultAction(),
					},
					{
						type: SomethingWentWrong.eventType,
						listener: (el, event) => el.#on_SomethingWentWrong(<CustomEvent>event),
					},
				],
				lifecycle: {
					connected: el => el.#on_connected(),
					disconnected: el => el.#on_disconnected(),
					willUpdate: el => el.#on_willUpdate(),
				},
			},
		};

	declare action_button_add_visible: boolean;
	declare action_button_remove_visible: boolean;
	declare game: Game;
	declare game_tag_views: TagView[];
	declare game_tile_inner_loading_overlay_visible: boolean;
	declare game_tile_play_visible: boolean;
	declare instruments_panel_visible: boolean;
	declare kids_mode: string;

	#on_willUpdate() {
		this.instruments_panel_visible = this.kids_mode !== 'true';
	}

	private intention(action_button: string): GameActionButtonClick.EventDetail {
		return {action_button, game: this.game};
	}

	#on_changed_game(a: Game) {
		this.#on_changed_game_tags(a.tags);
		this.#on_changed_game_unavailable(a.unavailable);
	}
	
	#on_changed_game_tags(game_tags: Tag[]) {
		if (!game_tags) return;
		this.game_tag_views = game_tags.map(
			tag =>
				({
					tag_name: tag.name,
					tag_url: `./InventoryWorld.html?name=${tag.name}`,
				})
		);
	}

	#on_changed_game_unavailable(aBoolean: boolean)
	{
		const available = !aBoolean;
		this.game_tile_play_visible = available;
		if (!available) this.#get_game_tile().classList.add('game-unavailable');
	}
	
	#get_game_tile() {
		return this.renderRoot.querySelector('.game-tile')!;
	}

	#mouseenter_hot_zone(e: Event) {
		e.preventDefault();
		const buttonOfDefaultAction = this.#buttonOfDefaultAction();
		if (buttonOfDefaultAction == null) return;
		buttonOfDefaultAction.classList.add('what-will-happen');
	}

	#mouseleave_hot_zone(e: Event) {
		e.preventDefault();
		const buttonOfDefaultAction = this.#buttonOfDefaultAction();
		if (buttonOfDefaultAction == null) return;
		buttonOfDefaultAction.classList.remove('what-will-happen');
	}

	#buttonOfDefaultAction(): HTMLElement {
		// return the first of them all
		return this.renderRoot.querySelector('.game-tile-command')!;
	}

	#performDefaultAction()
	{
		const w = this.#buttonOfDefaultAction();
		if (w)
		{
			w.click();
		}
		else {
			// without action buttons (like in kids mode) the only way to win is... to play
			this.#playPlease();
		}
	}

	#on_NowPlayingEvent(e: CustomEvent<NowPlaying.EventDetail>) {
		if (e.detail.fun.id !== this.game.appid) return;

		this.setAttribute('now_playing', 'true');

		const {fun_endpoint, funName, dryRun} = e.detail;
		toastOrNot({
			content: imagineDryRun({imagine: `you're playing ${funName}`, url: fun_endpoint, dryRun}),
			target: this.renderRoot.lastElementChild!
		});
	}

	#playPlease() {
		// target must be the card because "now playing" overlay blocks events on action buttons
		Skyward.fly<PlayPlease.EventDetail>(this, {
			type: PlayPlease.eventType,
			detail: {
				fun: new Fun(this.game.appid),
				funName: this.game.name,
				fun_endpoint: this.game.link,
			}
		});
	}

	#on_SomethingWentWrong(event: CustomEvent<SomethingWentWrong.EventDetail>)
	{
		toastError({content: event.detail.error, target: this.renderRoot.lastElementChild!});
	}

	#on_GameOverEvent(e: CustomEvent<GameOver.EventDetail>) {
		if (e.detail.fun.id === this.game.appid) {
			this.setAttribute('now_playing', 'false');
		}
	}

	#on_changed_now_playing(a: string | boolean | null) {
		this.game_tile_inner_loading_overlay_visible = isOn(a);
	}

	#on_changed_game_tile_inner_loading_overlay_visible(a: boolean) {
		const underlay: Element = this.renderRoot.querySelector('.game-tile-inner')!;
		if (a) {
			underlay.classList.add('game-tile-inner-blurred');
		}
		else {
			underlay.classList.remove('game-tile-inner-blurred');
		}
	}

	private readonly subscriptions: Array<{type: string, listener: EventListener}> = [
		{
			type: NowPlaying.eventType,
			listener: event => this.#on_NowPlayingEvent(<CustomEvent>event),
		},
		{
			type: GameOver.eventType,
			listener: event => this.#on_GameOverEvent(<CustomEvent>event),
		},
	];

	#on_connected()
	{
		EventBusSubscribeOnConnected.subscribe(this, this.subscriptions);
	}

	#on_disconnected()
	{
		EventBusUnsubscribeOnDisconnected.unsubscribe(this, this.subscriptions)
	}
}
Customary.declare(GameCardElement);

type TagView = {
	tag_name: string,
	tag_url: string,
}

function isOn(a: string | boolean | null): boolean {
	if (a === 'false') return false;
	return !!a;
}