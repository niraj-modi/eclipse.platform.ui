/*******************************************************************************
 * Copyright (c) 2006 The Pampered Chef and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     The Pampered Chef - initial API and implementation
 ******************************************************************************/

package org.eclipse.jface.examples.databinding.compositetable.timeeditor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.examples.databinding.compositetable.day.internal.CalendarableEventControl;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * This class represents an event that can be displayed on a calendar.
 * 
 * @since 3.2
 */
public class Calendarable {
	
	/**
	 * A comparator for Calenarable objects
	 */
	public static final Comparator comparator = new Comparator() {
		public int compare(Object c1, Object c2) {
			Calendarable cal1 = (Calendarable) c1;
			Calendarable cal2 = (Calendarable) c2;
			if (cal1.isAllDayEvent()) {
				if (cal2.isAllDayEvent()) {
					return 0;
				}
				return -1;
			}
			if (cal2.isAllDayEvent()) {
				return 1;
			}
			return cal1.getStartTime().compareTo(cal2.getStartTime());
		}
	};
	
	private boolean allDayEvent = false;
	
	/**
	 * Returns if this Calenderable represents an all-day event.
	 * 
	 * @return true if this is an all-day event; false otherwise.
	 */
	public boolean isAllDayEvent() {
		return allDayEvent;
	}
	
	/**
	 * Sets if this Calenderable represents an all-day event.
	 * 
	 * @param allDayEvent true if this is an all-day event; false otherwise.
	 */
	public void setAllDayEvent(boolean allDayEvent) {
		this.allDayEvent = allDayEvent;
	}
	
	private Date startTime = null;
	
	/**
	 * Gets the event's start time.  This value is ignored if this is an all-day event.
	 * 
	 * @return the start time for the event.
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Sets the event's start time.  This value is ignored if this is an all-day event.
	 * 
	 * @param startTime the event's start time.
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	private Date endTime = null;


	/**
	 * Returns the event's end time.  This value is ignored if this is an all-day event.
	 * 
	 * @return the event's end time.  This value is ignored if this is an all-day event.
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Sets the event's end time.  This value is ignored if this is an all-day event.
	 * 
	 * @param endTime the event's end time.  This value is ignored if this is an all-day event.
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private Image image;

	/**
	 * Return the IEvent's image or <code>null</code>.
	 * 
	 * @return the image of the label or null
	 */
	public Image getImage() {
		return this.image;
	}

	/**
	 * Set the IEvent's Image.
	 * The value <code>null</code> clears it.
	 * 
	 * @param image the image to be displayed in the label or null
	 * 
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	private String text = null;

	/**
	 * Returns the widget text.
	 * <p>
	 * The text for a text widget is the characters in the widget, or
	 * an empty string if this has never been set.
	 * </p>
	 *
	 * @return the widget text
	 *
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the contents of the receiver to the given string. If the receiver has style
	 * SINGLE and the argument contains multiple lines of text, the result of this
	 * operation is undefined and may vary from platform to platform.
	 *
	 * @param string the new text
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the string is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 */
	public void setText(String string) {
		this.text = string;
	}
	
	/**
	 * Reset the event object to its default state.  This method does not
	 * dispose any Color or Image objects that may be set into it.
	 */
	public void reset() {
		text = null;
		startTime = null;
		endTime = null;
		image = null;
		upperLeftPositionInDayRowCoordinates = null;
		lowerRightPositionInDayRowCoordinates = null;
	}

	/**
	 * Disposes of the operating system resources associated with
	 * the receiver and all its descendents. After this method has
	 * been invoked, the receiver and all descendents will answer
	 * <code>true</code> when sent the message <code>isDisposed()</code>.
	 * Any internal connections between the widgets in the tree will
	 * have been removed to facilitate garbage collection.
	 *
	 * @see #addDisposeListener
	 * @see #removeDisposeListener
	 */
	public void dispose() {
		fireDisposeEvent();
	}
	
	private List disposeListeners = new ArrayList();

	private void fireDisposeEvent() {
		for (Iterator disposeListenerIter = disposeListeners.iterator(); disposeListenerIter.hasNext();) {
			DisposeListener listener = (DisposeListener) disposeListenerIter.next();
			listener.widgetDisposed(this);
		}
	}

	/**
	 * Adds the listener to the collection of listeners who will
	 * be notifed when the widget is disposed. When the widget is
	 * disposed, the listener is notified by sending it the
	 * <code>widgetDisposed()</code> message.
	 *
	 * @param listener the listener which should be notified when the receiver is disposed
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
	 * </ul>
	 *
	 * @see DisposeListener
	 * @see #removeDisposeListener
	 */
	public void addDisposeListener(DisposeListener listener) {
		disposeListeners.add(listener);
	}

	/**
	 * Removes the listener from the collection of listeners who will
	 * be notifed when the widget is disposed.
	 * @param listener the listener which should no longer be notified when the receiver is disposed
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 *
	 * @see DisposeListener
	 * @see #addDisposeListener
	 */	
	public void removeDisposeListener(DisposeListener listener) {
		disposeListeners.remove(listener);
	}
	
	private Point upperLeftPositionInDayRowCoordinates = null;
	

	/**
	 * @return Returns the upperLeftPositionInDayRowCoordinates.
	 */
	public Point getUpperLeftPositionInDayRowCoordinates() {
		return upperLeftPositionInDayRowCoordinates;
	}

	/**
	 * Sets the upper left position of the bounding box and initializes the
	 * lower right position to be the same as the upper left.
	 * 
	 * @param upperLeftPositionInDayRowCoordinates The upperLeftPositionInDayRowCoordinates to set.
	 */
	public void setUpperLeftPositionInDayRowCoordinates(
			Point upperLeftPositionInDayRowCoordinates) {
		this.upperLeftPositionInDayRowCoordinates = upperLeftPositionInDayRowCoordinates;
		this.lowerRightPositionInDayRowCoordinates = upperLeftPositionInDayRowCoordinates;
	}
	
	private Point lowerRightPositionInDayRowCoordinates = null;

	/**
	 * @return Returns the lowerRightPositionInDayRowCoordinates.
	 */
	public Point getLowerRightPositionInDayRowCoordinates() {
		return lowerRightPositionInDayRowCoordinates;
	}

	/**
	 * Sets the lower right position of the bounding box.
	 * 
	 * @param lowerRightPositionInDayRowCoordinates The lowerRightPositionInDayRowCoordinates to set.
	 */
	public void setLowerRightPositionInDayRowCoordinates(
			Point lowerRightPositionInDayRowCoordinates) {
		this.lowerRightPositionInDayRowCoordinates = lowerRightPositionInDayRowCoordinates;
	}

	private CalendarableEventControl control = null;
	
	/**
	 * Returns the UI control for this Calendarable.
	 * 
	 * @return The UI control for this Calendarable or null if there is none.
	 */
	public CalendarableEventControl getControl() {
		return control;
	}

	/**
	 * Set the UI control for this Calendarable.
	 * 
	 * @param control The control to set.
	 */
	public void setControl(CalendarableEventControl control) {
		this.control = control;
	}
}


