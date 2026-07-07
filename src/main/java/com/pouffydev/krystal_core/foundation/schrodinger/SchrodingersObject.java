package com.pouffydev.krystal_core.foundation.schrodinger;

import org.jetbrains.annotations.Nullable;

/**
 * Represents an object that could potentially exist and allows a safe way of retrieving it.
 * @param <T> The object that might or might not exist
 */
public interface SchrodingersObject<T> {
    @Nullable T get();
}
