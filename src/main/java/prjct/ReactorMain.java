package prjct;

import prjct.MessageProtocolModule.EchoProtocol;
import prjct.MessageProtocolModule.LineMessageEncoderDecoder;
import prjct.ServerModule.Reactor;
import java.io.IOException;

public class ReactorMain {

    public static void main(String[] args) throws IOException {

        Reactor reactServer = new Reactor(3, Integer.parseInt(args[0]), () -> new EchoProtocol(), () -> new LineMessageEncoderDecoder());
        reactServer.serve();
    }
}
