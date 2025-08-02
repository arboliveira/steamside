import { Customary, CustomaryElement } from "#customary";
import { CommandBoxElement } from "#steamside/elements-command-box-steamside.js";
import { CommandHintWithVerbAndSubjectElement } from "#steamside/elements-command-hint-with-verb-and-subject-steamside.js";
import { CommandBoxValue } from "#steamside/elements/command-box/CommandBoxValue.js";
export class WorldHomeSearchCommandBoxElement extends CustomaryElement {
    static { this.customary = {
        config: {
            attributes: ['last_played_name_1', 'last_played_name_2'],
            state: ['command_box_entered', 'verbA', 'subjectA', 'verbB', 'subjectB'],
        },
        name: 'elements-world-home-search-command-box-steamside',
        hooks: {
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            requires: [CommandBoxElement, CommandHintWithVerbAndSubjectElement],
            lifecycle: {
                willUpdate: el => el.#on_willUpdate(),
            },
            events: [
                {
                    type: CommandBoxValue.eventTypeChanged,
                    listener: (el, event) => el.#on_changed_input_text_command_box_value(event),
                },
                {
                    selector: '.searched-recently',
                    listener: (el, event) => {
                        const commandBox = el.renderRoot
                            .querySelector('elements-command-box-steamside');
                        commandBox.input_text_command_box_value = event.target.textContent.trim();
                        commandBox.commandPlease();
                    },
                },
                /*
            {
                listener: (el, event) =>
                    (el.querySelector('elements-command-box-steamside')
                as CommandBoxElement)!
.set_input_text_command_box_value(event.target.textContent)


//selector: ".searched-recently",
            },
                 */
            ],
        }
    }; }
    #on_changed_input_text_command_box_value(event) {
        this.command_box_entered = event.detail.input_text_command_box_value;
        // FIXME Third command button: Windosill community hub
    }
    #on_willUpdate() {
        if (this.command_box_entered) {
            this.verbA = 'search';
            this.subjectA = this.command_box_entered;
            this.verbB = 'play first result for';
            this.subjectB = this.command_box_entered;
        }
        else {
            this.verbA = 'continue';
            this.subjectA = this.last_played_name_1;
            // FIXME only one continue? hide alternate
            this.verbB = 'continue';
            this.subjectB = this.last_played_name_2;
        }
        // FIXME no continues? exploreACommandModel, exploreBCommandModel
    }
}
Customary.declare(WorldHomeSearchCommandBoxElement);
//# sourceMappingURL=elements-world-home-search-command-box-steamside.js.map