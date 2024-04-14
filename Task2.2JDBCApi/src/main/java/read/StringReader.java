package read;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.ReaderInputStream;

import exception.ReaderException;

public class StringReader<T> implements Reader<String> {

    @Override
    public List<String> read(String File) throws ReaderException {
        try (InputStream inputStream = ReaderInputStream.class.getClassLoader().getResourceAsStream(File);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
            String line;
            List<String> dataList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                dataList.add(line);
            }
            return dataList;
        } catch (IOException e) {
            throw new ReaderException("File not found");
        }
    }
}
