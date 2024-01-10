package pl.kolby.stream_crypt.cryptography

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.kolby.stream_crypt.utils.readAllBytesFromStream
import java.io.InputStream
import java.io.OutputStream
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream


internal class CryptoImpl(private val cipherProvider: CipherProvider) : Crypto {

    override suspend fun encrypt(rawBytes: ByteArray, outputStream: OutputStream) {
        withContext(Dispatchers.Default) {
            val cipher = cipherProvider.encryptCipher
            outputStream.write(cipher.iv.size)
            outputStream.write(cipher.iv)
            CipherOutputStream(outputStream, cipher).use {
                it.write(rawBytes)
            }
        }
    }

    override suspend fun decrypt(inputStream: InputStream): ByteArray {
        return withContext(Dispatchers.Default) {
            val ivSize = inputStream.read()
            val iv = ByteArray(ivSize)
            inputStream.read(iv)
            val cipher = cipherProvider.decryptCipher(iv)
            CipherInputStream(inputStream, cipher).use {
                it.readAllBytesFromStream()
            }
        }
    }

}