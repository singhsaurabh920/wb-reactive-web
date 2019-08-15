package org.wb.reactive.web.io;

import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

class Io {

		public void synchronousRead(File f, Consumer<BytesPayload> consumer) {
				try {
					Synchronous synchronous = new Synchronous();
					synchronous.read(f, consumer);
				}
				catch (Exception e) {
					 ReflectionUtils.rethrowRuntimeException(e);
				}
		}

		public void asynchronousRead(File f, Consumer<BytesPayload> consumer) {
				try {
					Asynchronous asynchronous = new Asynchronous();
					asynchronous.read(f, consumer);
				}
				catch (Exception ex) {
					ReflectionUtils.rethrowRuntimeException(ex);
				}
		}
}
