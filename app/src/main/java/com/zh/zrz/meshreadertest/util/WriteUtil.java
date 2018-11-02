package com.zh.zrz.meshreadertest.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class WriteUtil {
    public static void writeUint32(DataOutputStream out, long num) throws IOException {
        out.write(new byte[]{(byte) (num & 0x000000ff), (byte) (num >> 8 & 0x000000ff), (byte) (num >> 16 & 0x000000ff), (byte) (num >> 24 & 0x000000ff)});
    }

    public static void writeUint16(DataOutputStream out, int num) throws IOException {
        out.write(new byte[]{(byte) (num & 0x000000ff), (byte) (num >> 8 & 0x000000ff)});
    }

    public static void writeString(DataOutputStream out, String string) throws IOException {
        writeUint16(out, string.length());
        for (int i = 0; i < string.length(); i++) {
            out.writeByte(string.charAt(i));
        }
    }

    public static void writeFloat(DataOutputStream out, float num) throws IOException {
        ByteBuffer bf = ByteBuffer.allocate(4);
        bf.putFloat(num);
        byte[] temp = bf.array();
//        out.write(new byte[]{temp[7], temp[6], temp[5], temp[4], temp[3], temp[2], temp[1], temp[0]});
        out.write(new byte[]{temp[3], temp[2], temp[1], temp[0]});
    }
}
