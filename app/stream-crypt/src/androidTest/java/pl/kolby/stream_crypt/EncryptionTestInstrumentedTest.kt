package pl.kolby.stream_crypt

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.random.Random


@RunWith(AndroidJUnit4::class)
class EncryptionTestInstrumentedTest {

    @Test
    fun checkCryptoTest() {
        runBlocking {
            val message = "testMessage"
            val fileName = "test.txt"
            val crypto = StreamCrypt.getCrypto("test")
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            val file = File(appContext.filesDir, fileName)
            val fileOutputStream = FileOutputStream(file)
            crypto.encrypt(message.toByteArray(), fileOutputStream)
            fileOutputStream.close()
            val fileInputStream = FileInputStream(file)
            val decryptedMessage: String = crypto.decrypt(fileInputStream).decodeToString()
            fileInputStream.close()
            assertEquals(message, decryptedMessage)
        }
    }

    @Test
    fun largeFileTest() {
        runBlocking {
            val crypto = StreamCrypt.getCrypto("largeFile")
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            val plainTextFile = File(appContext.filesDir, "plainTextFile.txt")
            for (i in 0..1000) {
                val byteArray = ByteArray(1000)
                Random.nextBytes(byteArray)
                plainTextFile.writeBytes(byteArray)
            }
            val encryptedTextFile = File(appContext.filesDir, "encryptedTextFile.txt")
            crypto.encrypt(plainTextFile.readBytes(), encryptedTextFile.outputStream())
            val decryptedTextFile = File(appContext.filesDir, "decryptedTextFile.txt")
            decryptedTextFile.writeBytes(crypto.decrypt(encryptedTextFile.inputStream()))
            assertEquals(plainTextFile.readBytes().size, decryptedTextFile.readBytes().size)
        }
    }
}