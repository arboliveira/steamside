import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

export class SegmentElement extends CustomaryElement {
    static customary: CustomaryDeclaration<SegmentElement> =
        {
            name: 'segment-steamside',
            config: {
                define: {
                    fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
                },
            },
            hooks: {
                externalLoader: {
                    import_meta: import.meta,
                },
            }
        };
}
Customary.declare(SegmentElement);
