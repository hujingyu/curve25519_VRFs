package org.whispersystems.curve25519.java.vrf;

import org.whispersystems.curve25519.java.*;

public class fe_sqrt {

    static byte[] i_bytes = {
            (byte) 0xb0, (byte) 0xa0, (byte) 0x0e, (byte) 0x4a, (byte) 0x27, (byte) 0x1b, (byte) 0xee, (byte) 0xc4,
            (byte) 0x78, (byte) 0xe4, (byte) 0x2f, (byte) 0xad, (byte) 0x06, (byte) 0x18, (byte) 0x43, (byte) 0x2f,
            (byte) 0xa7, (byte) 0xd7, (byte) 0xfb, (byte) 0x3d, (byte) 0x99, (byte) 0x00, (byte) 0x4d, (byte) 0x2b,
            (byte) 0x0b, (byte) 0xdf, (byte) 0xc1, (byte) 0x4f, (byte) 0x80, (byte) 0x24, (byte) 0x83, (byte) 0x2b
    };


//    void fe_sqrt(fe out, const fe a)
//    {
//        fe exp, b, b2, bi, i;
//    #ifndef NDEBUG
//            fe legendre, zero, one;
//    #endif
//
//        fe_frombytes(i, i_bytes);
//        fe_pow22523(exp, a);             /* b = a^(q-5)/8        */
//
//        /* PRECONDITION: legendre symbol == 1 (square) or 0 (a == zero) */
//    #ifndef NDEBUG
//            fe_sq(legendre, exp);            /* in^((q-5)/4) */
//            fe_sq(legendre, legendre);       /* in^((q-5)/2) */
//            fe_mul(legendre, legendre, a);   /* in^((q-3)/2) */
//            fe_mul(legendre, legendre, a);   /* in^((q-1)/2) */
//
//            fe_0(zero);
//            fe_1(one);
//            assert(fe_isequal(legendre, zero) || fe_isequal(legendre, one));
//    #endif
//
//        fe_mul(b, a, exp);       /* b = a * a^(q-5)/8    */
//        fe_sq(b2, b);            /* b^2 = a * a^(q-1)/4  */
//
//        /* note b^4 == a^2, so b^2 == a or -a
//         * if b^2 != a, multiply it by sqrt(-1) */
//        fe_mul(bi, b, i);
//        fe_cmov(b, bi, 1 ^ fe_isequal(b2, a));
//        fe_copy(out, b);
//
//            /* PRECONDITION: out^2 == a */
//    #ifndef NDEBUG
//            fe_sq(b2, out);
//            assert(fe_isequal(a, b2));
//    #endif
//    }

    public static void fe_sqrt(int[] out, int[] a)
//        throws InternalError
    {

        int[] exp      = new int[10], b    = new int[10], b2  = new int[10], i = new int[10], bi = new int[10];
        int[] legendre = new int[10], zero = new int[10], one = new int[10];

        fe_frombytes.fe_frombytes(i, i_bytes);
        fe_pow22523.fe_pow22523(exp, a);

//        USEFUL FOR DEBUGGING:
//        fe_sq.fe_sq(legendre, exp);
//        fe_sq.fe_sq(legendre, legendre);
//        fe_mul.fe_mul(legendre, legendre, a);
//        fe_mul.fe_mul(legendre, legendre, a);
//
//        fe_0.fe_0(zero);
//        fe_1.fe_1(one);
//        assert(fe_isequal(legendre, zero) || fe_isequal(legendre, one));


        fe_mul.fe_mul(b, a, exp);
        fe_sq.fe_sq(b2, b);

        fe_mul.fe_mul(bi, b, i);
        fe_cmov.fe_cmov(b, bi, 1 ^ fe_isequal.fe_isequal(b2, a));
        fe_copy.fe_copy(out, b);

//        fe_sq.fe_sq(b2, out);
//        assert(fe_isequal.fe_isequal(a, b2))

    }

}
