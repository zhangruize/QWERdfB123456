package com.zh.zrz.meshreadertest.entity;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static com.zh.zrz.meshreadertest.util.PrintUtil.print;
import static com.zh.zrz.meshreadertest.util.PrintUtil.println;
import static com.zh.zrz.meshreadertest.util.ReadUtil.nextFloat;
import static com.zh.zrz.meshreadertest.util.ReadUtil.nextInt32;
import static com.zh.zrz.meshreadertest.util.ReadUtil.nextString;
import static com.zh.zrz.meshreadertest.util.ReadUtil.nextUint16;
import static com.zh.zrz.meshreadertest.util.ReadUtil.nextUint32;
import static com.zh.zrz.meshreadertest.util.ReadUtil.nextUint8;

public class MeshFileData {
    public String filePath;

    public long magic;
    public long version;
    public String animFile;
    public String textureFile;
    public long numMeshes;
    public MeshInfo[] meshInfos;
    public long numVerts;
    public VertexInfo[] vertexInfos;
    public long numIndices;
    public long[] indexInfos;
    public long numBones;
    public BoneInfo[] boneInfos;

    public static class MeshInfo {
        public String name;
        public long vStart;
        public long vCount;
        public long iStart;
        public long iCount;
    }

    public static class VertexInfo {
        public float x, y, z;
        public float nx, ny, nz, nw;
        public float u, v;
        public int bx, by, bz, bw;
        public float wx, wy, wz, ww;
    }

    public static class BoneInfo {
        public String name;
        public int parent;
        public float scale;
        public float[] orgiMatrix = new float[16];
        public float[] incrMatrix = new float[16];
    }

    public static MeshFileData loadMesh(String filePath) throws IOException {
        MeshFileData fileData = new MeshFileData();
        fileData.filePath = filePath;

        DataInputStream in = new DataInputStream(new FileInputStream(filePath));
        fileData.magic = nextUint32(in);
        println("magic:\t" + fileData.magic);

        fileData.version = nextUint32(in);
        println("version:\t" + fileData.version);

        fileData.animFile = nextString(in);
        println("animFile:\t" + fileData.animFile);

        fileData.textureFile = nextString(in);
        println("textureFile:\t" + fileData.textureFile);

        fileData.numMeshes = nextUint32(in);
        fileData.meshInfos = new MeshFileData.MeshInfo[(int) fileData.numMeshes];
        println("numMeshes:\t" + fileData.numMeshes);
        int i = 0;
        while (i++ < fileData.numMeshes) {
            MeshFileData.MeshInfo info = new MeshFileData.MeshInfo();
            info.name = nextString(in);
            println("name:\t" + info.name);
            info.vStart = nextUint32(in);
            println("vStart:\t" + info.vStart);
            info.vCount = nextUint32(in);
            println("vCount:\t" + info.vCount);
            info.iStart = nextUint32(in);
            println("iStart:\t" + info.iStart);
            info.iCount = nextUint32(in);
            println("iCount:\t" + info.iCount);
            fileData.meshInfos[i - 1] = info;
        }
        fileData.numVerts = nextUint32(in);
        fileData.vertexInfos = new MeshFileData.VertexInfo[(int) fileData.numVerts];
        i = 0;
        println("numVerts:\t" + fileData.numVerts);
        while (i++ < fileData.numVerts) {
            MeshFileData.VertexInfo info = new MeshFileData.VertexInfo();
            info.x = nextFloat(in);
            info.y = nextFloat(in);
            info.z = nextFloat(in);
            info.nx = nextFloat(in);
            info.ny = nextFloat(in);
            info.nz = nextFloat(in);
            info.u = nextFloat(in);
            info.v = nextFloat(in);
            info.bx = nextUint8(in);
            info.by = nextUint8(in);
            info.bz = nextUint8(in);
            info.bw = nextUint8(in);
            info.wx = nextFloat(in);
            info.wy = nextFloat(in);
            info.wz = nextFloat(in);
            info.ww = nextFloat(in);
            //normalize
            double sum = Math.sqrt(info.nx * info.nx + info.ny * info.ny + info.nz * info.nz);
            info.nx = (float) (info.nx / sum);
            info.ny = (float) (info.ny / sum);
            info.nz = (float) (info.nz / sum);

            fileData.vertexInfos[i - 1] = info;
        }
        fileData.numIndices = nextUint32(in);
        fileData.indexInfos = new long[(int) fileData.numIndices];
        i = 0;
        println("numIndices:\t" + fileData.numIndices);
        while (i++ < fileData.numIndices) {
            fileData.indexInfos[i - 1] = nextUint16(in);
            println(String.format("index:\t%d", fileData.indexInfos[i - 1]));
        }
        fileData.numBones = nextUint32(in);
        fileData.boneInfos = new MeshFileData.BoneInfo[(int) fileData.numBones];
        i = 0;
        println("numBones:\t" + fileData.numBones);
        while (i++ < fileData.numBones) {
            MeshFileData.BoneInfo info = new MeshFileData.BoneInfo();
            info.name = nextString(in);
            info.parent = nextInt32(in);
            info.scale = nextFloat(in);
            println(String.format("name:\t%s\tparent:\t%d\tscale:\t%f", info.name, info.parent, info.scale));
            int j = 0;
            print("origMatrix");
            while (j++ < 16) {
                info.orgiMatrix[j - 1] = nextFloat(in);
                print(String.format("\t%f", info.orgiMatrix[j - 1]));
            }

            print("\nincrMatrix");
            j = 0;
            while (j++ < 16) {
                if (fileData.version >= 2) {
                    info.incrMatrix[j - 1] = nextFloat(in);
                }//todo else indentify matric
                print(String.format("\t%f", info.incrMatrix[j - 1]));
            }
            fileData.boneInfos[i - 1] = info;
            print("\n");
        }
        return fileData;
    }
}
