import 'mocha';
import { CustomaryTesting as CT } from "#customary-testing";
import { expect } from "chai";
import {GameCardElement} from "#steamside/elements-game-card-steamside";
import {MockBackend} from "#steamside/test-mock-data-backend";

const suite = {
    title: 'Kids Home',
    subject_html: 'test-elements-kids-home-test.html',
};

describe(suite.title, async function () {
    this.timeout(4000);
    this.slow(500);
    let window: Window;
    let card_view_el: GameCardElement;
    let loadingOverlay: Element;
    let backend: MockBackend;
    before(() => window = CT.open(suite.subject_html));
    after(() => window.close());
    describe('happy day', async function () {
        it('looks good', async function () {
            this.retries(64);

            card_view_el = CT.querySelector('elements-game-card-steamside', window);
        });
        it('interact', async function () {
            const game_link: HTMLElement = CT.querySelector('.game-link', card_view_el);

            // No asserts, just don't crash on mouseenter and mouseleave
            game_link.dispatchEvent(new MouseEvent('mouseenter', {bubbles: true}));
            game_link.dispatchEvent(new MouseEvent('mouseleave', {bubbles: true}));
            
            game_link.click();
        });
        it('looks good', async function () {
            this.retries(64);

            loadingOverlay = CT.querySelector('.game-tile-inner-loading-overlay', card_view_el);
            expect(loadingOverlay.checkVisibility());
        });
        it('unblock', async function () {
            backend = card_view_el.backend as MockBackend;

            backend.well_done_fetchBackend = true;
        });
        it('looks good', async function () {
            this.retries(64);

            expect(loadingOverlay.checkVisibility()).false;
            
            const aUrl = backend.fetchBackend_url;
            expect(aUrl).eq('would be URL');
        });
    });
});
