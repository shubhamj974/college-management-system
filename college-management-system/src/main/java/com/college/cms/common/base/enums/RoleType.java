package com.college.cms.common.base.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    SUPER_ADMIN("Super Admin"),
    ADMIN("Admin"),
    HOD("Head of Department"),
    PROFESSOR("Professor"),
    ASSISTANT_PROFESSOR("Assistant Professor"),
    LECTURER("Lecturer"),
    STUDENT("Student"),
    ACCOUNTANT("Accountant"),
    LIBRARIAN("Librarian"),
    LAB_ASSISTANT("Lab Assistant"),
    EXAM_CONTROLLER("Exam Controller"),
    ADMISSION_OFFICER("Admission Officer");

    private final String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}