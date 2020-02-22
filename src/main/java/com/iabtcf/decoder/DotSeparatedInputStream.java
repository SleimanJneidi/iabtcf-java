package com.iabtcf.decoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

class DotSeparatedInputStream extends InputStream {

    private StringReader reader;
    private boolean hasNext = true;

    public DotSeparatedInputStream(String input) {
        reader = new StringReader(input);
    }

    /**
     * @return next char or -1 if the current char is either a dot
     * or indeed the end of the steam
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
        int read = reader.read();
        if(read == '.') {
            hasNext = true;
            return -1;
        }
        if(read == -1) {
            hasNext = false;
        }
        return read;
    }

    public boolean hasNext() {
        return hasNext;
    }
}

