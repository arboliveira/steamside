import {Customary, CustomaryElement} from "#customary";
import {CommandBoxElement} from "#steamside/elements-command-box-steamside.js";
import {
    CommandHintWithVerbAndSubjectElement
} from "#steamside/elements-command-hint-with-verb-and-subject-steamside.js";

import {CustomaryDeclaration} from "#customary";

export class CollectionNewEmptyCommandBoxElement extends CustomaryElement {
    static customary: CustomaryDeclaration<CollectionNewEmptyCommandBoxElement> =
        {
            name: 'elements-collection-new-empty-command-box-steamside',
            config: {
                attributes: [
                ],
              state: [
                  'commandBox_inputValue',
                  'verbA', 'subjectA', 'verbB', 'subjectB',
              ],
            },
            hooks: {
                externalLoader: {
                    import_meta: import.meta,
                    css_dont: true,
                },
                requires: [CommandBoxElement, CommandHintWithVerbAndSubjectElement],
                lifecycle: {
                    firstUpdated: el => el.#on_firstUpdated(),
                    willUpdate: el => el.#on_willUpdate(),
                },
                changes: {
                },
                events: [
                    {
                        type: 'CommandBoxElement:InputValueChanged',
                        listener: (el, event) =>
                                el.#on_CommandBoxElement_InputValueChanged(<CustomEvent>event),
                    },
                ],
            }
        }
    declare commandBox_inputValue: string;
    declare verbA: string;
    declare verbB: string;
    declare subjectA: string;
    declare subjectB: string;

    #on_firstUpdated() {
        const commandBox: CommandBoxElement = this.renderRoot.querySelector('elements-command-box-steamside')!;
        // FIXME focus when displayed
        //commandBox.focus_on_input();
    }

    #on_CommandBoxElement_InputValueChanged(event: CustomEvent) {
        this.commandBox_inputValue = event.detail;
    }

    #on_willUpdate() {
        /*
		this.elCommandHintA.find(selectorAfterwards).text("add games");
		this.elCommandHintB.find(selectorAfterwards).text("stay here");
         */

        this.verbA = 'Create'; // [HTML]
        this.verbB = 'Create'; // [HTML]

        if (this.commandBox_inputValue) {
            this.subjectA = `${this.commandBox_inputValue} collection and add games`;
            this.subjectB = `${this.commandBox_inputValue} collection and stay here`;
        } else {
            this.subjectA = `(current date) collection and add games`;
            this.subjectB = `(current date) collection and stay here`;
        }
    }
}
Customary.declare(CollectionNewEmptyCommandBoxElement);
