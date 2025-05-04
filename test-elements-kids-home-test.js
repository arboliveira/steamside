import 'mocha';
import { CustomaryTesting as CT } from "#customary-testing";
import { expect } from "chai";
const suite = {
    title: 'Kids Home',
    subject_html: 'test-elements-kids-home-test.html',
};
describe(suite.title, async function () {
    this.timeout(4000);
    this.slow(500);
    let window;
    let home_el;
    let card_view_el;
    let game_link;
    let loadingOverlay;
    let mockBackend;
    before(() => window = CT.open(suite.subject_html));
    after(() => window.close());
    describe('happy day', async function () {
        it('looks good', async function () {
            this.retries(64);
            home_el = CT.querySelector('elements-world-home-steamside', window);
            card_view_el = CT.querySelector('elements-game-card-steamside', home_el);
            game_link = CT.querySelector('.game-link', card_view_el);
        });
        it('interact', async function () {
            // No asserts, just don't crash on mouseenter and mouseleave
            game_link.dispatchEvent(new MouseEvent('mouseenter', { bubbles: true }));
            game_link.dispatchEvent(new MouseEvent('mouseleave', { bubbles: true }));
            game_link.click();
        });
        it('looks good', async function () {
            this.retries(64);
            loadingOverlay = CT.querySelector('.game-tile-inner-loading-overlay', card_view_el);
            expect(loadingOverlay.checkVisibility());
        });
        it('unblock', async function () {
            mockBackend = home_el.mockBackend;
            mockBackend.let_it_go = true;
        });
        it('looks good', async function () {
            this.retries(64);
            expect(loadingOverlay.checkVisibility()).false;
            const aUrl = mockBackend.played_url;
            expect(aUrl).eq('would be URL');
        });
    });
});
//# sourceMappingURL=test-elements-kids-home-test.js.map