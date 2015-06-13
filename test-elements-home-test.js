import 'mocha';
import { CustomaryTesting as CT } from "#customary-testing";
import { assert, expect } from "chai";
const suite = {
    title: 'Home',
    subject_html: 'index.html',
};
describe(suite.title, async function () {
    this.timeout(48000);
    this.slow(500);
    let _window;
    before(() => _window = CT.open(suite.subject_html));
    after(() => _window.close());
    function findHome() {
        const element1 = CT.querySelector('elements-world-home-steamside', _window);
        return CT.querySelector('elements-world-home-advanced-mode-steamside', element1);
    }
    function findSegment(segmentName) {
        const element2 = findHome();
        return CT.querySelector(segmentName, element2);
    }
    describe('happy day: Continues', async function () {
        let segment;
        let b;
        it('looks good', async function () {
            this.retries(256);
            segment = findSegment('#continues-segment');
            const element3 = CT.querySelector('elements-game-card-deck-steamside', segment);
            b = CT.querySelector('.more-icon', element3);
            expect(b.checkVisibility(), 'visible more button');
            const visibleGamesBeforeMoreClicked = visibleGames(segment);
            expect(visibleGamesBeforeMoreClicked).to.equal(3);
        });
        it('interact', async function () {
            b.click();
        });
        it('looks good', async function () {
            this.retries(512);
            const visibleGamesAfterMoreClicked = visibleGames(segment);
            expect(visibleGamesAfterMoreClicked).to.equal(13);
        });
    });
    describe('happy day: Search', async function () {
        let commandBox, button;
        it('looks good', async function () {
            this.retries(64);
            const element1 = findHome();
            const element2 = CT.querySelector('elements-world-home-search-segment-steamside', element1);
            const element3 = CT.querySelector('elements-world-home-search-command-box-steamside', element2);
            const element4 = CT.querySelector('elements-command-box-steamside', element3);
            commandBox = CT.querySelector('.command-box', element4);
        });
        it('interact', async function () {
            const input = commandBox.querySelector('.command-text-input');
            CT.input('goat', input);
        });
        it('looks good', async function () {
            this.retries(64);
            button = commandBox.querySelector('.command-button');
            expect(button.value).eq('Enter');
        });
        it('interact', async function () {
            button.click();
        });
        it('looks good', async function () {
            this.retries(64);
            // FIXME input content changes on screen, but DOM input value does not ?!?
            /*
            const searchResults = visibleGames(segment);
            expect(searchResults).to.equal(3);
             */
        });
    });
    describe('happy day: Favorites: more', async function () {
        let segment, b;
        it('looks good', async function () {
            this.retries(64);
            const element1 = findHome();
            const element2 = CT.querySelector('elements-world-home-favorites-segment-steamside', element1);
            segment = CT.querySelector('#favorites-segment', element2);
            const element3 = CT.querySelector('elements-game-card-deck-steamside', segment);
            b = CT.querySelector('.more-icon', element3);
            expect(b.checkVisibility(), 'visible more button');
            const visibleGamesBeforeMoreClicked = visibleGames(segment);
            expect(visibleGamesBeforeMoreClicked).to.equal(3);
        });
        it('interact', async function () {
            b.click();
        });
        it('looks good', async function () {
            this.retries(512);
            const visibleGamesAfterMoreClicked = visibleGames(segment);
            expect(visibleGamesAfterMoreClicked).to.equal(4);
        });
    });
    describe('happy day: Favorites: switch', async function () {
        let segment;
        it('looks good', async function () {
            this.retries(64);
            const element1 = findHome();
            const element2 = CT.querySelector('elements-world-home-favorites-segment-steamside', element1);
            segment = CT.querySelector('#favorites-segment', element2);
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
function visibleGames(withDeck) {
    const element1 = CT.querySelector('elements-game-card-deck-steamside', withDeck);
    const cards = [...CT.querySelectorAll('elements-game-card-steamside', element1)];
    const visibleCards = cards.filter(card => card.checkVisibility());
    return visibleCards.length;
}
//# sourceMappingURL=test-elements-home-test.js.map