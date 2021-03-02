package com.aplikasijagad.add

import com.aplikasijagad.models.Driver
import com.aplikasijagad.models.Users

interface FirebaseLoadData {
    fun onFirebaseLoadSuccess(kurirList: List<Driver>)
    fun onFirebaseLoadFailed(message:String)
}