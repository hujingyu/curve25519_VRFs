package org.whispersystems.curve25519.java.vrf;

import org.whispersystems.curve25519.java.*;

public class curve_VXEdDSA {


    private static int LABELSETMAXLEN   = VXEdDSA_consts.LABELSETMAXLEN ;
    private static int LABELMAXLEN   = VXEdDSA_consts.LABELMAXLEN ;
    private static int BUFLEN           = VXEdDSA_consts.BUFLEN         ;
    private static int BLOCKLEN         = VXEdDSA_consts.BLOCKLEN       ;
    private static int HASHLEN          = VXEdDSA_consts.HASHLEN        ;
    private static int POINTLEN         = VXEdDSA_consts.POINTLEN       ;
    private static int SCALARLEN        = VXEdDSA_consts.SCALARLEN      ;
    private static int RANDLEN          = VXEdDSA_consts.RANDLEN        ;
    private static int SIGNATURELEN     = VXEdDSA_consts.SIGNATURELEN   ;
    private static int VRFSIGNATURELEN  = VXEdDSA_consts.VRFSIGNATURELEN;
    private static int VRFOUTPUTLEN     = VXEdDSA_consts.VRFOUTPUTLEN   ;
    private static int MSTART           = VXEdDSA_consts.MSTART         ;
    private static int MSGMAXLEN        = VXEdDSA_consts.MSGMAXLEN      ;



