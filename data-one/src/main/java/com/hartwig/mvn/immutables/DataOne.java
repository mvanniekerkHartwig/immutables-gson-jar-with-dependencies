package com.hartwig.mvn.immutables;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters
@Value.Immutable
public interface DataOne {
    String dataOne();
}
