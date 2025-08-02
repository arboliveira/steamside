import {Customary, CustomaryElement} from "#customary";
import {CustomaryDeclaration} from "#customary";

import {
    CommandHintWithVerbAndSubjectElement
} from "#steamside/elements-command-hint-with-verb-and-subject-steamside.js";

import {ConfirmPlease} from "#steamside/elements/command-box/ConfirmPlease.js";
import {CommandPlease} from "#steamside/elements/command-box/CommandPlease.js";
import {CommandAlternatePlease} from "#steamside/elements/command-box/CommandAlternatePlease.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {CommandBoxValue} from "#steamside/elements/command-box/CommandBoxValue.js";

export class CommandBoxElement extends CustomaryElement
{
    static customary: CustomaryDeclaration<CommandBoxElement> =
    {
        name: 'elements-command-box-steamside',
        config: {
            attributes: [
                'input_text_command_box_value',
                'command_label_text',
                'command_label_visible',
                'placeholder_text',
                'is_textarea',
            ],
            state: [
                'command_a_button_face',
                'command_b_button_face',
                'command_c_button_face',
                'command_confirm_visible',
                'command_confirm_what_text',
                'command_trouble_visible',
            ],
        },
        values: {
            '____input_text_command_box_value': '',
            'command_label_text': 'Command label',
            'command_confirm_visible': false,
            'command_confirm_what_text': 'Yes, do it already',
            'command_trouble_visible': false,
        },
        hooks: {
            requires: [
                CommandHintWithVerbAndSubjectElement,
            ],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            changes: {
                'input_text_command_box_value': (el, a) =>
                    el.#on_changed_input_text_command_box_value(a),
            },
            events: [
                {
                    type: 'keydown',
                    selector: 'input, textarea', // #input-text-command-box, 
                    listener: (el, e) => el.#event_keydown_input(<KeyboardEvent>e),
                },
                /*
                {
                    type: 'keyup',
                    selector: '#input-text-command-box',
                    listener: (el, e) => el.#event_keyup_input(e),
                },
                 */
                {
                    type: 'click',
                    selector: '#command-button',
                    listener: (el) => el.#doCommand(),
                },
                {
                    type: 'click',
                    selector: '#command-button-alternate',
                    listener: (el) => el.#doCommandAlternate(),
                },
                {
                    type: 'click',
                    selector: '#command-button-confirm',
                    listener: (el) => el.#doCommandConfirm(),
                },
            ],
            lifecycle: {
                willUpdate: (el) => el.#on_willUpdate(),
                updated: (el) => el.#on_updated(),
            }
        }
    }
    declare next_render_do_focus: boolean;
    declare input_text_command_box_value: string;
    declare command_confirm_visible: boolean;
    declare command_a_button_face: string;
    declare command_b_button_face: string;
    declare is_textarea: string;

    #on_updated() {
        if (this.next_render_do_focus) {
            this.next_render_do_focus = false;
            let inputQueryEl: HTMLInputElement = this.input_query_el()!;
            inputQueryEl.focus();
            inputQueryEl.select();
        }
    }

    #on_willUpdate() {
        const plain = !this.is_textarea;
        if (plain) {
            this.command_a_button_face = 'Enter';
            this.command_b_button_face = 'Ctrl+Enter';
        }
        else {
            this.command_a_button_face = 'Ctrl+Enter';
            this.command_b_button_face = 'Ctrl+Shift+Enter';
        }
    }

    #on_changed_input_text_command_box_value(a: string) {
        Skyward.fly<CommandBoxValue.ChangedDetail>(
            this,
            {
                type: CommandBoxValue.eventTypeChanged,
                detail: {
                    input_text_command_box_value: this.#trim(a),
                }
            }
        );
    }

    #doCommand()
    {
        this.commandPlease();
    }

    commandPlease() {
        Skyward.fly<CommandPlease.EventDetail>(this, {type: CommandPlease.eventType, detail: {
                input_text_command_box_value: this.#trim(this.input_text_command_box_value),
            }});
    }

    #doCommandAlternate()
    {
        Skyward.fly<CommandAlternatePlease.EventDetail>(this,
            {type: CommandAlternatePlease.eventType, detail: {
                input_text_command_box_value: this.#trim(this.input_text_command_box_value),
            }});
    }
    
    #doCommandConfirm()
    {
        Skyward.fly<ConfirmPlease.EventDetail>(
            this,
            {type: ConfirmPlease.eventType, detail: {
                input_text_command_box_value: this.#trim(this.input_text_command_box_value),
            }}
        )
    }

    #trim(s: string) {
        return s?.trim() || '';
    }

    #event_keyup_input(e: Event) {
        // TODO figure out why hints break if this event is removed
        e.preventDefault();
        this.input_text_command_box_value = (e.target as HTMLInputElement).value;
    }

    #event_keydown_input(e: KeyboardEvent) {
        if (e.code === 'Enter') {
            this.#on_EnterKeyPressed(e);
        }
    }
    
    #on_EnterKeyPressed(e: KeyboardEvent) {
        const plain = !this.is_textarea;

        if (plain) {
            if (!e.ctrlKey && !e.shiftKey) {
                e.preventDefault();
                this.#doCommand();
                return;
            }

            if (e.ctrlKey && !e.shiftKey) {
                e.preventDefault();
                this.#doCommandAlternate();
                return;
            }

            if (!e.ctrlKey && e.shiftKey) {
                e.preventDefault();
                this.#doCommandConfirm();
                return;
            }
        }
        else {
            if (e.ctrlKey && !e.shiftKey) {
                e.preventDefault();
                this.#doCommand();
                return;
            }

            if (e.ctrlKey && e.shiftKey) {
                e.preventDefault();
                this.#doCommandAlternate();
                return;
            }

            if (!e.ctrlKey && e.shiftKey) {
                e.preventDefault();
                this.#doCommandConfirm();
                return;
            }
        }
    }

    input_query_el(): HTMLInputElement {
        return this.renderRoot.querySelector('.command-text-input')!;
    }

    set_input_text_command_box_value(value: string)
    {
        // TODO broken, write test. value is set but input is not updated
        this.input_text_command_box_value = value;
    }

    showCommandConfirm() {
        this.command_confirm_visible = true;
    }
    
    focus_on_input() {
        this.next_render_do_focus = true;
        this.requestUpdate();
    }
}
Customary.declare(CommandBoxElement);
