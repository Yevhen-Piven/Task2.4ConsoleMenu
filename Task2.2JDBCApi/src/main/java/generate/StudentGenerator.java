package generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Student;
import exception.ReaderException;
import read.StringReader;

public class StudentGenerator implements Generator<Student> {

    private String fileNames = "names.txt";
    private String fileSurnames = "surnames.txt";
    private StringReader<String> stringReader = new StringReader<>();
    private List<Student> students = new ArrayList<>();

    @Override
    public List<Student> generate(int number) throws ReaderException {
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int random_id_course = random.nextInt(10) + 1;
            int random_id_name = random.nextInt(20);
            int random_id_surname = random.nextInt(20);
            boolean active = true;
            students.add(new Student(i + 1, random_id_course, stringReader.read(fileNames).get(random_id_name),
                    stringReader.read(fileSurnames).get(random_id_surname), active));
        }
        return students;
    }
}
