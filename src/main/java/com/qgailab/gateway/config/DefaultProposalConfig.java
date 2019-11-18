package com.qgailab.gateway.config;

import com.qgailab.gateway.model.Proposal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author linxu
 * @date 2019/11/17
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
@Configuration
@PropertySource("classpath:proposal.properties")
@Getter
@Setter
public class DefaultProposalConfig {
    @Value("${default.timeout}")
    private long timeout = 2000;


}
