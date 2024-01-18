import 'mocha';
import { CustomaryTesting as CT } from "#customary-testing";
import { expect, assert } from "chai";

const suite = {
    title: 'Home',
    subject_html: 'Steamside.html',
};

describe(suite.title, async function () {
    this.timeout(48000);
    this.slow(500);
    let _window;

    before(() => _window = CT.open(suite.subject_html));
    after(() => _window.close());
    
    describe('happy day: Continues', async function () {
        let segment;
        let b;
        it('looks good', async function () {
            this.retries(256);

            segment = _window.$('#continues-segment');
            
            b = segment.find('.more-button-text');
            assert.equal(b.is(':visible'), true, 'visible more button');

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
        let segment, button;

        it('looks good', async function () {
            this.retries(64);

            segment = _window.$('#search-segment');
        });
        it('interact', async function () {
            const input = segment.find('.command-text-input');
            input.val('anything');
        });
        it('looks good', async function () {
            this.retries(64);

            button = segment.find('.command-button').get(0);
            expect(button.value).eq('Enter');
        });
        it('interact', async function () {
            // FIXME
            if (false) {
                button.click();
            }
        });
        it('looks good', async function () {
            // FIXME
            if (false) {
                this.retries(512);

                const searchResults = segment.find('.game-tile');
                expect(searchResults.length).to.equal(4);
            }
        });
    });
    
    describe('happy day: Favorites: more', async function () {
        let segment, b;
        it('looks good', async function () {
            this.retries(64);

            segment = _window.$('#favorites-segment');

            b = segment.find('.more-button-text');
            assert.equal(b.is(':visible'), true, 'visible more button');

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

            segment = _window.$('#favorites-segment');
        });
        it('interact', async function () {
            const l = segment.find("#side-link-favorite-switch").get(0);
            l.click();
        });
        it('looks good', async function () {
            this.retries(512);

            const listView = _window.$("#TagStickersView");
            expect(listView.length).to.equal(1);

            const info = listView.find("#TagStickerView");
            assert.equal(info.length > 0, true, 'tag stickers');
        });
    });
});

function visibleGames(segment) {
    return segment.find('.game-tile').filter(':visible').length;
}
