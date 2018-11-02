package com.zh.zrz.meshreadertest;


import com.zh.zrz.meshreadertest.entity.MeshFileData;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.zh.zrz.meshreadertest.entity.MeshFileData.loadMesh;

public class ReadTest {
    String workPath = "D:\\x\\lll\\";

    @Test
    public void test() throws Exception {
        String filePath = workPath + "cube_test.lmesh";
        MeshFileData fileData = loadMesh(filePath);

        String outputFilePath = workPath + fileData.textureFile + ".obj";
        convert2Obj(fileData, outputFilePath);

        Runtime.getRuntime().exec("cmd /c " + outputFilePath);
    }

    public void convert2Obj(MeshFileData fileData, String outputFilePath) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(outputFilePath));

        //create mtl file
        String mtlFilePath = workPath + fileData.textureFile + ".mtl";
        DataOutputStream mtlOut = new DataOutputStream(new FileOutputStream(mtlFilePath));
        writeString(mtlOut, String.format("newmtl %s\n", fileData.textureFile));
        writeString(mtlOut, String.format("Kd %f %f %f\n", 1f, 1f, 1f));
        writeString(mtlOut, String.format("map_Kd %s\n", workPath + fileData.textureFile + ".png"));
        mtlOut.close();

        writeString(out, String.format("mtllib %s\n", fileData.textureFile + ".mtl"));
        writeString(out, String.format("usemtl %s\n", fileData.textureFile));

        int i = 0;
        while (i < fileData.numVerts) {
            MeshFileData.VertexInfo info = fileData.vertexInfos[i];
            writeString(out, String.format("v %f %f %f\n", info.x, info.y, info.z));
            i++;
        }
        i = 0;
        while (i < fileData.numVerts) {
            MeshFileData.VertexInfo info = fileData.vertexInfos[i];
            writeString(out, String.format("vn %f %f %f\n", info.nx, info.ny, info.nz));
            i++;
        }
        i = 0;
        while (i < fileData.numVerts) {
            MeshFileData.VertexInfo info = fileData.vertexInfos[i];
            writeString(out, String.format("vt %f %f\n", info.u, info.v));
            i++;
        }
        writeString(out, "g default\ns 1\nillum 2\n");
        i = 0;
        while (i < fileData.numIndices) {
            long i1 = fileData.indexInfos[(int) (i++ % fileData.numIndices)] + 1;
            long i2 = fileData.indexInfos[(int) (i++ % fileData.numIndices)] + 1;
            long i3 = fileData.indexInfos[(int) (i++ % fileData.numIndices)] + 1;
//            writeString(out, String.format("f %d/%d/%d %d/%d/%d %d/%d/%d\n", i1, i1, i1, i2, i2, i2, i3, i3, i3));
            writeString(out, String.format("f %d/%d %d/%d %d/%d\n", i1, i1, i2, i2, i3, i3));
        }
        out.close();
    }

    public void writeString(DataOutputStream out, String data) throws IOException {
        int i = 0;
        while (i < data.length()) {
            out.write((byte) data.charAt(i));
            i++;
        }
    }
}