package org.whispersystems.curve25519.java.vrf;


import org.whispersystems.curve25519.java.*;

public class elligator {

    //    unsigned int legendre_is_nonsquare(fe in)
    //    {
    //        fe temp;
    //        unsigned char bytes[32];
    //        fe_pow22523(temp, in);  /* temp = in^((q-5)/8) */
    //        fe_sq(temp, temp);      /*        in^((q-5)/4) */
    //        fe_sq(temp, temp);      /*        in^((q-5)/2) */
    //        fe_mul(temp, temp, in); /*        in^((q-3)/2) */
    //        fe_mul(temp, temp, in); /*        in^((q-1)/2) */
    //
    //        /* temp is now the Legendre symbol:
    //         * 1  = square
    //         * 0  = input is zero
    //         * -1 = nonsquare
    //         */
    //        fe_tobytes(bytes, temp);
    //        return 1 & bytes[31];
    //    }

    static int legendre_is_nonsquare(int[] f_in)
    {
        int[] fe_tmp = new int[10];

        byte[] fe_tmp_bytes = new byte[32];

        fe_pow22523.fe_pow22523(fe_tmp, f_in        );
        fe_sq.fe_sq(            fe_tmp, fe_tmp      );
        fe_sq.fe_sq(            fe_tmp, fe_tmp      );
        fe_mul.fe_mul(          fe_tmp, fe_tmp, f_in);
        fe_mul.fe_mul(          fe_tmp, fe_tmp, f_in);

        fe_tobytes.fe_tobytes(fe_tmp_bytes, fe_tmp);

        return 1&fe_tmp_bytes[32];
    }



    //    void elligator(fe u, const fe r)
    //    {
    //        /* r = input
    //         * x = -A/(1+2r^2)                # 2 is nonsquare
    //         * e = (x^3 + Ax^2 + x)^((q-1)/2) # legendre symbol
    //         * if e == 1 (square) or e == 0 (because x == 0 and 2r^2 + 1 == 0)
    //         *   u = x
    //         * if e == -1 (nonsquare)
    //         *   u = -x - A
    //         */
    //        fe A, one, twor2, twor2plus1, twor2plus1inv;
    //        fe x, e, Atemp, uneg;
    //        unsigned int nonsquare;
    //
    //        fe_1(one);
    //        fe_0(A);
    //        A[0] = 486662;                         /* A = 486662 */
    //
    //        fe_sq2(twor2, r);                      /* 2r^2 */
    //        fe_add(twor2plus1, twor2, one);        /* 1+2r^2 */
    //        fe_invert(twor2plus1inv, twor2plus1);  /* 1/(1+2r^2) */
    //        fe_mul(x, twor2plus1inv, A);           /* A/(1+2r^2) */
    //        fe_neg(x, x);                          /* x = -A/(1+2r^2) */
    //
    //        fe_mont_rhs(e, x);                     /* e = x^3 + Ax^2 + x */
    //        nonsquare = legendre_is_nonsquare(e);
    //
    //        fe_0(Atemp);
    //        fe_cmov(Atemp, A, nonsquare);          /* 0, or A if nonsquare */
    //        fe_add(u, x, Atemp);                   /* x, or x+A if nonsquare */
    //        fe_neg(uneg, u);                       /* -x, or -x-A if nonsquare */
    //        fe_cmov(u, uneg, nonsquare);           /* x, or -x-A if nonsquare */
    //    }


    static void elligator(int[] fe_u, int[] fe_r)
    {
        int[] A = new int[10], one = new int[10], twor2 = new int[10], twor2plus1 = new int[10], twor2plus1inv = new int[10];
        int[] x = new int[10], e = new int[10], Atemp = new int[10], uneg = new int[10];

        int nonsquare;

        fe_1.fe_1(one); fe_0.fe_0(A); A[0] = 486662;

        fe_sq2.fe_sq2(twor2, fe_r);
        fe_add.fe_add(twor2plus1, twor2, one);
        fe_invert.fe_invert(twor2plus1inv, twor2plus1);
        fe_mul.fe_mul(x, twor2plus1inv, A);
        fe_neg.fe_neg(x,x);

        fe_mont_rhs.fe_mont_rhs(e, x);
        nonsquare = legendre_is_nonsquare(e);

        fe_0.fe_0(Atemp);
        fe_cmov.fe_cmov(Atemp, A, nonsquare);
        fe_add.fe_add(fe_u, x, Atemp);
        fe_neg.fe_neg(uneg, fe_u);
        fe_cmov.fe_cmov(fe_u, uneg, nonsquare);

    }

//    void hash_to_point(ge_p3* p, const unsigned char* in, const unsigned long in_len)
//    {
//        unsigned char hash[64];
//        fe h, u;
//        unsigned char sign_bit;
//        ge_p3 p3;
//
//        crypto_hash_sha512(hash, in, in_len);
//
//        /* take the high bit as Edwards sign bit */
//        sign_bit = (hash[31] & 0x80) >> 7;
//        hash[31] &= 0x7F;
//        fe_frombytes(h, hash);
//        elligator(u, h);
//
//        ge_montx_to_p3(&p3, u, sign_bit);
//        ge_scalarmult_cofactor(p, &p3);
//    }



    static void hash_to_point(ge_p3 p, int[] in, long in_len)
    {
        int[] h = new int[10], u = new int[10];
        int sign_bit;
        ge_p3 p3;

        byte[] hash = crypto_hash_sha512.crypto_hash_sha512(in, in_len);

        sign_bit = (hash[31] & 0x80) >> 7;
        hash[31] &= 0x7F;
        fe_frombytes.fe_frombytes(h, hash);
        elligator(u, h);

        p3 = ge_montx_to_p3.ge_montx_to_p3(u, sign_bit);
        p = ge_scalarmult_cofactor.ge_scalarmult_cofactor(p3);
    }
}
