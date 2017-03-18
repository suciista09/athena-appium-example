package listeners;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by buddyarifin on 7/22/16.
 */
public class RetryListeners implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retryAnalyzer = annotation.getRetryAnalyzer();
        if(retryAnalyzer==null) {
            annotation.setRetryAnalyzer(Retry.class);
        }
    }
}
