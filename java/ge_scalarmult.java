package org.whispersystems.curve25519.java;


public class ge_scalarmult {

    static int equal(byte b,byte c)
    {
        int ub = b;
        int uc = c;
        int x = ub ^ uc; /* 0: yes; 1..255: no */
        int y = x; /* 0: yes; 1..255: no */
        y -= 1; /* 4294967295: yes; 0..254: no */
        y >>>= 31; /* 1: yes; 0: no */
        return y;
    }

    static int negative(byte b)
    {
        long x = b; /* 18446744073709551361..18446744073709551615: yes; 0..255: no */
        x >>= 63; /* 1: yes; 0: no */
        return (int)x;
    }

    static void cmov(ge_cached t,ge_cached u,int b)
    {
        fe_cmov.fe_cmov(t.YplusX,u.YplusX,b);
        fe_cmov.fe_cmov(t.YminusX,u.YminusX,b);
        fe_cmov.fe_cmov(t.Z, u.Z, b);
        fe_cmov.fe_cmov(t.T2d,u.T2d,b);

    }

    static void select(ge_cached t,ge_cached pre[],byte b)
    {
        ge_cached minust = new ge_cached();

        int bnegative = negative(b);
        int babs = b - (((-bnegative) & b) << 1);

        fe_1.fe_1(t.YminusX);
        fe_1.fe_1(t.YplusX);
        fe_1.fe_1(t.Z);
        fe_0.fe_0(t.T2d);

        cmov(t,pre[0],equal((byte)babs,(byte)1));
        cmov(t,pre[1],equal((byte)babs,(byte)2));
        cmov(t,pre[2],equal((byte)babs,(byte)3));
        cmov(t,pre[3],equal((byte)babs,(byte)4));
        cmov(t,pre[4],equal((byte)babs,(byte)5));
        cmov(t,pre[5],equal((byte)babs,(byte)6));
        cmov(t,pre[6],equal((byte)babs,(byte)7));
        cmov(t,pre[7],equal((byte)babs,(byte)8));
        fe_copy.fe_copy(minust.YplusX,t.YminusX);
        fe_copy.fe_copy(minust.YminusX,t.YplusX);
        fe_neg.fe_neg(minust.T2d,t.T2d);
        cmov(t,minust,bnegative);
    }

    public static void ge_scalarmult(ge_p3 h, byte[] a, ge_p3 A){

        byte[] e = new byte[64];
        byte carry;

        ge_p1p1 r = new ge_p1p1();
        ge_p2   s = new ge_p2();
        ge_p3 t0  = new ge_p3(), t1 = new ge_p3(), t2 = new ge_p3();
        ge_cached t = new ge_cached(); ge_cached pre[] = new ge_cached[8];
        int i;

        for (i=0; i<32; ++i){
            e[2*i+0] = (byte)((a[i] >> 0) & 15);
            e[2*i+1] = (byte)((a[i] >> 4) & 15);
        }

        carry = 0;
        for (i=0;i<63; ++i){
            e[i] += carry;
            carry = (byte)(e[i] + 8);
            carry >>= 4;
            e[i] -= carry << 4;
        }
        e[63] += carry;

        ge_p3_to_cached.ge_p3_to_cached(pre[0], A);

        ge_p3_dbl.ge_p3_dbl(r, A);
        ge_p1p1_to_p3.ge_p1p1_to_p3(t0, r);
        ge_p3_to_cached.ge_p3_to_cached(pre[1], t0);

        ge_add.ge_add(r, A, pre[1]);
        ge_p1p1_to_p3.ge_p1p1_to_p3(t1, r);
        ge_p3_to_cached.ge_p3_to_cached(pre[2], t1);

        ge_p3_dbl.ge_p3_dbl(r, t0);
        ge_p1p1_to_p3.ge_p1p1_to_p3(t0, r);
        ge_p3_to_cached.ge_p3_to_cached(pre[3], t0);

        ge_add.ge_add(r, A, pre[3]);
        ge_p1p1_to_p3.ge_p1p1_to_p3(t2, r);
        ge_p3_to_cached.ge_p3_to_cached(pre[4], t2);

        ge_p3_dbl.ge_p3_dbl(r, t1);
        ge_p1p1_to_p3.ge_p1p1_to_p3(t1, r);
        ge_p3_to_cached.ge_p3_to_cached(pre[5], t1);

        ge_add.ge_add(r, A, pre[5]);
        ge_p1p1_to_p3.ge_p1p1_to_p3(t1, r);
        ge_p3_to_cached.ge_p3_to_cached(pre[6], t1);

        ge_p3_dbl.ge_p3_dbl(r, t0);
        ge_p1p1_to_p3.ge_p1p1_to_p3(t0, r);
        ge_p3_to_cached.ge_p3_to_cached(pre[7], t0);

        ge_p3_0.ge_p3_0(h);

        for (i=63; i>0; i--){
            select(t, pre, e[i]);
            ge_add.ge_add(r, h, t);
            ge_p1p1_to_p2.ge_p1p1_to_p2(s, r);

            ge_p2_dbl.ge_p2_dbl(r, s); ge_p1p1_to_p2.ge_p1p1_to_p2(s, r);
            ge_p2_dbl.ge_p2_dbl(r, s); ge_p1p1_to_p2.ge_p1p1_to_p2(s, r);
            ge_p2_dbl.ge_p2_dbl(r, s); ge_p1p1_to_p2.ge_p1p1_to_p2(s, r);
            ge_p2_dbl.ge_p2_dbl(r, s); ge_p1p1_to_p3.ge_p1p1_to_p3(h, r);
        }

        select(t, pre, e[0]);
        ge_add.ge_add(r, h, t);
        ge_p1p1_to_p3.ge_p1p1_to_p3(h, r);
    }
}