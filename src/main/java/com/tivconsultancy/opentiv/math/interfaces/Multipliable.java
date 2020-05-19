/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */
package com.tivconsultancy.opentiv.math.interfaces;

/**
 *
 * @author Thomas Ziegenhein
 */
public interface Multipliable<T> {
    public T mult(Double d);
    public T mult2(Double d, String sType);
}
