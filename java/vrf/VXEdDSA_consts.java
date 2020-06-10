package org.whispersystems.curve25519.java.vrf;

public class VXEdDSA_consts {

    //    #define LABELSETMAXLEN 512
//            #define LABELMAXLEN 128
//            #define BUFLEN 1024
//            #define BLOCKLEN 128 /* SHA512 */
//            #define HASHLEN 64   /* SHA512 */
//            #define POINTLEN 32
//            #define SCALARLEN 32
//            #define RANDLEN 32
//            #define SIGNATURELEN 64
//            #define VRFSIGNATURELEN 96
//            #define VRFOUTPUTLEN 32
//            #define MSTART 2048
//            #define MSGMAXLEN 1048576

    static int LABELSETMAXLEN   = 512    ;
    static int LABELMAXLEN      = 128    ;
    static int BUFLEN           = 1024   ;
    static int BLOCKLEN         = 128    ;
    static int HASHLEN          = 64     ;
    static int POINTLEN         = 32     ;
    static int SCALARLEN        = 32     ;
    static int RANDLEN          = 32     ;
    static int SIGNATURELEN     = 64     ;
    static int VRFSIGNATURELEN  = 32     ;
    static int VRFOUTPUTLEN     = 32     ;
    static int MSTART           = 2048   ;
    static int MSGMAXLEN        = 1048576;

}
