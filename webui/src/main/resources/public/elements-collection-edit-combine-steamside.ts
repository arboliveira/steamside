import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {CollectionPickerElement} from "#steamside/elements-collection-picker-steamside.js";
import {
	CollectionEditCombineCommandBoxElement
} from "#steamside/elements-collection-edit-combine-command-box-steamside.js";
import {Backend} from "#steamside/data-backend.js";
import {pop_toast, toastError, toastOrNot} from "#steamside/vfx-toaster.js";

import {Tag} from "#steamside/data-tag.js";
import {CombineAndDeleteSourcesPlease} from "#steamside/application/inventory/combine/CombineAndDeleteSourcesPlease.js";
import {CombineAndDeleteSourcesDone} from "#steamside/application/inventory/combine/CombineAndDeleteSourcesDone.js";
import {ConfirmPlease} from "#steamside/elements/command-box/ConfirmPlease.js";
import {CommandPlease} from "#steamside/elements/command-box/CommandPlease.js";
import {CollectionPicked} from "#steamside/elements/collection-picker/CollectionPicked.js";
import {CommandAlternatePlease} from "#steamside/elements/command-box/CommandAlternatePlease.js";
import {imagineDryRun} from "#steamside/data-offline-mode.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {SomethingWentWrong} from "#steamside/application/SomethingWentWrong.js";
import {EventBusSubscribeOnConnected, EventBusUnsubscribeOnDisconnected} from "#steamside/event-bus/EventBusSubscribe.js";
import {CombineAndDeleteSourcesDoing} from "#steamside/application/inventory/combine/CombineAndDeleteSourcesDoing.js";

