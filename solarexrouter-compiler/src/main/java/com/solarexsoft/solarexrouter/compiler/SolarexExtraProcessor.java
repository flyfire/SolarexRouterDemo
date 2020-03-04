package com.solarexsoft.solarexrouter.compiler;

import com.google.auto.service.AutoService;
import com.solarexsoft.solarexrouter.annotation.SolarexExtra;
import com.solarexsoft.solarexrouter.compiler.utils.Constants;
import com.solarexsoft.solarexrouter.compiler.utils.Log;
import com.solarexsoft.solarexrouter.compiler.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 17:27/2020/3/4
 *    Desc:
 * </pre>
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({Constants.ANN_TYPE_EXTRA})
public class SolarexExtraProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Types typeUtils;
    private Filer filerUtils;
    private Log log;

    private Map<TypeElement, List<Element>> extraEnclosingElementMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        log = Log.newLog(processingEnvironment.getMessager());
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        filerUtils = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!Utils.isEmpty(set)) {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(SolarexExtra.class);
            if (!Utils.isEmpty(elements)) {
                try {
                    categories(elements);
                    generateAutoInject();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    private void generateAutoInject() {

    }

    private void categories(Set<? extends Element> elements) {
        for (Element element : elements) {
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            if (extraEnclosingElementMap.containsKey(enclosingElement)) {
                extraEnclosingElementMap.get(enclosingElement).add(element);
            } else {
                List<Element> extraElements = new ArrayList<>();
                extraElements.add(element);
                extraEnclosingElementMap.put(enclosingElement, extraElements);
            }
        }
    }
}
