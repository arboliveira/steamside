import {Customary, CustomaryElement} from "#customary";

//import {CustomaryDeclaration} from "#customary";

export class CommandHintWithVerbAndSubjectElement extends CustomaryElement {
    /**
     * @type {CustomaryDeclaration<CommandHintWithVerbAndSubjectElement>}
     */
    static customary =
        {
            name: 'elements-command-hint-with-verb-and-subject-steamside',
            config: {
                attributes: [
                    'verb', 'subject',
                ],
                define: {
                    fontLocation: 'https://fonts.googleapis.com/css?family=Karla:regular, bold' 
                }
            },
            hooks: {
                externalLoader: {
                    import_meta: import.meta,
                    css_dont: true,
                },
            }
        }
}
Customary.declare(CommandHintWithVerbAndSubjectElement);
