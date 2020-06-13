package org.whispersystems.curve25519;


import org.whispersystems.curve25519.java.*;

import static org.junit.jupiter.api.Assertions.*;

class PlaygroundTest {

    Sha512 provider;
    Curve25519 Instance;
    JCESecureRandomProvider randomProvider;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        provider = new JCESha512Provider();

        randomProvider = new JCESecureRandomProvider();

        Instance = Curve25519.getInstance(Curve25519.NATIVE);


        byte[] random = new byte[32];
        byte[] signature = new byte[64];
        Curve25519KeyPair kp = Instance.generateKeyPair();
        byte[] privKey = kp.getPublicKey();
        byte[] msg = "Hi".getBytes();
        long msg_len = msg.length;
        byte[] customization_label = "wwop".getBytes();
        long custom_label_len = customization_label.length;
        for (int i=0; i<32; i++){
            random[i] = (byte) randomProvider.nextInt(2^31);
        }

        org.whispersystems.curve25519.java.vrf.curve_veddsa.curve_25519_vrf_sign(signature, privKey, msg, msg_len, random, customization_label, custom_label_len);
        System.out.println(signature);


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