package org.wb.reactive.web.io;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wb.reactive.web.controller.ProfileRestController;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

@Log4j2
public class IoDemo {

	public static void main(String args[]) throws IOException {
		    log.info("==========IoDemo Started==========");
			File desktop = new File(System.getProperty("user.home"), "Desktop");
			File input = new File(desktop, "input.txt");
			Consumer<BytesPayload> consumer = (bytes) -> log.info(String.format("Bytes available! got %d bytes.", bytes.getLength()));
			Io io = new Io();
			log.info("---------------------------------");
			io.synchronousRead(input, consumer);
			log.info("---------------------------------");
			io.asynchronousRead(input, consumer);System.in.read();
	}
}

