package prjct.MessageProtocolModule;

import prjct.User;
import prjct.commands.Callback;

import java.lang.UnsupportedOperationException;

import java.util.HashMap;

public class EchoProtocol implements MessagingProtocol<String> {

    private boolean shouldTerminate = false;
    private HashMap<String, Callback> requestExecute;
    private User currUser = null;

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

        COURSESTAT:
                   students sorted alphabetically:
                                 //        if (list.size() > 0) {
//                                                Collections.sort(list, new Comparator<Campaign>() {
                                //                @Override
                                //                public int compare(final Campaign object1, final Campaign object2) {
                            //                    return object1.getName().compareTo(object2.getName());
//                }
//            });
//        }

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
