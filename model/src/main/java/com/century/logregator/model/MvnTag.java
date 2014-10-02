package com.century.logregator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Информация об артефакте maven
 */
@ToString
public class MvnTag {
    /**
     * первичный ключ
     */
    @Getter @Setter
    private Long id;

    /**
     * groupId артефакра
     */
    @Getter @Setter
    private String groupId;

    /**
     * имя артефакта
     */
    @Getter @Setter
    private String artifactId;

    /**
     * Версия артифакта
     */
    @Getter @Setter
    private String version;

    public MvnTag(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MvnTag tag = (MvnTag) o;

        if (!artifactId.equals(tag.artifactId)) return false;
        if (!groupId.equals(tag.groupId)) return false;
        if (!version.equals(tag.version)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId.hashCode();
        result = 31 * result + artifactId.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }
}
