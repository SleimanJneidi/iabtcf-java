package com.iabtcf.decoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.iabtcf.BitVector;
import com.iabtcf.model.TCModel;
import com.iabtcf.v2.BitVectorTCModelV2;
import com.iabtcf.v2.FieldConstants;

public class TCModelDecoderImpl implements TCModelDecoder {

    private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

    @Override
    public TCModel decode(String consentString) {
        try (DotSeparatedInputStream is = new DotSeparatedInputStream(consentString);
             InputStream decodedStream = DECODER.wrap(is)) {
            BitVector core = BitVector.from(decodedStream);
            int version =
                    core.readUnsignedInt(
                            FieldConstants.CoreStringConstants.VERSION_OFFSET,
                            FieldConstants.Type.TINY_INT.length());

            switch (version) {
                case 1:
                    // TODO : add version1
                    throw new UnsupportedOperationException("Version 1 is unsupported yet");
                case 2:
                    List<BitVector> remainingVectors = new ArrayList<>();
                    while (is.hasNext()) {
                        try (InputStream nextInputStream = DECODER.wrap(is)) {
                            BitVector nextBitVector = BitVector.from(nextInputStream);
                            remainingVectors.add(nextBitVector);
                        }
                    }
                    return BitVectorTCModelV2.fromBitVector(core, remainingVectors.toArray(new BitVector[0]));
                default:
                    throw new UnsupportedOperationException("Version " + version + "is unsupported yet");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
