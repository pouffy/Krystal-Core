package com.pouffydev.krystal_core.foundation.utility.nullified;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Nonnull
public @interface NonnullType {
}
