package com.mycompany.myapp.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Caffeine caffeine = jHipsterProperties.getCache().getCaffeine();

        CaffeineConfiguration<Object, Object> caffeineConfiguration = new CaffeineConfiguration<>();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(caffeine.getMaxEntries()));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(caffeine.getTimeToLiveSeconds())));
        caffeineConfiguration.setStatisticsEnabled(true);
        jcacheConfiguration = caffeineConfiguration;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.ApplicationUser.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ApplicationUser.class.getName() + ".ownedWorkEntries");
            createCache(cm, com.mycompany.myapp.domain.ApplicationUser.class.getName() + ".emails");
            createCache(cm, com.mycompany.myapp.domain.ApplicationUser.class.getName() + ".phoneNums");
            createCache(cm, com.mycompany.myapp.domain.ApplicationUser.class.getName() + ".addresses");
            createCache(cm, com.mycompany.myapp.domain.ApplicationUser.class.getName() + ".tags");
            createCache(cm, com.mycompany.myapp.domain.Email.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Address.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PhoneNum.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ClientCompany.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ClientCompany.class.getName() + ".hourlyRates");
            createCache(cm, com.mycompany.myapp.domain.ClientCompany.class.getName() + ".projectServices");
            createCache(cm, com.mycompany.myapp.domain.ClientCompany.class.getName() + ".contactInfos");
            createCache(cm, com.mycompany.myapp.domain.ClientCompany.class.getName() + ".ccEmails");
            createCache(cm, com.mycompany.myapp.domain.ClientCompany.class.getName() + ".ccPhoneNums");
            createCache(cm, com.mycompany.myapp.domain.ClientCompany.class.getName() + ".ccAddresses");
            createCache(cm, com.mycompany.myapp.domain.ClientCompany.class.getName() + ".workEntries");
            createCache(cm, com.mycompany.myapp.domain.ClientCompany.class.getName() + ".accountsPayables");
            createCache(cm, com.mycompany.myapp.domain.ClientCompany.class.getName() + ".approvers");
            createCache(cm, com.mycompany.myapp.domain.CCEmail.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CCPhoneNum.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CCAddress.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Approver.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Approver.class.getName() + ".approvals");
            createCache(cm, com.mycompany.myapp.domain.Approver.class.getName() + ".approvedWorkEntries");
            createCache(cm, com.mycompany.myapp.domain.Approver.class.getName() + ".applicationUsers");
            createCache(cm, com.mycompany.myapp.domain.Approver.class.getName() + ".clientCompanies");
            createCache(cm, com.mycompany.myapp.domain.WorkEntry.class.getName());
            createCache(cm, com.mycompany.myapp.domain.WorkEntry.class.getName() + ".approvals");
            createCache(cm, com.mycompany.myapp.domain.WorkEntry.class.getName() + ".approvers");
            createCache(cm, com.mycompany.myapp.domain.WorkEntry.class.getName() + ".tags");
            createCache(cm, com.mycompany.myapp.domain.Tag.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Tag.class.getName() + ".workEntries");
            createCache(cm, com.mycompany.myapp.domain.Approval.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ContactInfo.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ContactInfo.class.getName() + ".ciEmails");
            createCache(cm, com.mycompany.myapp.domain.ContactInfo.class.getName() + ".ciPhoneNums");
            createCache(cm, com.mycompany.myapp.domain.ContactInfo.class.getName() + ".ciAddresses");
            createCache(cm, com.mycompany.myapp.domain.CIEmail.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CIPhoneNum.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CIAddress.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Invoice.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Invoice.class.getName() + ".workEntries");
            createCache(cm, com.mycompany.myapp.domain.InvoiceBillingInfo.class.getName());
            createCache(cm, com.mycompany.myapp.domain.HourlyRate.class.getName());
            createCache(cm, com.mycompany.myapp.domain.HourlyRate.class.getName() + ".workEntries");
            createCache(cm, com.mycompany.myapp.domain.ProjectService.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ProjectService.class.getName() + ".workEntries");
            createCache(cm, com.mycompany.myapp.domain.AccountsPayable.class.getName());
            // jhipster-needle-caffeine-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
