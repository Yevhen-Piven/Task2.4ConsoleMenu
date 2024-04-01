package entity;

import java.util.Objects;

public class Group {
    private int groupId;
    private String GroupName;

    public Group(int groupId, String groupName) {
        this.groupId = groupId;
        GroupName = groupName;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public Group() {
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(GroupName, groupId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        return Objects.equals(GroupName, other.GroupName) && groupId == other.groupId;
    }

    @Override
    public String toString() {
        return "Group [groupId=" + groupId + ", GroupName=" + GroupName + "]";
    }
}
