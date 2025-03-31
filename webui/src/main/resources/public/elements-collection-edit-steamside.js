import {Customary, CustomaryElement} from "#customary";
import {TagStickerElement} from "#steamside/elements-tag-sticker-steamside.js";
import {GameCardDeckElement} from "#steamside/elements-game-card-deck-steamside.js";
import {Backend} from "#steamside/data-backend.js";
import {pop_toast} from "#steamside/vfx-toaster.js";
import {TagAGameElement} from "#steamside/elements-tag-a-game-steamside.js";
import {fetchInventoryContentsOfTag} from "#steamside/data-inventory.js";
import {CollectionEditAddGamesElement} from "#steamside/elements-collection-edit-add-games-steamside.js";
import {CollectionEditCombineElement} from "#steamside/elements-collection-edit-combine-steamside.js";
import {Sideshow} from "#steamside/vfx-sideshow.js";

//import {CustomaryDeclaration} from "#customary";

export class CollectionEditElement extends CustomaryElement {
	/**
	 * @type {CustomaryDeclaration<CollectionEditElement>}
	 */
	static customary =
		{
			name: 'elements-collection-edit-steamside',
			config: {
				define: {
					fontLocations: [
						"https://fonts.googleapis.com/css?family=Arvo:regular,bold",
						'https://fonts.googleapis.com/css?family=Karla:regular,bold'
					],
				},
				attributes: [
					'simplified',
				],
				state: [
					'_tag', '_inventory',
					'add_games_segment_visible',
					'combine_collection_picker_visible',
				],
			},
			values: {
			},
			hooks: {
 				requires: [
					TagStickerElement, GameCardDeckElement, 
				    CollectionEditAddGamesElement,
				    CollectionEditCombineElement,
			    ],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
					firstUpdated: el => el.#on_firstUpdated(),
					willUpdate: el => el.#on_willUpdate(),
				},
				changes: {
					'_tag': (el, a) => el.#on_changed_tag(a),
					'__inventory_dynamic': (el, a) => el._inventory = a,
				},
				events: [
					{
						type: 'CollectionEditAddGamesElement:AddGamePlease',
						selector: '.segment',
						listener: (el, e) => el.#on_CollectionEditAddGamesElement_AddGamePlease(e),
					},
					{
						type: 'GameCardElement:ActionButtonClick',
						selector: '.segment',
						listener: (el, e) => el.#on_GameCardElement_ActionButtonClick(e),
					},
					{
						type: 'click',
						selector: "#side-link-combine",
						listener: (el, e) => el.#combineClicked(e),
					},
				],
			}
		}
		
	#on_willUpdate() {
		const read_only =
			this._tag?.builtin
			|| (this.simplified === 'true');
		this.add_games_segment_visible = !read_only;
	}
	
	async #on_GameCardElement_ActionButtonClick(event) {
		const {action_button, game, targetInteractedWith} = event.detail;
		if (action_button === 'remove') {
			await this.#removeGameFromInventory(game, targetInteractedWith);
		}
		if (action_button === 'tag') {
			this.#openTagPickerWithinSegment(game, event.currentTarget);
		}
	}

	async #on_CollectionEditAddGamesElement_AddGamePlease(event) {
		await this.#addGameToInventory(
			event.detail.game,
			event.detail.targetInteractedWith
		);
	}

	async #addGameToInventory(game, toastAnchor) {
		const name = this._tag.name;
		const aUrl = "api/collection/" + name + "/add/" + game.appid;
		
		// FIXME display 'adding...'
		try {
			await this.backend.fetchBackend({url: aUrl});
			
			// FIXME nice to have: animate the card dropping into the inventory
			await this.#fetch_inventory();
			
			// FIXME nice to have: filter tagged cards out of search results
		} catch (error) {
			pop_toast({
				error,
				offline_imagine_spot: `${game.name} was added to ${name}`,
				target: toastAnchor,
			});
		}
	}

	async #removeGameFromInventory(game, element_clicked) {
		const name = this._tag.name;

		const aUrl = "api/collection/" + name + "/remove/" + game.appid;
		// FIXME display 'removing...'
		try 
		{
			await this.backend.fetchBackend({url: aUrl});
			await this.#fetch_inventory();
		} 
		catch (error) {
			pop_toast({
				error,
				offline_imagine_spot: `${game.name} was removed from ${name}`,
				target: element_clicked,
			});
		}
	}

	#openTagPickerWithinSegment(game, segment) {
		// FIXME too messy, open new window instead
		new TagAGameElement()
			.showTagAGame({game, container: segment});
	}

	async #fetch_inventory()
	{
		if (!this._tag) return;
		this._inventory = await fetchInventoryContentsOfTag(this._tag);
	}

	async #on_changed_tag(a) {
		try
		{
			await this.#fetch_inventory();
		}
		catch (error)
		{
			pop_toast({error, target: this});
		}
	}

	#combineClicked(e) {
		e.preventDefault();

		this.combine_collection_picker_visible = true;
	}

	#on_firstUpdated() {
		Sideshow.customary_dispatchEvent_Customary_lifecycle_firstUpdated(this);
	}

	async #on_connected()
	{
		await this.backend.fetchSessionDataAndDisableBackendIfOffline();
	}

	backend = new Backend();
}
Customary.declare(CollectionEditElement);
