package com.springbatch.demo.callbacks;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

/**
 * @author Adam Hartley
 */
@Component
public class HeaderCallbackCsv implements FlatFileHeaderCallback {

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write("first-name,last-name");
    }
}
