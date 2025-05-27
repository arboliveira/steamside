import {Customary, CustomaryElement} from "#customary";
import {Backend} from "#steamside/data-backend.js";
import {pop_toast} from "#steamside/vfx-toaster.js";
import {
	CollectionNewEmptyCommandBoxElement
} from "#steamside/elements-collection-new-empty-command-box-steamside.js";

import {CustomaryDeclaration} from "#customary";
import {CommandPlease} from "#steamside/elements/command-box/CommandPlease.js";
import {CommandAlternatePlease} from "#steamside/elements/command-box/CommandAlternatePlease.js";

export class WorldCollectionNewElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<WorldCollectionNewElement> =
		{
			name: 'elements-world-collection-new-steamside',
			config: {
				define: {
					fontLocations: [
						"https://fonts.googleapis.com/css?family=Arvo:regular,bold",
						'https://fonts.googleapis.com/css?family=Karla:regular',
						'https://fonts.googleapis.com/css?family=Jura:regular,bold'
					],
				},
				state: [
					'__tagless_tag', 
					'__owned_tag',
				]
			},
			hooks: {
				requires: [CollectionNewEmptyCommandBoxElement],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
					firstUpdated: el => el.#on_firstUpdated(),
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
						type: 'click',
						selector: '.button-copy-em-all',
						listener: (el, event) => el.#buttonCopyEmAll(event),
					},
				],
			}
		}

	async #on_CommandBoxElement_CommandPlease(event: CustomEvent<CommandPlease.EventDetail>) {
		const inputValue = event.detail.input_text_command_box_value;
		await this.#createEmpty({name: inputValue, stay: false});
	}

	async #on_CommandBoxElement_CommandAlternatePlease(event: CustomEvent) {
		const inputValue = event.detail;
		await this.#createEmpty({name: inputValue, stay: true});
	}

	async #createEmpty({name, stay}:{name:string, stay: boolean}) {
		const aUrl = "api/collection/" + encodeURIComponent(name) + "/create";

		// FIXME display 'creating...'
		try
		{
			await this.backend.fetchBackend({url: aUrl});
		}
		catch (error) {
			pop_toast({
				error: error as Error,
				offline_imagine_spot: `${name} collection was created`,
				target: this.renderRoot.firstElementChild!,
			});
		}

				if (stay) {
					// TODO Command box input: clear and focus
				} else {
					// TODO Open in another window: `./InventoryWorld.html?name=${encoded}`
				}
	}

	async #buttonCopyEmAll(e: Event) {
		e.preventDefault();

		const aUrl = "api/collection/copy-all-steam-categories";

		// FIXME display 'creating...'
		try
		{
			await this.backend.fetchBackend({url: aUrl});
		}
		catch (error) {
			pop_toast({
				error: error as Error,
				offline_imagine_spot: `all Steam Categories were copied`,
				target: <Element>e.currentTarget,
			});
		}

		// TODO reveal 'finished' with button to navigate to "My Games"
	}

	#on_firstUpdated()
	{
	}

	async #on_connected()
	{
		await this.backend.fetchSessionDataAndDisableBackendIfOffline();
	}

	backend = new Backend();
}
Customary.declare(WorldCollectionNewElement);
