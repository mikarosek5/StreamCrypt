package pl.kolby.securitylibpublish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.kolby.securitylibpublish.ui.theme.SecurityLibPublishTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val file = File(applicationContext.filesDir, "text.txt")
        setContent {
            SecurityLibPublishTheme {
                val viewModel by viewModels<MainViewModel>()
                val string by viewModel.state.collectAsState()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(string, {
                        viewModel.encryptText(file)
                    }, {
                        viewModel.decryptText(file)
                    })
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    onEncrypt: () -> Unit,
    onDecrypt: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.then(Modifier.fillMaxSize()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Current file content: $name",
            modifier = modifier
        )
        Button(onClick = onEncrypt) {
            Text(text = "Click me to encrypt")
        }
        Button(onClick = onDecrypt) {
            Text(text = "Click me to decrypt")
        }
    }
}
