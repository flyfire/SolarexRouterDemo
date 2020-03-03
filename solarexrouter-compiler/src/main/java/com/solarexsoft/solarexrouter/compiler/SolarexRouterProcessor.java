package com.solarexsoft.solarexrouter.compiler;

import com.google.auto.service.AutoService;
import com.solarexsoft.solarexrouter.compiler.utils.Constants;
import com.solarexsoft.solarexrouter.compiler.utils.Log;
import com.solarexsoft.solarexrouter.compiler.utils.Utils;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@AutoService(Processor.class)
@SupportedOptions(Constants.ARGUMENTS_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({Constants.ANN_TYPE_ROUTE})
public class SolarexRouterProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Types typeUtils;
    private Filer filerUtils;
    private String moduleName;
    private Log log;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        log = Log.newLog(processingEnvironment.getMessager());
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        filerUtils = processingEnvironment.getFiler();

        Map<String, String> buildArgs = processingEnvironment.getOptions();
        if (!Utils.isEmpty(buildArgs)) {
            moduleName = buildArgs.get(Constants.ARGUMENTS_NAME);
        }
        log.i("SolarexRouterProcessor processing module: " + moduleName);
        if (Utils.isEmpty(moduleName)) {
            throw new RuntimeException("Processor arguments module name not found");
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
