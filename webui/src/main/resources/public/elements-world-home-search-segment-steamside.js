import {Customary, CustomaryElement} from "#customary";
import {WorldHomeSearchCommandBoxElement} from "#steamside/elements-world-home-search-command-box-steamside.js";
import {GameCardDeckElement} from "#steamside/elements-game-card-deck-steamside.js";
import {fetchSearchData} from "#steamside/data-search.js";

//import {CustomaryDeclaration} from "#customary";

export class WorldHomeSearchSegmentElement extends CustomaryElement {
	/**
	 * @type {CustomaryDeclaration<WorldHomeSearchSegmentElement>}
	 */
	static customary =
		{
			name: 'elements-world-home-search-segment-steamside',
			config: {
				define: {
					fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
				},
				state: [
					'searchResults',
				],
				attributes: [
					'last_played_name_1',
					'last_played_name_2',
				],
			},
			hooks: {
				requires: [WorldHomeSearchCommandBoxElement, GameCardDeckElement],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
				},
				changes: {
				},
				events: [
					{
						type: 'CommandBoxElement:CommandPlease',
						listener: (el, event) =>
							el.#on_CommandBoxElement_CommandPlease(event),
					},
					{
						type: 'CommandBoxElement:CommandAlternatePlease',
						listener: (el, event) =>
							el.#on_CommandBoxElement_CommandAlternatePlease(event),
					},
					{
						type: 'CommandBoxElement:CommandConfirmPlease',
						listener: (el, event) =>
							el.#on_CommandBoxElement_CommandConfirmPlease(event),
					},
				],
			}
		}

	async #on_CommandBoxElement_CommandPlease(event) {
		const command_box_entered = event.detail;
		if (command_box_entered) {
			await this.#search(command_box_entered);
		}
		else
		{
			this.#continue_game_1();
		}
	}

	#on_CommandBoxElement_CommandAlternatePlease(event) {
		const command_box_entered = event.detail;
		if (command_box_entered) {
			this.#search_and_play(command_box_entered);
		}
		else
		{
			this.#continue_game_2();
		}
	}

	#on_CommandBoxElement_CommandConfirmPlease(event) {
		const command_box_entered = event.detail;
		if (command_box_entered) {
			// FIXME this.#explore_1(command_box_entered);
		}
		else
		{
			// FIXME this.#explore_2();
		}
	}

	async #on_connected()
	{
	}

	async #search(command_box_entered) {
		this.searchResults = await fetchSearchData(command_box_entered);
	}

	#continue_game_1() {
		this.dispatchEvent(
			new CustomEvent(
				'WorldHomeSearchSegmentElement:asks-to:continue-game',
				{
					detail: {lastPlayed: 1},
					composed: true,
				}
			)
		);
	}

	#search_and_play(command_box_entered) {
		
	}

	#continue_game_2() {
		this.dispatchEvent(
			new CustomEvent(
				'WorldHomeSearchSegmentElement:asks-to:continue-game',
				{
					detail: {lastPlayed: 2},
					composed: true,
				}
			)
		);
	}
	
	#getSearchResultsDeck() {
		return this.renderRoot.querySelector(
			'elements-game-card-deck-steamside');
	}
}
Customary.declare(WorldHomeSearchSegmentElement);
