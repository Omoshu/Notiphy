package com.frostphyr.notiphy;

import android.app.Activity;

import com.frostphyr.notiphy.io.JSONDecoder;
import com.frostphyr.notiphy.io.JSONEncoder;
import com.frostphyr.notiphy.reddit.RedditActivity;
import com.frostphyr.notiphy.reddit.RedditEntryDecoder;
import com.frostphyr.notiphy.reddit.RedditEntryEncoder;
import com.frostphyr.notiphy.reddit.RedditPostDecoder;
import com.frostphyr.notiphy.twitter.TweetDecoder;
import com.frostphyr.notiphy.twitter.TwitterActivity;
import com.frostphyr.notiphy.twitter.TwitterEntryDecoder;
import com.frostphyr.notiphy.twitter.TwitterEntryEncoder;

public enum EntryType {

    TWITTER(new TwitterEntryEncoder(false), new TwitterEntryDecoder(),
            new TwitterEntryEncoder(true), new TweetDecoder(),
            TwitterActivity.class, R.drawable.ic_twitter_logo, R.string.help_message_twitter),

    REDDIT(new RedditEntryEncoder(false), new RedditEntryDecoder(),
            new RedditEntryEncoder(true), new RedditPostDecoder(),
            RedditActivity.class, R.drawable.ic_reddit_logo, R.string.help_message_reddit);

    private final JSONEncoder<? extends Entry> entryEncoder;
    private final JSONDecoder<? extends Entry> entryDecoder;
    private final JSONEncoder<? extends Entry> entryTransportEncoder;
    private final JSONDecoder<? extends Message> messageDecoder;
    private final Class<? extends Activity>  activityClass;
    private final int iconResId;
    private final int helpMessageRedId;

    private EntryType(JSONEncoder<? extends Entry> entryEncoder, JSONDecoder<? extends Entry> entryDecoder,
                      JSONEncoder<? extends Entry> entryTransportEncoder, JSONDecoder<? extends Message> messageDecoder,
                      Class<? extends Activity> activityClass, int iconResId, int helpMessageRedId) {
        this.entryEncoder = entryEncoder;
        this.entryDecoder = entryDecoder;
        this.entryTransportEncoder = entryTransportEncoder;
        this.messageDecoder = messageDecoder;
        this.activityClass = activityClass;
        this.iconResId = iconResId;
        this.helpMessageRedId = helpMessageRedId;
    }

    public JSONEncoder<? extends Entry> getEntryEncoder() {
         return entryEncoder;
    }

    public JSONDecoder<? extends Entry> getEntryDecoder() {
        return entryDecoder;
    }

    public JSONEncoder<? extends Entry> getEntryTransportEncoder() {
        return entryTransportEncoder;
    }

    public JSONDecoder<? extends Message> getMessageDecoder() {
        return messageDecoder;
    }

    public Class<? extends Activity>  getActivityClass() {
        return activityClass;
    }

    public int getIconResourceId() {
        return iconResId;
    }

    public int getHelpMessageRedId() {
        return helpMessageRedId;
    }

    public String getName() {
        String s = toString();
        return s.charAt(0) + s.substring(1).toLowerCase();
    }

}
