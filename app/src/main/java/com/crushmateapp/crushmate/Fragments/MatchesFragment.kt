package com.crushmateapp.crushmate.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crushmateapp.crushmate.Adapters.ChatAdapter

import com.crushmateapp.crushmate.R
import com.crushmateapp.crushmate.activities.CallbackInterace
import com.crushmateapp.crushmate.util.Chat
import com.crushmateapp.crushmate.util.DATA_MATCHES
import com.crushmateapp.crushmate.util.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_matches.*

/**
 * A simple [Fragment] subclass.
 */
class MatchesFragment : Fragment() {

    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference
    private var callback: CallbackInterace? = null

    private lateinit var chatDatabase: DatabaseReference

    private val chatAdapter = ChatAdapter(ArrayList())

    fun setCallback(callback: CallbackInterace) {
        this.callback = callback
        userId = callback.onGetUserId()
        userDatabase = callback.getUserDatabase()
        chatDatabase = callback.getChatDatabase()
        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter =chatAdapter
        }
    }

    fun getData() {

        val chat = Chat(userId, "100100", "1002", "Hinduja brothers", "https://thumbor.forbes.com/thumbor/fit-in/416x416/filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F5c7269a031358e35dd2701d6%2F0x0.jpg%3Fbackground%3D000000%26cropX1%3D771%26cropX2%3D3428%26cropY1%3D11%26cropY2%3D2667")
        chatAdapter.addElement(chat)

        userDatabase.child(userId).child(DATA_MATCHES).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.hasChildren()) {
                    p0.children.forEach { child ->
                        val matchId = child.key
                        val chatId = child.value.toString()
                        if(!matchId.isNullOrEmpty()) {
                            userDatabase.child(matchId).addListenerForSingleValueEvent(object: ValueEventListener{
                                override fun onCancelled(p0: DatabaseError) {
                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                    val user = p0.getValue(User::class.java)
                                    if(user != null) {
                                        val chat = Chat(userId, chatId, user.uid, user.name, user.imageurl)
                                        chatAdapter.addElement(chat)
                                    }
                                }

                            })
                        }
                    }
                }
            }

        })
    }

}
