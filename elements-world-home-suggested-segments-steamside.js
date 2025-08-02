import { Customary, CustomaryElement } from "#customary";
import { CollectionEditElement } from "#steamside/elements-collection-edit-steamside.js";
import { fetchTagSuggestionsData } from "#steamside/data-tag-suggestions.js";
export class WorldHomeSuggestedSegmentsElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-world-home-suggested-segments-steamside',
        config: {
            construct: { shadowRootDont: true },
            define: {
                fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular",
            },
            state: [
                'tag_suggestions',
            ],
            attributes: [],
        },
        values: {},
        hooks: {
            requires: [CollectionEditElement],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
            },
        }
    }; }
    async #fetch_tag_suggestions() {
        this.tag_suggestions = await fetchTagSuggestionsData();
    }
    async #on_connected() {
        await this.#fetch_tag_suggestions();
    }
}
Customary.declare(WorldHomeSuggestedSegmentsElement);
//# sourceMappingURL=elements-world-home-suggested-segments-steamside.js.map