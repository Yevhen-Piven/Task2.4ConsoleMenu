package generate;

import java.util.ArrayList;
import java.util.List;

import entity.Course;
import exception.ReaderException;
import read.StringReader;

public class CourseGenerator implements Generator<Course> {

    private String fileFacultets = "facultets.txt";
    private String fileDescriptions = "descriptions.txt";
    private List<Course> courses = new ArrayList<>();
    private StringReader<String> stringReader = new StringReader<>();

    @Override
    public List<Course> generate(int number) throws ReaderException {
        for (int i = 0; i < number; i++) {
            courses.add(new Course(i + 1, stringReader.read(fileFacultets).get(i),
                    stringReader.read(fileDescriptions).get(i)));
        }
        return courses;
    }
}
