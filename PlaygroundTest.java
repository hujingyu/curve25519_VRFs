package org.whispersystems.curve25519;
import org.whispersystems.curve25519.java.*;

import static org.junit.jupiter.api.Assertions.*;

class PlaygroundTest {

    Sha512 provider;
    Curve25519 Instance;
    JCESecureRandomProvider randomProvider;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

//        provider = new JCESha512Provider();
//
//        randomProvider = new JCESecureRandomProvider();
//
//        Instance = Curve25519.getInstance("NATIVE");


    }

    @org.junit.jupiter.api.Test
    void randooooo() {


        char stick = 0xFF;

        System.out.println("YEE");
        System.out.println(stick);

        skrrt(stick);


        System.out.println(Character.toString(stick));
//        Curve25519KeyPair keyPair = Instance.generateKeyPair();
//
//        byte[] msg = new byte[64];
//
//        for (int i=0; i<msg.length; i++){
//            msg[i] = 0x7F;
//        }
//
//
//        byte[] signature = new byte[64];
//
//        assertTrue(curve_sigs.curve25519_vrf_sign(provider, signature, keyPair.getPrivateKey(), msg, msg.length, null) == 0);

    }

    private void skrrt(char pepe) {
        pepe = 0x00;
        System.out.println(pepe);
//        System.out.println(pepe);
    }
}