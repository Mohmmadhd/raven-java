package net.kencochrane.raven.logback;


import ch.qos.logback.classic.spi.LoggingEvent;
import net.kencochrane.raven.spi.RavenMDC;
import org.slf4j.MDC;

public class LogbackMDC extends RavenMDC {

    private static final ThreadLocal<LoggingEvent> THREAD_LOGGING_EVENT
            = new ThreadLocal<LoggingEvent>();

    public void setThreadLoggingEvent(LoggingEvent event) {
        THREAD_LOGGING_EVENT.set(event);
    }

    public void removeThreadLoggingEvent() {
        THREAD_LOGGING_EVENT.remove();
    }

    @Override
    public Object get(String key) {
        if(THREAD_LOGGING_EVENT.get() != null)
        {
            return THREAD_LOGGING_EVENT.get().getMDCPropertyMap();
        }
        return MDC.get(key);
    }

    @Override
    public void put(String key, Object value) {
        MDC.put(key, (String) value);
    }

    @Override
    public void remove(String key) {
        MDC.remove(key);
    }

    @Override
    public void put(String key, String value) {
        MDC.put(key,value);
    }
}
