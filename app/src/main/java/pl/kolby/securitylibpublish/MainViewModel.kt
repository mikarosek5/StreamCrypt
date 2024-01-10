package pl.kolby.securitylibpublish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.kolby.stream_crypt.StreamCrypt
import java.io.File

class MainViewModel : ViewModel() {
    private val crypto = StreamCrypt.getCrypto("TEST")
    private val _state = MutableStateFlow("TEST MESSAGE")
    val state: StateFlow<String>
        get() = _state

    fun encryptText(file: File) {
        viewModelScope.launch {
            crypto.encrypt(state.value.toByteArray(), file.outputStream())
            _state.emit(file.readText())
        }
    }

    fun decryptText(file: File) {
        viewModelScope.launch {
            _state.emit(crypto.decrypt(file.inputStream()).decodeToString())
        }
    }

}

