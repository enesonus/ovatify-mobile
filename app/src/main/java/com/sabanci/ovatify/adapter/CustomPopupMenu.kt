import android.content.Context
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.sabanci.ovatify.R

interface OnPopupMenuDismissListener {
    fun onDismiss(selectedItem: String?)
}
class CustomPopupMenu(private val context: Context, private val anchorView: View) {

    private var onDismissListener: OnPopupMenuDismissListener? = null

    fun setOnDismissListener(listener: OnPopupMenuDismissListener) {
        onDismissListener = listener
    }

    fun showPopupMenu(dataList: List<String>) {
        val popupMenu = PopupMenu(ContextThemeWrapper(context, R.style.PopupMenuStyle), anchorView)

        for ((index, data) in dataList.withIndex()) {
            popupMenu.menu.add(0, index, index, data)
        }

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            val selectedItem = item.title.toString()
            onDismissListener?.onDismiss(selectedItem)
            true
        }

        popupMenu.show()
    }
}
