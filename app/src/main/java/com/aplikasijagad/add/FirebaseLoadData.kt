package com.aplikasijagad.add

import com.aplikasijagad.models.Users

interface FirebaseLoadData {
    fun onFirebaseLoadSuccess(kurirList: List<Users>)
    fun onFirebaseLoadFailed(message:String)
}