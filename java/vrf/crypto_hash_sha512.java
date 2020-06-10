package org.whispersystems.curve25519.java.vrf;

public class crypto_hash_sha512 {


//    static const unsigned char iv[64] = {
//        0x6a,0x09,0xe6,0x67,0xf3,0xbc,0xc9,0x08,
//                0xbb,0x67,0xae,0x85,0x84,0xca,0xa7,0x3b,
//                0x3c,0x6e,0xf3,0x72,0xfe,0x94,0xf8,0x2b,
//                0xa5,0x4f,0xf5,0x3a,0x5f,0x1d,0x36,0xf1,
//                0x51,0x0e,0x52,0x7f,0xad,0xe6,0x82,0xd1,
//                0x9b,0x05,0x68,0x8c,0x2b,0x3e,0x6c,0x1f,
//                0x1f,0x83,0xd9,0xab,0xfb,0x41,0xbd,0x6b,
//                0x5b,0xe0,0xcd,0x19,0x13,0x7e,0x21,0x79
//    } ;
//
//    int crypto_hash_sha512(unsigned char *out,const unsigned char *in,unsigned long long inlen)
//    {
//        unsigned char h[64];
//        unsigned char padded[256];
//        int i;
//        unsigned long long bytes = inlen;
//7] = bytes << 3;
//            blocks(h,padded,128);
//        for (i = 0;i < 64;++i) h[i] = iv[i];
//
//        blocks(h,in,inlen);
//        in += inlen;
//        inlen &= 127;
//        in -= inlen;
//
//        for (i = 0;i < inlen;++i) padded[i] = in[i];
//        padded[inlen] = 0x80;
//
//        if (inlen < 112) {
//            for (i = inlen + 1;i < 119;++i) padded[i] = 0;
//            padded[119] = bytes >> 61;
//            padded[120] = bytes >> 53;
//            padded[121] = bytes >> 45;
//            padded[122] = bytes >> 37;
//            padded[123] = bytes >> 29;
//            padded[124] = bytes >> 21;
//            padded[125] = bytes >> 13;
//            padded[126] = bytes >> 5;
//            padded[12
//        } else {
//            for (i = inlen + 1;i < 247;++i) padded[i] = 0;
//            padded[247] = bytes >> 61;
//            padded[248] = bytes >> 53;
//            padded[249] = bytes >> 45;
//            padded[250] = bytes >> 37;
//            padded[251] = bytes >> 29;
//            padded[252] = bytes >> 21;
//            padded[253] = bytes >> 13;
//            padded[254] = bytes >> 5;
//            padded[255] = bytes << 3;
//            blocks(h,padded,256);
//        }
//
//        for (i = 0;i < 64;++i) out[i] = h[i];
//
//        return 0;
//    }
    static byte iv[] = {(byte)0x6a, (byte)0x09, (byte)0xe6, (byte)0x67, (byte)0xf3, (byte)0xbc, (byte)0xc9, (byte)0x08,
                        (byte)0xbb, (byte)0x67, (byte)0xae, (byte)0x85, (byte)0x84, (byte)0xca, (byte)0xa7, (byte)0x3b,
                        (byte)0x3c, (byte)0x6e, (byte)0xf3, (byte)0x72, (byte)0xfe, (byte)0x94, (byte)0xf8, (byte)0x2b,
                        (byte)0xa5, (byte)0x4f, (byte)0xf5, (byte)0x3a, (byte)0x5f, (byte)0x1d, (byte)0x36, (byte)0xf1,
                        (byte)0x51, (byte)0x0e, (byte)0x52, (byte)0x7f, (byte)0xad, (byte)0xe6, (byte)0x82, (byte)0xd1,
                        (byte)0x9b, (byte)0x05, (byte)0x68, (byte)0x8c, (byte)0x2b, (byte)0x3e, (byte)0x6c, (byte)0x1f,
                        (byte)0x1f, (byte)0x83, (byte)0xd9, (byte)0xab, (byte)0xfb, (byte)0x41, (byte)0xbd, (byte)0x6b,
                        (byte)0x5b, (byte)0xe0, (byte)0xcd, (byte)0x19, (byte)0x13, (byte)0x7e, (byte)0x21, (byte)0x79};

    public static byte[] crypto_hash_sha512(byte[] in, long inlen){
        byte h[] = new byte[64];
        byte padded[] = new byte[256];
        int i;
        long bytes = inlen;

        for (i=0; i<64; i++){
            h[i] = iv[i];
        }

        in = blocks.crypto_hashblocks_sha512(h, in, inlen);
        long diff = inlen - inlen&127;
        in = blocks.arraySlice(in, (int)diff);
        inlen &= 127;

        for (i=0; i<inlen; ++i) {
            padded[i] = in[i];
        }

        padded[(int)inlen] = (byte)0x80;

        if (inlen < 112) {
            for (i = (int)inlen + 1;i < 119;++i) {
                padded[i] = 0;
            }
            padded[119] = (byte)(bytes >> 61);
            padded[120] = (byte)(bytes >> 53);
            padded[121] = (byte)(bytes >> 45);
            padded[122] = (byte)(bytes >> 37);
            padded[123] = (byte)(bytes >> 29);
            padded[124] = (byte)(bytes >> 21);
            padded[125] = (byte)(bytes >> 13);
            padded[126] = (byte)(bytes >> 5 );
            padded[127] = (byte)(bytes << 3 );
            h = blocks.crypto_hashblocks_sha512(h,padded,128);
        } else {
            for (i = (int)inlen + 1;i < 247;++i) padded[i] = 0;
            padded[247] = (byte)(bytes >> 61);
            padded[248] = (byte)(bytes >> 53);
            padded[249] = (byte)(bytes >> 45);
            padded[250] = (byte)(bytes >> 37);
            padded[251] = (byte)(bytes >> 29);
            padded[252] = (byte)(bytes >> 21);
            padded[253] = (byte)(bytes >> 13);
            padded[254] = (byte)(bytes >> 5 );
            padded[255] = (byte)(bytes << 3 );
            h = blocks.crypto_hashblocks_sha512(h,padded,256);
        }

        byte[] out = new byte[64];

        for (i = 0;i < 64;++i){
            out[i] = (byte)h[i];
        }

        return out;

    }


}