    private static int generalized_calculate_Bv(ge_p3 Bv_point, byte[] labelset, long labelset_len,
                                                byte[] K_bytes, byte[] M_buf   , long M_start     ,
                                                long M_len)
    {
        byte[] bufptr =  M_buf;
        long prefix_len = 0;

        if (!gen_labelset.labelset_validate(labelset, labelset_len))
            return -1;
        if (Bv_point == null || K_bytes == null || M_buf == null)
            return -1;

        prefix_len = 2*VXEdDSA_consts.POINTLEN + labelset_len;
        if (prefix_len>M_start)
            return -1;

        int offset = (int)(M_start - prefix_len);
        bufptr = gen_labelset.buffer_add(bufptr, gen_labelset.B_bytes, VXEdDSA_consts.POINTLEN, offset);
        offset+=VXEdDSA_consts.POINTLEN;
        bufptr = gen_labelset.buffer_add(bufptr, labelset, labelset_len, offset);
        offset+=VXEdDSA_consts.POINTLEN;
        bufptr = gen_labelset.buffer_add(bufptr, K_bytes, VXEdDSA_consts.POINTLEN, (int)(M_start - prefix_len)+gen_labelset.B_bytes.length);
        if (bufptr == null || offset != M_start)
            return -1;

        byte[] M_buf_sliced = new byte[(int)(M_len-M_start+prefix_len)];
        for (int i=0; i<M_buf_sliced.length; i++){
            M_buf_sliced[i] = M_buf[i+(int)(M_start-prefix_len)];
        }

        elligator.hash_to_point(Bv_point, M_buf_sliced, (int)(prefix_len+M_len));

        if (ge_isneutral.ge_isneutral(Bv_point))
            return -1;

        return 0;

    }

//    VRF Sign Pseudocode:
//
//    Bv = hash_to_point(A || M)
//    V = aBv
//    r = hash3(a || V || Z) (mod q)
//    R = rB
//    Rv = rBv
//    h = hash4(A || V || R || Rv || M) (mod q)
//    s = r + ha (mod q)
//    v = hash5(cV) (mod 2b)
//            return (V || h || s), v



//    int generalized_veddsa_25519_sign(
//            unsigned char* signature_out,
//                  const unsigned char* eddsa_25519_pubkey_bytes,
//                  const unsigned char* eddsa_25519_privkey_scalar,
//                  const unsigned char* msg,
//                  const unsigned long msg_len,
//                  const unsigned char* random,
//                  const unsigned char* customization_label,
//                  const unsigned long customization_label_len)
//    {
//        unsigned char labelset[LABELSETMAXLEN];
//        unsigned long labelset_len = 0;
//        ge_p3 Bv_point;
//        ge_p3 Kv_point;
//        ge_p3 Rv_point;
//        unsigned char Bv_bytes[POINTLEN];
//        unsigned char Kv_bytes[POINTLEN];
//        unsigned char Rv_bytes[POINTLEN];
//        unsigned char R_bytes[POINTLEN];
//        unsigned char r_scalar[SCALARLEN];
//        unsigned char h_scalar[SCALARLEN];
//        unsigned char s_scalar[SCALARLEN];
//        unsigned char extra[3*POINTLEN];
//        unsigned char* M_buf = NULL;
//        char* protocol_name = "VEdDSA_25519_SHA512_Elligator2";
//
//        if (signature_out == NULL)
//    goto err;
//        memset(signature_out, 0, VRFSIGNATURELEN);
//
//        if (eddsa_25519_pubkey_bytes == NULL)
//    goto err;
//        if (eddsa_25519_privkey_scalar == NULL)
//    goto err;
//        if (msg == NULL)
//    goto err;
//        if (customization_label == NULL && customization_label_len != 0)
//    goto err;
//        if (customization_label_len > LABELMAXLEN)
//    goto err;
//        if (msg_len > MSGMAXLEN)
//    goto err;
//
//        if ((M_buf = malloc(msg_len + MSTART)) == 0) {
//    goto err;
//        }
//        memcpy(M_buf + MSTART, msg, msg_len);
//
//        //  labelset = new_labelset(protocol_name, customization_label)
//        if (labelset_new(labelset, &labelset_len, LABELSETMAXLEN,
//            (unsigned char*)protocol_name, strlen(protocol_name),
//            customization_label, customization_label_len) != 0)
//    goto err;
//
//        //  labelset1 = add_label(labels, "1")
//        //  Bv = hash(hash(labelset1 || K) || M)
//        //  Kv = k * Bv
//        labelset_add(labelset, &labelset_len, LABELSETMAXLEN, (unsigned char*)"1", 1);
//        if (generalized_calculate_Bv(&Bv_point, labelset, labelset_len,
//            eddsa_25519_pubkey_bytes, M_buf, MSTART, msg_len) != 0)
//    goto err;
//        ge_scalarmult(&Kv_point, eddsa_25519_privkey_scalar, &Bv_point);
//        ge_p3_tobytes(Bv_bytes, &Bv_point);
//        ge_p3_tobytes(Kv_bytes, &Kv_point);
//
//        //  labelset2 = add_label(labels, "2")
//        //  R, r = commit(labelset2, (Bv || Kv), (K,k), Z, M)
//        labelset[labelset_len-1] = (unsigned char)'2';
//        memcpy(extra, Bv_bytes, POINTLEN);
//        memcpy(extra + POINTLEN, Kv_bytes, POINTLEN);
//        if (generalized_commit(R_bytes, r_scalar,
//                labelset, labelset_len,
//                extra, 2*POINTLEN,
//                eddsa_25519_pubkey_bytes, eddsa_25519_privkey_scalar,
//                random, M_buf, MSTART, msg_len) != 0)
//    goto err;
//
//        //  Rv = r * Bv
//        ge_scalarmult(&Rv_point, r_scalar, &Bv_point);
//        ge_p3_tobytes(Rv_bytes, &Rv_point);
//
//        //  labelset3 = add_label(labels, "3")
//        //  h = challenge(labelset3, (Bv || Kv || Rv), R, K, M)
//        labelset[labelset_len-1] = (unsigned char)'3';
//        memcpy(extra + 2*POINTLEN, Rv_bytes, POINTLEN);
//        if (generalized_challenge(h_scalar,
//                labelset, labelset_len,
//                extra, 3*POINTLEN,
//                R_bytes, eddsa_25519_pubkey_bytes,
//                M_buf, MSTART, msg_len) != 0)
//    goto err;
//
//        //  s = prove(r, k, h)
//        if (generalized_prove(s_scalar, r_scalar, eddsa_25519_privkey_scalar, h_scalar) != 0)
//    goto err;
//
//        //  return (Kv || h || s)
//        memcpy(signature_out, Kv_bytes, POINTLEN);
//        memcpy(signature_out + POINTLEN, h_scalar, SCALARLEN);
//        memcpy(signature_out + POINTLEN + SCALARLEN, s_scalar, SCALARLEN);
//
//        zeroize(r_scalar, SCALARLEN);
//        zeroize_stack();
//        free(M_buf);
//        return 0;
//
//        err:
//        zeroize(r_scalar, SCALARLEN);
//        zeroize_stack();
//        free(M_buf);
//        return -1;
//    }


