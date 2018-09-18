package com.sukowidodo.controlsuara

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.widget.Toast
import com.sukowidodo.controlsuara.model.Result
import com.sukowidodo.controlsuara.retrofit.RetroInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import com.sukowidodo.controlsuara.retrofit.RetroClient
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private val REQ_CODE_SPEECH_INPUT = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAction.setOnClickListener { v: View? ->
            promptSpeechInput()
        }

    }

    fun promptSpeechInput(){
        intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.speech_prompt))
        startActivityForResult(intent,REQ_CODE_SPEECH_INPUT)
    }

    fun DoAction(name:String, value:Int) {
        val service: RetroInterface = RetroClient.getRetrofitInstance().create(RetroInterface::class.java)
        service.Action(name,value)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::HandleResponse,
                        this::HandleError
                )
    }

    fun HandleResponse(result: Result){
        Toast.makeText(this,"Berhasil",Toast.LENGTH_LONG).show()
    }

    fun HandleError(error:Throwable){
        Log.e("Error",error.message)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQ_CODE_SPEECH_INPUT->
                if (resultCode== Activity.RESULT_OK && null!=data) run {
                    var result: ArrayList<String> = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val D1 = "4"
                    val D2 = "5"
                    when(result[0]){
                        "nyalakan lampu satu"-> DoAction(D1,1)
                        "matikan lampu satu"->DoAction(D1,0)
                        "nyalakan lampu 2"-> DoAction(D2,1)
                        "matikan lampu 2"->DoAction(D2,0)
                        "turn on the light One"->DoAction(D1,1)
                        "turn on the light 2"->DoAction(D2,1)
                        "turn off the light One"->DoAction(D1,0)
                        "turn off the light 2"->DoAction(D2,0)
                    }
                    tvCoba.setText("OK.."+result[0])
                }
        }
    }
}
