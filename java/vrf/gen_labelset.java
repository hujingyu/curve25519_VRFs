package org.whispersystems.curve25519.java.vrf;

public class gen_labelset {


    static byte[] B_bytes = {
            0x58, 0x66, 0x66, 0x66, 0x66, 0x66, 0x66, 0x66,
            0x66, 0x66, 0x66, 0x66, 0x66, 0x66, 0x66, 0x66,
            0x66, 0x66, 0x66, 0x66, 0x66, 0x66, 0x66, 0x66,
            0x66, 0x66, 0x66, 0x66, 0x66, 0x66, 0x66, 0x66,
    };



    static byte[] buffer_pad(byte[] buf, int bufptr, int buf_len){

        int count = 0;
        int pad_len = (VXEdDSA_consts.BLOCKLEN - (bufptr % VXEdDSA_consts.BLOCKLEN)) % VXEdDSA_consts.BLOCKLEN;

//        if (buf_len - bufptr < pad_len){
//            throw new IllegalArgumentException();
//        }

        for (count=0; count < pad_len; count++){
//            if (bufptr+count >= buf_len)
//                throw new IllegalArgumentException();

            buf[bufptr+count] = 0;
        }

        return buf;

    }


//    unsigned char* buffer_add(unsigned char* bufptr, const unsigned char* bufend,
//                          const unsigned char* in, const unsigned long in_len)
//    {
//        unsigned long count = 0;
//
//        if (bufptr == NULL || bufend == NULL || bufptr > bufend)
//            return NULL;
//        if (in == NULL && in_len != 0)
//            return NULL;
//        if (bufend - bufptr < in_len)
//            return NULL;
//
//        for (count=0; count < in_len; count++) {
//            if (bufptr >= bufend)
//                return NULL;
//    *bufptr++ = *in++;
//        }
//        return bufptr;
//    }

    static byte[] buffer_add(byte[] buffer, byte[] in, long in_len, int skip){
        byte[] new_buffer = new byte[buffer.length+(int)in_len];

        int count = 0;

        for (count = skip; count < in_len; count++){
                new_buffer[count] = in[count-skip];
        }
        return new_buffer;
    }

//    int labelset_new(unsigned char* labelset, unsigned long* labelset_len, const unsigned long labelset_maxlen,
//                 const unsigned char* protocol_name, const unsigned char protocol_name_len,
//                 const unsigned char* customization_label, const unsigned char customization_label_len)
//    {
//        unsigned char* bufptr;
//
//  *labelset_len = 0;
//        if (labelset == NULL)
//            return -1;
//        if (labelset_len == NULL)
//            return -1;
//        if (labelset_maxlen > LABELSETMAXLEN)
//            return -1;
//        if (labelset_maxlen < 3 + protocol_name_len + customization_label_len)
//            return -1;
//        if (protocol_name == NULL && protocol_name_len != 0)
//            return -1;
//        if (customization_label == NULL && customization_label_len != 0)
//            return -1;
//        if (protocol_name_len > LABELMAXLEN)
//            return -1;
//        if (customization_label_len > LABELMAXLEN)
//            return -1;
//
//        bufptr = labelset;
//  *bufptr++ = 2;
//  *bufptr++ = protocol_name_len;
//        bufptr = buffer_add(bufptr, labelset + labelset_maxlen, protocol_name, protocol_name_len);
//        if (bufptr != NULL && bufptr < labelset + labelset_maxlen)
//              *bufptr++ = customization_label_len;
//        bufptr = buffer_add(bufptr, labelset + labelset_maxlen,
//                customization_label, customization_label_len);
//
//        if (bufptr != NULL && bufptr - labelset == 3 + protocol_name_len + customization_label_len) {
//              *labelset_len = bufptr - labelset;
//            return 0;
//        }
//        return -1;
//    }


