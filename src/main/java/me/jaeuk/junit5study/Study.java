package me.jaeuk.junit5study;

import lombok.Builder;
import lombok.Getter;


public class Study {

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;

    public StudyStatus getStatus() {
        return status;
    }

    public int getLimit() {
        return limit;
    }

    public Study(int limit) {
        if(limit < 0){
            throw new IllegalArgumentException("limit는 0보다 커야한다.");
        }
        this.limit = limit;
    }
}
