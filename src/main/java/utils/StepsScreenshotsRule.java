package utils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.ITestResult;
import org.testng.Reporter;
import pages.BasePage;
import pages.InstanceDriver;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by buddyarifin on 8/23/16.
 * Rewrite ConsoleAppender log4j and added screenshots steps
 */
public class StepsScreenshotsRule extends WriterAppender{
    Object obj;
    AndroidDriver driver;
    BasePage base;

    public static final String SYSTEM_OUT = "System.out";
    public static final String SYSTEM_ERR = "System.err";
    protected String target = SYSTEM_OUT;

    /**
     *  Determines if the appender honors reassignments of System.out
     *  or System.err made after configuration.
     */
    private boolean follow = false;

    /**
     * Constructs an unconfigured appender.
     */
    public StepsScreenshotsRule() {
    }

    @Override
    protected void subAppend(LoggingEvent event) {
        this.qw.write(this.layout.format(event));
//        catchStep();
        if(layout.ignoresThrowable()) {
            String[] s = event.getThrowableStrRep();
            if (s != null) {
                int len = s.length;
                for(int i = 0; i < len; i++) {
                    this.qw.write(s[i]);
                    this.qw.write(Layout.LINE_SEP);
                }
            }
        }

        if(shouldFlush(event)) {
            this.qw.flush();
        }
    }

    protected void catchStep() {
        try {
            ITestResult tr = Reporter.getCurrentTestResult();
            obj = tr.getInstance();
            driver = ((InstanceDriver)obj).driver;
            base = new BasePage(driver);
            base.getAttachment("hoho-"+driver.getSessionId().toString()+".png");
        } catch (Exception e){
            System.out.println("Yahh liwat dah");
        }
    }

    /**
     * Creates a configured appender.
     *
     * @param layout layout, may not be null.
     */
    public StepsScreenshotsRule(Layout layout) {
        this(layout, SYSTEM_OUT);
    }

    /**
     *   Creates a configured appender.
     * @param layout layout, may not be null.
     * @param target target, either "System.err" or "System.out".
     */
    public StepsScreenshotsRule(Layout layout, String target) {
        setLayout(layout);
        setTarget(target);
        activateOptions();
    }

    /**
     *  Sets the value of the <b>Target</b> option. Recognized values
     *  are "System.out" and "System.err". Any other value will be
     *  ignored.
     * */
    public
    void setTarget(String value) {
        String v = value.trim();

        if (SYSTEM_OUT.equalsIgnoreCase(v)) {
            target = SYSTEM_OUT;
        } else if (SYSTEM_ERR.equalsIgnoreCase(v)) {
            target = SYSTEM_ERR;
        } else {
            targetWarn(value);
        }
    }

    /**
     * Returns the current value of the <b>Target</b> property. The
     * default value of the option is "System.out".
     *
     * See also {@link #setTarget}.
     * */
    public
    String getTarget() {
        return target;
    }

    /**
     *  Sets whether the appender honors reassignments of System.out
     *  or System.err made after configuration.
     *  @param newValue if true, appender will use value of System.out or
     *  System.err in force at the time when logging events are appended.
     *  @since 1.2.13
     */
    public final void setFollow(final boolean newValue) {
        follow = newValue;
    }

    /**
     *  Gets whether the appender honors reassignments of System.out
     *  or System.err made after configuration.
     *  @return true if appender will use value of System.out or
     *  System.err in force at the time when logging events are appended.
     *  @since 1.2.13
     */
    public final boolean getFollow() {
        return follow;
    }

    void targetWarn(String val) {
        LogLog.warn("["+val+"] should be System.out or System.err.");
        LogLog.warn("Using previously set target, System.out by default.");
    }

    /**
     *   Prepares the appender for use.
     */
    public void activateOptions() {
        if (follow) {
            if (target.equals(SYSTEM_ERR)) {
                setWriter(createWriter(new StepsScreenshotsRule.SystemErrStream()));
            } else {
                setWriter(createWriter(new StepsScreenshotsRule.SystemOutStream()));
            }
        } else {
            if (target.equals(SYSTEM_ERR)) {
                setWriter(createWriter(System.err));
            } else {
                setWriter(createWriter(System.out));
            }
        }

        super.activateOptions();
    }

    /**
     *  {@inheritDoc}
     */
    protected
    final
    void closeWriter() {
        if (follow) {
            super.closeWriter();
        }
    }

    /**
     * An implementation of OutputStream that redirects to the
     * current System.err.
     *
     */
    private static class SystemErrStream extends OutputStream {
        public SystemErrStream() {
        }

        public void close() {
        }

        public void flush() {
            System.err.flush();
        }

        public void write(final byte[] b) throws IOException {
            System.err.write(b);
        }

        public void write(final byte[] b, final int off, final int len)
                throws IOException {
            System.err.write(b, off, len);
        }

        public void write(final int b) throws IOException {
            System.err.write(b);
        }
    }

    /**
     * An implementation of OutputStream that redirects to the
     * current System.out.
     *
     */
    private static class SystemOutStream extends OutputStream {
        public SystemOutStream() {
        }

        public void close() {
        }

        public void flush() {
            System.out.flush();
        }

        public void write(final byte[] b) throws IOException {
            System.out.write(b);
        }

        public void write(final byte[] b, final int off, final int len)
                throws IOException {
            System.out.write(b, off, len);
        }

        public void write(final int b) throws IOException {
            System.out.write(b);
        }
    }
}
