package com.volie.instagramclonecompose.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.volie.instagramclonecompose.data.model.post.PostData
import com.volie.instagramclonecompose.data.model.user.UserData
import com.volie.instagramclonecompose.util.Constant.POSTS
import com.volie.instagramclonecompose.util.Constant.USERS
import com.volie.instagramclonecompose.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class IgViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val popupNotification = mutableStateOf<Event<String>?>(null)

    init {
//        auth.signOut()
        val currentUser = auth.currentUser
        signedIn.value = currentUser != null
        currentUser?.uid?.let { uid ->
            getUserData(uid)
        }
    }

    fun onSignup(username: String, email: String, pass: String) {

        if (username.isEmpty() or email.isEmpty() or pass.isEmpty()) {
            handleException(customMessage = "Please fill in all fields")
            return
        }

        inProgress.value = true

        db.collection(USERS).whereEqualTo("username", username).get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    handleException(customMessage = "Username already exist!")
                    inProgress.value = false
                } else {
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            signedIn.value = true
                            createOrUpdateProfile(username = username)
                        } else {
                            handleException(task.exception, customMessage = "Signup Failed")
                        }
                        inProgress.value = false
                    }
                }
            }.addOnFailureListener { }
    }

    fun onLogin(email: String, pass: String) {
        if (email.isEmpty() or pass.isEmpty()) {
            handleException(customMessage = "Please fill in all fields")
            return
        }
        inProgress.value = true

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                signedIn.value = true
                inProgress.value = false
                auth.currentUser?.uid?.let { uid ->
//                        handleException(customMessage = "Login successfully")
                    getUserData(uid = uid)
                }
            } else {
                handleException(task.exception, "Login failed!")
                inProgress.value = false
            }
        }.addOnFailureListener { exception ->
            handleException(exception = exception, "Login failed!")
            inProgress.value = false
        }
    }

    private fun createOrUpdateProfile(
        name: String? = null,
        username: String? = null,
        bio: String? = null,
        imageUrl: String? = null
    ) {
        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            username = username ?: userData.value?.username,
            bio = bio ?: userData.value?.bio,
            imageUrl = imageUrl ?: userData.value?.imageUrl,
            following = userData.value?.following
        )

        uid?.let { uid ->
            inProgress.value = true
            db.collection(USERS).document(uid).get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    documentSnapshot.reference.update(userData.toMap()).addOnSuccessListener {
                        this.userData.value = userData
                        inProgress.value = false
                    }.addOnFailureListener {
                        handleException(it, "Cannot update user")
                        inProgress.value = false
                    }
                } else {
                    db.collection(USERS).document(uid).set(userData)
                    getUserData(uid = uid)
                    inProgress.value = false
                }
            }.addOnFailureListener { exception ->
                handleException(exception = exception, "Cannot create user")
                inProgress.value = false
            }
        }
    }

    private fun getUserData(uid: String) {
        inProgress.value = true
        db.collection(USERS).document(uid).get().addOnSuccessListener {
            val user = it.toObject<UserData>()
            userData.value = user
            inProgress.value = false
        }.addOnFailureListener { exception ->
            handleException(exception = exception, "Cannot retrieve user data")
            inProgress.value = false
        }
    }

    fun handleException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMsg else "$customMessage $errorMsg"
        popupNotification.value = Event(message)
    }

    fun updateProfileData(name: String, username: String, bio: String) {
        createOrUpdateProfile(
            name = name, username = username, bio = bio
        )
    }

    private fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit) {
        inProgress.value = true

        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageRef.child("image/$uuid") // folder
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener(onSuccess)
        }.addOnFailureListener { exception ->
            handleException(exception = exception)
            inProgress.value = false
        }
    }

    fun uploadProfileImage(uri: Uri) {
        uploadImage(uri = uri) {
            createOrUpdateProfile(imageUrl = it.toString())
        }
    }

    fun onLogout() {
        auth.signOut()
        signedIn.value = false
        userData.value = null
        popupNotification.value = Event(content = "Logged out")
    }

    fun onNewPost(uri: Uri, description: String, onPostSuccess: () -> Unit) {
        uploadImage(uri = uri) {
            onCreatePost(
                imageUri = it, description = description, onPostSuccess = onPostSuccess
            )
        }
    }

    private fun onCreatePost(imageUri: Uri, description: String, onPostSuccess: () -> Unit) {

        inProgress.value = true
        val currentUid = auth.currentUser?.uid
        val currentUsername = userData.value?.username
        val currentUserImage = userData.value?.imageUrl

        if (currentUid != null) {
            val postUuid = UUID.randomUUID().toString()
            val post = PostData(
                postId = postUuid,
                userId = currentUid,
                username = currentUsername,
                userImage = currentUserImage,
                postImage = imageUri.toString(),
                postDescription = description,
                time = System.currentTimeMillis()
            )

            db.collection(POSTS).document(postUuid).set(post).addOnSuccessListener {
                popupNotification.value = Event(content = "Post successfully created")
                inProgress.value = false
                onPostSuccess.invoke()
            }.addOnFailureListener { exception ->
                handleException(exception, "Unable to create post")
                inProgress.value = false
            }
        } else {
            handleException(customMessage = "Error username unavailable. Unable to create post")
            onLogout()
            inProgress.value = false
        }
    }
}