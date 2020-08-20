package cn.itlou.genutils.swagger;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据Controller接口自动生成Swagger注解
 *
 * @author yuanyl
 * @date 2020/5/7 17:41
 **/
public class SwaggerGenMain {

    public static void main(String[] args) throws IOException {
//        List<File> javaFile = getPathJavaFile("D:\\ideaworkspace\\IdeawprkspaceJxx\\src\\main\\java\\com\\controller");
//        for (File file : javaFile) {
//            System.out.println(genSwaggerControllerApi(file));
//            System.out.println("************************************");
//            System.out.println("************************************");
//            System.out.println("************************************");
//        }
        String result = genSwaggerControllerApi(new File("D:\\ideaworkspace\\IdeawprkspaceJxx\\src\\main\\java\\com\\controller\\YwCollectionController.java"));
        System.out.println(result);
    }

    public static String genSwaggerControllerApi(File file) throws IOException{
        //初始化JavaParser
        CompilationUnit cu = JavaParser.parse(file);
        //设置导入的包，不会出现重复
        cu.addImport("io.swagger.annotations.Api");
        cu.addImport("io.swagger.annotations.ApiOperation");
        cu.addImport("org.springframework.web.bind.annotation.PostMapping");
        //使用该回调方法可以读取Java源代码中的各种属性
        VoidVisitorAdapter<Object> adapter = new VoidVisitorAdapter<Object>() {
            @Override
            //ClassOrInterfaceDeclaration 类或接口等级的信息
            public void visit(ClassOrInterfaceDeclaration clz, Object org){
                super.visit(clz, org);
                //注解有多个值时使用
                NodeList<Expression> annotationParamValueList = new NodeList<>();
//                annotationParamValueList.add(new StringLiteralExpr("Value1"));
//                ArrayInitializerExpr annotationParamValueArrayInitializerExpr = new ArrayInitializerExpr(annotationParamValueList);
                Name annotationName = new Name("Api");
                NodeList<MemberValuePair> annotationParamList = new NodeList<>();
                MemberValuePair tags = new MemberValuePair();
                tags.setName(new SimpleName("tags"));
                tags.setValue(new StringLiteralExpr(clz.getNameAsString()));
                MemberValuePair description = new MemberValuePair();
                description.setName(new SimpleName("description"));
                if (clz.getComment().isPresent()) {
                    String commentStr = clz.getComment().get().getContent();
                    try {
                        description.setValue(new StringLiteralExpr(readLineByStr(commentStr, "0") + "控制器"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    description.setValue(new StringLiteralExpr(clz.getNameAsString().replace("Controller", "")
                            + "控制器"));
                }
                clz.getComment();
                annotationParamList.add(tags);
                annotationParamList.add(description);
                AnnotationExpr annotationExpr = new NormalAnnotationExpr(annotationName, annotationParamList);
                clz.addAnnotation(annotationExpr);
            }
            @Override
            //MethodDeclaration 方法等级的信息
            public void visit(MethodDeclaration method, Object org){
                super.visit(method, org);
                Name annotationName = new Name("ApiOperation");
                NodeList<MemberValuePair> annotationParamList = new NodeList<>();
                MemberValuePair value = new MemberValuePair();
                value.setName(new SimpleName("value"));
                value.setValue(new StringLiteralExpr(method.getNameAsString()));
                MemberValuePair notes = new MemberValuePair();
                notes.setName(new SimpleName("notes"));
                if (method.getComment().isPresent()) {
                    String commentContent = method.getComment().get().getContent();
                    String commentStr = method.getComment().get().toString();
                    try {
                        if (commentStr.startsWith("/**")) {
                            notes.setValue(new StringLiteralExpr(readLineByStr(commentContent, "0")));
                        } else if (commentStr.startsWith("/*")) {
                            notes.setValue(new StringLiteralExpr(readLineByStr(commentContent, "0")));
                        } else {
                            notes.setValue(new StringLiteralExpr(commentContent));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    notes.setValue(new StringLiteralExpr(method.getNameAsString()));
                }
                annotationParamList.add(value);
                annotationParamList.add(notes);
                AnnotationExpr annotationExpr = new NormalAnnotationExpr(annotationName, annotationParamList);
                method.addAnnotation(annotationExpr);
            }
        };
        adapter.visit(cu, null);
//        System.out.println(cu.toString().replace("RequestMapping", "PostMapping"));
        return cu.toString().replace("RequestMapping", "PostMapping");
    }

    public static String readLineByStr(String src, String type) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(src.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        String line;
        String result = "";
        while ((line = br.readLine()) != null ) {
            if(!line.replace("*", "").trim().equals("")){
                if (type.equals("0")) {
                    //文档注释
                    result = line.replace("*", "").trim();
                    break;
                }
            }
        }
        br.close();
        return result;
    }

    public static List<File> getPathJavaFile(String filePath) {
        List<File> fileList = new ArrayList<>();
        File file = new File(filePath);
        //获取目录下的所有文件或文件夹
        File[] files = file.listFiles();
        if (files == null) {
            // 如果目录为空，直接退出
            return fileList;
        }
        //遍历，目录下的所有文件
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            } else if (f.isDirectory()) {
                getPathJavaFile(f.getAbsolutePath());
            }
        }
        return fileList;
    }


}
