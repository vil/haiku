/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HaikuSubscribe Annotation.
 * <p>
 * This annotation is used to subscribe methods to events in the Haiku event bus system. It allows
 * targeting only methods to avoid misplacement. It is annotated with @Retention to let JVM know
 * that this annotation should be kept at runtime. Default retention policy is RUNTIME.
 * <p>
 * This annotation has an attribute 'lambda'. Its default value is 'false'. This attribute
 * indicates whether the annotated method is a lambda function or not.
 * <p>
 * Note: For this annotation to work correctly, it needs to be processed by the Haiku EventBus
 * system. This processing includes reflective operations that could affect the performance of
 * your application if used inappropriately.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HaikuSubscribe {
    boolean lambda() default false;
}
