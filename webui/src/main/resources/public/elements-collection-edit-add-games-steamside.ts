import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {GameCardDeckElement} from "#steamside/elements-game-card-deck-steamside.js";
import {TagAGameElement} from "#steamside/elements-tag-a-game-steamside.js";
import {CollectionEditAddGamesCommandBoxElement} from "#steamside/elements-collection-edit-add-games-command-box-steamside.js";
import {fetchSearchData} from "#steamside/data-search.js";

import {Game} from "#steamside/data-game.js";
import {TagDoneEvent} from "#steamside/requests/tag/TagDoneEvent.js";
import {toastOrNot} from "#steamside/vfx-toaster.js";
import {
	GameCardElement_ActionButtonClick_eventDetail,
	GameCardElement_ActionButtonClick_eventName
} from "#steamside/elements/game-card/GameCardElement_ActionButtonClick_Event.js";

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
						type: GameCardElement_ActionButtonClick_eventName,
						selector: '.segment',
						listener: (el, e) => el.#on_GameCardElement_ActionButtonClick(<CustomEvent>e),
					},
					{
						type: TagDoneEvent.eventName,
						listener: (el, e) => el.#on_TagDoneEvent(<TagDoneEvent>e),
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

	async #on_GameCardElement_ActionButtonClick(
		event: CustomEvent<GameCardElement_ActionButtonClick_eventDetail>
	) {
		switch (event.detail.action_button) {
			case 'tag':
				// FIXME too messy, open new window instead
				new TagAGameElement().showTagAGame({game: event.detail.game, container: <Element>event.currentTarget});
				break;
		}
	}

	async #on_TagDoneEvent(tagDoneEvent: TagDoneEvent) {
		toastOrNot({
			content: tagDoneEvent.detail.toast_content,
			target: this.renderRoot.lastElementChild!,
		});
	}
}
Customary.declare(CollectionEditAddGamesElement);
