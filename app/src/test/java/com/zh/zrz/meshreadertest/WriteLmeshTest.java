package com.zh.zrz.meshreadertest;

import android.os.SystemClock;

import com.zh.zrz.meshreadertest.entity.MeshFileData;
import com.zh.zrz.meshreadertest.util.ReadUtil;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.zh.zrz.meshreadertest.util.WriteUtil.writeFloat;
import static com.zh.zrz.meshreadertest.util.WriteUtil.writeString;
import static com.zh.zrz.meshreadertest.util.WriteUtil.writeUint16;
import static com.zh.zrz.meshreadertest.util.WriteUtil.writeUint32;

public class WriteLmeshTest {
    String workPath = "D:\\x\\lll\\";

    @Test
    public void test() throws IOException {
        //we want to create a simple cube model in .lmesh file format, to illustrate what's the
        //difference between the webGl render and .obj render process.
        String outputFilePath = workPath + "cube_test.lmesh";
        DataOutputStream out = new DataOutputStream(new FileOutputStream(outputFilePath));
        writeUint32(out, 604210091L);
        writeUint32(out, 2);//version
        writeString(out, "");//anim
        writeString(out, "KilaPart03_06");//texture
//        writeString(out, "https://github.com/zhangruize/zero2one/blob/master/KilaPart03_06.jpg");//texture
        writeUint32(out, 0);//meshes
        writeUint32(out, 4);//vertex
        writeVertex(out, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, 0, 0, 0, 0);
        writeVertex(out, 1, 0, 0, 0, 0, 0, 1, 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, 0, 0, 0, 0);
        writeVertex(out, 1, 0, 1, 0, 0, 0, 1, 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, 0, 0, 0, 0);
        writeVertex(out, 0, 0, 1, 0, 0, 0, 0, 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, 0, 0, 0, 0);
        writeUint32(out, 6);//indices
        writeUint16(out, 0);
        writeUint16(out, 1);
        writeUint16(out, 2);
        writeUint16(out, 0);
        writeUint16(out, 2);
        writeUint16(out, 3);
        writeUint32(out, 0);//bones
        out.close();

        MeshFileData fileData = MeshFileData.loadMesh(outputFilePath);
        System.out.println("ss");
    }

    public void writeVertex(DataOutputStream out, float x, float y, float z, float nx, float ny, float nz, float u, float v, byte b1, byte b2, byte b3, byte b4, float w1, float w2, float w3, float w4) throws IOException {
        writeFloat(out, x);
        writeFloat(out, y);
        writeFloat(out, z);
        writeFloat(out, nx);
        writeFloat(out, ny);
        writeFloat(out, nz);
        writeFloat(out, u);
        writeFloat(out, v);
        out.write(new byte[]{b1,b2,b3,b4});
        writeFloat(out, w1);
        writeFloat(out, w2);
        writeFloat(out, w3);
        writeFloat(out, w4);
    }
}
