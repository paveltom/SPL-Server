package MessageProtocolModule;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;

public class EchoProtocol implements MessagingProtocol<String> {

    private boolean shouldTerminate = false;

    @Override
    public String process(String msg) {
        throw new NotImplementedException();
    }

    private String createEcho(String message) {
        throw new NotImplementedException();
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
