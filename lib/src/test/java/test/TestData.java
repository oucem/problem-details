package test;

import com.github.t1.problemdetails.jaxrs.lib.ProblemDetailBuilder;
import org.eclipse.microprofile.problemdetails.Detail;
import org.eclipse.microprofile.problemdetails.Extension;
import org.eclipse.microprofile.problemdetails.Instance;
import org.eclipse.microprofile.problemdetails.Status;
import org.eclipse.microprofile.problemdetails.Title;
import org.eclipse.microprofile.problemdetails.Type;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.eclipse.microprofile.problemdetails.ResponseStatus.BAD_REQUEST;

class TestData {
    static final ProblemDetailBuilder PROBLEM_DETAILS = new ProblemDetailBuilder(new SomeException()) {
        @Override protected boolean useExceptionMessageAsDetail() { return true; }
        @Override protected String findMediaTypeSubtype() { return null; }
    };


    @Type("urn:some-type")
    @Title("some-title")
    @Status(BAD_REQUEST)
    public static class SomeException extends RuntimeException {
        @Detail String detail = "some-detail";
        @Instance URI instance = URI.create("urn:some-instance");
        @Extension String k1 = "v1";
        @Extension List<URI> k2 = v2();
        @Extension Map<String, Object> k3 = v3();
        @Extension Map<String, Object> k4 = v4();
        @Extension String k5 = null;
    }

    static List<URI> v2() {
        return asList(URI.create("urn:1"), null, URI.create("urn:2"));
    }

    static Map<String, Object> v3() {
        Map<String, Object> v3 = new LinkedHashMap<>();
        v3.put("k3.1", "v3.1");
        v3.put("k3.2", "v3.2");
        return v3;
    }

    static Map<String, Object> v4() {
        Map<String, Object> v4 = new LinkedHashMap<>();
        v4.put("k4.1", asList("v4.1.1", "v4.1.2", "v4.1.3"));
        v4.put("k4.2", singletonList("v4.2.1"));
        v4.put("k4.3", asList("v4.3.1", "v4.3.2"));
        v4.put("k4.4", emptyList());
        return v4;
    }
}