    static int[] arraySliceToInt(byte[] array, int loc){
        int[] array_copy =  new int[array.length-loc];

        for (int i = loc; i<array.length; i++){
            array_copy[i-loc] = array[i];
        }

        return array_copy;

    }

    public static int sign(
        byte[] signature,
        byte[] eddsa_25519_pubkey_bytes,
        byte[] eddsa_25519_privkey_scalar,
        byte[] msg, long msg_len,
        byte[] random,
        byte[] customization_label,
        long customization_label_len
    )
    {
        byte[] labelset = new byte[LABELSETMAXLEN];
        long labelset_len = 0;
        ge_p3 Bv_point = new ge_p3(), Kv_point = new ge_p3(), Rv_point = new ge_p3();
        byte[] Bv_bytes = new byte[POINTLEN], Kv_bytes = new byte[POINTLEN], Rv_bytes = new byte[POINTLEN];
        byte[] R_bytes  = new byte[POINTLEN];
        byte[] r_scalar = new byte[SCALARLEN], h_scalar = new byte[SCALARLEN], s_scalar = new byte[SCALARLEN];
        byte[] extra = new byte[3*POINTLEN];
        byte[] M_buf = new byte[(int)msg_len+MSTART];
        byte[] protocol_name = "VEdDSA_25519_SHA512_Elligator2".getBytes();

        /**
         * Still gotta handle rest of checks
         */
//        if (customization_label == null && customization_label_len != 0)
//            throw IllegalArgumentException;
//        if (customization_label_len > LABELMAXLEN)
//            throw IllegalArgumentException;
//        if (mlen > MSGMAXLEN)
//            throw IllegalArgumentException;

        for (int i=0; i<msg_len; i++){
            M_buf[MSTART+i] = msg[i];
        }

        labelset_len = gen_labelset.labelset_new(labelset, labelset_len, LABELMAXLEN,
                                                 protocol_name, protocol_name.length,
                                                 customization_label, customization_label_len);

        byte[] label = {1};
        labelset_len = gen_labelset.labelset_add(labelset, labelset_len, LABELSETMAXLEN, label, 1);


        generalized_calculate_Bv(Bv_point, labelset, labelset_len, eddsa_25519_pubkey_bytes, M_buf, MSTART, msg_len);

        ge_scalarmult.ge_scalarmult(Kv_point, eddsa_25519_privkey_scalar, Bv_point);
        ge_p3_tobytes.ge_p3_tobytes(Bv_bytes, Bv_point);
        ge_p3_tobytes.ge_p3_tobytes(Kv_bytes, Kv_point);

        labelset[(int)labelset_len-1] = 2;
        for (int i = 0; i<POINTLEN; i++){
            extra[i] = Bv_bytes[i];
        }
        for (int i = POINTLEN; i<POINTLEN*2; i++){
            extra[i] = Kv_bytes[i-POINTLEN];
        }

        gen_eddsa.generalized_commit(R_bytes, r_scalar,
                                    labelset, labelset_len,
                                    extra, 2*POINTLEN,
                                    eddsa_25519_pubkey_bytes, eddsa_25519_privkey_scalar,
                                    random, M_buf, MSTART, msg_len);

        ge_scalarmult.ge_scalarmult(Rv_point, r_scalar, Bv_point);
        ge_p3_tobytes.ge_p3_tobytes(Rv_bytes, Rv_point);

        labelset[(int)labelset_len-1] = 3;
        for (int i=2*POINTLEN; i<3*POINTLEN; i++){
            extra[i] = Rv_bytes[i-2*POINTLEN];
        }
        gen_eddsa.generalized_challenge(h_scalar,
                                        labelset, labelset_len,
                                        extra, 3*POINTLEN,
                                        R_bytes, eddsa_25519_pubkey_bytes,
                                        M_buf, MSTART, msg_len);
        gen_eddsa.generalized_prove(s_scalar, r_scalar, eddsa_25519_privkey_scalar, h_scalar);

        for (int i=0; i<POINTLEN; i++){
            signature[i] = Kv_bytes[i];
        }
        for (int i = POINTLEN; i<POINTLEN+SCALARLEN; i++){
            signature[i] = h_scalar[i-SCALARLEN];
        }
        for(int i = POINTLEN+SCALARLEN; i<POINTLEN+2*SCALARLEN; i++){
            signature[i] = s_scalar[i-POINTLEN-SCALARLEN];
        }

        /**
         * Zeroize?
         */

        return 0;
    }
}
