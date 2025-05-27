import 'mocha';
import * as CT from "#customary-testing";
import {expect} from "chai";

const suite = {
    title: 'Inventory World',
    subject_html: 'InventoryWorld.html?name=Indie',
};

describe(suite.title, async function () {
    this.timeout(48000);
    this.slow(500);

    let _window: Window;
    before(() => _window = CT.open(suite.subject_html));
    after(() => _window.close());

    function findWorld() {
        const page = CT.querySelector('elements-world-inventory-steamside', _window);
        return CT.querySelector('elements-collection-edit-steamside', page);
    }

    function findAtWorld(segmentName: string) {
        const world = findWorld();
        return CT.querySelector(segmentName, world);
    }

    function findDeck(container: Element) {
        return CT.querySelector('elements-game-card-deck-steamside', container);
    }

    function spotCard(needle: string, deck: Element): Element {
        return <Element>CT.spot(needle, deck, {selectors: 'elements-game-card-steamside'});
    }

    function findInventoryEditor() {
        return findAtWorld('elements-collection-edit-add-games-steamside');
    }

    describe('happy day: Search', async function () {
        function findCommandBox() {
            const inventoryEditor = findInventoryEditor();
            const container = CT.querySelector('elements-collection-edit-add-games-command-box-steamside', inventoryEditor);
            return CT.querySelector('elements-command-box-steamside', container);
        }

        it('looks good', async function () {
            this.retries(64);

            // FIXME figure out why command box never gets matched without this delay
            await new Promise(resolve => setTimeout(resolve, 64));

            const commandBox = findCommandBox();
            const inputAndHints: Element = CT.querySelector('.command-box', commandBox);
            commandInput = inputAndHints.querySelector('.command-text-input')!;
        });
        let commandInput: HTMLInputElement;
        it('interact: type the Search', async function () {
            CT.input('goat', commandInput);
            
            // FIXME figure out the glitch with elements-command-box-steamside event_keyup_input
            commandInput.dispatchEvent(new Event('change'));
        });

        it('looks good', async function () {
            this.retries(128);

            const needle = 'search games for goat';

            const commandBox = findCommandBox();
            const target = CT.querySelector('*[slot]', commandBox, {shadowNot: true});

            const element = CT.spot(needle, target, {
                selectors: 'elements-command-hint-with-verb-and-subject-steamside',
            })
            expect(element);

            const inputAndHints: Element = CT.querySelector('.command-box', commandBox);
            button = inputAndHints.querySelector('.command-button')!;
            expect(button.value).eq('Enter');
        });
        let button: HTMLInputElement;
        it('interact: click the Search', async function () {
            button.click();
        });

        it('looks good', async function () {
            this.retries(64);

            const inventoryEditor = findInventoryEditor();
            const searchResults = countVisibleCardsIn(inventoryEditor);
            expect(searchResults).to.equal(3);
        });
    });
    describe('happy day: Add', async function () {
        let inventoryEditor: Element;

        let face: HTMLImageElement;
        
        it('looks good', async function () {
            this.retries(256);

            inventoryEditor = findInventoryEditor();
            const searchDeck = findDeck(inventoryEditor);
            
            const card = spotCard("Goat Simulator", searchDeck);
            face = CT.querySelector('img', card) as HTMLImageElement;
        });
        it('interact: click the Card', async function () {
            face.click();
        });
    });
});

function countVisibleCardsIn(deckHolder: Element) {
    const deck = CT.querySelector('elements-game-card-deck-steamside', deckHolder);
    const cards = [...CT.querySelectorAll('elements-game-card-steamside', deck)];
    const visibleCards = cards.filter(card => card.checkVisibility());
    return visibleCards.length;
}
