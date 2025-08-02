import { Customary, CustomaryElement } from "#customary";
import { CommandBoxElement } from "#steamside/elements-command-box-steamside.js";
import { CommandHintWithVerbAndSubjectElement } from "#steamside/elements-command-hint-with-verb-and-subject-steamside.js";
import { CommandBoxValue } from "#steamside/elements/command-box/CommandBoxValue.js";
export class CollectionEditAddGamesCommandBoxElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-collection-edit-add-games-command-box-steamside',
        config: {
            attributes: [
                'firstSearchResult_name',
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
                'firstSearchResult_name': el => el.requestUpdate(),
            },
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
        commandBox.focus_on_input();
    }
    #on_CommandBoxElement_InputValueChanged(event) {
        this.commandBox_inputValue = event.detail.input_text_command_box_value;
    }
    #on_willUpdate() {
        if (this.commandBox_inputValue) {
            this.verbA = 'search games for';
            this.subjectA = this.commandBox_inputValue;
            this.verbB = 'search hubs for';
            this.subjectB = this.commandBox_inputValue;
        }
        else {
            this.verbA = 'pick from';
            this.subjectA = 'never tagged';
            this.verbB = 'pick from';
            this.subjectB = 'recently played';
        }
        // FIXME no continues? exploreACommandModel, exploreBCommandModel
    }
}
Customary.declare(CollectionEditAddGamesCommandBoxElement);
//# sourceMappingURL=elements-collection-edit-add-games-command-box-steamside.js.map