package chess.resources.immutables.configuration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author George Evangelou - email: gevangelou@hotmail.com
 * Created on: 2021-05-19
 */
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS) // Make it class retention for incremental compilation
@JsonSerialize
@JsonDeserialize
@Value.Style(
        of = "new",
        get = {"is*", "get*"}, // Detect 'get' and 'is' prefixes in accessor methods
        init = "set*", // Builder initialization methods will have 'set' prefix
        typeAbstract = {"Abstract*"}, // 'Abstract' prefix will be detected and trimmed
//        typeImmutable = "*", // No prefix or suffix for generated immutable type
//        builder = "new", // construct builder using 'new' instead of factory method
//        build = "create", // rename 'build' method on builder to 'create'
        visibility = Value.Style.ImplementationVisibility.PUBLIC,
        builderVisibility = Value.Style.BuilderVisibility.PUBLIC, // We will extend builder to make it public,
//        overshadowImplementation = true,
        depluralize = true,
        defaults = @Value.Immutable(copy = false)) // Disable copy methods by default,
public @interface ImmutablesConfiguration {
}
