package com.solarexsoft.solarexrouter.compiler;

import com.google.auto.service.AutoService;
import com.solarexsoft.solarexrouter.compiler.utils.Constants;

import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

@AutoService(Processor.class)
@SupportedOptions(Constants.ARGUMENTS_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({Constants.ANN_TYPE_ROUTE})
public class SolarexRouterProcessor {
}
