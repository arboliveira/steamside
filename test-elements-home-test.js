import 'mocha';
import * as CT from "#customary-testing";
import { assert, expect } from "chai";
const suite = {
    title: 'Home World',
    subject_html: 'index.html',
};
describe(suite.title, async function () {
    this.timeout(48000);
    this.slow(500);
    let _window;
    before(() => _window = CT.open(suite.subject_html));
    after(() => _window.close());
    function findHome() {
        const page = CT.querySelector('elements-world-home-steamside', _window);
        return CT.querySelector('elements-world-home-advanced-mode-steamside', page);
    }
    function findAtHome(segmentName) {
        const homeElement = findHome();
        return CT.querySelector(segmentName, homeElement);
    }
    function findDeck(container) {
        return CT.querySelector('elements-game-card-deck-steamside', container);
    }
    function spotCard(needle, deck) {
        return CT.spot(needle, deck, { selectors: 'elements-game-card-steamside' });
    }
    function findLoadingOverlay(card) {
        return CT.querySelector('.game-tile-inner-loading-overlay', card);
    }
    let _continuesSegment;
    let _continuesDeck;
    let _suggestedElement;
    describe('happy day: Continues: Play', async function () {
        const represent = 'Sid Meier’s Civilization® VI';
        let face;
        let loadingOverlay;
        it('looks good', async function () {
            this.retries(128);
            _continuesSegment = findAtHome('#continues-segment');
            _continuesDeck = findDeck(_continuesSegment);
            _suggestedElement = findAtHome('elements-world-home-suggested-segments-steamside');
            const card = spotCard(represent, _continuesDeck);
            face = CT.querySelector('img', card);
        });
        it('interact: click the Card', async function () {
            face.click();
        });
        it('looks good: Card in Continues', async function () {
            this.retries(128);
            const card = spotCard(represent, _continuesDeck);
            loadingOverlay = findLoadingOverlay(card);
            expect(loadingOverlay.checkVisibility());
        });
        it('looks good: Card in Hub', async function () {
            const collectionEditElement = CT.querySelector('elements-collection-edit-steamside', _suggestedElement);
            const collectionEditDeck = findDeck(collectionEditElement);
            const card2 = spotCard(represent, collectionEditDeck);
            const loadingOverlay2 = findLoadingOverlay(card2);
            expect(loadingOverlay2.checkVisibility());
        });
    });
    describe('happy day: Continues: More expand', async function () {
        let moreIcon;
        it('looks good', async function () {
            this.retries(256);
            moreIcon = CT.querySelector('.more-icon', _continuesDeck);
            expect(moreIcon.checkVisibility(), 'visible more icon');
            const visibleCardsBeforeMoreClicked = countVisibleCardsIn(_continuesSegment);
            expect(visibleCardsBeforeMoreClicked).to.equal(3);
        });
        it('interact: expand the More', async function () {
            moreIcon.click();
        });
        it('looks good', async function () {
            this.retries(256);
            const visibleGamesAfterMoreClicked = countVisibleCardsIn(_continuesSegment);
            expect(visibleGamesAfterMoreClicked).to.equal(11);
        });
    });
    describe('happy day: Search', async function () {
        let commandBox;
        let inputAndHints;
        let button;
        let searchSegment;
        it('looks good', async function () {
            this.retries(64);
            searchSegment = findAtHome('elements-world-home-search-segment-steamside');
            const container = CT.querySelector('elements-world-home-search-command-box-steamside', searchSegment);
            commandBox = CT.querySelector('elements-command-box-steamside', container);
            inputAndHints = CT.querySelector('.command-box', commandBox);
        });
        it('interact: type the Search', async function () {
            const input = inputAndHints.querySelector('.command-text-input');
            CT.input('goat', input);
            // FIXME figure out the glitch with elements-command-box-steamside event_keyup_input
            input.dispatchEvent(new Event('change'));
        });
        it('looks good', async function () {
            this.retries(64);
            const needle = 'search goat';
            const target = CT.querySelector('*[slot]', commandBox, { shadowNot: true });
            // FIXME input content changes on screen, but DOM input value does not ?!?
            const element = CT.spot(needle, target, {
                selectors: 'elements-command-hint-with-verb-and-subject-steamside',
            });
            expect(element);
            button = inputAndHints.querySelector('.command-button');
            expect(button.value).eq('Enter');
        });
        it('interact: click the Search', async function () {
            button.click();
        });
        it('looks good', async function () {
            this.retries(64);
            const searchResults = countVisibleCardsIn(searchSegment);
            expect(searchResults).to.equal(3);
        });
    });
    describe('happy day: Continues: More contract', async function () {
        let moreIcon;
        it('looks good', async function () {
            this.retries(256);
            moreIcon = CT.querySelector('.more-icon', _continuesDeck);
            expect(moreIcon.checkVisibility(), 'visible more icon');
            const visibleCardsBeforeMoreClicked = countVisibleCardsIn(_continuesSegment);
            expect(visibleCardsBeforeMoreClicked).to.equal(11);
        });
        it('interact: contract the More', async function () {
            moreIcon.click();
        });
        it('looks good', async function () {
            this.retries(256);
            const visibleGamesAfterMoreClicked = countVisibleCardsIn(_continuesSegment);
            expect(visibleGamesAfterMoreClicked).to.equal(3);
        });
    });
    let _favoritesSegment;
    describe('happy day: Favorites', async function () {
        it('looks good', async function () {
            this.retries(64);
            _favoritesSegment = findAtHome('elements-world-home-favorites-segment-steamside');
        });
    });
    describe('happy day: Favorites: more', async function () {
        let segment, b;
        it('looks good', async function () {
            this.retries(64);
            segment = CT.querySelector('#favorites-segment', _favoritesSegment);
            const element3 = CT.querySelector('elements-game-card-deck-steamside', segment);
            b = CT.querySelector('.more-icon', element3);
            expect(b.checkVisibility(), 'visible more button');
            const visibleGamesBeforeMoreClicked = countVisibleCardsIn(segment);
            expect(visibleGamesBeforeMoreClicked).to.equal(3);
        });
        it('interact', async function () {
            b.click();
        });
        it('looks good', async function () {
            this.retries(512);
            const visibleGamesAfterMoreClicked = countVisibleCardsIn(segment);
            expect(visibleGamesAfterMoreClicked).to.equal(5);
        });
    });
    describe('happy day: Favorites: switch', async function () {
        let segment;
        it('looks good', async function () {
            this.retries(64);
            segment = CT.querySelector('#favorites-segment', _favoritesSegment);
        });
        it('interact', async function () {
            const l = segment.querySelector("#side-link-favorite-switch");
            l.click();
        });
        it('looks good', async function () {
            this.retries(512);
            const element3 = CT.querySelector('elements-collection-picker-steamside', segment);
            const element4 = CT.querySelector('elements-tag-stickers-steamside', element3);
            const listView = CT.querySelector("#TagStickersView", element4);
            const info = listView.querySelectorAll("elements-tag-sticker-steamside");
            assert.equal(info.length > 0, true, 'tag stickers');
        });
    });
});
function countVisibleCardsIn(deckHolder) {
    const deck = CT.querySelector('elements-game-card-deck-steamside', deckHolder);
    const cards = [...CT.querySelectorAll('elements-game-card-steamside', deck)];
    const visibleCards = cards.filter(card => card.checkVisibility());
    return visibleCards.length;
}
//# sourceMappingURL=test-elements-home-test.js.map