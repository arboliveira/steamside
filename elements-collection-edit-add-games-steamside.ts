import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {GameCardDeckElement} from "#steamside/elements-game-card-deck-steamside.js";
import {TagAGameElement} from "#steamside/elements-tag-a-game-steamside.js";
import {CollectionEditAddGamesCommandBoxElement} from "#steamside/elements-collection-edit-add-games-command-box-steamside.js";
import {fetchSearchData} from "#steamside/data-search.js";

import {Game} from "#steamside/data-game.js";
import {CommandPlease} from "#steamside/elements/command-box/CommandPlease.js";
import {CommandAlternatePlease} from "#steamside/elements/command-box/CommandAlternatePlease.js";
import {CardTag} from "#steamside/elements/game-card/CardTag.js";
import {CardView} from "#steamside/elements-game-card-steamside.js";
import {translateGameToCardView} from "#steamside/application/game/Game.js";

export class CollectionEditAddGamesElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<CollectionEditAddGamesElement> =
		{
			name: 'elements-collection-edit-add-games-steamside',
			config: {
				construct: {shadowRootDont: true},
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
						type: CommandPlease.eventType,
						listener: (el, e) =>
							el.#on_CommandBoxElement_CommandPlease(<CustomEvent>e),
					},
					{
						type: CommandAlternatePlease.eventType,
						listener: (el, e) =>
							el.#on_CommandBoxElement_CommandAlternatePlease(<CustomEvent>e),
					},
					{
						type: CardTag.eventTypePlease,
						listener: (_el, event) =>
							// FIXME too messy, open new window instead
							new TagAGameElement().showTagAGame({
								card: (<CustomEvent<CardTag.PleaseDetail>>event).detail.card,
								container: <Element>event.currentTarget,
							}),
					},
				],
			}
		}
	declare firstSearchResult: CardView;
	declare collectionEditSearchResults: CardView[];

	#on_collectionEditSearchResults_change(a: CardView[]) {
		this.firstSearchResult = a[0];
	}
		
	async #on_CommandBoxElement_CommandPlease(event: CustomEvent<CommandPlease.EventDetail>) {
		const inputValue = event.detail.input_text_command_box_value;
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
		const searchResults: Game[] = await fetchSearchData(query);
		this.collectionEditSearchResults = searchResults.map(game => translateGameToCardView(game));
	}
}
Customary.declare(CollectionEditAddGamesElement);
