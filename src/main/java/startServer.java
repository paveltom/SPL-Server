import prjct.MessageProtocolModule.EchoProtocol;
import prjct.MessageProtocolModule.LineMessageEncoderDecoder;
import prjct.ServerModule.BaseServer;
import prjct.ServerModule.BlockingConnectionHandler;
import prjct.ServerModule.Reactor;
import prjct.ServerModule.TPCServer;

import java.io.IOException;
import java.util.Arrays;

public class startServer {

    public static void main(String[] args) throws IOException {
//        System.out.println("Present Project Directory : "+ System.getProperty("user.dir")+"/Courses.txt");

//        byte[] bytes = new byte[1 << 10]
//        String result = new String(, 0, len, StandardCharsets.UTF_8);
//        Reactor baseServer = new Reactor<String>(1, 6666, () -> new EchoProtocol(), () -> new LineMessageEncoderDecoder());
//        baseServer.serve();

        TPCServer tpcServer = new TPCServer(7777, () -> new EchoProtocol(), () -> new LineMessageEncoderDecoder());
        tpcServer.serve();

        //STUDENTREG Morty a123
        //LOGIN Morty a123
        //STUDENTSTAT Morty
        //COURSESTAT 32
        //LOGOUT
//        System.out.println("Present Project Directory : "+ System.getProperty("user.dir")+"/Courses.txt");
        //530->912->482

//        EchoProtocol echo = new EchoProtocol();
//        System.out.println(echo.process("02 Morty a123"));
//        System.out.println(echo.process("03 Morty a123"));
//        System.out.println(echo.process("05 530"));
//        System.out.println(echo.process("05 912"));
//        System.out.println(echo.process("05 482"));
//        System.out.println(echo.process("04"));
//        //912, 482, 530 need to be displayed in Morty's courses
//        System.out.println(echo.process("01 Morty a123"));
//        System.out.println(echo.process("01 Rick a123"));
//        System.out.println(echo.process("03 Rick a123"));
//        System.out.println(echo.process("08 Morty"));
//        System.out.println(echo.process("07 32"));
//        System.out.println(echo.process("07 342"));
//        System.out.println(echo.process("07 530"));
//        System.out.println(echo.process("07 912"));
//        System.out.println(echo.process("07 482"));
//        System.out.println(echo.process("04"));




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
