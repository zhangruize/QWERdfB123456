package com.zh.zrz.meshreadertest.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * todo should use ByteBuffer to parse data
 */
public class ReadUtil {
    public static int nextUint8(DataInputStream in) throws IOException {
        return (int) (in.readByte() & 0x000000ffL);
    }

    public static int nextUint16(DataInputStream in) throws IOException {
        return (int) ((in.readByte() & 0x000000ffL) | (in.readByte() & 0x000000ffL) << 8);
    }

    public static long nextUint32(DataInputStream in) throws IOException {
        return (in.readByte() & 0x000000ffL) | (in.readByte() & 0x000000ffL) << 8 | (in.readByte() & 0x000000ffL) << 16 | (in.readByte() & 0x000000ffL) << 24;
    }

    public static int nextInt32(DataInputStream in) throws IOException {
        return in.readInt();
    }

    public static float nextFloat(DataInputStream in) throws IOException {
        return ByteBuffer.wrap(new byte[]{in.readByte(), in.readByte(), in.readByte(), in.readByte()}).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static String nextString(DataInputStream in) throws IOException {
        int len = nextUint16(in);
        StringBuilder sb = new StringBuilder();
        while (len-- > 0) {
            sb.append((char) in.readByte());
        }
        return sb.toString();
    }
}
