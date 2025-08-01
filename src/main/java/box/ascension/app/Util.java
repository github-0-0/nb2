package box.ascension.app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;;

public class Util {

    public static class Pair<A, B> {
        
        public A a;
        public B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @JacksonAnnotationsInside
    @JsonAutoDetect(
        fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE
    )
    public @interface JsonWhitelist { }

}
