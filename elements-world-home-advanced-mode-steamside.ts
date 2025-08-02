import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {WorldHomeFavoritesSegmentElement} from "#steamside/elements-world-home-favorites-segment-steamside.js";
import {WorldHomeSearchSegmentElement} from "#steamside/elements-world-home-search-segment-steamside.js";
import {WorldHomeSuggestedSegmentsElement} from "#steamside/elements-world-home-suggested-segments-steamside.js";
import {fetchOwnedCountData} from "#steamside/data-owned-count-tag.js";

import {Tag} from "#steamside/data-tag.js";
import {ContinuePlay} from "#steamside/application/modules/continue/ContinuePlay.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {AreaContinueElement} from "#steamside/elements/area-continue/area-continue-steamside.js";
import {ContinueName} from "#steamside/application/modules/continue/ContinueName.js";

export class WorldHomeAdvancedModeElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<WorldHomeAdvancedModeElement> =
		{
			name: 'elements-world-home-advanced-mode-steamside',
			config: {
				construct: {shadowRootDont: true},
				define: {
					fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
				},
				state: [
					'last_played_name_1',
					'last_played_name_2',
					'numberOfGamesOwned',
				],
			},
			hooks: {
				requires: [
					AreaContinueElement,
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
						type: ContinuePlay.eventTypePlease,
						listener: (el, event) =>
							el.#on_asked_continue_game(event as CustomEvent),
					},
					{
						type: ContinueName.eventTypeChanged,
						listener: (el, event) =>
							el.#on_changed_continue_name(event as CustomEvent),
					},
				],
			}
		}

	declare last_played_name_1: string;
	declare last_played_name_2: string;
	declare numberOfGamesOwned: number | undefined;

	#on_changed_continue_name(event: CustomEvent<ContinueName.EventDetail>) {
		if (event.detail.lastPlayed === 1) {
			this.last_played_name_1 = event.detail.name ?? '';
		}
		if (event.detail.lastPlayed === 2) {
			this.last_played_name_2 = event.detail.name ?? '';
		}
	}

	#on_asked_continue_game(event: CustomEvent<ContinuePlay.EventDetail>) {
		const continueArea = this.#getContinueArea();
		Skyward.stage(event, continueArea, event);
	}

	#getContinueArea(): Element {
		return this.renderRoot
			.querySelector("area-continue-steamside")!;
	}

	async #on_connected()
	{
		const classList = document.body.classList;
		classList.add("steamside-body-background");

		const ownedCount: Tag = await fetchOwnedCountData();
		this.numberOfGamesOwned = ownedCount.count;
	}
}
Customary.declare(WorldHomeAdvancedModeElement);
