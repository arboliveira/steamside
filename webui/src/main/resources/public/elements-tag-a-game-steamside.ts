import {Customary, CustomaryElement} from "#customary";
import {CustomaryDeclaration} from "#customary";

import {GameCardElement} from "#steamside/elements-game-card-steamside.js";
import {TagStickersElement} from "#steamside/elements-tag-stickers-steamside.js";
import {CommandBoxElement} from "#steamside/elements-command-box-steamside.js";
import {fetchTagSuggestionsData} from "#steamside/data-tag-suggestions.js";
import {toastOrNot} from "#steamside/vfx-toaster.js";

import {Game} from "#steamside/data-game.js";
import {Tag} from "#steamside/data-tag.js";
import {
	TagStickerElement_TagClicked_eventDetail,
	TagStickerElement_TagClicked_eventName
} from "#steamside/elements/tag-sticker/TagStickerElement_TagClicked_Event.js";
import {AppTagDoneEvent} from "#steamside/requests/app/AppTagDoneEvent.js";
import {AppTagPleaseEvent} from "#steamside/requests/app/AppTagPleaseEvent.js";

export class TagAGameElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<TagAGameElement> =
		{
			name: 'elements-tag-a-game-steamside',
			config: {
				define: {
					fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular",
				},
				attributes: [
				],
				state: [
					'game',
					'command_box_entered', 'commandHintsSubject',
					'suggestions',
				],
			},
			values: {
			},
			hooks: {
 				requires: [
					GameCardElement, 
				    TagStickersElement, 
				    CommandBoxElement
			    ],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					willUpdate: el => el.#on_willUpdate(),
					connected: el => el.#on_connected(),
				},
				changes: {
				},
				events: [
					{
						type: 'CommandBoxElement:InputValueChanged',
						listener: (el, event) =>
							el.#on_changed_input_text_command_box_value(<CustomEvent>event),
					},
					{
						type: 'CommandBoxElement:CommandPlease',
						listener: (el, event) =>
							el.#on_CommandBoxElement_CommandPlease((<CustomEvent>event).detail, event),
					},
					{
						type: TagStickerElement_TagClicked_eventName,
						listener: (el, e) => el.#on_TagStickerElement_TagClicked(<CustomEvent>e),
					},
					{
						type: AppTagDoneEvent.eventName,
						listener: (el, e) => el.#on_AppTagDoneEvent(<AppTagDoneEvent>e),
					},
				],
			}
		}
	declare game: Game;
	declare command_box_entered: string;
	declare commandHintsSubject: string;
	declare suggestions: Tag[];
		
	showTagAGame({game, container}:{game: Game, container: Element}) {
		this.game = game;
		container.appendChild(this);
	}	
		
	#on_changed_input_text_command_box_value(event: CustomEvent) {
		this.command_box_entered = event.detail;
	}
	
	async #on_CommandBoxElement_CommandPlease(
			detail: string | undefined, event: Event) {
		const nameForCollection = this.#nameForCollection(detail);
		await this.#addTagToGame(nameForCollection, <Element>event.target);
	}

	async #addTagToGame(collection: string, originator: Element) {
		this.dispatchEvent(new AppTagPleaseEvent({
			game: this.game,
			collection,
			originator,
		}));
	}

	async #on_TagStickerElement_TagClicked(event: CustomEvent<TagStickerElement_TagClicked_eventDetail>) {
		event.stopPropagation();

		await this.#addTagToGame(event.detail.tagName, <Element>event.target);
	}

	async #on_AppTagDoneEvent(appTagDoneEvent: AppTagDoneEvent) {
		toastOrNot({
			content: appTagDoneEvent.detail.toast_content,
			target: this.renderRoot.lastElementChild!,
		});

		const commandBox: CommandBoxElement = this.renderRoot.querySelector('elements-command-box-steamside')!;
		commandBox.set_input_text_command_box_value('');
	}

	#on_willUpdate() {
		this.commandHintsSubject = this.#nameForCollection(this.command_box_entered);
	}

	#nameForCollection(input?: string) {
		return input?.trim() || "Favorites";
	}

	async #on_connected()
	{
		this.suggestions = await fetchTagSuggestionsData();
	}
}
Customary.declare(TagAGameElement);
