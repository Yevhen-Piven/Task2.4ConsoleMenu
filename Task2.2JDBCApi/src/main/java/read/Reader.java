package read;

import java.util.List;

import exception.ReaderException;

public interface Reader<T> {
    public List<String> read(T input) throws ReaderException;
}
