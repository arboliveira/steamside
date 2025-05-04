import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {WorldHomeFavoritesSegmentElement} from "#steamside/elements-world-home-favorites-segment-steamside.js";
import {GameCardDeckElement} from "#steamside/elements-game-card-deck-steamside.js";
import {WorldHomeSearchSegmentElement} from "#steamside/elements-world-home-search-segment-steamside.js";
import {WorldHomeSuggestedSegmentsElement} from "#steamside/elements-world-home-suggested-segments-steamside.js";
import {TagAGameElement} from "#steamside/elements-tag-a-game-steamside.js";
import {fetchContinuesData} from "#steamside/data-continues.js";
import {fetchOwnedCountData} from "#steamside/data-owned-count-tag.js";

import {Game} from "#steamside/data-game.js";
import {Tag} from "#steamside/data-tag.js";
import {
	GameCardElement_ActionButtonClick_eventDetail,
	GameCardElement_ActionButtonClick_eventName
} from "#steamside/elements/game-card/GameCardElement_ActionButtonClick_Event.js";

export class WorldHomeAdvancedModeElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<WorldHomeAdvancedModeElement> =
		{
			name: 'elements-world-home-advanced-mode-steamside',
			config: {
				define: {
					fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
				},
				state: [
					'continues', 'numberOfGamesOwned',
				],
				attributes: [
					'last_played_name_1',
					'last_played_name_2',
				],
			},
			hooks: {
				requires: [
					GameCardDeckElement,
					WorldHomeSearchSegmentElement,
					WorldHomeFavoritesSegmentElement,
					WorldHomeSuggestedSegmentsElement,
				],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
				},
				events: [
					{
						type: 'WorldHomeSearchSegmentElement:asks-to:continue-game',
						listener: (el, event) =>
							el.#on_asked_continue_game(event as CustomEvent),
					},
					{
						selector: '.segment',
						type: GameCardElement_ActionButtonClick_eventName,
						listener: (el, event) =>
							el.#on_game_card_action_button_click(event as CustomEvent),
					},
				],
			}
		}
	declare continues: Game[];
	declare numberOfGamesOwned: number | undefined;
	declare last_played_name_1: string;
	declare last_played_name_2: string;

	async #on_game_card_action_button_click(
		event: CustomEvent<GameCardElement_ActionButtonClick_eventDetail>
	) {
		switch (event.detail.action_button) {
			case 'tag':
				new TagAGameElement().showTagAGame({
					game: event.detail.game,
					container: <Element>event.currentTarget
				});
				break;
		}
	}

	#on_asked_continue_game(event: CustomEvent) {
		const {lastPlayed} = event.detail;
		this.#getContinuesDeck().getCardAtIndex(lastPlayed - 1)?.playGame();
	}

	#getContinuesDeck(): GameCardDeckElement {
		return this.renderRoot
			.querySelector("#continues-segment")!
			.querySelector('elements-game-card-deck-steamside')!;
	}

	async #on_connected()
	{
		const classList = document.body.classList;
		classList.add("steamside-body-background");

		this.continues = await fetchContinuesData();
		const ownedCount: Tag = await fetchOwnedCountData();
		this.numberOfGamesOwned = ownedCount.count;
		const last_played_1 = this.continues[0];
		const last_played_2 = this.continues[1];
		this.last_played_name_1 = last_played_1 ? last_played_1.name : '';
		this.last_played_name_2 = last_played_2 ? last_played_2.name : '';
	}
}
Customary.declare(WorldHomeAdvancedModeElement);
