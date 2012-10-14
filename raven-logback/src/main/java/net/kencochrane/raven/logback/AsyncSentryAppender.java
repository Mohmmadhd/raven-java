package net.kencochrane.raven.logback;


import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AsyncAppenderBase;
import net.kencochrane.raven.Client;
import net.kencochrane.raven.SentryDsn;

public class AsyncSentryAppender<E> extends AsyncAppenderBase<E> {

    private String sentryDsn;
    private String jsonProcessors;
    private SentryAppender appender;
    private boolean messageCompressionEnabled = true;

    public AsyncSentryAppender() {
        SentryAppender.initMDC();
    }

    public String getSentryDsn() {
        return sentryDsn;
    }

    public void setSentryDsn(String sentryDsn) {
        this.sentryDsn = sentryDsn;
    }

    public boolean isMessageCompressionEnabled() {
        return messageCompressionEnabled;
    }

    public void setMessageCompressionEnabled(boolean messageCompressionEnabled) {
        this.messageCompressionEnabled = messageCompressionEnabled;
    }


    public void setJsonProcessors(String jsonProcessors) {
        this.jsonProcessors = jsonProcessors;
    }

    @Override
    protected void append(E eventObject) {
        appender.notifyProcessors();
        super.append(eventObject);
    }

    @Override
    public void start() {
        SentryAppender appender = new SentryAppender();
        appender.setAsync(true);
        appender.setMessageCompressionEnabled(messageCompressionEnabled);
        appender.setSentryDsn(sentryDsn);
        appender.setJsonProcessors(jsonProcessors);
        appender.setName(this.getName());
        appender.start();
        this.appender = appender;
        addAppender(appender);
        super.start();
    }

}
