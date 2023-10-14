package com.example.chat_relex.template;

public interface TemplateEngine {

    String compile(String template, Object model);

    default String compile(Template template, Object model) {
        return compile(template.getName(), model);
    }
}