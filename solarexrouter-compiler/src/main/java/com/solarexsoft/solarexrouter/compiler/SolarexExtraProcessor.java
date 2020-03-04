package com.solarexsoft.solarexrouter.compiler;

import com.google.auto.service.AutoService;
import com.solarexsoft.solarexrouter.annotation.SolarexExtra;
import com.solarexsoft.solarexrouter.compiler.utils.Constants;
import com.solarexsoft.solarexrouter.compiler.utils.LoadExtraBuilder;
import com.solarexsoft.solarexrouter.compiler.utils.Log;
import com.solarexsoft.solarexrouter.compiler.utils.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
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

    private void generateAutoInject() throws IOException {
        TypeMirror activity = elementUtils.getTypeElement(Constants.ACTIVITY).asType();
        TypeElement iExtra = elementUtils.getTypeElement(Constants.IEXTRA);

        // Object target;
        ParameterSpec targetParamSpec = ParameterSpec.builder(TypeName.OBJECT, "target").build();
        if (!Utils.isEmpty(extraEnclosingElementMap)) {
            for (Map.Entry<TypeElement, List<Element>> entry : extraEnclosingElementMap.entrySet()) {
                TypeElement enclosingClass = entry.getKey();
                if (!typeUtils.isSubtype(enclosingClass.asType(), activity)) {
                    throw new RuntimeException("Only support activity field : " + enclosingClass);
                }
                LoadExtraBuilder loadExtraBuilder = new LoadExtraBuilder(targetParamSpec);
                loadExtraBuilder.setElementUtils(elementUtils);
                loadExtraBuilder.setTypeUtils(typeUtils);
                ClassName className = ClassName.get(enclosingClass);
                loadExtraBuilder.injectTarget(className);

                for (int i = 0; i < entry.getValue().size(); i++) {
                    Element element = entry.getValue().get(i);
                    loadExtraBuilder.buildStatement(element);
                }

                String extraClassName = enclosingClass.getSimpleName() + Constants.ROUTE_EXTRA_NAME;
                JavaFile.builder(className.packageName(),
                        TypeSpec.classBuilder(extraClassName)
                                .addSuperinterface(ClassName.get(iExtra))
                                .addModifiers(Modifier.PUBLIC)
                                .addMethod(loadExtraBuilder.build()).build())
                        .build().writeTo(filerUtils);
                log.i("Generated Extra: " + className.packageName() + "." + extraClassName);
            }
        }
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
