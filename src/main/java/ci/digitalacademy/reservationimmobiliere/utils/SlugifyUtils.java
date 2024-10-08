package ci.digitalacademy.reservationimmobiliere.utils;

import com.github.slugify.Slugify;

import java.util.UUID;


public final class SlugifyUtils {


    private SlugifyUtils() {}
    public static String generate(String value) {

        String text = String.format("%s %s", value, UUID.randomUUID());
        final Slugify slug = Slugify.builder().build();

        return slug.slugify(text);
    }
}
