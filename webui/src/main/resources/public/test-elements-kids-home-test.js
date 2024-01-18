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
    let card_view_el;
    before(() => window = CT.open(suite.subject_html));
    after(() => window.close());
    describe('happy day', async function () {
        it('looks good', async function () {
            this.retries(64);

            card_view_el = CT.querySelector('#KidsGameCard', window);
        });
        it('interact', async function () {
            const game_link = CT.querySelector('.game-link', card_view_el);

            // No asserts, just don't crash on mouseenter and mouseleave
            game_link.dispatchEvent(new MouseEvent('mouseenter', {bubbles: true}));
            game_link.dispatchEvent(new MouseEvent('mouseleave', {bubbles: true}));
            
            game_link.click();
        });
        it('looks good', async function () {
            this.retries(512);

            const loadingOverlay = CT.querySelector('.game-tile-inner-loading-overlay', card_view_el);
            expect(loadingOverlay.checkVisibility());

            const backend = card_view_el.parentElement.__GameCardView.backend;
            
            const backend_invoked = backend.__backend_invoked;
            backend_invoked.resolve();
            await backend_invoked;
            
            const aUrl = backend.__aUrl__ajax_ajax_promise_2;
            expect(aUrl).eq('would be URL');

            expect(loadingOverlay.checkVisibility()).false;
        });
    });
});
