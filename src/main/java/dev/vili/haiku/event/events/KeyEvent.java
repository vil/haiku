/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.event.events;

import dev.vili.haiku.event.Event;

public class KeyEvent extends Event {
    private final int key;
    private final int code;
    private final Status status;

    public KeyEvent(int key, int code, Status status) {
        this.key = key;
        this.code = code;
        this.status = status;
    }

    /**
     * Gets the key.
     * @return
     */
    public int getKey() {
        return key;
    }

    /**
     * Gets the key code.
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * Event enums.
     */
    public enum Status {
        PRESSED,
        RELEASED
    }
}
