
/*
 * Copyright 2013 Marc Wiedenhoeft - GliblyBits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.gliblybits.bitsengine.gui.widgets.listener;

import net.gliblybits.bitsengine.gui.widgets.BitsButtonWidget;

/**
 * Interface for pressing buttons. ;)<br>
 * <br>
 * @author Marc Wiedenhoeft - GliblyBits
 * @version 0.1 - Streak
 * @since 0.1 - Streak
 */
public interface BitsButtonListener {
	/**
	 * This method will be called through this interface,<br>
	 * if the corresponding button was pressed.<br>
	 * <br>
	 * @param button - The pressed button
	 */
	public boolean onButtonPressed( final int id, final BitsButtonWidget button );
}
