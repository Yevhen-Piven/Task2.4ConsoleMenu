package entity;

import lombok.Data;

@Data
public class Group {
    private int groupId;
    private String GroupName;

    public Group(int groupId, String groupName) {
        this.groupId = groupId;
        GroupName = groupName;
    }

    public Group() {
    }
}
