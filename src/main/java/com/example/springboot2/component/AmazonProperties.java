package com.example.springboot2.component;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 加载配置属性
 *
 * 配置属性在调优方面十分有用，这里说的调优不仅涵盖了自动配置的组件，还包
 * 括注入自有应用程序Bean的细节。
 *
 * 但如果我们想为不同的部署环境配置不同的属性又该怎么办？让我们看看如何使用Spring的Profile来设置特定环境的配置。
 */
@Component
@ConfigurationProperties("amazon")
public class AmazonProperties {

    private String associateId;


    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }

    public String getAssociateId() {
        return associateId;
    }
}
