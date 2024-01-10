package pl.kolby.stream_crypt

import pl.kolby.stream_crypt.cryptography.AesCipherProvider
import pl.kolby.stream_crypt.cryptography.CipherProvider
import pl.kolby.stream_crypt.cryptography.Crypto
import pl.kolby.stream_crypt.cryptography.CryptoImpl
import java.security.KeyStore

object StreamCrypt {
    private const val keyStoreName = "AndroidKeyStore"
    private val keyStore: KeyStore = KeyStore.getInstance(keyStoreName).apply { load(null) }

    fun getCrypto(keyName: String): Crypto {
        val cipherProvider: CipherProvider = AesCipherProvider(keyName, keyStore, keyStoreName)
        return CryptoImpl(cipherProvider)
    }
}