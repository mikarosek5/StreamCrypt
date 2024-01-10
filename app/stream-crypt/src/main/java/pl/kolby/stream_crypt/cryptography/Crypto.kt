package pl.kolby.stream_crypt.cryptography

import java.io.InputStream
import java.io.OutputStream

interface Crypto {
    suspend fun encrypt(rawBytes: ByteArray, outputStream: OutputStream)
    suspend fun decrypt(inputStream: InputStream): ByteArray
}