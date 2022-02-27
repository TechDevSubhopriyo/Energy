package com.techdevsubhopriyo.ijp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.get
import com.google.android.material.slider.Slider
import com.techdevsubhopriyo.ijp.databinding.ActivityMainBinding
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tl:TableLayout
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tl=binding.tbl

        var v: View =layoutInflater.inflate(R.layout.slide,null)
        v.findViewById<TextView>(R.id.t1).text="1"
        v.findViewById<TextView>(R.id.t2).text="100"
        tl.addView(v)

        check(0)
    }
    fun check(n:Int){
        var i:Int=tl.childCount
        while(i>n){
            val tr=tl.get(i-1)
            val row = tr.findViewById<Slider>(R.id.slr)

            row.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {
                    tr.findViewById<TextView>(R.id.t1).text="${slider.valueFrom.roundToInt()}"
                    tr.findViewById<TextView>(R.id.t2).text="${slider.value.roundToInt()}"
                }

                override fun onStopTrackingTouch(slider: Slider) {
                    if((slider.valueTo.roundToInt()-slider.value.roundToInt()==1
                                && slider.value.roundToInt()!=slider.valueFrom.roundToInt())||
                        slider.value.roundToInt()-slider.valueFrom.roundToInt()==1){

                        Toast.makeText(
                            this@MainActivity,
                            "Min length of segment is 2",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    tr.findViewById<TextView>(R.id.t1).text="${slider.valueFrom.roundToInt()}"
                    tr.findViewById<TextView>(R.id.t2).text="${slider.value.roundToInt()}"
                    if(slider.value.roundToInt()==1){
                        tl.removeAllViews()
                        var v: View =layoutInflater.inflate(R.layout.slide,null)
                        v.findViewById<TextView>(R.id.t1).text="1"
                        v.findViewById<TextView>(R.id.t2).text="100"
                        tl.addView(v)
                        check(0)
                        return
                    }
                    else if(slider.value.roundToInt()==slider.valueFrom.roundToInt()){
                        tl.removeView(tr)
                        check(tl.childCount-1)
                        return
                    }
                    else if(slider.value.roundToInt()!=slider.valueTo.roundToInt()){
                        var v: View =layoutInflater.inflate(R.layout.slide,null)
                        v.findViewById<TextView>(R.id.t1).text="${slider.value.roundToInt()+1}"
                        v.findViewById<TextView>(R.id.t2).text="${slider.valueTo.roundToInt()}"
                        v.findViewById<Slider>(R.id.slr).valueFrom=slider.value+1
                        v.findViewById<Slider>(R.id.slr).valueTo=slider.valueTo
                        v.findViewById<Slider>(R.id.slr).value=slider.valueTo
                        tl.addView(v)

                        tr.findViewById<TextView>(R.id.t1).text="${slider.valueFrom.roundToInt()}"
                        tr.findViewById<TextView>(R.id.t2).text="${slider.value.roundToInt()}"


                        slider.valueTo= slider.value
                        check(tl.childCount-1)
                        return
                    }
                }
            })
            i--
        }
    }
}