    static long labelset_new(byte[] labelset, long labelset_len, long labelset_maxlen,
                             byte[] protocol_name      , long protocol_name_len      ,
                             byte[] customization_label, long customization_label_len)
    {
        labelset_len = 0;

//        if (labelset == null)
//            throw new IllegalArgumentException();

//        if (labelset_len < 0 )
//            throw new IllegalArgumentException();

//        if (labelset_maxlen < VXEdDSA_consts.LABELSETMAXLEN)
//            throw new IllegalArgumentException();

//        if (labelset_maxlen < 3 + protocol_name_len + customization_label_len)
//            throw new IllegalArgumentException();

//        if (protocol_name == null && protocol_name_len != 0)
//            throw new IllegalArgumentException();

//        if (customization_label == null && customization_label_len != 0)
//            throw new IllegalArgumentException();

//        if (protocol_name_len > VXEdDSA_consts.LABELMAXLEN)
//            throw new IllegalArgumentException();

//        if (customization_label_len > VXEdDSA_consts.LABELMAXLEN)
//            throw new IllegalArgumentException();

        byte[] labelset_buffer = labelset;
        labelset_buffer[0] = 2;
        labelset_buffer[1] = (byte)protocol_name_len;
        labelset_buffer =  buffer_add(labelset_buffer, protocol_name, protocol_name_len, 2);
        if (labelset_buffer.length < labelset_maxlen){
            byte[] customization_label_len_array = {(byte)customization_label_len};
            labelset_buffer = buffer_add(labelset_buffer, customization_label_len_array, 1, 2+(int)protocol_name_len);
        }

        labelset_buffer = buffer_add(labelset_buffer, customization_label, customization_label_len, 3+(int)protocol_name_len);

        if (labelset_buffer.length - labelset_len == 3 + protocol_name_len + customization_label_len){
            labelset = labelset_buffer;
            return labelset_buffer.length;
        }

        throw new IllegalArgumentException();

    }


//    int labelset_add(unsigned char* labelset, unsigned long* labelset_len, const unsigned long labelset_maxlen,
//              const unsigned char* label, const unsigned char label_len)
//    {
//        unsigned char* bufptr;
//        if (labelset_len == NULL)
//            return -1;
//        if (*labelset_len > LABELSETMAXLEN || labelset_maxlen > LABELSETMAXLEN)
//        return -1;
//        if (*labelset_len >= labelset_maxlen || *labelset_len + label_len + 1 > labelset_maxlen)
//        return -1;
//        if (*labelset_len < 3 || labelset_maxlen < 4)
//        return -1;
//        if (label_len > LABELMAXLEN)
//            return -1;
//
//        labelset[0]++;
//        labelset[*labelset_len] = label_len;
//        bufptr = labelset + *labelset_len + 1;
//        bufptr = buffer_add(bufptr, labelset + labelset_maxlen, label, label_len);
//        if (bufptr == NULL)
//            return -1;
//        if (bufptr - labelset >= labelset_maxlen)
//            return -1;
//        if (bufptr - labelset != *labelset_len + 1 + label_len)
//        return -1;
//
//        *labelset_len += (1 + label_len);
//        return 0;
//    }

    static long labelset_add(byte[] labelset, long labelset_len, long labelset_maxlen,
                     byte[] label, long label_len)
    {

//        if (labelset_len < 0 || labelset_len == null)
//            throw new IllegalArgumentException();

//        if (labelset_len > VXEdDSA_consts.LABELSETMAXLEN || labelset_maxlen > VXEdDSA_consts.LABELSETMAXLEN)
//            throw new IllegalArgumentException();

//        if (labelset_len >= labelset_maxlen || labelset_len + label_len + 1 > labelset_maxlen)
//            throw new IllegalArgumentException();

//        if (labelset_len < 3 || labelset_maxlen < 4)
//            throw new IllegalArgumentException();

//        if (label_len > VXEdDSA_consts.LABELMAXLEN)
//            throw new IllegalArgumentException();

        labelset[0]++;
        labelset[(int)labelset_len] = (byte)label_len;
        byte[] labelset_buffer = buffer_add(labelset, label, label_len, (int)labelset_len + 1);

//        if (labelset_buffer.length-labelset_len >= labelset_maxlen)
//            throw new IllegalArgumentException();

//        if (labelset_buffer.length - labelset_len != labelset_len + 1 + label_len)
//            throw new IllegalArgumentException();

        return labelset_len + 1 + label_len;
    }

    static boolean labelset_validate(byte[] labelset, long labelset_len){
        int num_labels = 0;
        int count = 0;
        int offset = 0;
        long label_len = 0;

        if (labelset == null)
            return false;
        if (labelset_len < 3 || labelset_len > VXEdDSA_consts.LABELSETMAXLEN)
            return false;

        num_labels = labelset[0];
        offset = 1;

        for (count = 0; count<num_labels; count++){
            label_len = labelset[offset];
            if (label_len>VXEdDSA_consts.LABELMAXLEN)
                return false;
            offset+=1+label_len;
            if (offset > labelset_len)
                return false;
        }
        if (offset != labelset_len)
            return false;

        return true;
    }


    static boolean labelset_is_empty(byte[] labelset, long labelset_len){
        if (labelset_len != 3)
            return true;
        return false;
    }
}
