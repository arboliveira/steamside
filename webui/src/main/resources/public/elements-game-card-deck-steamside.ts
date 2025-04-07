import {Customary, CustomaryElement} from "#customary";
import {GameCardElement} from "#steamside/elements-game-card-steamside.js";

import {CustomaryDeclaration} from "#customary";
import {Game} from "#steamside/data-game";

export class GameCardDeckElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<GameCardDeckElement> =
		{
			name: 'elements-game-card-deck-steamside',
			config: {
				attributes: [
					'include_action_button_remove',
					'include_action_button_add',
					'kids_mode',
				],
				state: [
					'collection', 'headliners', 'tailgaters',
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
					'collection': (el, a) =>
						el.#on_changed_collection(a),
				},
				lifecycle: {
					willUpdate: el => el.#on_willUpdate(), 
				},
				events: {
					'.more-icon': (el, e) => el.#on_more_clicked(e),
				},
			}
		}
	declare kids_mode: string;
	declare tailgaters_visible: boolean;
	declare more_icon_visible: boolean;
	declare more_icon_symbol: string;
	declare tailgaters_expanded: boolean;
	declare deck_size: number;
	declare headliners: Game[];
	declare tailgaters: Game[];

	#on_willUpdate() {
		const expanded_permanently = this.kids_mode === 'true';
		
		// FIXME slide down and up on expand and contract
		this.tailgaters_visible =
			expanded_permanently
			|| this.tailgaters_expanded;
		
		this.more_icon_visible =
			!expanded_permanently
			&& !!this.tailgaters?.length;
		this.more_icon_symbol = this.tailgaters_expanded ? '🡩' : '🡫';
	}
	
	getCardAtIndex(index: number): GameCardElement {
		return this.renderRoot.querySelectorAll(
		'elements-game-card-steamside')[index] as GameCardElement;
	}

	#on_changed_collection(a: Game[]) {
		this.deck_size = a.length;
		this.headliners = a.slice(0, CARDS_PER_ROW);
		this.tailgaters = a.slice(CARDS_PER_ROW);
	}

	#on_more_clicked(e: Event) {
		e.preventDefault();
		this.tailgaters_expanded = !this.tailgaters_expanded;
	}
}
Customary.declare(GameCardDeckElement);

const CARDS_PER_ROW = 3;