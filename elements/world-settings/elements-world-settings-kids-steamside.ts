import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";
import {Backend} from "#steamside/data-backend.js";
import {fetchKidsData, Kid} from "#steamside/data-kids.js";
import {TagStickerElement} from "#steamside/elements-tag-sticker-steamside.js";
import {
	KidData_View,
	WorldSettingsKidsKidEditElement
} from "#steamside/elements/world-settings/elements-world-settings-kids-kid-edit-steamside.js";
import {pop_toast} from "#steamside/vfx-toaster.js";
import {Sideshow} from "#steamside/vfx-sideshow.js";

import {SegmentElement} from "#steamside/elements/segment/segment-steamside.js";

export class WorldSettingsKidsElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<WorldSettingsKidsElement> =
		{
			name: 'elements-world-settings-kids-steamside',
			config: {
				state: [
					'__kidsData',
					'__kidsData_view',
				],
				define: {
					fontLocations: [
						"https://fonts.googleapis.com/css?family=Arvo:regular,bold",
						'https://fonts.googleapis.com/css?family=Jura:regular',
						'https://fonts.googleapis.com/css?family=Karla:regular',
					],
				},
				construct: {shadowRootDont: true},
			},
			hooks: {
				requires: [
					SegmentElement,
					TagStickerElement,
					WorldSettingsKidsKidEditElement,
				],
				externalLoader: {
					import_meta: import.meta,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
					firstUpdated: el => el.#on_firstUpdated(),
				},
				changes: {
					'__kidsData': (el, a) => el.#on_kidsData_change(a),
				},
				events: [
					{
						selector: "#AddKid", 
						listener: (el, e) => el.#on_AddKid_click(e),
					},
					{
						selector: ".kid-container",
						type: 'click',
						listener: (el, e) => el.#on_kid_container_click(e),
					},
					{
						type: 'WorldSettingsKidsKidEditElement:SavePlease',
						listener: (el, e) => 
							el.#on_WorldSettingsKidsKidEditElement_SavePlease(e as CustomEvent),
					},
					{
						type: 'WorldSettingsKidsKidEditElement:DeletePlease',
						listener: (el, e) => 
							el.#on_WorldSettingsKidsKidEditElement_DeletePlease(e as CustomEvent),
					},
				],
			}
		}
	declare __kidsData_view: KidData_View[];
	declare __kidsData: Kid[];

	#on_AddKid_click(e: Event)
	{
		e.preventDefault();
		this.#addKid();
	}

	#addKid()
	{
		const kid: KidData_View = {};
		this.#editKid(kid);
	}

	#on_kid_container_click(e: Event) {
		e.preventDefault();
		const target: Element = <Element>e.currentTarget!;
		const attr = target.getAttribute('data-customary-index')!;
		const index = parseInt(attr);
		const kid: KidData_View = this.__kidsData_view[index];
		this.#editKid(kid);
	}

	#editKid(kid: KidData_View) {
		 const editor = new WorldSettingsKidsKidEditElement();
		 editor.__kid = kid;
		 this.parentElement!.appendChild(editor);
		// FIXME editor segment title: "New Kid", "Edit Kid"
	}

	async #on_WorldSettingsKidsKidEditElement_SavePlease(e: CustomEvent) {
		const toastAnchor = e.currentTarget as Element;

		const {kid} = e.detail.data;
		
		const payload = {
			id: kid.id,
			name: kid.name,
			user: kid.user,
			inventory: kid.inventory,
		};

		const base = 'api/kids/kid';

		const {url, method} =
			payload.id === undefined ?
				{
					url: base,
					method: 'POST',
				}
				:
				{
					url: base + '/' + encodeURIComponent(payload.id) + '.json',
					method: 'PUT',
				};

		// FIXME popover-tooltip "Saving..."
		try {
			await this.backend.fetchBackend({
				url,
				requestInit: {
					method,
					body: JSON.stringify(payload),
				},
			});
			// FIXME popover-tooltip "Saved." (see SaveButton.title for full text)
			
			await this.#loadKidsData();
		}
		catch (error)
		{
			pop_toast({
				error: error as Error,
				offline_imagine_spot: `${payload.name} was saved`,
				target: toastAnchor,
			});
		}
	}

	async #on_WorldSettingsKidsKidEditElement_DeletePlease(e: CustomEvent) {
		const toastAnchor = e.currentTarget as Element;

		const {id, name} = e.detail.data.kid;
		
		// FIXME popover-tooltip "Deleting..."
		try {
			await this.backend.fetchBackend({
				url: 'api/kids/kid/' + encodeURIComponent(id) + '.json',
				requestInit: {method: 'DELETE'},
			});
			// FIXME popover-tooltip "Deleted." (see SaveButton.title for full text)
			
			await this.#loadKidsData();
		}
		catch (error)
		{
			pop_toast({
				error: error as Error,
				offline_imagine_spot: `${name} was deleted`,
				target: toastAnchor,
			});
		}
	}

	#on_kidsData_change(a: Kid[]) {
		// FIXME fetch the actual tag so count is displayed too
		this.__kidsData_view = a.map(kid => ({
			id: kid.id,
			name: kid.name,
			user: kid.user,
			inventory_tag_for_sticker: {
				name: kid.inventory,
			}
		}));
	}

	#on_firstUpdated() {
		Sideshow.customary_dispatchEvent_Customary_lifecycle_firstUpdated(this);
	}

	async #on_connected()
	{
		await this.backend.fetchSessionDataAndDisableBackendIfOffline();
		
		await this.#loadKidsData();
	}

	async #loadKidsData() {
		this.__kidsData = await fetchKidsData();
	}

	backend = new Backend();
}
Customary.declare(WorldSettingsKidsElement);
