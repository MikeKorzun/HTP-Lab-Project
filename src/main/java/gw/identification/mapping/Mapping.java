package gw.identification.mapping;

import java.io.IOException;

public interface Mapping<T, R> {
    R toObject(T from) throws IOException;
}
