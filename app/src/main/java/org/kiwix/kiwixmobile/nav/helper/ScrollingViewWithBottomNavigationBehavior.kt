/*
 * Kiwix Android
 * Copyright (c) 2020 Kiwix <android.kiwix.org>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.kiwix.kiwixmobile.nav.helper

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.kiwix.kiwixmobile.R

class ScrollingViewWithBottomNavigationBehavior(context: Context, attrs: AttributeSet) :
  AppBarLayout.ScrollingViewBehavior(context, attrs) {
  // We add a bottom margin to avoid the bottom navigation bar
  private var bottomMargin = 0

  override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean =
    super.layoutDependsOn(parent, child, dependency) || dependency is BottomNavigationView

  override fun onDependentViewChanged(
    parent: CoordinatorLayout,
    child: View,
    dependency: View
  ): Boolean {
    val result = super.onDependentViewChanged(parent, child, dependency)
    val readerBottomAppBar: BottomAppBar? = child.findViewById(R.id.bottom_toolbar)
    if (readerBottomAppBar != null) {
      val navBarYPosition = dependency.y
      val coordinatorHeight = parent.height
      val readerNavNewMargin = (coordinatorHeight - navBarYPosition).toInt()
      if (dependency is BottomNavigationView && readerNavNewMargin != bottomMargin) {
        bottomMargin = readerNavNewMargin
        val layout = readerBottomAppBar.layoutParams as ViewGroup.MarginLayoutParams
        layout.bottomMargin = bottomMargin
        readerBottomAppBar.requestLayout()
        return true
      }
    }
    return result
  }

  override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
    super.onDependentViewRemoved(parent, child, dependency)
  }
}
