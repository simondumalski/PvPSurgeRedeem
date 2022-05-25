package me.simondumalski.pvpsurgeredeem.utils;

public enum Console {

    PREFIX("[Redeem] "),

    ERROR_SAVING_DATA_YML("&cError saving data.yml!"),
    SAVED_DATA_YML("&aSaved data.yml"),
    NO_DATA_TO_LOAD("&cNo data found in data.yml"),
    NO_DATA_YML_FOUND("&cFile data.yml was not found, creating one"),
    LOADED_DATA_YML("&aLoaded data.yml"),
    ERROR_LOADING_DATA_YML("&cError loading data.yml!"),

    NO_CREATORS_TO_LOAD("&cNo creators were found in the config.yml"),
    SUCCESSFULLY_LOADED_CREATORS("&aSuccessfully loaded %args0% creators");

    private String message;

    Console(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
