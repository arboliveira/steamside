import {LitElement} from "lit";

export class Sideshow {
    #performers: LitElement[] = [];
    #performers_ready = 0;

    showtime(element: LitElement) {
        const performers = [...element.renderRoot.querySelectorAll('.sideshow-performer')];
        this.#performers.push(...performers as LitElement[]);
    }

    static customary_dispatchEvent_Customary_lifecycle_firstUpdated(sender: Element) {
        sender.dispatchEvent(
            new CustomEvent(
                'CustomaryEvent:lifecycle:firstUpdated',
                {
                    detail: {dispatcher: sender},
                    composed: true,
                    bubbles: true,
                }
            )
        );
    }

    on_firstUpdated_Event(event: CustomEvent) {
        const dispatcher = event.detail.dispatcher;
        if (!this.#performers.includes(dispatcher)) return;
        this.#performers_ready++;
        if (this.#performers_ready !== this.#performers.length) return;
        let left = true;
        for (const performer of this.#performers) {
            left = !left;
            const segment = performer.renderRoot.querySelector('.segment')!;
            Sideshow.#perform(segment, left ? "left" : "right");
        }
    }

    static #perform(segment: Element, side: string) {
        const header = segment.querySelector('.side-header')!;
        header.classList.remove('side-header-at-left');
        header.classList.remove('side-header-at-right');
        const content = segment.querySelector('.content')!;
        content.classList.remove('content-at-right');
        content.classList.remove('content-at-left');
        if (side === "left") {
            header.classList.add('side-header-at-left');
            content.classList.add('content-at-right');
            segment.classList.add('animated', 'fadeInRight', 'steamside-animated');
        } else {
            header.classList.add('side-header-at-right');
            content.classList.add('content-at-left');
            segment.classList.add('animated', 'fadeInLeft', 'steamside-animated');
        }
    }
}