package network.warzone.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import network.warzone.manager.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jorge on 2/23/2018.
 */

@AllArgsConstructor @Getter
public class PlayerProfile {

    private UUID uuid;
    private String name;
    private String displayTag;
    private List<String> tags;

    public void setName(String name) {
        this.name = name;
        save();
    }

    public void setDisplayTag(String displayTag) {
        this.displayTag = displayTag;
        save();
    }

    public boolean addTag(String tag) {
        boolean r;
        if (this.tags == null) this.tags = new ArrayList<>();
        if (!this.tags.contains(tag)) {
            this.tags.add(tag);
            r = true;
        } else {
            r = false;
        }
        save();
        return r;
    }

    public boolean removeTag(String tag) {
        boolean r;
        if (tag.equals(this.displayTag)) this.displayTag = "";
        if (this.tags == null) {
            this.tags = new ArrayList<>();
            r = false;
        } else if (this.tags.contains(tag)) {
            this.tags.remove(tag);
            r = true;
        } else {
            r = false;
        }
        save();
        return r;
    }

    private void save() {
        Manager.getInstance().getPlayerManager().saveProfileToStorage(this);
    }

    public boolean shouldSave() {
        return (!this.displayTag.equals("") && this.displayTag != null) || (!this.getTags().isEmpty() && this.getTags() != null);
    }

}
