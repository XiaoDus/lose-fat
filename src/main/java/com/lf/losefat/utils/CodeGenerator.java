package com.lf.losefat.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        generator();
    }

    public static void generator(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/lose_fat?serverTimeZone=GMT%2b8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("小肚") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/xiaodu/IdeaProjects/LoseFat/src/main/java"); // 指定输出目录(绝对路径)
                })
                .packageConfig(builder -> {
                    builder.parent("com.lf.losefat") // 设置父包名（复制路径中复制引用）
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/xiaodu/IdeaProjects/LoseFat/src/main/resources/mapper/")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok(); //使用Lombok
//                    builder.mapperBuilder().enableMapperAnnotation().build(); //给mapper添加@Mapper注解
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude("food_knowledge") // 设置需要生成的表名
                            .addTablePrefix("smsApp_", "c_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}