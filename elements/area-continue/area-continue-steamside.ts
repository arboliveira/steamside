import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";
import {GameCardDeckElement} from "#steamside/elements-game-card-deck-steamside.js";
import {TagAGameElement} from "#steamside/elements-tag-a-game-steamside.js";
import {fetchContinuesData} from "#steamside/data-continues.js";
import {Game} from "#steamside/data-game.js";
import {CardTag} from "#steamside/elements/game-card/CardTag.js";
import {SegmentElement} from "#steamside/elements/segment/segment-steamside.js";
import {CardView} from "#steamside/elements-game-card-steamside.js";
import {translateGameToCardView} from "#steamside/application/game/Game.js";
import {ContinuePlay} from "#steamside/application/modules/continue/ContinuePlay.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {ContinueName} from "#steamside/application/modules/continue/ContinueName.js";

export class AreaContinueElement extends CustomaryElement
{
    static customary: CustomaryDeclaration<AreaContinueElement> =
        {
            name: 'area-continue-steamside',
            config: {
                construct: {shadowRootDont: true},
                state: [
                    'continues',
                ],
            },
            hooks: {
                requires: [
                    SegmentElement,
                    GameCardDeckElement,
                ],
                externalLoader: {
                    import_meta: import.meta,
                    css_dont: true,
                },
                lifecycle: {
                    connected: el => el.#on_connected(),
                },
                changes: {
                    'continues': el => el.#on_changed_continues(),
                },
                events: [
                    {
                        type: ContinuePlay.eventTypePlease,
                        listener: (el, event) =>
                            el.#on_asked_continue_game(event as CustomEvent),
                    },
                    {
                        selector: 'segment-steamside',
                        type: CardTag.eventTypePlease,
                        listener: (_el, event) =>
                            new TagAGameElement().showTagAGame({
                                card: (<CustomEvent<CardTag.PleaseDetail>>event).detail.card,
                                // TODO segment selector not really great to "tag a game" next to the game clicked
                                container: <Element>event.currentTarget
                            })
                    },
                ],
            },
        };

    declare continues: CardView[];

    #on_changed_continues() {
        const cards = this.continues;

        if (!cards[0]) return;

        Skyward.fly<ContinueName.EventDetail>(this, {
            type: ContinueName.eventTypeChanged,
            detail: {lastPlayed: 1, name: cards[0].name}
        });

        if (!cards[1]) return;

        Skyward.fly<ContinueName.EventDetail>(this, {
            type: ContinueName.eventTypeChanged,
            detail: {lastPlayed: 2, name: cards[1].name}
        });
    }

    #on_asked_continue_game(event: CustomEvent<ContinuePlay.EventDetail>) {
        const deck = this.#getDeck();
        Skyward.stage(event, deck, event);
    }

    #getDeck(): GameCardDeckElement {
        return this.renderRoot
            .querySelector('elements-game-card-deck-steamside')!;
    }

    async #on_connected()
    {
        const continues: Game[] = await fetchContinuesData();
        const cards: CardView[] = continues.map(game => translateGameToCardView(game));
        for (const card of cards) {
            card.card_scale = '231x87';
        }
        const last_played_1 = cards[0];
        const last_played_2 = cards[1];
        last_played_1.card_scale = '616x353';
        last_played_2.card_scale = 'header';
        this.continues = cards;
    }
}
Customary.declare(AreaContinueElement);
