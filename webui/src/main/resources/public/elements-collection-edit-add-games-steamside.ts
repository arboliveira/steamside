import {Customary, CustomaryElement} from "#customary";
import {GameCardDeckElement} from "#steamside/elements-game-card-deck-steamside.js";
import {TagAGameElement} from "#steamside/elements-tag-a-game-steamside.js";
import {CollectionEditAddGamesCommandBoxElement} from "#steamside/elements-collection-edit-add-games-command-box-steamside.js";
import {fetchSearchData} from "#steamside/data-search.js";

import {CustomaryDeclaration} from "#customary";
import {Game} from "#steamside/data-game";

export class CollectionEditAddGamesElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<CollectionEditAddGamesElement> =
		{
			name: 'elements-collection-edit-add-games-steamside',
			config: {
				define: {
					fontLocations: [
						"https://fonts.googleapis.com/css?family=Arvo:regular,bold",
						'https://fonts.googleapis.com/css?family=Karla:regular,bold'
					],
				},
				state: [
					'collectionEditSearchResults', 'firstSearchResult'
				],
			},
			values: {
			},
			hooks: {
 				requires: [
					 GameCardDeckElement, CollectionEditAddGamesCommandBoxElement
			    ],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				changes: {
					 'collectionEditSearchResults': (el, a) =>
						 el.#on_collectionEditSearchResults_change(a),
				},
				events: [
					{
						type: 'CommandBoxElement:CommandPlease',
						listener: (el, event) =>
							el.#on_CommandBoxElement_CommandPlease(<CustomEvent>event),
					},
					{
						type: 'CommandBoxElement:CommandAlternatePlease',
						listener: (el, event) =>
							el.#on_CommandBoxElement_CommandAlternatePlease(<CustomEvent>event),
					},
					{
						type: 'GameCardElement:ActionButtonClick',
						selector: '.segment',
						listener: (el, e) => el.#on_GameCardElement_ActionButtonClick(<CustomEvent>e),
					},
				],
			}
		}
	declare firstSearchResult: Game;
	declare collectionEditSearchResults: Game[];

	#on_collectionEditSearchResults_change(a: Game[]) {
		this.firstSearchResult = a[0];
	}
		
	async #on_CommandBoxElement_CommandPlease(event: CustomEvent) {
		const inputValue = event.detail;
		if (inputValue) {
			await this.#search(inputValue);
		}
		else
		{
			// FIXME this.#displayTagless();
		}
	}

	async #on_CommandBoxElement_CommandAlternatePlease(event: CustomEvent) {
		const inputValue = event.detail;
		if (inputValue) {
			// FIXME this.#searchHubs(inputValue);
		}
		else
		{
			// FIXME await this.#displayRecentlyPlayed();
		}
	}

	async #search(query: string) {
		this.collectionEditSearchResults = await fetchSearchData(query);
	}

	async #on_GameCardElement_ActionButtonClick(event: CustomEvent) {
		const {action_button, game, targetInteractedWith} = event.detail;
		if (action_button === 'add') {
			await this.#askAddGamePlease(game, targetInteractedWith);
		}
		if (action_button === 'tag') {
			this.#openTagPickerWithinSegment(game, <Element>event.currentTarget);
		}
	}

	async #askAddGamePlease(game: Game, targetInteractedWith: Element) {
		this.dispatchEvent(
			new CustomEvent(
				'CollectionEditAddGamesElement:AddGamePlease',
				{
					detail: {
						game,
						targetInteractedWith
					},
					composed: true,
					bubbles: true,
				}
			)
		);
	}

	#openTagPickerWithinSegment(game: Game, segment: Element) {
		// FIXME too messy, open new window instead
		new TagAGameElement()
			.showTagAGame({game, container: segment});
	}
}
Customary.declare(CollectionEditAddGamesElement);
