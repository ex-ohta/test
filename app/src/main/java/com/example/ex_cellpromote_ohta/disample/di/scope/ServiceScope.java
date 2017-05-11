package com.example.ex_cellpromote_ohta.disample.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * require Dagger2.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceScope {
}
