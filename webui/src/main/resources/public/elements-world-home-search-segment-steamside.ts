import {Customary, CustomaryElement} from "#customary";
import {CustomaryDeclaration} from "#customary";

import {WorldHomeSearchCommandBoxElement} from "#steamside/elements-world-home-search-command-box-steamside.js";
import {GameCardDeckElement} from "#steamside/elements-game-card-deck-steamside.js";
import {fetchSearchData} from "#steamside/data-search.js";

import {Game} from "#steamside/data-game.js";
import {CommandPlease} from "#steamside/elements/command-box/CommandPlease.js";
import {CommandAlternatePlease} from "#steamside/elements/command-box/CommandAlternatePlease.js";
import {ConfirmPlease} from "#steamside/elements/command-box/ConfirmPlease.js";
import {PlayError} from "#steamside/application/play/PlayError.js";
import {toastError} from "#steamside/vfx-toaster.js";

export class WorldHomeSearchSegmentElement extends CustomaryElement 
{
	static customary: CustomaryDeclaration<WorldHomeSearchSegmentElement> =
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
						type: ConfirmPlease.eventType,
						listener: (el, e) =>
							el.#on_CommandBoxElement_CommandConfirmPlease(<CustomEvent>e),
					},
					{
						type: PlayError.eventType,
						listener: (el, e) =>
							toastError({
								content: (<CustomEvent<PlayError.EventDetail>>e).detail.error,
								target: el.renderRoot.lastElementChild!,
							}),
					},
				],
			}
		}

	declare searchResults: Game[];

	async #on_CommandBoxElement_CommandPlease(event: CustomEvent<CommandPlease.EventDetail>) {
		const command_box_entered = event.detail.input_text_command_box_value;
		if (command_box_entered) {
			await this.#search(command_box_entered);
		}
		else
		{
			this.#continue_game_1();
		}
	}

	#on_CommandBoxElement_CommandAlternatePlease(event: CustomEvent<CommandAlternatePlease.EventDetail>) {
		const command_box_entered = event.detail.input_text_command_box_value;
		if (command_box_entered) {
			this.#search_and_play(command_box_entered);
		}
		else
		{
			this.#continue_game_2();
		}
	}

	#on_CommandBoxElement_CommandConfirmPlease(event: CustomEvent<ConfirmPlease.EventDetail>) {
		const command_box_entered = event.detail.input_text_command_box_value;
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

	async #search(command_box_entered: string) {
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

	#search_and_play(command_box_entered: string) {
		// TODO search then play
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
}
Customary.declare(WorldHomeSearchSegmentElement);
