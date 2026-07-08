package com.pouffydev.krystal_core.foundation.utility.nullified;

import javax.annotation.Nullable;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Nullable
public @interface NullableType {
}
