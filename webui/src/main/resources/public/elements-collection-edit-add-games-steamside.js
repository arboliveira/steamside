import {Customary, CustomaryElement} from "#customary";
import {GameCardDeckElement} from "#steamside/elements-game-card-deck-steamside.js";
import {TagAGameElement} from "#steamside/elements-tag-a-game-steamside.js";
import {CollectionEditAddGamesCommandBoxElement} from "#steamside/elements-collection-edit-add-games-command-box-steamside.js";
import {fetchSearchData} from "#steamside/data-search.js";

//import {CustomaryDeclaration} from "#customary";

export class CollectionEditAddGamesElement extends CustomaryElement {
	/**
	 * @type {CustomaryDeclaration<CollectionEditAddGamesElement>}
	 */
	static customary =
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
							el.#on_CommandBoxElement_CommandPlease(event),
					},
					{
						type: 'CommandBoxElement:CommandAlternatePlease',
						listener: (el, event) =>
							el.#on_CommandBoxElement_CommandAlternatePlease(event),
					},
					{
						type: 'GameCardElement:ActionButtonClick',
						selector: '.segment',
						listener: (el, e) => el.#on_GameCardElement_ActionButtonClick(e),
					},
				],
			}
		}

	#on_collectionEditSearchResults_change(a) {
		this.firstSearchResult = a[0];
	}
		
	async #on_CommandBoxElement_CommandPlease(event) {
		const inputValue = event.detail;
		if (inputValue) {
			await this.#search(inputValue);
		}
		else
		{
			// FIXME this.#displayTagless();
		}
	}

	async #on_CommandBoxElement_CommandAlternatePlease(event) {
		const inputValue = event.detail;
		if (inputValue) {
			// FIXME this.#searchHubs(inputValue);
		}
		else
		{
			// FIXME await this.#displayRecentlyPlayed();
		}
	}

	async #search(query) {
		this.collectionEditSearchResults = await fetchSearchData(query);
	}

	async #on_GameCardElement_ActionButtonClick(event) {
		const {action_button, game, targetInteractedWith} = event.detail;
		if (action_button === 'add') {
			await this.#askAddGamePlease(game, targetInteractedWith);
		}
		if (action_button === 'tag') {
			this.#openTagPickerWithinSegment(game, event.currentTarget);
		}
	}

	async #askAddGamePlease(game, targetInteractedWith) {
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

	#openTagPickerWithinSegment(game, segment) {
		// FIXME too messy, open new window instead
		new TagAGameElement()
			.showTagAGame({game, container: segment});
	}
}
Customary.declare(CollectionEditAddGamesElement);
