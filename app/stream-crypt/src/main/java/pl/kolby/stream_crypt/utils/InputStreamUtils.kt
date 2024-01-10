package pl.kolby.stream_crypt.utils

import java.io.ByteArrayOutputStream
import java.io.InputStream

internal fun InputStream.readAllBytesFromStream(): ByteArray {
    val bufLen = 1024
    val buf = ByteArray(bufLen)
    var readLen: Int
    val outputStream = ByteArrayOutputStream()
    while (this.read(buf, 0, bufLen).also { readLen = it } != -1) outputStream.write(
        buf,
        0,
        readLen
    )
    return outputStream.toByteArray()
}