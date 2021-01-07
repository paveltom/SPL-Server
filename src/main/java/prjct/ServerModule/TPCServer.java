package prjct.ServerModule;

import prjct.MessageProtocolModule.MessageEncoderDecoder;
import prjct.MessageProtocolModule.MessagingProtocol;

import java.util.function.Supplier;

public class TPCServer<T> extends BaseServer<T>{

    public TPCServer(int port, Supplier<MessagingProtocol<T>> protocolFactory, Supplier<MessageEncoderDecoder<T>> encDecFactory){
        super (port, protocolFactory, encDecFactory);
    }

    @Override
    protected void execute(BlockingConnectionHandler<T> handler) {
        new Thread(handler).start();
    }
}
