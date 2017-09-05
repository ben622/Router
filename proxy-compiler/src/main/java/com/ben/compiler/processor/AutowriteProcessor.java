package com.ben.compiler.processor;

import com.ben.annotations.annotation.Autowrite;
import com.ben.compiler.utils.Consts;
import com.ben.compiler.utils.TypeKind;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
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
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.1
 * @create 2017-08-03
 * @desc 参数自动注入
 *
 * @update by @zhangchuan622@gmail.com 支持按注解指定的key进行映射
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes(Consts.ANNOTATION_TYPE)
public class AutowriteProcessor extends AbstractProcessor {
    private Filer mFiler;
    private Messager mMessager;
    private Elements mElements;
    private Map<TypeElement, List<Element>> mElementTypeListMap = new HashMap<TypeElement, List<Element>>();
    /**
     * class meta info
     */
    private Types mTypes;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mElements = processingEnv.getElementUtils();
        mTypes = processingEnv.getTypeUtils();

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            if (!validateElement(roundEnv.getElementsAnnotatedWith(Autowrite.class))) {
                return true;
            }
            generate();
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    /**
     * 生成java文件
     */
    @SuppressWarnings("unchecked")
    private void generate() throws IOException {
        //获得element类型
        TypeElement classTypeElement = mElements.getTypeElement(Consts.PROXY_ROUTER_CLASS_NAME);
        TypeMirror activityTypeMirror = mElements.getTypeElement(Consts.ACTIVITY).asType();
        TypeMirror fragmentTypeMirror = mElements.getTypeElement(Consts.FRAGMENT).asType();
        TypeMirror fragemntV4TypeMirror = mElements.getTypeElement(Consts.FRAGMENT_V4).asType();

        for (Map.Entry<TypeElement, List<Element>> entry : mElementTypeListMap.entrySet()) {
            TypeElement typeElement = entry.getKey();
            List<Element> elementList = entry.getValue();

            //生成@IRouterProxy接口中的inject方法
            MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder(Consts.PROXY_ROUTER_METHOD_NAME)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addParameter(TypeName.OBJECT, "target", Modifier.FINAL);

            //将Object(target)强转换注解所在类类型
            //TIP:Activity distinct = (Activity)target;
            injectMethodBuilder.addStatement("$T distinct = ($T)target;", ClassName.get(typeElement), ClassName.get(typeElement));

            //遍历被注解的字段
            for (Element element : elementList) {
                Autowrite autowrite = element.getAnnotation(Autowrite.class);
                //解析字段名称
                String fieldName = element.getSimpleName().toString();
                //生成inject方法中的逻辑代码；根据target类型生成 如果是fragment类型则按照fragment类型注入参数
                if (mTypes.isSubtype(typeElement.asType(), activityTypeMirror)) {
                    //activity类型
                    String act = "distinct." + fieldName + " = distinct.getIntent().";
                    injectMethodBuilder.addStatement(act + parseStatement(getFieldType(element), true), (autowrite.name() == null || "".equals(autowrite.name())) ? fieldName : autowrite.name());
                } else if (mTypes.isSubtype(typeElement.asType(), fragmentTypeMirror)
                        || mTypes.isSubtype(typeElement.asType(), fragemntV4TypeMirror)) {
                    //fragemnt类型
                    String act = "distinct." + fieldName + " = distinct.getArguments().";
                    injectMethodBuilder.addStatement(act + parseStatement(getFieldType(element), false), (autowrite.name() == null || "".equals(autowrite.name())) ? fieldName : autowrite.name());
                }
            }

            TypeSpec calssTypeBuilder = TypeSpec.classBuilder(typeElement.getSimpleName() + Consts.PROXY_ROUTER_SUFFIX)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(ClassName.get(classTypeElement))
                    .addJavadoc(Consts.JAVADOC)
                    .addMethod(injectMethodBuilder.build())
                    .build();

            //生成PackageName
            String packageName = mElements.getPackageOf(typeElement).getQualifiedName().toString();
            JavaFile.builder(packageName, calssTypeBuilder).build().writeTo(mFiler);
        }
    }

