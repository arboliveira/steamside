import { Customary, CustomaryElement } from "#customary";
import { SegmentElement } from "#steamside/elements/segment/segment-steamside.js";
import { CommandBoxElement } from "#steamside/elements-command-box-steamside.js";
import { CommandPlease } from "#steamside/elements/command-box/CommandPlease.js";
import { SearchFormatDetector, SteamIdCSV, SteamIdNameJson, SteamSearch } from "#steamside/elements/new-game-easy/SearchFormatDetector.js";
export class NewGameEasyElement extends CustomaryElement {
    static { this.customary = {
        name: 'new-game-easy-steamside',
        config: {
            attributes: ['inventory_name', 'search_results'],
            state: ['search_url', 'search_quest_visible, equip_deck_visible', 'equip_deck_items'],
            construct: { shadowRootDont: true },
        },
        values: {
            inventory_name: 'My Games',
        },
        hooks: {
            requires: [SegmentElement, CommandBoxElement],
            externalLoader: {
                import_meta: import.meta,
            },
            events: [
                {
                    type: CommandPlease.eventType,
                    listener: (el, e) => el.#on_Command_PLEASE(e),
                },
                {
                    selector: '#loot-button',
                    listener: el => el.#on_loot_button(),
                },
            ],
            lifecycle: {
                willUpdate: el => el.#on_willUpdate(),
            }
        }
    }; }
    #on_Command_PLEASE(e) {
        const command = e.detail.input_text_command_box_value;
        if (command) {
            this.#on_search(command);
        }
    }
    #on_search(query) {
        const searchFormatDetected = SearchFormatDetector.detectSearchFormat(query);
        const result = searchFormatDetected.result;
        if (result instanceof SteamIdCSV) {
            this.#on_search_SteamIdsSet(result);
        }
        else if (result instanceof SteamIdNameJson) {
            this.#on_search_SteamIdNameJson(result);
        }
        else if (result instanceof SteamSearch) {
            this.search_url = `https://store.steampowered.com/api/storesearch/?term=${query}&l=english&cc=US`;
            window.open(this.search_url, '_blank');
            // TODO option of search endpoint
            /*
                `https://store.steampowered.com/search/results/?term=${query}&json=1`,
                `https://steamcommunity.com/actions/SearchApps/${query}`
             */
        }
        else
            throw Object.assign(new Error('Unrecognized'), { errors: searchFormatDetected.errors });
        /*
        Skyward.stage<SearchMany.PleaseDetail>(e, this, {
            type: SearchMany.eventTypePlease,
            detail: {query: command},
        });
         */
    }
    #on_search_SteamIdNameJson(json) {
        const games = json.json.map(({ id, name }) => this.translateIdNameToCardView({ id, name }));
        this.#show_deck(games);
    }
    translateIdNameToCardView({ id, name }) {
        return {
            appid: id,
            name,
            link: `steam://run/${id}`,
            image: `https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/${id}/capsule_231x87.jpg`,
            store: '#', unavailable: false, tags: []
        };
    }
    #on_search_SteamIdsSet(set) {
        const games = set.ids.map(id => ({ appid: id, name: '(resolving name...)', link: '#',
            image: `https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/${id}/capsule_231x87.jpg`,
            store: '#', unavailable: false, tags: [] }));
        this.#show_deck(games);
    }
    #show_deck(cards) {
        this.equip_deck_items = cards;
        this.equip_deck_visible = true;
    }
    #on_willUpdate() {
        this.search_quest_visible = !!this.search_url;
    }
    #on_loot_button() {
        const json = JSON.parse(this.search_results);
        const items = json.items;
        const games = items.map(({ id, name, tiny_image }) => this.asCardView({ id, name, tiny_image }));
        this.#show_deck(games);
    }
    asCardView({ id, name, tiny_image }) {
        return {
            appid: id, name, link: '#', image: tiny_image, store: '#', unavailable: false, tags: []
        };
    }
}
Customary.declare(NewGameEasyElement);
//# sourceMappingURL=new-game-easy-steamside.js.map