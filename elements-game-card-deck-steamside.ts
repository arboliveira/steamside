import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {CardView, GameCardElement} from "#steamside/elements-game-card-steamside.js";
import {ContinuePlay} from "#steamside/application/modules/continue/ContinuePlay.js";
import {CardDefaultActionPlease} from "#steamside/elements/game-card/CardDefaultActionPlease.js";

export class GameCardDeckElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<GameCardDeckElement> =
		{
			name: 'elements-game-card-deck-steamside',
			config: {
				construct: {shadowRootDont: true},
				attributes: [
					'collapsed_size',
					'include_action_button_remove',
					'include_action_button_add',
					'kids_mode',
				],
				state: [
					'cards',
					'deck_size',
					'tailgaters_visible', 'tailgaters_expanded',
					'more_icon_visible', 'more_icon_symbol',
				],
			},
			values: {
				'tailgaters_visible': false,
			},
			hooks: {
				externalLoader: {
					import_meta: import.meta,
				},
				requires: [GameCardElement],
				changes: {
					'cards': (el, a) =>
						el.#on_changed_cards(a),
				},
				lifecycle: {
					connected: el => el.#on_connected(),
					willUpdate: el => el.#on_willUpdate(),
				},
				events: [
					{
						selector: '.button-expand-card-deck',
						listener: (el, e) => el.#on_more_clicked(e),
					},
					{
						type: ContinuePlay.eventTypePlease,
						listener: (el, event) =>
							el.#on_asked_continue_game(event as CustomEvent),
					},
				],
			}
		}
	declare cards: CardView[];
	declare collapsed_class: string;
	declare kids_mode: string;
	declare tailgaters_visible: boolean;
	declare more_icon_visible: boolean;
	declare more_icon_symbol: string;
	declare tailgaters_expanded: boolean;
	declare deck_size: number;

	#on_willUpdate() {
		const expanded_permanently = this.kids_mode === 'true';

		this.tailgaters_visible =
			expanded_permanently
			|| this.tailgaters_expanded;

		const expanded = this.tailgaters_visible;

		this.collapsed_class = expanded ? 'expanded' : 'collapsed';

		// TODO Unit test: card deck without cards provided (i.e. undefined)
		this.more_icon_visible =
			!expanded_permanently
			&& this.cards?.length > this.collapsedSize;

		this.more_icon_symbol = expanded ? '🡩' : '🡫';
	}

	#on_asked_continue_game(event: CustomEvent<ContinuePlay.EventDetail>) {
		event.stopPropagation();
		const {lastPlayed} = event.detail;
		const gameCardElement = this.getCardAtIndex(lastPlayed - 1);
		gameCardElement.dispatchEvent(new CustomEvent(CardDefaultActionPlease.eventType));
	}

	private getCardAtIndex(index: number): GameCardElement {
		return this.renderRoot.querySelectorAll(
		'elements-game-card-steamside')[index] as GameCardElement;
	}

	#on_changed_cards(a: CardView[]) {
		this.deck_size = a.length;
	}

	#on_more_clicked(e: Event) {
		e.preventDefault();
		this.tailgaters_expanded = !this.tailgaters_expanded;
	}

	async #on_connected()
	{
		const styleSheet = document.createElement('style');
		this.renderRoot.appendChild(styleSheet);
		const selector = `.deck-unified.collapsed > :nth-child(n+${this.collapsedSize + 1})`;
		const rule = '{ display: none; }';
		styleSheet.sheet?.insertRule(`${selector} ${rule}`, styleSheet.sheet?.cssRules.length);
	}

	private readonly collapsedSize = 3;
}
Customary.declare(GameCardDeckElement);

