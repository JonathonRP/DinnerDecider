package com.company.thecreater.dinnerdecider

import android.app.Activity
import android.support.annotation.NonNull
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.util.*
import com.firebase.ui.auth.AuthUI


private const val RC_SIGN_IN: Int = 123
internal val auth by lazy { FirebaseAuth.getInstance() }
private lateinit var authListener: FirebaseAuth.AuthStateListener
private val db by lazy { FirebaseFirestore.getInstance() }
private lateinit var register : ListenerRegistration
internal val foods = mutableListOf<Food>().toSortedSet(compareBy { it.food })
internal lateinit var oldFood: Food

open class Kitchen {

    constructor()

    constructor(caller: Activity): this() {
        authListener = FirebaseAuth.AuthStateListener {
            if (auth.currentUser == null) {
                signIn(caller)
            }
        }
    }

    init {

        FirebaseFirestoreSettings
                .Builder().setTimestampsInSnapshotsEnabled(true).build()

        register = db.collection("foods")
                .addSnapshotListener( EventListener<QuerySnapshot> { snapshots, e ->
                    if (e != null) {
                        return@EventListener
                    }

                    for(doc in snapshots!!.documentChanges) {
                        when(doc.type) {
                            DocumentChange.Type.ADDED -> {
                                val food = doc.document.toObject(Food::class.java)
                                        .withId(doc.document.id)
                                foods.add(food)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val food = doc.document.toObject(Food::class.java)
                                        .withId(doc.document.id)
                                foods.remove(oldFood)
                                foods.add(food)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val food = doc.document.toObject(Food::class.java)
                                        .withId(doc.document.id)
                                foods.remove(food)
                            }
                        }
                    }
                })
    }

    private fun signIn(caller: Activity) {
        // Choose authentication providers
        val providers = Arrays.asList(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.mipmap.ic_launcher)
                        .setTheme(R.style.AppTheme)
                        .build(),
                RC_SIGN_IN)
    }

    fun attachAuthListener() {
        auth.addAuthStateListener(authListener)
    }

    companion object: Kitchen() {

        fun add (food : String) : Task<DocumentReference> {

            val newFood = Food(food).toMap()

            return db.collection("foods")
                        .add(newFood)
        }

        fun update (id: String, food: String) : Task<Void> {

            val newFood = Food(food).toMap()

            return db.collection("foods").document(id)
                    .update(newFood)
        }

        fun delete (id: String) : Task<Void> {

            return  db.collection("foods").document(id)
                    .delete()
        }

        fun loadFoods() : Task<QuerySnapshot> {

            return db.collection("foods")
                    .get()
                    .addOnSuccessListener { docs ->
                        if (foods.size > 0) {
                            foods.clear()
                        }

                        for (doc in docs) {

                            val food = doc.toObject(Food::class.java)
                                    .withId(doc.id)
                            foods.add(food)
                        }
                    }
        }

        fun signOut(caller: Activity) {
            AuthUI.getInstance()
                    .signOut(caller)
                    .addOnCompleteListener {
                        Kitchen(caller).attachAuthListener()
                    }
        }

        fun detachAuthListener() {
            auth.removeAuthStateListener(authListener)
        }

        fun stopListening() {
            register.remove()
        }
    }
}

class Food() {
    lateinit var id : String
    lateinit var food: String

    constructor(food: String): this() {
        this.food = food
    }

    fun toMap(): Map<String, Any> {
        val result: HashMap<String, Any> = HashMap()
        result["food"] = this.food
        return result
    }

    fun withId(@NonNull id: String) : Food {
        this.id = id
        return this
    }
}