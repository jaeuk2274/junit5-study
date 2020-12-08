package me.jaeuk.junit5study;

import lombok.Builder;
import lombok.Getter;


public class Study {

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    private String name;

    public StudyStatus getStatus() {
        return status;
    }
    public int getLimit() {
        return limit;
    }
    public String getName() {
        return name;
    }

    public Study(int limit) {
        if(limit < 0){
            throw new IllegalArgumentException("limit는 0보다 커야한다.");
        }
        this.limit = limit;
    }

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
