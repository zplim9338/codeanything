package com.anything.codeanything.enums;

public enum UserStatusEnum implements EnumBase {
    INACTIVE(0,"Inactive"),
    ACTIVE(1,"Active"),
    PENDING(2,"Pending"),
    LOCKED(3,"Locked");

    UserStatusEnum(Integer pCode, String pDescription) {
        this.mCode = pCode;
        this.mDescription = pDescription;
    }

    private final Integer mCode;
    private final String mDescription;

    @Override
    public Integer getCode() {
        return mCode;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }
}
