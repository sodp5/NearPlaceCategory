package com.munny.nearplacecategory

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.munny.nearplacecategory.extensions.startActivity
import com.munny.nearplacecategory.ui.nearsearch.NearSearchActivity

class MilestoneActivity : AppCompatActivity() {
    private val list = arrayListOf(
        "Start MapActivity" to { startActivity<MapActivity>() },
        "Start NearSearchActivity" to { startActivity<NearSearchActivity>() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_milestone)

        findViewById<LinearLayout>(R.id.ll_container).let { container ->
            list.forEach { pair ->
                val button = MaterialButton(container.context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                    ).apply {
                        marginStart = 16
                        marginEnd = 16
                    }
                    isAllCaps = false

                    text = pair.first

                    setOnClickListener {
                        pair.second.invoke()
                    }
                }

                container.addView(button)
            }
        }
    }
}