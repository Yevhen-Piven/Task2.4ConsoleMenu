package generate;

import java.io.IOException;
import java.util.List;

import exception.ReaderException;

public interface Generator<T> {

    public List<T> generate(int number) throws IOException, ReaderException;
}
