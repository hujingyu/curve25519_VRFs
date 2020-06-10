package org.whispersystems.curve25519.java.vrf;

import org.whispersystems.curve25519.java.*;

import static org.whispersystems.curve25519.java.vrf.VXEdDSA_consts.*;
import static org.whispersystems.curve25519.java.vrf.gen_labelset.B_bytes;
import static org.whispersystems.curve25519.java.vrf.gen_labelset.buffer_add;

public class gen_eddsa {

    static void generalized_commit(byte[] R_bytes, byte[] r_scalar,
                                   byte[] labelset, long labelset_len,
                                   byte[] extra, long extra_len,
                                   byte[] K_bytes, byte[] k_scalar,
                                   byte[] Z,
                                   byte[] M_buf, long M_start, long M_len)
    {
        ge_p3 R_point = new ge_p3();
        byte[] hash = new byte[HASHLEN];

//        if (!gen_labelset.labelset_validate(labelset, labelset_len))
//            throw new IllegalArgumentException();

        /**
         * handle rest of errors
         */

        long prefix_len = 0;
        prefix_len += POINTLEN + labelset_len + RANDLEN;
        prefix_len += ((BLOCKLEN - (prefix_len % BLOCKLEN)) % BLOCKLEN);
        prefix_len += SCALARLEN;
        prefix_len += ((BLOCKLEN - (prefix_len % BLOCKLEN)) % BLOCKLEN);
        prefix_len += labelset_len + POINTLEN + extra_len;

//        if (prefix_len > M_start)
//            throw new IllegalArgumentException();

        int bufstart = (int)(M_start - prefix_len);
        int bufend = (int)M_start;

        byte[] M_buf_copy = M_buf;

        int offset = bufstart;
        M_buf_copy = buffer_add(M_buf_copy, B_bytes, POINTLEN, offset);
        offset += POINTLEN;
        M_buf_copy = buffer_add(M_buf_copy, labelset, labelset_len, offset);
        offset += labelset_len;
        M_buf_copy = buffer_add(M_buf_copy, Z, RANDLEN, offset);
        offset += RANDLEN;
        M_buf_copy = gen_labelset.buffer_pad(M_buf_copy, offset, bufend);
        offset += (BLOCKLEN - (offset % BLOCKLEN)) % BLOCKLEN;
        M_buf_copy = buffer_add(M_buf_copy, k_scalar, SCALARLEN, offset);
        offset += SCALARLEN;
        M_buf_copy = gen_labelset.buffer_pad(M_buf_copy, offset, bufend);
        offset += (BLOCKLEN - (offset % BLOCKLEN)) % BLOCKLEN;
        M_buf_copy = buffer_add(M_buf_copy, labelset, labelset_len, offset);
        offset += labelset_len;
        M_buf_copy = buffer_add(M_buf_copy, K_bytes, POINTLEN, offset);
        offset += POINTLEN;
        M_buf_copy = buffer_add(M_buf_copy, extra, extra_len, offset);
        offset += extra_len;
        
        if (offset != bufend || offset != M_start || offset-bufstart != prefix_len)
            throw new IllegalArgumentException();

        hash = crypto_hash_sha512.crypto_hash_sha512(blocks.arraySlice(M_buf, (int)(M_start-prefix_len)), prefix_len+M_len);
        sc_reduce.sc_reduce(hash);
        ge_scalarmult_base.ge_scalarmult_base(R_point, hash);
        ge_p3_tobytes.ge_p3_tobytes(R_bytes, R_point);

        for (int i = 0; i< SCALARLEN; i++){
            r_scalar[i] = hash[i];
        }

        /**
         * STILL NEED TO DO LAST ZEROIZE CALLS
         */
    }


    static void generalized_challenge(byte[] h_scalar,
                                      byte[] labelset, long labelset_len,
                                      byte[] extra   , long extra_len   ,
                                      byte[] R_bytes,
                                      byte[] K_bytes,
                                      byte[] M_buf  , long M_start, long M_len)
    {
        byte[] hash = new byte[HASHLEN];
        long prefix_len = 0;
        byte[] M_buf_copy = M_buf;

//        if (h_scalar == null)
//            throw new IllegalArgumentException();

        h_scalar = new byte[SCALARLEN];

//        if (!gen_labelset.labelset_validate(labelset, labelset_len))
//            throw new IllegalArgumentException();

//        if (R_bytes == null || K_bytes == null || M_buf == null)
//            throw new IllegalArgumentException();
//        if (extra == null && extra_len != null)
//            throw new IllegalArgumentException();
//        if (extra != null && extra_len == 0)
//            throw new IllegalArgumentException();
//        if (extra != null && gen_labelset.labelset_is_empty(labelset, labelset_len))
//            throw new IllegalArgumentException();
        if (gen_labelset.labelset_is_empty(labelset, labelset_len)){
//            if (2*VXEdDSA_consts.POINTLEN > M_start)
//                throw new IllegalArgumentException();

//            if (extra != null && extra_len != 0)
//                throw new IllegalArgumentException();

            for(int i = (int)(M_start-2* POINTLEN); i < POINTLEN; i++){
                M_buf[i] = R_bytes[i-(int)(M_start-2* POINTLEN)];
                M_buf[i+ POINTLEN] = K_bytes[i-(int)(M_start-2* POINTLEN)];
            }
        } else {
            prefix_len = 3* POINTLEN + 2*labelset_len + extra_len;

//            if (prefix_len > M_start)
//                throw new IllegalArgumentException();

            int bufstart = (int)(M_start-prefix_len);
            int bufend = (int)M_start;
            int offset = bufstart;
            M_buf_copy = buffer_add(M_buf_copy, B_bytes, POINTLEN, offset);
            offset += POINTLEN;
            M_buf_copy = buffer_add(M_buf_copy, labelset, labelset_len, offset);
            offset += labelset_len;
            M_buf_copy = buffer_add(M_buf_copy, R_bytes, POINTLEN, offset);
            offset += POINTLEN;
            M_buf_copy = buffer_add(M_buf_copy, labelset, labelset_len, offset);
            offset += labelset_len;
            M_buf_copy = buffer_add(M_buf_copy, K_bytes, POINTLEN, offset);
            offset += POINTLEN;
            M_buf_copy = buffer_add(M_buf_copy, extra, extra_len, offset);
            offset += extra_len;

//            if (offset != + M_start || offset != prefix_len)
//                throw new IllegalArgumentException();
        }

        M_buf = M_buf_copy;

        hash = crypto_hash_sha512.crypto_hash_sha512(blocks.arraySlice(M_buf, (int)(M_start-prefix_len)), (int)(prefix_len+M_len));
        sc_reduce.sc_reduce(hash);
        for (int i=0; i<SCALARLEN;i++){
            h_scalar[i] = hash[i];
        }
    }

    static void generalized_prove(byte[] out_scalar, byte[] r_scalar,
                                  byte[] k_scalar  , byte[] h_scalar)
    {
        sc_muladd.sc_muladd(out_scalar, h_scalar, k_scalar, r_scalar);
    }

}
