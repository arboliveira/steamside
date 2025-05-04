import { Customary, CustomaryElement } from "#customary";
import 'mocha';
export class TesterCustomary extends CustomaryElement {
    static { this.customary = {
        name: 'tester-customary',
        config: {
            state: [
                'run_requested', 'import_tests_callback', 'mocha_css_location',
                'mocha_stats_placeholder_hidden'
            ],
        },
        hooks: {
            lifecycle: {
                connected: (el) => el.on_connected()
            },
            events: {
                button: (el) => el.run_requested = true
            },
            changes: {
                run_requested: (el) => el.run_tests(),
                import_tests_callback: (el) => el.run_tests(),
                mocha_css_location: (el) => el.adopt_mocha_css()
            },
            externalLoader: { import_meta: import.meta },
        },
    }; }
    static attach(el, mocha_css_location, import_tests_callback) {
        const tester = el.shadowRoot.querySelector('tester-customary');
        tester.mocha_css_location = mocha_css_location;
        tester.import_tests_callback = import_tests_callback;
    }
    async run_tests() {
        if (!this.run_requested) {
            return;
        }
        if (!this.import_tests_callback) {
            return;
        }
        this.run_requested = false;
        await this.import_tests_callback();
        this.mocha_stats_placeholder_hidden = true;
        document.getElementById('mocha')?.replaceChildren();
        mocha.run();
    }
    on_connected() {
        const htmlDivElement = document.createElement('div');
        htmlDivElement.id = 'mocha';
        document.body.append(htmlDivElement);
        // https://medium.com/dailyjs/running-mocha-tests-as-native-es6-modules-in-a-browser-882373f2ecb0
        mocha.setup({ ui: 'bdd', checkLeaks: true });
        mocha.cleanReferencesAfterRun(false);
        if (!new URLSearchParams(window.location.search).has("run-dont"))
            this.run_requested = true;
    }
    async adopt_mocha_css() {
        if (!this.mocha_css_location) {
            return;
        }
        document.adoptedStyleSheets.push(await this.fetchCSSStyleSheet(this.mocha_css_location));
    }
    async fetchCSSStyleSheet(baseURL) {
        const text = await (await fetch(baseURL)).text();
        const cssStyleSheet = new CSSStyleSheet({ baseURL });
        return cssStyleSheet.replace(text);
    }
}
Customary.declare(TesterCustomary);
//# sourceMappingURL=tester-customary.js.map