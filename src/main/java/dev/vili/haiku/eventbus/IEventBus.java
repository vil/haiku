/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.eventbus;

public interface IEventBus {
    void register(Object registerClass);

    void unregister(Object registerClass);

    void post(HaikuEvent event);
}
