import { Customary, CustomaryElement } from "#customary";
import { CommandBoxElement } from "#steamside/elements-command-box-steamside.js";
import { CommandHintWithVerbAndSubjectElement } from "#steamside/elements-command-hint-with-verb-and-subject-steamside.js";
import { CommandBoxValue } from "#steamside/elements/command-box/CommandBoxValue.js";
export class CollectionNewEmptyCommandBoxElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-collection-new-empty-command-box-steamside',
        config: {
            attributes: [],
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
            changes: {},
            events: [
                {
                    type: CommandBoxValue.eventTypeChanged,
                    listener: (el, event) => el.#on_CommandBoxElement_InputValueChanged(event),
                },
            ],
        }
    }; }
    #on_firstUpdated() {
        const commandBox = this.renderRoot.querySelector('elements-command-box-steamside');
        // FIXME focus when displayed
        //commandBox.focus_on_input();
    }
    #on_CommandBoxElement_InputValueChanged(event) {
        this.commandBox_inputValue = event.detail.input_text_command_box_value;
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
        }
        else {
            this.subjectA = `(current date) collection and add games`;
            this.subjectB = `(current date) collection and stay here`;
        }
    }
}
Customary.declare(CollectionNewEmptyCommandBoxElement);
//# sourceMappingURL=elements-collection-new-empty-command-box-steamside.js.map