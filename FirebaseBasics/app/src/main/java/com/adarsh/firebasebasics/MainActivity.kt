package com.adarsh.firebasebasics

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.*
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    val RC_SIGN_IN:Int = 1102
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val i = 0/0 for crashing the app, to get analytics.
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.log("my message")
        if(auth.currentUser != null){
            firebaseDatabaseCalled()
        }else{
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(
                        Arrays.asList(
                            GoogleBuilder().build(),
                            EmailBuilder().build(),
                            PhoneBuilder().build()
                        )
                    )
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if(requestCode == RC_SIGN_IN){
                if(resultCode == RESULT_OK){
                    firebaseDatabaseCalled()
                }
            } else{
                val response = IdpResponse.fromResultIntent(data)
                //Sign In failed.
                if(response == null){
                    return;
                }
                if(response.error?.errorCode == ErrorCodes.NO_NETWORK){
                    //show no network
                    return;
                }
                //show unkown error
            }
    }

    fun firebaseDatabaseCalled(){
        val dbRef = FirebaseDatabase.getInstance().reference //takes reference of root node.
        val notebook = ArrayList<String>()
//        val arrayAdapter = ArrayAdapter<String>(this, R.layout.item_row, R.id.textView2, notebook)
//        myNotes.adapter = arrayAdapter
        val notebook2 = mutableListOf<Note>()
        val adapter = NotesAdapter(notebook2)
        myrecycler.layoutManager = LinearLayoutManager(this)
        myrecycler.adapter = adapter

        //write to Database.
        btn.setOnClickListener {
            val notesInString = Notes.text.toString()
            //Upload Notes to database.

            //This below line will overwrite the earlier values.
            // ->  FirebaseDatabase.getInstance().reference.setValue(notesInString)  //Gets the reference to the root node.
            // To not overwrite the values, add push() to the line
            // -> FirebaseDatabase.getInstance().reference.push().setValue(notesInString)

            //Adding childs to node as "save notes" and "todo"
            //dbRef.child("Save Node").push().setValue(notesInString)
            //dbRef.child("Todo").push().setValue(notesInString)
            //push() -> always creates a new ID for the reference, no matter what.

            //Adding Class objects to database in form of Key-Value pair (Only primitive types or error)
            val title = Notes.text.toString()
            val subtitle = subtitle_text.text.toString()

            val n = Note(title, subtitle)
            auth.currentUser?.uid.toString()
                .let { it1 -> dbRef.child("Save Node").child(it1).push().setValue(n) }

        }

        //Read to Database.

        dbRef.child("Save Node").child(auth.currentUser?.uid.toString()).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //Called when a new data node is inserted in "Save Node".
                //val gettingString = snapshot.getValue(String::class.java) //For getting Primitive
                /*
                if (gettingString != null) {
                    notebook.add(gettingString)
                    arrayAdapter.notifyDataSetChanged()
                } */
                val objectNote = snapshot.getValue(Note::class.java) //For getting POJO
                notebook2.add(objectNote!!)
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                //An existing node is modified
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                //When data at subnode is removed.
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                //When position of subnode is changed.
            }

            override fun onCancelled(error: DatabaseError) {
                //When Read operation fails.
            }
        })

        //Value Event Listener, whenever there is change in database, it will give the whole value
        //of database at once. While childEvent listener will give the recent added one.
        dbRef.child("Todo").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Gets the entire database regardless of operation.
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}