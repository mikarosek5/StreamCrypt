package pl.kolby.stream_crypt.cryptography

import javax.crypto.Cipher

internal interface CipherProvider {
    val encryptCipher: Cipher
    fun decryptCipher(iv: ByteArray): Cipher
}