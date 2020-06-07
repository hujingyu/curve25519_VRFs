package org.whispersystems.curve25519.java.vrf;

import org.whispersystems.curve25519.java.*;

public class ge_montx_to_p3 {

//    static unsigned char A_bytes[32] = {
//                0x06, 0x7e, 0x45, 0xff, 0xaa, 0x04, 0x6e, 0xcc,
//                0x82, 0x1a, 0x7d, 0x4b, 0xd1, 0xd3, 0xa1, 0xc5,
//                0x7e, 0x4f, 0xfc, 0x03, 0xdc, 0x08, 0x7b, 0xd2,
//                0xbb, 0x06, 0xa0, 0x60, 0xf4, 0xed, 0x26, 0x0f
//    };

    static byte[] A_bytes = {
                (byte) 0x06, (byte) 0x7e, (byte) 0x45, (byte) 0xff, (byte) 0xaa, (byte) 0x04, (byte) 0x6e, (byte) 0xcc,
                (byte) 0x82, (byte) 0x1a, (byte) 0x7d, (byte) 0x4b, (byte) 0xd1, (byte) 0xd3, (byte) 0xa1, (byte) 0xc5,
                (byte) 0x7e, (byte) 0x4f, (byte) 0xfc, (byte) 0x03, (byte) 0xdc, (byte) 0x08, (byte) 0x7b, (byte) 0xd2,
                (byte) 0xbb, (byte) 0x06, (byte) 0xa0, (byte) 0x60, (byte) 0xf4, (byte) 0xed, (byte) 0x26, (byte) 0x0f
    };



//    void ge_montx_to_p3(ge_p3* p, const fe u, const unsigned char ed_sign_bit)
//    {
//        fe x, y, A, v, v2, iv, nx;
//
//        fe_frombytes(A, A_bytes);
//
//        /* given u, recover edwards y */
//        /* given u, recover v */
//        /* given u and v, recover edwards x */
//
//        fe_montx_to_edy(y, u);       /* y = (u - 1) / (u + 1) */
//
//        fe_mont_rhs(v2, u);          /* v^2 = u(u^2 + Au + 1) */
//        fe_sqrt(v, v2);              /* v = sqrt(v^2) */
//
//        fe_mul(x, u, A);             /* x = u * sqrt(-(A+2)) */
//        fe_invert(iv, v);            /* 1/v */
//        fe_mul(x, x, iv);            /* x = (u/v) * sqrt(-(A+2)) */
//
//        fe_neg(nx, x);               /* negate x to match sign bit */
//        fe_cmov(x, nx, fe_isnegative(x) ^ ed_sign_bit);
//
//        fe_copy(p->X, x);
//        fe_copy(p->Y, y);
//        fe_1(p->Z);
//        fe_mul(p->T, p->X, p->Y);
//
//        /* POSTCONDITION: check that p->X and p->Y satisfy the Ed curve equation */
//        /* -x^2 + y^2 = 1 + dx^2y^2 */
//        #ifndef NDEBUG
//        {
//            fe one, d, x2, y2, x2y2, dx2y2;
//
//            unsigned char dbytes[32] = {
//            0xa3, 0x78, 0x59, 0x13, 0xca, 0x4d, 0xeb, 0x75,
//                    0xab, 0xd8, 0x41, 0x41, 0x4d, 0x0a, 0x70, 0x00,
//                    0x98, 0xe8, 0x79, 0x77, 0x79, 0x40, 0xc7, 0x8c,
//                    0x73, 0xfe, 0x6f, 0x2b, 0xee, 0x6c, 0x03, 0x52
//        };
//
//            fe_frombytes(d, dbytes);
//            fe_1(one);
//            fe_sq(x2, p->X);                /* x^2 */
//            fe_sq(y2, p->Y);                /* y^2 */
//
//            fe_mul(dx2y2, x2, y2);           /* x^2y^2 */
//            fe_mul(dx2y2, dx2y2, d);         /* dx^2y^2 */
//            fe_add(dx2y2, dx2y2, one);       /* dx^2y^2 + 1 */
//
//            fe_neg(x2y2, x2);                /* -x^2 */
//            fe_add(x2y2, x2y2, y2);          /* -x^2 + y^2 */
//
//            assert(fe_isequal(x2y2, dx2y2));
//        }
//    #endif
//    }

    public static ge_p3 ge_montx_to_p3(int[] u, int ed_sign_bit){

        int[] x = new int[10], y = new int[10], A = new int[10], v = new int[10], v2 = new int[10], iv = new int[10], nx = new int[10];

//        int[] ge_p3_x = new int[10], ge_p3_y = new int[10], ge_p3_z = new int[10], ge_p3_T = new int[10];
        ge_p3 p = new ge_p3();

        fe_frombytes.fe_frombytes(A, A_bytes);

        fe_montx_to_edy.fe_montx_to_edy(y, u);

        fe_mont_rhs.fe_mont_rhs(v2, u);
        fe_sqrt.fe_sqrt(v, v2);

        fe_mul.fe_mul(x, u, A);
        fe_invert.fe_invert(iv, v);
        fe_mul.fe_mul(x, x, iv);

        fe_neg.fe_neg(nx, x);
        fe_cmov.fe_cmov(x, nx, fe_isnegative.fe_isnegative(x) ^ ed_sign_bit);

        fe_copy.fe_copy(p.X, x); fe_copy.fe_copy(p.Y, y); fe_1.fe_1(p.Z);
        fe_mul.fe_mul(p.T, p.X, p.Y);


//        Debugging:
//        int[] one = new int[10], d = new int[10], x2 = new int[10], y2 = new int[10], x2y2 = new int[10], dx2y2 = new int[10];
//
//        byte[] dbytes = {
//                (byte) 0xa3, (byte) 0x78, (byte) 0x59, (byte) 0x13, (byte) 0xca, (byte) 0x4d, (byte) 0xeb, (byte) 0x75,
//                (byte) 0xab, (byte) 0xd8, (byte) 0x41, (byte) 0x41, (byte) 0x4d, (byte) 0x0a, (byte) 0x70, (byte) 0x00,
//                (byte) 0x98, (byte) 0xe8, (byte) 0x79, (byte) 0x77, (byte) 0x79, (byte) 0x40, (byte) 0xc7, (byte) 0x8c,
//                (byte) 0x73, (byte) 0xfe, (byte) 0x6f, (byte) 0x2b, (byte) 0xee, (byte) 0x6c, (byte) 0x03, (byte) 0x52
//         };
//
//        fe_frombytes.fe_frombytes(d, dbytes);
//        fe_1.fe_1(one);
//        fe_sq.fe_sq(x2, p.X); fe_sq.fe_sq(y2, p.Y);
//
//        fe_mul.fe_mul(dx2y2, x2, y2); fe_mul.fe_mul(dx2y2, dx2y2, d);
//        fe_add.fe_add(dx2y2, dx2y2, one);
//
//        fe_neg.fe_neg(x2y2, x2); fe_add.fe_add(x2y2, x2y2, y2);
//
//        assert(fe_isequal.fe_isequal(x2y2, dx2y2));

        return p;
    }

}
