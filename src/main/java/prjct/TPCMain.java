package prjct;

import prjct.MessageProtocolModule.EchoProtocol;
import prjct.MessageProtocolModule.LineMessageEncoderDecoder;
import prjct.ServerModule.TPCServer;

import java.io.IOException;

public class TPCMain {

    public static void main(String[] args) throws IOException {

        TPCServer tpcServer = new TPCServer(Integer.parseInt(args[0]), () -> new EchoProtocol(), () -> new LineMessageEncoderDecoder());
        tpcServer.serve();
    }
}
