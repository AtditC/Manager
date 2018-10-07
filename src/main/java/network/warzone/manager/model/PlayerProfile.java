package network.warzone.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import network.warzone.manager.Manager;
import org.bukkit.Sound;

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
    private Sound joinSound;
    private List<Sound> joinSounds;

    public void setName(String name) {
        this.name = name;
        save();
    }

    public void setDisplayTag(String displayTag) {
        this.displayTag = displayTag;
        save();
    }

    public void setSound(Sound sound) {
        this.joinSound = sound;
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

    public boolean addSound(Sound sound) {
        if (this.joinSound == sound) return false;
        else if (this.joinSounds == null) {
            this.joinSounds = new ArrayList<>();
            this.joinSounds.add(sound);
        } else if (this.joinSounds.isEmpty() || !this.joinSounds.contains(sound)) {
            this.joinSounds.add(sound);
        } else {
            return false;
        }
        save();
        return true;
    }

    public boolean removeSound(Sound sound) {
        if (this.joinSound == sound) this.joinSound = null;
        if (this.joinSounds == null || this.joinSounds.isEmpty()) {
            return false;
        } else if (this.joinSounds.contains(sound)) {
            joinSounds.remove(sound);
        } else {
            return false;
        }
        save();
        return true;
    }

    private void save() {
        Manager.get().getPlayerManager().saveProfileToStorage(this);
    }

    public boolean shouldSave() {
        return (this.displayTag != null && !this.displayTag.equals("")) || (this.getTags() != null && !this.getTags().isEmpty());
    }

}
