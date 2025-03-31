import {Customary, CustomaryElement} from "#customary";
import {GameCardElement} from "#steamside/elements-game-card-steamside.js";

//import {CustomaryDeclaration} from "#customary";

export class GameCardDeckElement extends CustomaryElement {
	/**
	 * @type {CustomaryDeclaration<GameCardDeckElement>}
	 */
	static customary =
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

	#on_willUpdate() {
		const expanded_permanently = this.kids_mode === 'true';
		
		// FIXME slide down and up on expand and contract
		this.tailgaters_visible =
			expanded_permanently
			|| this.tailgaters_expanded;
		
		this.more_icon_visible =
			!expanded_permanently
			&& this.tailgaters?.length;
		this.more_icon_symbol = this.tailgaters_expanded ? '🡩' : '🡫';
	}
	
	getCardAtIndex(index) {
		return this.renderRoot.querySelectorAll(
		'elements-game-card-steamside')[index];
	}

	#on_changed_collection(a) {
		this.deck_size = a.length;
		this.headliners = a.slice(0, CARDS_PER_ROW);
		this.tailgaters = a.slice(CARDS_PER_ROW);
	}

	#on_more_clicked(e) {
		e.preventDefault();
		this.tailgaters_expanded = !this.tailgaters_expanded;
	}
}
Customary.declare(GameCardDeckElement);

const CARDS_PER_ROW = 3;