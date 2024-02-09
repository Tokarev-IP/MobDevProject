package com.example.mobdevproject.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobdevproject.content.ContentActivity
import com.example.mobdevproject.login.addition.PhoneAuthOptionsAddition
import com.example.mobdevproject.login.data.PhoneAuthCallback
import com.example.mobdevproject.login.presentation.SignInActivityCompose
import com.example.mobdevproject.login.presentation.SignInViewModel
import com.example.mobdevproject.login.presentation.UiIntents
import com.example.mobdevproject.ui.theme.MobDevProjectTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : ComponentActivity() {

    @Inject
    lateinit var phoneAuthCallback: PhoneAuthCallback

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInIntent: Intent
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInIntent = googleSignInClient.signInIntent

        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    signInViewModel.setIntent(UiIntents.SignInWithGoogle(task))
                }
            }

        setContent {
            MobDevProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StartUi()
                }
            }
        }
    }

    @Composable
    private fun StartUi(
    ) {
        signInViewModel = hiltViewModel<SignInViewModel>()

        when (signInViewModel.getUserStateFlow()) {
            is FirebaseAuth -> {
                goToContentActivity()
            }
        }

        SignInActivityCompose(
            onGoogleSignIn = {
                startForResult.launch(signInIntent)
            },
            onEmailSignIn = { email: String ->
                signInViewModel.setIntent(UiIntents.SignInWithEmail(email))
            },
            onPhoneSignIn = { smsCode: String ->
                signInViewModel.setIntent(UiIntents.SignWithSmsCode(smsCode))
            },
            onSendCodeToPhoneNumber = { phoneNumber: String ->
                signInViewModel.setIntent(
                    UiIntents.SignInWithPhone(
                        PhoneAuthOptionsAddition(
                            activity = this,
                            phoneAuthCallback = phoneAuthCallback
                        ).getPhoneAuthOptions(phoneNumber),
                        phoneNumber
                    )
                )
            }
        )
    }

    private fun goToContentActivity() {
        startActivity(Intent(this, ContentActivity::class.java))
    }

}