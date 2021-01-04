package prjct.MessageProtocolModule;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LineMessageEncoderDecoder implements MessageEncoderDecoder<String> {


    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    short op = 0;
    int zeroCount = 0;
    String result = "";
    int startFrom = 0;
    boolean flag1;
    boolean flag2;

    @Override
    public String decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (len == 2){
            op = bytesToShort(bytes);
            if(op<10)
            result += "0" + op + " ";
            else
            result += "" + op + " ";
            startFrom=2;
        }
//        if (nextByte == '\n') {
//            return popString();
//        }

        switch (op) {
            case 1: // Admin register
            flag1 = false;
            flag2 = false;

            if(bytes[len] == 0 & bytes[len-1] == 0) {
                zeroCount++;
                if (zeroCount == 1)
                    flag1 = true;
                if (zeroCount == 3)
                    flag2 = true;
            }
            if(flag1){
                result += (popString(startFrom,len-1)+" ");
                startFrom = len;
                flag1 = false;
            }
            if(flag2){
                result += (popString(startFrom,len-1)+" ");
                len = 0;
                zeroCount = 0;
                startFrom = 0;
                return result;
            }
            break;
            case 2: // student register
                flag1 = false;
                flag2 = false;

            if(bytes[len] == 0 & bytes[len-1] == 0) {
                zeroCount++;
                if (zeroCount == 1)
                    flag1 = true;
                if (zeroCount == 3)
                    flag2 = true;
            }
            if(flag1){
                result += (popString(startFrom,len-1)+" ");
                startFrom = len;
                flag1 = false;
            }
            if(flag2){
                result += (popString(startFrom,len-1)+" ");
                len = 0;
                zeroCount = 0;
                startFrom = 0;
                return result;
            }
            break;
            case 3: // login
                flag1 = false;
                flag2 = false;
                if(bytes[len] == 0 & bytes[len-1] == 0) {
                    zeroCount++;
                    if (zeroCount == 1)
                        flag1 = true;
                    if (zeroCount == 3)
                        flag2 = true;
                }
                if(flag1){
                    result += (popString(startFrom,len-1)+" ");
                    startFrom = len;
                    flag1 = false;
                }
                if(flag2){
                    result += (popString(startFrom,len-1)+" ");
                    len = 0;
                    zeroCount = 0;
                    startFrom = 0;
                    return result;
                }
                break;
            case 4: // logout
                len = 0;
                startFrom = 0;
                return result;
            case 5: // register to course
                if(len == 4){
                    byte[] tempBytes = new byte[1<<3];

                    tempBytes[0] = bytes[2];
                    tempBytes[1] = bytes[3];
                    short num = bytesToShort(tempBytes);
                    result+= (""+num);
                    len = 0;
                    startFrom = 0;
                    return result;
                }
                break;
//                pushByte(nextByte);
//                return null;
            case 6: // kdam cheks
            if(len == 4){
                byte[] tempBytes = new byte[1<<3];

                tempBytes[0] = bytes[2];
                tempBytes[1] = bytes[3];
                short num = bytesToShort(tempBytes);
                result+= (""+num);
                len = 0;
                startFrom = 0;
                return result;
            }
            break;
            case 7: // course stat
                if(len == 4){
                    byte[] tempBytes = new byte[1<<3];

                    tempBytes[0] = bytes[2];
                    tempBytes[1] = bytes[3];
                    short num = bytesToShort(tempBytes);
                    result+= (""+num);
                    len = 0;
                    startFrom = 0;
                    return result;
                }
                break;
            case 8: // student stat
                flag1 = false;
                if(bytes[len] == 0 & bytes[len-1] == 0) {
                    zeroCount++;
                    if (zeroCount == 1)
                        flag1 = true;
                }
                if(flag1){
                    result += (popString(startFrom,len-1)+" ");
                    len = 0;
                    zeroCount = 0;
                    startFrom = 0;
                    return result;
                }
                break;
            case 9: // is register
                if(len == 4){
                    byte[] tempBytes = new byte[1<<3];

                    tempBytes[0] = bytes[2];
                    tempBytes[1] = bytes[3];
                    short num = bytesToShort(tempBytes);
                    result+= (""+num);
                    len = 0;
                    startFrom = 0;
                    return result;
                }
                break;
            case 10: // unregister
                if(len == 4){
                    byte[] tempBytes = new byte[1<<3];

                    tempBytes[0] = bytes[2];
                    tempBytes[1] = bytes[3];
                    short num = bytesToShort(tempBytes);
                    result+= (""+num);
                    len = 0;
                    startFrom = 0;
                    return result;
                }
                break;
            case 11:
                len = 0;
                startFrom = 0;
                return result;
            default:
               break;
        }
        pushByte(nextByte);
        return null; //not a line yet
    }

    @Override
    public byte[] encode(String message) {
        return (message + "\n").getBytes(); //uses utf8 by default
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    public String popString(int startFrom , int end) { //change to private!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String curResult = new String(bytes, startFrom, end, StandardCharsets.UTF_8);
        //len = 0;
        return curResult;
    }

    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xFF) << 8);
        result += (short)(byteArr[1] & 0xFF);
        return result;
    }
}
