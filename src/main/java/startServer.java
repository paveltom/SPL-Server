import MessageProtocolModule.LineMessageEncoderDecoder;

import java.io.IOException;

public class startServer {

    public static void main(String[] args) throws IOException {
        LineMessageEncoderDecoder encdec = new LineMessageEncoderDecoder();
        Byte b = Byte.parseByte("12");
        System.out.println( b);
        String nextMessage = encdec.decodeNextByte(b);
//        if (nextMessage != null) {
//            T response = protocol.process(nextMessage);
    }
}
