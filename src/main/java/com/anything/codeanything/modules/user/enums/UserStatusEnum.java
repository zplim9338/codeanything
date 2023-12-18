package com.anything.codeanything.modules.user.enums;

public enum UserStatusEnum implements EnumBase {
    INACTIVE("0","Inactive"),
    ACTIVE("1","Active"),
    PENDING("2","Pending"),
    LOCKED("3","Locked");

    UserStatusEnum(String pCode, String pDescription) {
        this.mCode = pCode;
        this.mDescription = pDescription;
    }

    private final String mCode;
    private final String mDescription;

    @Override
    public String getCode() {
        return mCode;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }
}