    private String parseStatement(int type, boolean isActivity) {
        String statment = "";
        if (type == TypeKind.BOOLEAN.ordinal()) {
            statment += (isActivity ? ("getBooleanExtra($S, false)") : ("getBoolean($S)"));
        } else if (type == TypeKind.BYTE.ordinal()) {
            statment += (isActivity ? ("getByteExtra($S, (byte) 0)") : ("getByte($S)"));
        } else if (type == TypeKind.SHORT.ordinal()) {
            statment += (isActivity ? ("getShortExtra($S, (short) 0)") : ("getShort($S)"));
        } else if (type == TypeKind.INT.ordinal()) {
            statment += (isActivity ? ("getIntExtra($S, 0)") : ("getInt($S)"));
        } else if (type == TypeKind.LONG.ordinal()) {
            statment += (isActivity ? ("getLongExtra($S, 0)") : ("getLong($S)"));
        } else if (type == TypeKind.FLOAT.ordinal()) {
            statment += (isActivity ? ("getFloatExtra($S, 0)") : ("getFloat($S)"));
        } else if (type == TypeKind.DOUBLE.ordinal()) {
            statment += (isActivity ? ("getDoubleExtra($S, 0)") : ("getDouble($S)"));
        } else if (type == TypeKind.STRING.ordinal()) {
            statment += (isActivity ? ("getStringExtra($S)") : ("getString($S)"));
        } else if (type == TypeKind.PARCELABLE.ordinal()) {
            statment += (isActivity ? ("getParcelableExtra($S)") : ("getParcelable($S)"));
        } else if (type == TypeKind.ARRAYLIST.ordinal()) {
            statment += (isActivity ? ("getParcelableArrayListExtra($S)") : ("getParcelableArrayList($S)"));
        }
        return statment;
    }

    /**
     * 解析字段类型
     *
     * @param element
     * @return
     */
    private int getFieldType(Element element) {
        TypeMirror typeMirror = element.asType();
        switch (typeMirror.toString()) {
            case Consts.BYTE:
                return TypeKind.BYTE.ordinal();
            case Consts.SHORT:
                return TypeKind.SHORT.ordinal();
            case Consts.INTEGER:
                return TypeKind.INT.ordinal();
            case Consts.LONG:
                return TypeKind.LONG.ordinal();
            case Consts.FLOAT:
                return TypeKind.FLOAT.ordinal();
            case Consts.DOUBEL:
                return TypeKind.DOUBLE.ordinal();
            case Consts.BOOLEAN:
                return TypeKind.BOOLEAN.ordinal();
            case Consts.STRING:
                return TypeKind.STRING.ordinal();
            case Consts.ARRAYLIST:
                return TypeKind.ARRAYLIST.ordinal();
            default:
                if (mTypes.isSubtype(typeMirror, mElements.getTypeElement(Consts.PARCELABLE).asType())) {  // PARCELABLE
                    return TypeKind.PARCELABLE.ordinal();
                }
        }
        return TypeKind.STRING.ordinal();
    }


    /**
     * 验证注解是否符合注解规则
     *
     * @param elements
     * @return
     */
    private boolean validateElement(Set<? extends Element> elements) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            String qualifiedName = typeElement.getQualifiedName().toString();
            if (typeElement.getModifiers().contains(Modifier.PRIVATE)) {
                error(element, "@" + qualifiedName + "must not be private");
                return false;
            }
            if (typeElement.getKind() != ElementKind.CLASS) {
                error(element, "@" + qualifiedName + "父元素必须为classes类型");
                return false;
            }

            if (mElementTypeListMap.containsKey(typeElement)) {
                mElementTypeListMap.get(typeElement).add(element);
            } else {
                //map中不存在当前element  追加
                List<Element> elementList = new ArrayList<>();
                elementList.add(element);
                mElementTypeListMap.put(typeElement, elementList);
            }

        }
        return true;
    }


    private void error(Element element, String message) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }

}
