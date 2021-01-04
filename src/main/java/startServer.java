import prjct.MessageProtocolModule.EchoProtocol;
import prjct.MessageProtocolModule.LineMessageEncoderDecoder;

import java.io.IOException;
import java.util.Arrays;

public class startServer {

    public static void main(String[] args) throws IOException {

//        byte[] bytes = new byte[1 << 10]
//        String result = new String(, 0, len, StandardCharsets.UTF_8);


        //STUDENTREG Morty a123
        //LOGIN Morty a123
        //STUDENTSTAT Morty
        //COURSESTAT 32
        //LOGOUT
        EchoProtocol echo = new EchoProtocol();
        System.out.println(echo.process("2 Morty a123"));
        System.out.println(echo.process("3 Morty a123"));
        System.out.println(echo.process("5 530"));
        System.out.println(echo.process("4"));
        System.out.println(echo.process("1 Morty a123"));
        System.out.println(echo.process("1 Rick a123"));
        System.out.println(echo.process("3 Rick a123"));
        System.out.println(echo.process("8 Morty"));
        System.out.println(echo.process("7 32"));
        System.out.println(echo.process("7 342"));
        System.out.println(echo.process("4"));




//        LineMessageEncoderDecoder encdec = new LineMessageEncoderDecoder();
//        String s = "00 02 Morty a123\n";
//        byte[] bytes = s.getBytes();
//        System.out.println(Arrays.toString(bytes));
//        for (int i = 0; i< bytes.length - 1; i++){
//            encdec.decodeNextByte(bytes[i]);
//        }
//        System.out.println(encdec.popString());

//        String s = "0012";
////        System.out.println( s.getBytes().getClass());
//        encdec.decodeNextByte(s.getBytes()[0]);
//        encdec.decodeNextByte(s.getBytes()[1]);
//        System.out.println(encdec.popString());
//        System.out.println(Arrays.toString(s.getBytes()));
//        System.out.write(b);
//        String nextMessage = encdec.decodeNextByte(b);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        stream.write(12);
//        byte[] mByteArray = String.getBytes();
//        //stream.write(12);
//        System.out.println(Arrays.toString(mByteArray));
//        if (nextMessage != null) {
//            T response = protocol.process(nextMessage);
    }
}
