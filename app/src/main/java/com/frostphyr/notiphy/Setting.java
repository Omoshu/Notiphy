package com.frostphyr.notiphy;

import android.content.Intent;

import androidx.core.content.ContextCompat;

import com.frostphyr.notiphy.io.NotiphyWebSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Setting<T> {

    public static final Setting WIFI_ONLY;
    public static final Setting SHOW_MEDIA;
    public static final Setting NSFW_CONTENT;

    private static Map<String, Setting> settingNames;
    private static List<Setting> settingIds;
    private static int nextId;

    private String name;
    private Class<T> type;
    private T defaultValue;
    private OnChangeHandler<T> onChangeHandler;
    private int id;

    static {
        settingNames = new HashMap<>();
        settingIds = new ArrayList<>();

        WIFI_ONLY = new Setting<>("WiFi Only", Boolean.class, false, new OnChangeHandler<Boolean>() {

            @Override
            public void onChange(NotiphyApplication application, Boolean value) {
                ContextCompat.startForegroundService(application, new Intent(application, NotiphyWebSocket.class)
                        .setAction(NotiphyWebSocket.ACTION_WIFI_ONLY)
                        .putExtra(NotiphyWebSocket.EXTRA_WIFI_ONLY, value));
            }

        });

        SHOW_MEDIA = new Setting<>("Show Media", Boolean.class, true, new OnChangeHandler<Boolean>() {

            @Override
            public void onChange(NotiphyApplication application, Boolean value) {
                application.getNotificationDispatcher().setShowMedia(value);
            }

        });

        NSFW_CONTENT = new Setting<>("NSFW Content", NsfwContent.class, NsfwContent.SHOW, new OnChangeHandler<NsfwContent>() {

            @Override
            public void onChange(NotiphyApplication application, NsfwContent value) {
                application.getNotificationDispatcher().setNsfwContent(value);
            }

        });
    }

    public Setting(String name, Class<T> type, T defaultValue, OnChangeHandler<T> onChangeHandler) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.onChangeHandler = onChangeHandler;

        id = nextId++;
        settingIds.add(this);
        settingNames.put(name, this);
    }

    public static Setting forId(int id) {
        return id >= 0 && id < settingIds.size() ? settingIds.get(id) : null;
    }

    public static int getCount() {
        return nextId;
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public OnChangeHandler<T> getOnChangeHandler() {
        return onChangeHandler;
    }

    public int getId() {
        return id;
    }

    public interface OnChangeHandler<T> {

        void onChange(NotiphyApplication application, T value);

    }

}
