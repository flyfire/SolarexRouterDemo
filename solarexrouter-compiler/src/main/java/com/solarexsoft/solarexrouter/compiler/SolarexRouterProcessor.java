package com.solarexsoft.solarexrouter.compiler;

import com.google.auto.service.AutoService;
import com.solarexsoft.solarexrouter.annotation.SolarexRouter;
import com.solarexsoft.solarexrouter.annotation.model.RouteMeta;
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
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
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

    private Map<String, String> rootRouteMap = new HashMap<>();
    private Map<String, List<RouteMeta>> groupRouteMap = new HashMap<>();

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
        if (!Utils.isEmpty(set)) {
            Set<? extends Element> routeElements = roundEnvironment.getElementsAnnotatedWith(SolarexRouter.class);
            if (!Utils.isEmpty(routeElements)) {
                try {
                    parseRouters(routeElements);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    private void parseRouters(Set<? extends Element> routeElements) {
        TypeElement activity = elementUtils.getTypeElement(Constants.ACTIVITY);
        TypeMirror activityType = activity.asType();
        TypeElement provider = elementUtils.getTypeElement(Constants.IPROVIDER);
        TypeMirror providerType = provider.asType();

        for (Element routeElement : routeElements) {
            RouteMeta routeMeta;
            TypeMirror typeMirror = routeElement.asType();
            log.i("route class: " + typeMirror.toString());
            SolarexRouter router = routeElement.getAnnotation(SolarexRouter.class);
            if (typeUtils.isSubtype(typeMirror, activityType)) {
                routeMeta = new RouteMeta(RouteMeta.JumpType.ACTIVITY, routeElement, router);
            } else if (typeUtils.isSubtype(typeMirror, providerType)) {
                routeMeta = new RouteMeta(RouteMeta.JumpType.PROVIDER, routeElement, router);
            } else {
                throw new RuntimeException("Activity/IProvider support only, element : " + routeElement);
            }
            categories(routeMeta);
        }

        TypeElement iRouteGroup = elementUtils.getTypeElement(Constants.IROUTE_GROUP);
        TypeElement iRouteRoot = elementUtils.getTypeElement(Constants.IROUTE_ROOT);

        generateGroup(iRouteGroup);

        generateRoot(iRouteRoot, iRouteGroup);
    }

    private void generateRoot(TypeElement iRouteRoot, TypeElement iRouteGroup) {
        
    }

    private void generateGroup(TypeElement iRouteGroup) {

    }

    private void categories(RouteMeta routeMeta) {
        if (verifyRoute(routeMeta)) {
            log.i("group info,group: " + routeMeta.getGroup() + ",path: " + routeMeta.getPath());
            List<RouteMeta> routeMetas = groupRouteMap.get(routeMeta.getGroup());
            if (Utils.isEmpty(routeMetas)) {
                routeMetas = new ArrayList<>();
                groupRouteMap.put(routeMeta.getGroup(), routeMetas);
            }
            routeMetas.add(routeMeta);
        } else {
            log.i("group info error, path: " + routeMeta.getPath());
        }
    }

    private boolean verifyRoute(RouteMeta routeMeta) {
        String path = routeMeta.getPath();
        String group = routeMeta.getGroup();
        if (Utils.isEmpty(path) || !path.startsWith("/")) {
            return false;
        }
        if (Utils.isEmpty(group)) {
            String defaultGroup = path.substring(1, path.indexOf("/", 1));
            if (Utils.isEmpty(defaultGroup)) {
                return false;
            }
            routeMeta.setGroup(defaultGroup);
        }
        return true;
    }
}
