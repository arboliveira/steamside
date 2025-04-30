const CopyPlugin = require("copy-webpack-plugin");
const RemovePlugin = require('remove-files-webpack-plugin');
const path = require('path');
const ReplaceInFileWebpackPlugin = require('replace-in-file-webpack-plugin');

module.exports = {
    plugins: [
        new CopyPlugin({
            patterns: [
                {
                    // html and js, unbundled. ship sources too; let browsers step debug
                    from: 'public/',
                },
                {
                    // contains customary and lit sources; maps let browsers step debug
                    context: 'node_modules/customary',
                    from: '.dist/bundled/customary.mjs',
                    to: '_dependencies/customary',
                },
                {
                    context: 'node_modules/customary-testing',
                    // FIXME from dist, but not bundled: tester-customary html and css
                    from: 'src/', // hop!
                    to: '_dependencies/customary-testing',
                },
                {
                    // FIXME confirm assertion, improve comment
                    // surprisingly, customary bundled does not ship lit decorators
                    context: 'node_modules',
                    from: '@lit/reactive-element/decorators/{property.js,}',
                    to: '_dependencies/',
                },
                {
                    // FIXME confirm assertion, improve comment
                    // surprisingly, customary bundled does not export lit classes
                    context: 'node_modules',
                    from: '@lit/reactive-element/{reactive-element.js,css-tag.js}',
                    to: '_dependencies/',
                },
                {
                    context: 'node_modules',
                    from: 'chai/{chai.js,}',
                    to: '_dependencies/',
                },
                {
                    context: 'node_modules',
                    from: 'mocha/{mocha.*,}',
                    to: '_dependencies/',
                },
                {
                    context: 'node_modules',
                    from: 'sinon/pkg/{sinon-esm.js,}',
                    to: '_dependencies/',
                },
            ]
        }),
        new ReplaceInFileWebpackPlugin([{
            dir: '.dist/website',
            test: /\.html$/,
            rules: [
                {
                    // code: only used in development (live compile)
                    search: /,?\s*\n\s+"#customary\/": ".*"/m,
                    replace: '',
                },
                {
                    // code: only used in development (bundled mjs has lit essentials)
                    search: /,?\s*\n\s+"#customary\/lit": ".*"/m,
                    replace: '',
                },
                {
                    // node_modules: from development (sibling) to production (child)
                    search: / "(.*)[.][.]\/node_modules(.*)"/g,
                    replace: ' "$1_dependencies$2"',
                },
                {
                    // code: from development (live compile) to production (bundled)
                    search: /customary\/src\/now[.]js/g,
                    replace: 'customary/customary.mjs',
                },
                {
                    // code: from development (live compile) to production (hop)
                    search: /customary-testing\/src/g,
                    replace: 'customary-testing',
                },
            ]
        }]),
        new RemovePlugin({
            after: {
                root: '.dist/website',
                include: [
                    '__ONLY_HERE_BECAUSE_CANT_SKIP_WEBPACK_ENTRY.js',
                ]
            }
        }),
    ],
    mode: "none",
    entry: './webpack-entry-SKIP.json',
    output: {
        path: path.resolve(__dirname, '.dist/website'),
        filename: '__ONLY_HERE_BECAUSE_CANT_SKIP_WEBPACK_ENTRY.js',
    },
}
