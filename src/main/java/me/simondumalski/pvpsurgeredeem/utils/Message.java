package me.simondumalski.pvpsurgeredeem.utils;

public enum Message {

    PREFIX("messages.prefix"),

    INSUFFICIENT_PERMISSIONS("messages.insufficient-permissions"),
    INVALID_USAGE("messages.invalid-usage"),
    ALREADY_REDEEMED("messages.already-redeemed"),
    INVALID_CREATOR("messages.invalid-creator"),

    REDEEMED("messages.redeemed"),

    LIST("messages.list"),
    LIST_ADMIN("messages.list-admin"),

    RELOAD("messages.reload");

    private String configValue;

    Message(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigValue() {
        return configValue;
    }
}
