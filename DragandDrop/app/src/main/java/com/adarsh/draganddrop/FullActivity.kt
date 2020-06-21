package com.adarsh.draganddrop

import android.content.ClipData
import android.content.ClipDescription
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.marginLeft
import kotlinx.android.synthetic.main.activity_full.*

class FullActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full)

        relative.setOnDragListener(dragListener)
        button.setOnLongClickListener {
            val clipText = "This is our ClipData Text"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }

    }

    val dragListener = View.OnDragListener { view, dragEvent ->
        when(dragEvent.action){
            DragEvent.ACTION_DRAG_STARTED -> {
                dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> true
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                val item = dragEvent.clipData.getItemAt(0)
                val dragData = item.text
                //Toast.makeText(baseContext, dragData, Toast.LENGTH_SHORT).show()

                Toast.makeText(baseContext, " ${dragEvent.x} and ${dragEvent.y} ", Toast.LENGTH_SHORT).show()
                view.invalidate()

                var v= dragEvent.localState as View

                val owner = v.parent as ViewGroup
                owner.removeView(v)
                val destination = view as RelativeLayout

                destination.addView(v)
                v.x = dragEvent.x - (v.width/2)
                v.y = dragEvent.y - (v.height/2)
                v.visibility = View.VISIBLE

                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                true
            }
            else -> false
        }
    }
}