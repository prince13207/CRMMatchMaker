package com.crushmateapp.crushmate.activities

import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aykuttasil.callrecord.CallRecord
import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.util.DATA_USERS
import com.crushmateapp.crushmate.util.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*


class UserDetailActivity : AppCompatActivity() {
    private lateinit var callRecord: CallRecord


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        callRecord = CallRecord.Builder(this)
            .setLogEnable(true)
            .setRecordFileName("CallRecorderTestFile")
            .setRecordDirName("CallRecorderTest")
            .setRecordDirPath(Environment.getExternalStorageDirectory().getPath())
            .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
            .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
            .setShowSeed(true)
            .build();

        callRecord.enableSaveFile();

        callRecord.startCallRecordService();


        val id = intent.getStringExtra("idpassed")
        if(id.isNullOrEmpty()) {
            finish()
        }
        val userDatabase = FirebaseDatabase.getInstance().reference.child(DATA_USERS)
        userDatabase.child(id).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)

                var temp2= User("1002", "Hinduja brothers", "62", "Hinduja.brothers@gmail.com",
                    "male",
                    "female",
                    "https://thumbor.forbes.com/thumbor/fit-in/416x416/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5c7269a031358e35dd2701d6%2F0x0.jpg%3Fbackground%3D000000%26cropX1%3D771%26cropX2%3D3428%26cropY1%3D11%26cropY2%3D2667")


                detailtv.text = temp2?.name

                if(temp2?.imageurl != null && temp2?.imageurl != "") {

                    Picasso.get().load(temp2.imageurl).into(detailiv)
                }
            }

        })

    }


    fun startCall(view: View){
        callRecord.startCallReceiver();

        val intent = Intent(Intent.ACTION_CALL);
        intent.data = Uri.parse("tel:8600505178")
        startActivity(intent)

    }


    fun stopCall(view: View){
        callRecord.stopCallReceiver()
    }



}

