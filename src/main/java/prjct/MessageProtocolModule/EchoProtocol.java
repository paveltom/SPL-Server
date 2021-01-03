package prjct.MessageProtocolModule;

import prjct.api.Callback;

import java.lang.UnsupportedOperationException;

import java.util.HashMap;

public class EchoProtocol implements MessagingProtocol<String> {

    private boolean shouldTerminate = false;
    private HashMap<String, Callback> requestExecute;

    @Override
    public String process(String msg) {

        //call the desired callback function from requestExecute
        //return a message depending on incoming command from user / admin


        throw new UnsupportedOperationException();
    }

    private String createEcho(String message) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    private void createRequestExecute() {
        /*
        ClientToServer:
        ADMINREG
        STUDENTREG
        LOGIN
        LOGOUT
        COURSEREG
        KDAMCHECK
        COURSESTAT
        STUDENTSTAT
        ISREGISTERED
        UNREGISTER
        MYCOURSES

        ServerToClient:
        ACK
        ERR
         */

        Callback<String> adminReg = new Callback<String>() {
            @Override
            public String call(String something) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
