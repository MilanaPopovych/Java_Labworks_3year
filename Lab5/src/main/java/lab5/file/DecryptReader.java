package lab5.file;

import java.io.*;

public class DecryptReader extends FilterReader{
    private final char key;
    public DecryptReader(Reader in, char key) {
        super(in);
        this.key=key;
    }
    @Override
    public int read() throws IOException {
        int c = super.read();
        return (c == -1) ? -1 : (c - key);
    }
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int result = super.read(cbuf, off, len);
        if (result != -1) {
            for (int i = off; i < off + result; i++) {
                cbuf[i] = (char) (cbuf[i] - key);
            }
        }
        return result;
    }
}
