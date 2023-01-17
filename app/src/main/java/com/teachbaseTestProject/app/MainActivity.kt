package com.teachbaseTestProject.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teachbaseTestProject.app_dependencies.token_storage.TokenStorage
import com.teachbasetestproject.app.R
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val tokenStorage: TokenStorage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tokenStorage.saveToken("VBJA9R2-ZVFMT0R-HSM7AQA-488TD54")
    }
}