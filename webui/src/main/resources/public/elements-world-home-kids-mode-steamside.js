import {Customary, CustomaryElement} from "#customary";
import {WorldHomeFavoritesSegmentElement} from "#steamside/elements-world-home-favorites-segment-steamside.js";

//import {CustomaryDeclaration} from "#customary";

export class WorldHomeKidsModeElement extends CustomaryElement {
	/**
	 * @type {CustomaryDeclaration<WorldHomeKidsModeElement>}
	 */
	static customary =
		{
			name: 'elements-world-home-kids-mode-steamside',
			config: {
				define: {
					fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular",
				},
			},
			hooks: {
				requires: [WorldHomeFavoritesSegmentElement],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
				},
			}
		}

	async #on_connected()
	{
		const classList = document.body.classList;
		classList.remove("steamside-body-background");
		classList.add("steamside-kids-body-background");
	}
}
Customary.declare(WorldHomeKidsModeElement);
