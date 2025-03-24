package org.krmdemo.sandbox;

import org.apache.maven.plugin.logging.Log;

/**
 * The log-level of "Hello!" or "GoodBye!" messages that also allowed
 * to print the greetings to standard-output and standard-error streams
 * of maven-process.
 * <hr/>
 * The default value is {@link #INFO}, where the greetings message
 * is displayed in usual un-suppressed and un-highlighted way.
 */
public enum GreetingsLogLevel {

    SYSTEM_OUT {
        @Override
        void printMsg(Log log, String messageToPrint) {
            System.out.println(messageToPrint);
        }
    },

    SYSTEM_ERROR {
        @Override
        void printMsg(Log log, String messageToPrint) {
            System.err.println(messageToPrint);
        }
    },

    ERROR {
        @Override
        void printMsg(Log log, String messageToPrint) {
            log.error(messageToPrint);
        }
    },

    WARN {
        @Override
        void printMsg(Log log, String messageToPrint) {
            log.warn(messageToPrint);
        }
    },

    INFO {
        @Override
        void printMsg(Log log, String messageToPrint) {
            log.info(messageToPrint);
        }
    },

    DEBUG {
        @Override
        void printMsg(Log log, String messageToPrint) {
            log.debug(messageToPrint);
        }
    };

    /**
     * Print the message either to standard-out or standard-error streams
     * of maven-process or log it using {@link Log internal maven-logger}
     *
     * @param log internal maven logger
     * @param messageToPrint a message to print (to log)
     */
    abstract void printMsg(Log log, String messageToPrint);
}
