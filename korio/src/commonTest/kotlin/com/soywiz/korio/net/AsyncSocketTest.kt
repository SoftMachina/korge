package com.soywiz.korio.net

import com.soywiz.kmem.*
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.async.suspendTestNoBrowser
import com.soywiz.korio.stream.readBytesExact
import com.soywiz.korio.stream.writeBytes
import kotlinx.coroutines.CompletableDeferred
import kotlin.test.Test
import kotlin.test.assertEquals

class AsyncSocketTest {
	@Test
	fun testClientAndServer() = suspendTestNoBrowser {
		if (Platform.isJsBrowser) return@suspendTestNoBrowser
		if (Platform.isJs) return@suspendTestNoBrowser
		if (Platform.isWindows && Platform.isNative) return@suspendTestNoBrowser
        if (Platform.isAndroid) return@suspendTestNoBrowser

		var port = 0
		val connected = CompletableDeferred<Unit>()
		val readSignal = CompletableDeferred<Unit>()
		val read = arrayListOf<ByteArray>()

		launchImmediately(coroutineContext) {
			val server = createTcpServer(AsyncServer.ANY_PORT)
			port = server.port
			connected.complete(Unit)
			val client = server.accept()
			read.add(client.readBytesExact(4))
			readSignal.complete(Unit)
		}

		connected.await()
		val client = createTcpClient("127.0.0.1", port)
		client.writeBytes(byteArrayOf(1, 2, 3, 4))
		readSignal.await()

		assertEquals(1, read.size)
		assertEquals(listOf(1, 2, 3, 4), read[0].map { it.toInt() })
	}
}
