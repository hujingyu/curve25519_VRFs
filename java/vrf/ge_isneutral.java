package org.whispersystems.curve25519.java.vrf;

import org.whispersystems.curve25519.java.fe_0;
import org.whispersystems.curve25519.java.ge_p3;

public class ge_isneutral {

    static boolean ge_isneutral(ge_p3 p){

        int[] zero = new int[10];
        fe_0.fe_0(zero);

        return ((fe_isequal.fe_isequal(p.X, zero) & fe_isequal.fe_isequal(p.X, p.Z)) == 0);
    }

}
