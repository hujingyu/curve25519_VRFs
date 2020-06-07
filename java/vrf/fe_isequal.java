package org.whispersystems.curve25519.java.vrf;

import org.whispersystems.curve25519.java.fe_isnonzero;
import org.whispersystems.curve25519.java.fe_sub;

public class fe_isequal {

    public static int fe_isequal(int[] f, int[] g) {

        int[] h = new int[10];
        fe_sub.fe_sub(h, f, g);

        return 1 ^ (1 & (fe_isnonzero.fe_isnonzero(h) >> 8));

    }
}
