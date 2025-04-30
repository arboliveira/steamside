import {Customary, CustomaryElement} from "#customary";
import {GameCardDeckElement} from "#steamside/elements-game-card-deck-steamside.js";
import {fetchFavoritesData} from "#steamside/data-favorites.js";
import {Backend} from "#steamside/data-backend.js";
import {pop_toast} from "#steamside/vfx-toaster.js";
import {CollectionPickerElement} from "#steamside/elements-collection-picker-steamside.js";

import {CustomaryDeclaration} from "#customary";
import {Game} from "#steamside/data-game";
import {
	CollectionPickerElement_CollectionPicked_eventDetail,
	CollectionPickerElement_CollectionPicked_eventName
} from "#steamside/elements/collection-picker/CollectionPickerElement_CollectionPicked_Event.js";

export class WorldHomeFavoritesSegmentElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<WorldHomeFavoritesSegmentElement> =
		{
			name: 'elements-world-home-favorites-segment-steamside',
			config: {
				define: {
					fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular",
				},
				state: [
					'favorites',
					'collectionPicker_visible',
				],
				attributes: [
					'kids_mode',
				],
			},
			hooks: {
				requires: [GameCardDeckElement, CollectionPickerElement],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
				},
				events: [
					{
						type: CollectionPickerElement_CollectionPicked_eventName,
						listener: (el, e) =>
								el.#on_select_new_favorite(<CustomEvent>e),
					},
					{
						selector: '#side-link-favorite-switch',
						listener: (el, e) =>
								el.#switchClicked(e),
					},
				],
			}
		}

	declare collectionPicker_visible: boolean;
	declare favorites: Game[];

	#switchClicked(e: Event) {
		e.preventDefault();

		this.collectionPicker_visible = true;

		// FIXME scroll to viewCollectionPick
		//$('html, body').scrollTop(viewCollectionPick.$el.offset().top);
	}

	async #on_select_new_favorite(event: CustomEvent<CollectionPickerElement_CollectionPicked_eventDetail>) {
		const name = event.detail.tagName;
		const target = event.currentTarget as Element;
		
		// FIXME display 'setting favorites...'
		try
		{
			const aUrl =
				"api/favorites/set/" + encodeURIComponent(name);
			await this.backend.fetchBackend({url: aUrl});
			await this.#fetch_favorites_collection();
		} catch (error) {
			pop_toast({
				error: error as Error,
				offline_imagine_spot: `${name} has been set as your favorites`,
				target
			});
		}
		this.collectionPicker_visible = false;
	}

	async #fetch_favorites_collection()
	{
		this.favorites = await fetchFavoritesData();
	}

	async #on_connected()
	{
		await this.#fetch_favorites_collection();
		await this.backend.fetchSessionDataAndDisableBackendIfOffline();
	}

	backend = new Backend();
}
Customary.declare(WorldHomeFavoritesSegmentElement);
