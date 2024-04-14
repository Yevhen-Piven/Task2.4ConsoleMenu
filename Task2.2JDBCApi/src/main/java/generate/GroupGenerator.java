package generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Group;

public class GroupGenerator implements Generator<Group> {
    private static final char HYPHEN = '-';
    private static final char CHARAKTER_A = 'A';
    private List<Group> groups = new ArrayList<>();

    @Override
    public List<Group> generate(int number) {
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            String groupName = generateRandomGroupName(random);
            groups.add(new Group(i + 1, groupName));
        }
        return groups;
    }

    public String generateRandomGroupName(Random random) {
        char firstChar = (char) (CHARAKTER_A + random.nextInt(26));
        char secondChar = (char) (CHARAKTER_A + random.nextInt(26));
        int firstDigit = random.nextInt(10);
        int secondDigit = random.nextInt(10);
        return String.valueOf(firstChar) + secondChar + HYPHEN + firstDigit + secondDigit;
    }
}
