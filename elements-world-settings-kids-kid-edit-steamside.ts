import {Customary, CustomaryElement} from "#customary";
import {Backend} from "#steamside/data-backend.js";
import {CollectionPickerElement} from "#steamside/elements-collection-picker-steamside.js";

import {CustomaryDeclaration} from "#customary";
import {Kid} from "#steamside/data-kids";
import {Tag} from "#steamside/data-tag";

export class WorldSettingsKidsKidEditElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<WorldSettingsKidsKidEditElement> =
		{
			name: 'elements-world-settings-kids-kid-edit-steamside',
			config: {
				attributes: [
					'kid_id',
					'kid_name',
					'kid_user',
					'kid_inventory',
				],
				state: [
					'__kid',
					'__collection_picker_visible',
					'__can_see_and_play_sticker_tag'
				],
				define: {
					fontLocations: [
						"https://fonts.googleapis.com/css?family=Arvo:regular,bold",
						'https://fonts.googleapis.com/css?family=Jura:regular',
						'https://fonts.googleapis.com/css?family=Karla:regular',
					],
				},
			},
			values: {
			},
			hooks: {
				requires: [CollectionPickerElement],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
					willUpdate: el => el.#on_willUpdate(),
					firstUpdated: el => el.#on_firstUpdated(),
				},
				changes: {
					'__kid': (el, a) => el.#on_kid_change(a),
				},
				events: [
					{
						selector: "input[data-customary-bind='kid_name']",
						listener: (el, e) =>
								el.kid_name = (e.target as HTMLInputElement).value,
					},
					{
						selector: "input[data-customary-bind='kid_user']",
						listener: (el, e) =>
								el.kid_user = (e.target as HTMLInputElement).value,
					},
					{
						type: 'TagStickerElement:TagClicked', 
						listener: (el) => el.#on_TagStickerElement_TagClicked(),
					},
					{
						type: 'CollectionPickerElement:CollectionPicked', 
						listener: (el, e) => el.#on_CollectionPickerElement_CollectionPicked(e as CustomEvent),
					},
					{
						selector: "#SaveButton",
						listener: (el) => el.#on_Save_click(),
					},
					{
						selector: "#DeleteKid",
						listener: (el, e) => el.#on_DeleteKid_click(e),
					},
					{
						type: "click",
						selector: "#side-link-inventory-switch",
						listener: (el, e) => el.#on_inventory_switch_click(e),
					}
				],
			}
		}

	declare kid_id: string | undefined;
	declare kid_name: string | undefined;
	declare kid_user: string | undefined;
	declare kid_inventory: string | undefined;
	declare __kid: KidData_View;
	declare __can_see_and_play_sticker_tag: Tag;
	declare __collection_picker_visible: boolean;

	#on_kid_change(a: KidData_View) {
		this.kid_id = a.id;
		this.kid_name = a.name;
		this.kid_user = a.user;
		this.kid_inventory = a.inventory_tag_for_sticker?.name;
	}

	#on_Save_click() {
		const kid = this.#thisKid();

		this.dispatchEvent(
			new CustomEvent(
				'WorldSettingsKidsKidEditElement:SavePlease',
				{
					detail: {data: {kid}},
					composed: true,
					bubbles: true,
				}
			)
		);
		
		// FIXME parent should call remove if backend succeeds, leave it showing if fails 
		this.remove();
	}

	#thisKid(): Kid {
		return {
			id: this.kid_id!,
			name: this.kid_name!,
			user: this.kid_user!,
			inventory: this.kid_inventory!,
		};
	}

	#on_DeleteKid_click(e: Event) {
		e.preventDefault();

		const kid = this.#thisKid();

		this.dispatchEvent(
			new CustomEvent(
				'WorldSettingsKidsKidEditElement:DeletePlease',
				{
					detail: {data: {kid}},
					composed: true,
					bubbles: true,
				}
			)
		);

		// FIXME parent should call remove if backend succeeds, leave it showing if fails
		this.remove();
	}

	#on_inventory_switch_click(e: Event) {
		e.preventDefault();
		this.#switchInventory();
	}

	#on_willUpdate() {
		this.__can_see_and_play_sticker_tag =
			this.kid_inventory 
				? {name: this.kid_inventory} 
				: {name: 'Choose...'};
	}

	#on_TagStickerElement_TagClicked() {
		this.#switchInventory();
	}

	#on_CollectionPickerElement_CollectionPicked(e: CustomEvent) {
		// FIXME receive or look up real tag so we can display count
		this.kid_inventory = e.detail;
		this.__collection_picker_visible = false;
		this.requestUpdate();
	}

	#switchInventory() {
		this.__collection_picker_visible = true;
	}

	#on_firstUpdated() {
		const input = this.renderRoot.querySelector("input[data-customary-bind='kid_name']") as HTMLInputElement;
		input.focus();
	}

	async #on_connected()
	{
		await this.backend.fetchSessionDataAndDisableBackendIfOffline();
	}

	backend = new Backend();
}
Customary.declare(WorldSettingsKidsKidEditElement);

export type KidData_View = {
	id?: string;
	name?: string;
	user?: string;
	inventory_tag_for_sticker?: Tag;
}