export class CollectionEditCombineElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<CollectionEditCombineElement> =
		{
			name: 'elements-collection-edit-combine-steamside',
			config: {
				construct: {shadowRootDont: true},
				define: {
					fontLocations: [
						"https://fonts.googleapis.com/css?family=Arvo:regular,bold",
						'https://fonts.googleapis.com/css?family=Karla:regular,bold'
					],
				},
				state: [
					'__tag',
					'combine_command_box_visible',
					'__tag_combining_name',
				],
			},
			hooks: {
 				requires: [
				    CollectionPickerElement,
				    CollectionEditCombineCommandBoxElement,
			    ],
				externalLoader: {
					import_meta: import.meta,
				},
				changes: {
					//'__tag': (el, a) => el.#on_tag_change(a),
				},
				lifecycle: {
					connected: el => el.#on_connected(),
					disconnected: el => el.#on_disconnected(),
				},
				events: [
					{
						type: CollectionPicked.eventType,
						listener: (el, e) =>
							el.#on_CollectionPickerElement_CollectionPicked(<CustomEvent>e),
					},
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
							el.#on_CommandBoxElement_ConfirmPlease(<CustomEvent>e),
					},
					{
						type: CombineAndDeleteSourcesDone.eventType,
						listener: (el, e) =>
							el.#on_CombineAndDeleteSourcesDoneEvent(<CustomEvent>e),
					},
					{
						type: SomethingWentWrong.eventType,
						listener: (el, event) => el.#on_SomethingWentWrong(<CustomEvent>event),
					},
				],
			}
		}

	declare __tag_combining_name: string;
	declare combine_command_box_visible: boolean;
	declare __tag: Tag;

	#on_CollectionPickerElement_CollectionPicked(e: CustomEvent<CollectionPicked.EventDetail>) {
		this.__tag_combining_name = e.detail.tagName;
		this.combine_command_box_visible = true;
	}
		
	#on_CommandBoxElement_CommandPlease(_e: CustomEvent<CommandPlease.EventDetail>) {
		// on command, all variations delete one or both supplier collections
		this.#askForConfirmation();
	}

	async #on_CommandBoxElement_CommandAlternatePlease(event: CustomEvent) {
		// on command alternate, a fresh collection keeps both supplier collections
		const collection_editing_name = this.__tag.name;
		const collection_combining_name = this.__tag_combining_name;
		const inputValue = event.detail;
		const fresh =
			inputValue 
			&& inputValue !== collection_editing_name
			&& inputValue !== collection_combining_name;
		if (!fresh) {
			this.#askForConfirmation();
			return;
		}
		await this.#combineAndKeepSources({receiver: inputValue});
	}

	#on_CommandBoxElement_ConfirmPlease(event: CustomEvent<ConfirmPlease.EventDetail>) {
		// TODO read state: previous action was command or command alternate
		// TODO determine whether destination is the editing or the combining

		Skyward.stage<CombineAndDeleteSourcesPlease.EventDetail>(
			event, this, {type: CombineAndDeleteSourcesPlease.eventType,
				detail: {
					inventory_editing_alias: this.__tag.name,
					inventory_combining_alias: this.__tag_combining_name,
					inventory_destination: event.detail.input_text_command_box_value,
			}});
	}

	#on_CombineAndDeleteSourcesDoing(e: CustomEvent<CombineAndDeleteSourcesDoing.EventDetail>) {
		if (e.detail.inventory_editing_alias !== this.__tag.name) return;

		const {inventory_combining_alias, inventory_editing_alias, inventory_destination,
			dryRun, endpoint} = e.detail;

		toastOrNot({
			content: imagineDryRun({
				imagine:
					`${inventory_editing_alias} and ${inventory_combining_alias}`
					+ ` were moved into ${inventory_destination}`,
				url: endpoint, dryRun
			}),
			target: this.renderRoot.lastElementChild!
		});
	}

	#on_CombineAndDeleteSourcesDoneEvent(event: CustomEvent<CombineAndDeleteSourcesDone.EventDetail>) {
		// TODO Parent: listen, close combine segment, and refresh tag on display
	}

	#on_SomethingWentWrong(event: CustomEvent<SomethingWentWrong.EventDetail>)
	{
		//////if (event.detail.originatingTarget !== this) return;
		//////event.stopPropagation();
		toastError({content: event.detail.error, target: this.renderRoot.lastElementChild!});
	}

	#askForConfirmation() {
		const el: CollectionEditCombineCommandBoxElement = this.renderRoot
			.querySelector('elements-collection-edit-combine-command-box-steamside')!;
		el.showCommandConfirm();
	}
	
	async #combineAndKeepSources({receiver}:{receiver: string}) {
		const collection_editing_name = this.__tag.name;
		const collection_combining_name = this.__tag_combining_name;

		const aUrl =
			"api/collection/" +
			encodeURIComponent(collection_editing_name) +
			"/combine/" +
			encodeURIComponent(collection_combining_name) +
			"/into/" +
			encodeURIComponent(receiver) +
			"/copy";
		// TODO display 'combining...'
		try 
		{
			await this.backend.fetchBackend({url: aUrl});

			// TODO Parent: listen, close combine segment, and refresh tag on display
			this.dispatchEvent(
				new CustomEvent(
					'CollectionEditCombineElement:Combined',
					{
						detail: {
							receiver,
							collection_editing_name,
							collection_combining_name,
						},
						composed: true,
						bubbles: true,
					}
				)
			);
		}
		catch (error) {
			const imagine =
				`${collection_editing_name} and` +
				` ${collection_combining_name} were copied into ${receiver}`;
			pop_toast({
				error: error as Error,
				offline_imagine_spot: imagine,
				target: this.renderRoot.firstElementChild!,
			});
		}
	}

	private readonly subscriptions: Array<{type: string, listener: EventListener}> = [
		{
			type: CombineAndDeleteSourcesDoing.eventType,
			listener: event => this.#on_CombineAndDeleteSourcesDoing(<CustomEvent>event),
		},
	];
	async #on_connected()
	{
		EventBusSubscribeOnConnected.subscribe(this, this.subscriptions);

		await this.backend.fetchSessionDataAndDisableBackendIfOffline();
	}
	#on_disconnected()
	{
		EventBusUnsubscribeOnDisconnected.unsubscribe(this, this.subscriptions)
	}

	backend = new Backend();
}
Customary.declare(CollectionEditCombineElement);
